package com.cloudelements.docbuilder.servlet

import groovy.servlet.GroovyServlet
import javax.servlet.http.HttpServletRequest;

/**
 * Main servlet that intercepts any incoming HTTP requests to our web server and invokes
 * the docbuilder groovlet
 *
 * @author: jjwyse
 */
class DocBuilderServlet extends GroovyServlet
{
    @Override
    protected String getScriptUri(HttpServletRequest request)
    {
        return "/DocBuilder.groovy"
    }
}
