package com.cloudelements.docbuilder.groovlet

import com.cloudelements.docbuilder.service.DocBuilderService
import com.cloudelements.docbuilder.service.impl.DocBuilderServiceImpl
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

DocBuilderService service = new DocBuilderServiceImpl()

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

// TODO - parse HTTP body and parameters and save them
service.saveHttpRequest(request.uri.toString(), getHttpParameters(), getHttpBody())

// TODO - forward on to actual tomcat endpoint (should be property driven but default to localhost:8080)

// TODO - parse JSON response

// TODO - generate .json file that swagger needs for documentation

response.setStatus(200)
json.response
        {
            success(true)
        }