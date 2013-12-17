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

def swaggerMethods = []
def swaggerMethodParameters = []
def swaggerModels = []

def createSwaggerModels()
{
    def reader = request.reader
    if (reader == null)
    {
        return
    }

    // Assuming the body is JSON if there's anything in the body
    HashMap httpBody = new JsonSlurper().parse(reader)

    def builder = new groovy.json.JsonBuilder()

    httpBody.each {
        mapKey ->
            if (mapKey instanceof HashMap)
            {
                swaggerModels << new SwaggerModel(
                        id: parseApiMethodName(request.uri.toString() + "Object"),
                        properties: createModelProperties())

                SwaggerModelProperty swaggerModelProperty = new SwaggerModelProperty(
                        type: "TODO",
                        description: "TODO")
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
swaggerMethodParameters << parameter

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
    createSwaggerModels()
}
