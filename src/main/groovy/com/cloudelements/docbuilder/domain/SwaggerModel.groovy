package com.cloudelements.docbuilder.domain

import groovy.transform.ToString

@ToString
class SwaggerModel {
   String id
   Map<String, SwaggerModelProperty> properties

   def addProperty(String key, SwaggerModelProperty value) {
      if (properties == null) {
         properties = new HashMap<String, SwaggerModelProperty>()
      }

      if (key != null && value != null) {
         properties.put(key, value);
      }
      else {
         throw new RuntimeException("WTF are you trying to do...")
      }
   }
}
