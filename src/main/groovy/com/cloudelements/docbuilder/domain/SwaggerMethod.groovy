package com.cloudelements.docbuilder.domain

import groovy.transform.ToString

/**
 * TODO - JJW
 *
 * @version %I%, %G%
 * @author jjwyse
 */
@ToString
class SwaggerMethod
{
    String methodName;
    String description;
    String model;
    List<SwaggerMethodParameter> parameters;
}
