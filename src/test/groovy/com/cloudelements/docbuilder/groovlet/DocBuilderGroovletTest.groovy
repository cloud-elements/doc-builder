package com.cloudelements.docbuilder.groovlet

import spock.lang.Specification

/**
 * TODO - JJW
 *
 * @version %I%, %G%
 * @author jjwyse
 */
class DocBuilderGroovletTest extends Specification
{
    DocBuilderGroovlet docBuilderGroovlet

    def setup()
    {
        docBuilderGroovlet = new DocBuilderGroovlet()
    }

    def "Test creating models from incoming JSON"()
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
        print response
    }
}
