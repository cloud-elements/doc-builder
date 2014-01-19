package com.cloudelements.docbuilder.domain

import groovy.transform.ToString

@ToString
class SwaggerModelArrayProperty extends SwaggerModelProperty {
   Map<String, String> items;
}
