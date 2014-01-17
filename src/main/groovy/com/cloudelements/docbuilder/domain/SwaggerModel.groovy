package com.cloudelements.docbuilder.domain

import groovy.transform.ToString

/**
 * TODO - JJW
 *
 * @version %I%, %G%
 * @author jjwyse
 */
@ToString
class SwaggerModel {
   String id;
   List<String> required = new ArrayList<>()
   Map<String, SwaggerModelProperty> properties;

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
