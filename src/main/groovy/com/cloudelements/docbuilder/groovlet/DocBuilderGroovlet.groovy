package com.cloudelements.docbuilder.groovlet

import com.cloudelements.docbuilder.domain.SwaggerMethod
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

/**
 * Parses the HTTP body payload from the HTTP request.  Assumes that's it is JSON
 *
 * @return The JSON slurper
 */
def getHttpBody()
{
    def reader = request.reader
    if (reader != null)
    {
        // TODO - need to verify that the reader has a body or else this throws an exception

        // Assuming the body is JSON if there's anything in the body
        new JsonSlurper().parse(reader)
    }
}

/**
 * Parses the HTTP request URL parameters
 *
 * @return The URL parameters in the HTTP request
 */
def getHttpParameters()
{
    request.parameters
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

def parameters = [description: "TODO", model: "TODO", parameterType: "body"]
if (request.method == "GET" || request.method == "DELETE")
{
    parameters << [parameterType: "query"]
}

swaggerMethods << new SwaggerMethod(methodName: parseApiMethodName(request.uri.toString()),
        description: "TODO",
        model: "TODO",
        parameters: parameters)

// Construct JSON which represents the Swagger model JSON
response.setStatus(200)
json {
    publish(true)
    description("Test")
    methods(
            swaggerMethods.each({
                swaggerMethod ->
            })
    )
}
