package com.jjw.docbuilder.groovlet

/**
 * Main groovlet, serving up the index.gsp page to allow the user to invoke
 * an HTTP endpoint and begin generation their documentation.
 *
 * @version $Revision: 1 $
 * @author: jjwyse
 * @since: 11/21/13
 */

// Initiate a session if it doesn't exist
if (!session)
{
    session = request.getSession(true)
    session.counter = 0
}
session.counter++

forward 'views/index.gsp'