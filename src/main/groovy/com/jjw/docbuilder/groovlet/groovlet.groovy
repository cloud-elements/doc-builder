package com.jjw.docbuilder.groovlet

import com.jjw.docbuilder.service.GroovletService
import com.jjw.docbuilder.service.impl.GroovletServiceImpl

/**
 * Groovlet to handle making an HTTP request to an endpoint and parsing
 * the response to create our documentation
 *
 * [Good documentation on Groovlets here: http://groovy.codehaus.org/Groovlets]
 *
 * @version $Revision: 1 $
 * @author: jjwyse
 * @since: 11/21/13
 */

// Use our service
GroovletService groovletService = new GroovletServiceImpl()

// Use the JSON builder to respond in JSON
response.contentType = 'application/json'
json.docbuilder {
    success(true)
}
