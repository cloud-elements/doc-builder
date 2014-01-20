[![Build Status](https://travis-ci.org/cloud-elements/doc-builder.png?branch=master)](https://travis-ci.org/cloud-elements/doc-builder)
#doc-builder
=========
Generates Swagger documentation for a RESTful service by analyzing the HTTP request and response

#setup
=========
1. Clone this repository
```
git clone https://github.com/cloud-elements/doc-builder.git
```

2. If you don't have GVM installed, do that first <a href="http://gvmtool.net/" target="_blank">here</a>
3. Once gvm is installed, install groovy and gradle
```
gvm install gradle
```
```
gvm install groovy
```

4. Use gradle to build and run the web application
```
gradle clean jettyRunWar
```

#instructions
=========
1. By default, this application runs on port 9111.  To change this port, modify the build.gradle file
2. Run the web application:
```
cd [doc-builder root]
```
```
gradle jettyRunWar
```

3. Whatever you use to send HTTP requests to our elements application (POSTMAN, cURL, etc.) - send the exact same HTTP request at the doc-builder web application
4. By default, doc-builder sets its 'elementsEndpoint' to be localhost:8080.  To change this endpoint, specify an 'elementsEndpoint' query parameter in your request.
   * Valid 'elementsEndpoint' parameter values:
      * localhost:[PORT]
      * snapshot.cloud-elements.com
      * qa.cloud-elements.com
      * console.cloud-elements.com
   * This will forward the retrieveObject request to localhost:8080 since no 'elementsEndpoint' parameter is specified
```
http://localhost:9111/crm/retrieveObject?objectName=residence&id=a1v30000000LKYwAAO
```

   * This will forward the retrieveObject to snapshot.cloud-elements.com
```
http://localhost:9111/crm/retrieveObject?objectName=residence&id=a1v30000000LKYwAAO&elementsEndpoint=snapshot.cloud-elements.com
```

5. The HTTP response you receive will be the JSON 'methods' and 'models' you need to add to the Swagger JSON document.  Make sure you replace any 'TODO' fields manually. Example response:

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

![alt tag](http://stack.to/wp-content/uploads//Cloud-Elements.png)