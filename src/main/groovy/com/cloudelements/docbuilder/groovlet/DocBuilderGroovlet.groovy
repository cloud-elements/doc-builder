package com.cloudelements.docbuilder.groovlet

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

/**
 * Parses the JSON from the HTTP request
 *
 * @return The JSON slurper
 */
def getJson()
{
    new JsonSlurper().parse(request.reader)
}

/**
 * Parses the HTTP request URL parameters
 *
 * @return The URL parameters in the HTTP request
 */
def getParameters()
{
    request.parameters
}

// HTTP method (GET, POST, etc.)
def uri = request.getUri()
def method = request.method

def jsonSlurper, queryParameters
if (method == "POST" || method == "PUT")
{
    jsonSlurper = getJson()
}
else if (method == "GET" || method == "DELETE")
{
    queryParameters = getParameters()
}
else
{
    response.setStatus(400)
    json.response
            {
                success(false)
                message("Invalid HTTP method " + method)
            }
}

// TODO - parse JSON request

// TODO - forward on to actual tomcat endpoint (should be property driven but default to localhost:8080)

// TODO - parse JSON response

// TODO - generate .json file that swagger needs for documentation

response.setStatus(200)
json.response
        {
            success(true)
        }