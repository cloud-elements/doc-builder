package com.cloudelements.docbuilder.domain

import groovy.transform.ToString

@ToString
class SwaggerModelEnumProperty extends SwaggerModelProperty {
   List<String> enums;
}
