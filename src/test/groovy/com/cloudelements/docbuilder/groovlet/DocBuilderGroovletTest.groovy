package com.cloudelements.docbuilder.groovlet

import com.cloudelements.docbuilder.domain.SwaggerMethod
import groovy.json.JsonBuilder
import spock.lang.Specification

/**
 * Spock test to test creating the proper JSON that Swagger needs to render the documentation
 * properly for an API endpoint
 *
 * @version %I%, %G%
 * @author jjwyse
 */
class DocBuilderGroovletTest extends Specification {
   DocBuilderGroovlet docBuilderGroovlet

   String baseUrl = "http://localhost:8080/elements/api-vi"

   def setup() {
      docBuilderGroovlet = new DocBuilderGroovlet()
   }

   def "Test create 'method' JSON with no JSON body"() {
      given:
      String url = baseUrl + "/crm/retrieveObject"
      def params = ['objectName': 'residence', 'id': 'a1v30000000LKYwAAO']

      when:
      def response = docBuilderGroovlet.createSwaggerMethod(url, params, 'GET')

      then:
      response != null
      response instanceof List
      response.size() == 1

      SwaggerMethod swaggerMethodResponse = response[0]
      swaggerMethodResponse.parameters.size() == 2

      // print it out prettily to the console
      JsonBuilder jsonBuilder = new JsonBuilder()
      jsonBuilder {
         methods(
               response.each({
                  swaggerMethod ->
                     swaggerMethod.parameters.each({
                        swaggerMethodParameter ->
                     })
               })
         )
      }
      print jsonBuilder.toPrettyString()
   }

   def "Test create 'method' JSON with JSON body and query parameters"() {
      given:
      String url = baseUrl + "/crm/createObject"
      def params = ['objectName': 'destination']

      when:
      def response = docBuilderGroovlet.createSwaggerMethod(url, params, 'POST')

      then:
      response != null
      response instanceof List
      response.size() == 1

      SwaggerMethod swaggerMethodResponse = response[0]
      swaggerMethodResponse.parameters.size() == 2

      // print it out prettily to the console
      JsonBuilder jsonBuilder = new JsonBuilder()
      jsonBuilder {
         methods(
               response.each({
                  swaggerMethod ->
                     swaggerMethod.parameters.each({
                        swaggerMethodParameter ->
                     })
               })
         )
      }
      print jsonBuilder.toPrettyString()
   }


   def "Test create 'model' JSON"() {
      given:
      JsonBuilder jsonBuilder = new JsonBuilder()
      def root = jsonBuilder.person {
         first_name 'josh'
         last_name 'wyse'
         is_developer true
         age 25
         hobbies 'running', 'developing', 'reading'
         height 71.5
      }

      when:
      def response = docBuilderGroovlet.createSwaggerModels(root, "createPerson", null, null);

      then:
      response != null
      response instanceof List
      response.size() == 2

      // print it out prettily to the console
      JsonBuilder jsonBuilderResponse = new JsonBuilder()
      jsonBuilderResponse {
         models(
               response.each {
                  responseSwaggerModel ->
               }
         )
      }
      print jsonBuilderResponse.toPrettyString()
   }

}
