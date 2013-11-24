package com.cloudelements.docbuilder.groovlet

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

// Initiate a session if it doesn't exist
if (!session)
{
    session = request.getSession(true)
    session.counter = 0
}
session.counter++

json.response
{
    success(true)
}