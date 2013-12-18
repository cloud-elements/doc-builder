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


def createSwaggerModels(HashMap jsonMap, String modelId)
{
    def swaggerModels = []

    def builder = new groovy.json.JsonBuilder()

    jsonMap.each {
        mapKey, mapValue ->
            if (mapValue instanceof HashMap)
            {
                SwaggerModelProperty swaggerModelProperty = new SwaggerModelProperty(
                        type: "TODO",
                        description: "TODO")

                def swaggerProperties = []
                swaggerProperties << swaggerModelProperty

                swaggerModels << new SwaggerModel(
                        id: modelId,
                        properties: swaggerProperties)

            }
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
def parseApiMethodName(String url)
{
    int endIndex = url.length()
    if (url.indexOf('?') >= 0)
    {
        endIndex = url.indexOf('?')
    }
    url.substring(url.lastIndexOf('/') + 1, endIndex)
}

SwaggerMethodParameter parameter = new SwaggerMethodParameter(
        description: "TODO",
        parameterName: "TODO",
        parameterType: "body",
        model: "TODO")
if (request.method == "GET" || request.method == "DELETE")
{
    parameter.parameterType = "query"
}

def swaggerMethodParameters = []
swaggerMethodParameters << parameter

def swaggerMethods = []
swaggerMethods << new SwaggerMethod(
        methodName: parseApiMethodName(request.uri.toString()),
        description: "TODO",
        model: parseApiMethodName(request.uri.toString() + "Object"),
        parameters: swaggerMethodParameters)

// Construct JSON which represents the Swagger Documentation
response.setStatus(200)
json {
    publish(true)
    description("TODO")
    methods(
            swaggerMethods.each({
                swaggerMethod ->
                    swaggerMethod.parameters.each({
                        swaggerMethodParameter ->
                    })
            })
    )
    if (request.method == "POST" || request.method == "PUT")
    {
        models(
                createSwaggerModels(new JsonSlurper().parse(request.reader), parseApiMethodName(request.uri.toString
                        ()) + "Object").each { swaggerModel -> }
        )
    }
}
