# Person Service
The person service exposes a Rest Service using the Camel DSL. It takes in a Person object (as JSON) and calls a BPMN process (residing on BPMS 6.1), passing it the person object. It also verifies the process was called successfully.

## Camel Route
![Camel Route](images/camelroute.png)
