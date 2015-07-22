# Person Service
The person service exposes a Rest Service using the Camel DSL. It takes in a Person object (as JSON) and calls a BPMN process (residing on BPMS 6.1), passing it the person object. It also verifies the process was called successfully.

## Camel Route
![Camel Route](images/camelroute.png)

# Workshop
## Configure the Rest endpoint
This camel route uses a relatively new feature in camel - the Rest DSL.  This allows rest services to be created quickly and easily.  

1. in Jboss Developer Studio, open ***personservice/src/main/resources/camel-config.xml*** and select the **Source** tab, at the bottom of the view.



2. Replace the **&gt;!-- REST_CONFIGURATION_PLACEHOLDER --&lt;** tag in the camel route with the following xml:

```xml
<rest path="/update">
  <post consumes="application/json" produces="application/json" type="com.redhat.techday.datamodel.Person">
    <to uri="direct:processPerson"/>
  </post>
</rest>
```
