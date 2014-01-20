[![Build Status](https://travis-ci.org/cloud-elements/doc-builder.png?branch=master)](https://travis-ci.org/cloud-elements/doc-builder)
#doc-builder
=========
Self-documents RESTful services by analyzing the request and response JSON payloads and generating the Swagger JSON documentation.

#Setup
=========
1. If you don't have GVM installed, do that first http://gvmtool.net/
2. Use gvm to install groovy and gradle
   * gvm install gradle
   * gvm install groovy
3. Use gradle to build and run the web application
   * gradle clean jettyRunWar 

#Instructions
=========
1. By default, this application runs on port 9111.  To change this port, modify the build.gradle file
2. Run the web application:  
```
gradle jettyRunWar
```
3. Whatever you use to send HTTP requests to soba (POSTMAN, cURL, etc.) - send the exact same HTTP request at the doc-builder web application
4. The HTTP response you receive will be the JSON 'methods' and 'models' you need to add to the Swagger JSON document
* Example response:
```JSON
{
       "methods": [
           {
               "parameters": [
                   {
                       "parameterName": "id",
                       "parameterType": "query",
                       "description": "TODO [a1v30000000LKYwAAO]"
                   },
                   {
                       "parameterName": "objectName",
                       "parameterType": "query",
                       "description": "TODO [residence]"
                   }
               ],
               "description": "TODO",
               "model": "retrieveObjectResponseObject",
               "methodName": "retrieveObject"
           }
       ],
       "models": [
           {
               "properties": {
                   "dataReturned": {
                       "type": "boolean",
                       "description": "TODO [true]"
                   },
                   "object": {
                       "type": "objectObject"
                   },
                   "success": {
                       "type": "boolean",
                       "description": "TODO [true]"
                   }
               },
               "id": "retrieveObjectResponseObject"
           },
           ...
}
```

5. Any fields with TODO will need to be updated manually
![alt tag](http://stack.to/wp-content/uploads//Cloud-Elements.png)