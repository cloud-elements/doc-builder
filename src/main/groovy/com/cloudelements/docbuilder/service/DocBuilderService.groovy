package com.cloudelements.docbuilder.service

/**
 * TODO
 *
 * @version $Revision: 1 $
 * @author: jjwyse
 * @since: 11/21/13
 */
interface DocBuilderService
{
    /**
     * Save the actual HTTP request that would go to one of the elements' endpoints.
     * @param url The URL to the endpoint excluding the base_url.  For example -
     *      localhost:8080/elements/api-v1/crm/retrieveAccount
     *      would just be
     *      /elements/api-v1/crm/retrieveAccount
     * @param parameters The HTTP query parameters
     * @param body The HTTP body payload (The JSON body)
     */
    def saveHttpRequest(url, parameters, body)

    /**
     * Save the actual HTTP response that comes back from one of the elements' endpoints.
     *
     * @param body The HTTP body payload (The JSON body) response from our request
     */
    def saveHttpResponse(body)
}
