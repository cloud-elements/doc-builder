package com.cloudelements.docbuilder.domain

import groovy.transform.ToString

/**
 * TODO - JJW
 *
 * @version %I%, %G%
 * @author jjwyse
 */
@ToString
class SwaggerModel
{
    String id;
    List <String> required;
    List<SwaggerModelProperty> properties;
}
