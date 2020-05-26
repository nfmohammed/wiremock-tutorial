#### Standalone Wiremock Server

Commands

    //start wiremock server on port 8080
    $ java -jar wiremock-standalone-2.26.3.jar
    
    //create stub mapping via Wiremock's POST api
    $ curl -X POST \
      --data '{ "request": { "url": "/get/this", "method": "GET" }, "response": { "status": 200, "body": "Here it is!\n" }}' \
      http://localhost:8080/__admin/mappings/new
    
    //fetch the mock response configured in previous step  
    $ curl http://localhost:8080/get/this


#### Configure using json files

- When the WireMock server starts it creates two directories: `mappings` and `__files`.

- See the following configuration files inside mappings folder:
    -  `mytest.json`
    - `multi-stub.json`


Command
    
    $ java -jar wiremock-standalone-2.26.3.jar
    
    $ curl http://localhost:8080/api/mytest
    output: More content
    
    $ curl http://localhost:808/one
    output: one is success
    
    $ curl http://localhost:8080/two
    output: two is success
