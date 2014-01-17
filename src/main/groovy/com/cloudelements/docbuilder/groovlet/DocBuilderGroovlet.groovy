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

def createSwaggerModels(HashMap json, String modelId, SwaggerModel swaggerModel, List<SwaggerModel> swaggerModels) {

   if (swaggerModels == null) {
      swaggerModels = new ArrayList<>();
   }

   // If it's a hash map then we need to start creating a new model
   json.each {
      mapKey, mapValue ->
         if (swaggerModel == null) {
            swaggerModel = new SwaggerModel(id: modelId)
            swaggerModels << swaggerModel
         }

         if (mapValue instanceof Map) {
            swaggerModel.addProperty(mapKey, new SwaggerModelProperty(type: mapKey + 'Object', description: 'TODO'))
            createSwaggerModels(mapValue, mapKey + 'Object', null, swaggerModels)
         }
         else if (mapValue instanceof List) {
            // TODO
            swaggerModel.addProperty(mapKey, new SwaggerModelProperty(type: 'array', description: 'TODO'))
         }
         else {
            swaggerModel.addProperty(mapKey, new SwaggerModelProperty(type: parseType(mapValue), description: 'TODO'))
         }
   }

   return swaggerModels
}

def parseType(def clazz) {
   // might be a better way to do this (what i'm doing here is: retrieve 'boolean' from java.lang.Boolean, etc.)
   String className = clazz.class.name;
   String type = className.substring(className.lastIndexOf('.') + 1).toLowerCase()

   if (type.equalsIgnoreCase("bigdecimal")) {
      type = "double"
   }

   return type
}

def private parseApiMethodName(String url) {
   int endIndex = url.length()
   if (url.indexOf('?') >= 0) {
      endIndex = url.indexOf('?')
   }
   url.substring(url.lastIndexOf('/') + 1, endIndex)
}

def createMethodParameters(String modelName, HashMap queryParams, String httpMethod) {
   def swaggerMethodParameters = []

   queryParams.each { key, value ->
      SwaggerMethodQueryParameter queryParameter = new SwaggerMethodQueryParameter(
            description: "TODO ($value)",
            parameterName: "$key",
            parameterType: "query") // any parameters we're adding here must be query parameters, not body

      swaggerMethodParameters << queryParameter
   }

   if (httpMethod.equalsIgnoreCase('POST') || httpMethod.equalsIgnoreCase('PUT')) {
      SwaggerMethodBodyParameter bodyParameter = new SwaggerMethodBodyParameter(
            description: "TODO",
            parameterType: "body",
            model: modelName
      )
      swaggerMethodParameters << bodyParameter
   }

   return swaggerMethodParameters
}

def createSwaggerMethod(String url, Map params, String httpMethod) {
   def swaggerMethods = []
   swaggerMethods << new SwaggerMethod(
         methodName: parseApiMethodName(url),
         description: "TODO",
         model: parseApiMethodName(url) + "ResponseObject",
         parameters: createMethodParameters(parseApiMethodName(url) + "RequestObject", params, httpMethod))
}

def getRequestBody() {
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

def sendToElements(Map headers, String method, Map jsonBody) {
   def httpBuilder = new HTTPBuilder('http://localhost:8080/' + request.uri.toString())

   httpBuilder.setHeaders(headers)
   httpBuilder.getHeaders().putAt('Content-Length', null) // cannot set this or an exception is thrown

   httpBuilder.request(Method.valueOf(method), JSON) {
      if (jsonBody != null) {
         body = jsonBody
      }

      response.success = { resp, json ->
         assert resp.status == 200
         return json
      }

      response.failure = { resp ->
         println 'Error while talking to Elements - ' + resp.statusLine.statusCode
      }
   }
}

// Variables from the incoming HTTP request
Map requestParams = params
Map requestBody = getRequestBody()
Map requestHeaders = headers
String requestMethod = request.method
String requestUrl = request.uri.toString()

// Create the request methods and models
swaggerMethods = createSwaggerMethod(requestUrl, requestParams, requestMethod)
swaggerModels = createSwaggerModels(requestBody, parseApiMethodName(requestUrl + "RequestObject"), null, null)

// Send the request to the elements code
Map responseBody = sendToElements(requestHeaders, requestMethod, requestBody)

// Create the response models
swaggerModels.addAll(createSwaggerModels(responseBody, parseApiMethodName(requestUrl + "ResponseObject"), null, null))

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
