package com.cloudelements.docbuilder.service

import com.cloudelements.docbuilder.service.impl.DocBuilderServiceImpl
import spock.lang.Specification

/**
 * Unit tests for the DocBuilderService
 *
 * @version %I%, %G%
 * @author jjwyse
 */
class DocBuilderServiceTest extends Specification
{
    private DocBuilderService docBuilderService

    /**
     * Init our doc builder service
     */
    def setup()
    {
        docBuilderService = new DocBuilderServiceImpl()
    }

    /**
     * Unit test for saving a JSON request payload
     */
    def "save JSON request payload"()
    {
        given:
        def bodyParams = [account: 'name: Cloud Elements']
        def queryParams = ['elementToken': 'fakeelementtoken']
        def url = "/elements/api-v1/messaging/sendMessage?id=12&name=josh"

        when:
        docBuilderService.saveHttpRequest(url, queryParams, bodyParams)

        then:
        // TODO - how do we want to verify that it worked.  compare the output file to the actual JSON file example
        true == true
    }

    /**
     * Unit test for saving a JSON response payload
     */
    def "save JSON response payload"()
    {
        // TODO
    }
}
