package com.cloudelements.docbuilder.groovlet

import com.cloudelements.docbuilder.domain.SwaggerMethod
import com.cloudelements.docbuilder.domain.SwaggerMethodParameter
import com.cloudelements.docbuilder.domain.SwaggerModel
import com.cloudelements.docbuilder.domain.SwaggerModelProperty
import groovy.json.JsonSlurper

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

def private createSwaggerModel(String modelId, String value)
{
    def swaggerProperties = []
    SwaggerModel swaggerModel = new SwaggerModel(id: modelId, properties: swaggerProperties)

    // might be a better way to do this (what i'm doing here is: retrieve 'boolean' from java.lang
    // .Boolean, etc.)
    String className = value.getClass().name;
    String type = className.substring(className.lastIndexOf('.') + 1).toLowerCase()

    SwaggerModelProperty swaggerModelProperty = new SwaggerModelProperty(type: "$type", description: "TODO ($value)")

    swaggerProperties << swaggerModelProperty

    swaggerModel << swaggerProperties

    return swaggerModel
}


def createSwaggerModels(HashMap jsonMap, String modelId)
{
    def swaggerModels = []

    // If it's a hash map then we need to start creating a new model
    jsonMap.each {
        mapKey, mapValue ->
            SwaggerModel swaggerModel
            if (mapValue instanceof HashMap)
            {
                // TODO - recurse to create another model
            }
            else if (mapValue instanceof List)
            {
                // TODO - recurse to create another model
            }
            else
            {
                swaggerModel = createSwaggerModel(modelId, jsonMap);
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
def private parseApiMethodName(String url)
{
    int endIndex = url.length()
    if (url.indexOf('?') >= 0)
    {
        endIndex = url.indexOf('?')
    }
    url.substring(url.lastIndexOf('/') + 1, endIndex)
}

/**
 * Parse out the query parameters from a full URL (i.e. -
 * http://snapshot.cloud-elements.com/api-v1/crm/retrieveObject?objectName=residence&id=a1v30000000LKYwAAO)
 * should return a map of [objectName->residence, id->a1v30000000LKYwAAO]
 *
 * @param url The full url beginning with http and ending with potential query parameters
 */
def private parseQueryParameters(String url)
{
    int endIndex = url.length()
    if (url.indexOf('?') >= 0)
    {
        endIndex = url.indexOf('?')
    }
    Map queryParametersMap = new HashMap()

    String queryParametersString = url.substring(endIndex + 1)
    queryParametersString.split('&').each {
        queryParameter ->
            queryParametersMap.put(queryParameter.split('=')[0], queryParameter.split('=')[1])
    }

    return queryParametersMap
}

/**
 * @param url
 * @return
 */
def createMethodParameters(String url)
{
    def swaggerMethodParameters = []

    def queryParameters = parseQueryParameters(url)

    queryParameters.each { key, value ->
        SwaggerMethodParameter parameter = new SwaggerMethodParameter(
                description: "TODO (In this case the value of this parameter was $value)",
                parameterName: "$key",
                parameterType: "query") // any parameters we're adding here must be query parameters, not body

        swaggerMethodParameters << parameter
    }

    return swaggerMethodParameters
}

/**
 * Create the 'method' JSON object that will go in the 'methods' section of the JSON result
 *
 * @return A list of swagger method objects {@link SwaggerMethod}
 */
def createSwaggerMethods(String url)
{
    def swaggerMethods = []
    swaggerMethods << new SwaggerMethod(
            methodName: parseApiMethodName(url),
            description: "TODO",
            model: parseApiMethodName(url + "Object"),
            parameters: createMethodParameters(url))
}

// Construct JSON which represents the Swagger Documentation
response.setStatus(200)
json {
    publish(true)
    description("TODO")
    methods(
            createSwaggerMethods(request.method, request.uri.toString()).each({
                swaggerMethod ->
                    swaggerMethod.parameters.each({
                        swaggerMethodParameter ->
                    })
            })
    )
    if (request.method == "POST" || request.method == "PUT")
    {
        models(
                createSwaggerModels(
                        new JsonSlurper().parse(request.reader), parseApiMethodName(request.uri.toString()) +
                        "Object").each {
                    swaggerModel ->
                }
        )
    }
}
