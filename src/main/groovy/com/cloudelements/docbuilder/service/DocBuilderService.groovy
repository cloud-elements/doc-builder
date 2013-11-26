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
     *TODO
     * @param url
     * @param parameters
     * @param body
     * @return
     */
    def saveHttpRequest(url, parameters, body)

    /**
     * TODO
     * @param url
     * @param parameters
     * @param body
     * @return
     */
    def saveHttpResponse(url, parameters, body)
}
