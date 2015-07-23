# Person Service
The person service exposes a Rest Service using the Camel DSL. It takes in a Person object (as JSON) and calls a BPMN process (residing on BPMS 6.1), passing it the person object. It also verifies the process was called successfully.

## Camel Route
![Camel Route](images/camelroute.png)

# Workshop
## Configure the Rest endpoint
This camel route uses a relatively new feature in camel - the Rest DSL.  This allows rest services to be created quickly and easily.  Rest DSL is supported as of Fuse 6.2.

1. in Jboss Developer Studio, open ***personservice/src/main/resources/camel-config.xml*** and select the **Source** tab, at the bottom of the view.

2. Replace

  ```xml
    <rest id="Update_Me"/>
  ```

  with the following:

  ```xml
  <rest path="/update">
    <post consumes="application/json" produces="application/json" type="com.redhat.techday.datamodel.Person">
      <to uri="direct:processPerson"/>
    </post>
  </rest>

  ```

Note that this creates a rest endpoint (/update) that consumes / produces json and expects a Person object as input. It sends the input to the ***processPerson*** camel route.


## Configure the BPMS call
This camel route uses a custom camel component (defined in the bpms-client project) to call the BPMN process on BPMS 6.1. Update the camel route to call the BPMN process properly.

1. Replace
    ```xml
    <to uri="bpms://startProcess" id="Update_Me_Too"/>
    ```

     with the following:

    ```xml

    <to uri="bpms://startProcess?baseUrl=http://localhost:8080/business-central/&amp;deploymentId=com.redhat.techday:project:1.0&amp;username=bpmsAdmin&amp;password=p@ssw0rd&amp;processId=project.PersonProcess&amp;processVarName=p_person" id="callBpms"/>

    ```

2. Update the ***deploymentId, username, password, processId and processVarName*** as necessary.

## Test the Service

Camel has support for [Swagger](http://swagger.io), which is a web based framework for documenting and testing with web services. The PersonService project has been configured to support Swagger (see the web.xml file for configuration information).

1.  Navigate to http://localhost/ui/personService and click the *Test the service via Swagger* link that will take you to the Swagger UI (embedded in PersonService).

  ![Swagger Link](images/swaggerlink.png)

2. Click the *update* link, which is the name of the rest endpoint that we defined in the camel route. Click the */update* link below it to reveal the POST method

3. Click the JSON block on the right to populate the body of the message. Update the parameters to your liking

  ![JSON Body](images/swaggerjson.png)

4. Click the ***Try it out!*** button to make the post request. Swagger will show you the equivalent curl command, the request url, response body (JSON response from BPMS) and response headers.

  ![JSON Body](images/swaggerresponse.png)
