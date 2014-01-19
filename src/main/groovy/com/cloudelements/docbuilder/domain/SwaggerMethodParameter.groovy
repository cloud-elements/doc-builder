package com.cloudelements.docbuilder.domain

import groovy.transform.ToString

@ToString
abstract class SwaggerMethodParameter {
   String description
   String parameterType
}
