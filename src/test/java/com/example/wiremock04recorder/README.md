#### Recording Stub Response

Steps to record stub

- Start wiremock server `$ java -jar wiremock-standalone-2.26.3.jar`

- Open wiremock recorder page.
    - http://localhost:8080/__admin/recorder/

- Provide the target URL. We will use fake api provider.
    - https://reqres.in/

- Make an api request using Postman
    - http://localhost:8080/api/uses/2

- Wiremock acts as proxy and records the response received from actual api.
    - See file inside `mappings` folder.

- Stop the recorder function on recorder page.

- Make another api request using Postman and the response will be served from wiremock.


#### Playback

- Start wiremock server using `java -jar wiremock-standalone-xxx.jar`

- Execute Junit test inside `wiremock04recorder`    

#### Reference

- https://www.youtube.com/watch?v=mpbDuToT4NI
