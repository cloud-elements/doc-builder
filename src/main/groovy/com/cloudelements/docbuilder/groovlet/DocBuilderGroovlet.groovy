package com.cloudelements.docbuilder.groovlet

import com.cloudelements.docbuilder.domain.*
import groovy.json.JsonSlurper
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

import static groovyx.net.http.ContentType.JSON

/**
 * Groovlet to handle any HTTP requests to an elements endpoint and analyze
 * the request and response payloads to generate the Swagger documentation
 *
 * [Good documentation on Groovlets here: http://groovy.codehaus.org/Groovlets]
 *
 * @version $Revision: 1 $
 * @author: jjwyse
 * @since: 11/24/13
 */
List<SwaggerMethod> swaggerMethods
List<SwaggerModel> swaggerModels

def private createSwaggerModel(String modelId, def value) {
   Map<String, Map<String, SwaggerModelProperty>> swaggerProperties
   SwaggerModel swaggerModel = new SwaggerModel(id: modelId, properties: swaggerProperties)

   // might be a better way to do this (what i'm doing here is: retrieve 'boolean' from java.lang
   // .Boolean, etc.)
   String className = value.class.name;
   String type = className.substring(className.lastIndexOf('.') + 1).toLowerCase()

   return swaggerModel
}


def createSwaggerModels(HashMap json, String modelId) {
   def swaggerModels = []

   // If it's a hash map then we need to start creating a new model
   json.each {
      mapKey, mapValue ->
         SwaggerModel swaggerModel
         if (mapValue instanceof HashMap) {
            createSwaggerModels(mapValue, mapKey + "Object")
         }
         else if (mapValue instanceof List) {
            // TODO
         }
         else {
            swaggerModel = createSwaggerModel(mapKey, modelId);
         }
         swaggerModels << swaggerModel
   }

   return swaggerModels
}

/**
 * Parse out the last part of the URL (i.e. - /elements/getElement?id=1 should return getElement or
 * /elements/getElement should return getElement)
 *
 * @param url The URL to parse
 * @return The API that is being invoked
 */
def private parseApiMethodName(String url) {
   int endIndex = url.length()
   if (url.indexOf('?') >= 0) {
      endIndex = url.indexOf('?')
   }
   url.substring(url.lastIndexOf('/') + 1, endIndex)
}

/**
 * Handles creating the parameters list for a given HTTP request
 *
 * @param url The full URL with the potential HTTP query parameters on the end
 * @param json The potential JSON payload on the HTTP request
 * @return The list of parameters that are a part of the 'method' JSON
 */
def createMethodParameters(String modelName) {
   def swaggerMethodParameters = []

   params.each { key, value ->
      SwaggerMethodQueryParameter queryParameter = new SwaggerMethodQueryParameter(
            description: "TODO ($value)",
            parameterName: "$key",
            parameterType: "query") // any parameters we're adding here must be query parameters, not body

      swaggerMethodParameters << queryParameter
   }

   SwaggerMethodBodyParameter bodyParameter = new SwaggerMethodBodyParameter(
         description: "TODO",
         parameterType: "body",
         model: modelName
   )

   swaggerMethodParameters << bodyParameter

   return swaggerMethodParameters
}

def createSwaggerMethod(String url) {
   def swaggerMethods = []
   swaggerMethods << new SwaggerMethod(
         methodName: parseApiMethodName(url),
         description: "TODO",
         model: parseApiMethodName(url) + "ResponseObject",
         parameters: createMethodParameters(parseApiMethodName(url) + "RequestObject"))
}

def getJson() {
   HashMap json = null

   try {
      json = new JsonSlurper().parse(request.reader)
   }
   catch (Exception exception) {
      // eat it - this can happen if it's a GET or DELETE request which won't have any JSON
      // body or if the JSON body is just empty on a POST, PUT, PATCH, etc.
   }
   return json
}

// Create the request methods and models
swaggerMethods = createSwaggerMethod(request.uri.toString())
swaggerModels = createSwaggerModels(getJson(), parseApiMethodName(request.uri.toString()) + "RequestObject")

// Send the request to the elements code and create the response model
def http = new HTTPBuilder('http://localhost:8080/' + request.uri.toString())
http.setHeaders(headers)
http.request(Method.valueOf(request.method), JSON) {

   response.success = { resp, json ->
      assert resp.status == 200
      swaggerModels.addAll createSwaggerModels(json, parseApiMethodName(uri.toString()) + "ResponseObject")
   }

   response.'404' = { resp ->
      println '404 - Is Elements running?'
   }
}

// Construct JSON which represents the Swagger Documentation
response.setStatus(200)
json {
   methods(
         swaggerMethods.each({
            swaggerMethod ->
               swaggerMethod.parameters.each({
                  swaggerMethodParameter ->
               })
         })
   )
   models(
         swaggerModels.each {
            swaggerModel ->
         }
   )
}
