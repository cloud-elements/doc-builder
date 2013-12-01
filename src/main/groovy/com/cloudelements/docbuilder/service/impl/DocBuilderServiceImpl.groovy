package com.cloudelements.docbuilder.service.impl

import com.cloudelements.docbuilder.service.DocBuilderService

/**
 * TODO
 *
 * @version $Revision: 1 $
 * @author: jjwyse
 * @since: 11/21/13
 */
class DocBuilderServiceImpl implements DocBuilderService
{
    /**
     * {@inheritDoc}
     */
    @Override
    def saveHttpRequest(url, parameters, body)
    {
        String apiMethod = getApiMethodName(url);

        throw new RuntimeException("Method not implemented") // TODO - (jjwyse, 11/26/13)
    }

    /**
     * {@inheritDoc}
     */
    @Override
    def saveHttpResponse(url, parameters, body)
    {
        throw new RuntimeException("Method not implemented") // TODO - (jjwyse, 11/26/13)
    }

    /**
     * Parse out the last part of the URL (i.e. - /elements/getElement?id=1 should return getElement)
     *
     * @param url The URL to parse
     * @return The API that is being invoked
     */
    protected getApiMethodName(String url)
    {
        url.substring(url.lastIndexOf('/') + 1, url.indexOf('?'))
    }
}
