package com.cloudelements.docbuilder.groovlet

import com.cloudelements.docbuilder.domain.SwaggerMethod
import groovy.json.JsonBuilder
import spock.lang.Specification

/**
 * Spock test to test creating the proper JSON that Swagger needs to render the documentation
 * properly for an API endpoint
 *
 * @version %I%, %G%
 * @author jjwyse
 */
class DocBuilderGroovletTest extends Specification
{
    DocBuilderGroovlet docBuilderGroovlet

    String baseUri = "http://localhost:8080/elements/api-vi"

    def setup()
    {
        docBuilderGroovlet = new DocBuilderGroovlet()
    }

    def "Test create 'method' JSON with no JSON body"()
    {
        given:
        String uri = baseUri + "/crm/retrieveObject?objectName=residence&id=a1v30000000LKYwAAO"

        when:
        def response = docBuilderGroovlet.createSwaggerMethods(uri, null)

        then:
        response != null
        response instanceof List
        response.size() == 1

        SwaggerMethod swaggerMethodResponse = response[0]
        swaggerMethodResponse.parameters.size() == 2

        // print it out prettily to the console
        JsonBuilder jsonBuilder = new JsonBuilder()
        jsonBuilder {
            methods(
                    response.each({
                        swaggerMethod ->
                            swaggerMethod.parameters.each({
                                swaggerMethodParameter ->
                            })
                    })
            )
        }
        print jsonBuilder.toPrettyString()
    }

    def "Test create 'model' JSON"()
    {
        given:
        HashMap hashMap = new HashMap()
        HashMap contact = new HashMap()
        contact.put("id", "123")
        hashMap.put("contact", contact)

        when:
        def response = docBuilderGroovlet.createSwaggerModels(hashMap, "TestObject");

        then:
        response != null
        response instanceof List
        print response
    }
}
