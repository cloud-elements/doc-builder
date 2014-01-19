package com.cloudelements.docbuilder.domain

import groovy.transform.ToString

@ToString
class SwaggerMethod {
   String methodName;
   String description;
   String model;
   List<SwaggerMethodParameter> parameters;
}
