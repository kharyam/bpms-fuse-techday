# BPMS Java/Camel Client

## Red Hat Fuse Service Works Configuration
### Module Installation
1. Update $FSW_HOME/modules/system/layers/soa/org/drools/main/module.xml by adding the following module:

    <module name="javax.enterprise.api"/>

2. Update the pom by setting the <localRepoProperty> parameter to the location of the .m2/repository location of the target system.

3. Make sure JBOSS_HOME is set properly. Modify install.sh for your environment, it performs the following steps:

4. Build using:

    mvn clean install

on the target system.

5. Change directory to target/scripts

6. Update installmodule.cli with the location above

7. Run the script using the jboss CLI: *$JBOSS_HOME*/bin/jboss-cli.sh -c --file=installmodule.cli 

8. Restart FSW

**Note - if there is a certificate error, select P to accept the certificate and rerun the script**
 
### Module Removal
1. Run the script using the jboss CLI: *$JBOSS_HOME*/bin/jboss-cli.sh -c --file=removemodule.cli

## Usage
### Camel component in Fuse Service Works

BPMS process variables can be set in the header, in this case firstName and lastName are being set, prefixed with var_:

    <setHeader headerName="var_firstName">
      <simple>${body.firstName}</simple>
    </setHeader>
      <setHeader headerName="var_lastName">
      <simple>${body.lastName}</simple>
    </setHeader>
  
    <to uri="switchyard://BpmsCamelService"/>

Create "BpmsCamelService" reference with camel binding, uri set to:

    bpms://startProcess?baseUrl=http://localhost:18080/business-central&deploymentId=com.redhat:Workshop:1.0&username=bpmsAdmin&password=your_password&processId=Workshop.Workshop

**remember to add the CamelContextMapper class to the camel reference otherwise headers don't propagate!**

### Standalone camel
The above technique can be used, replacing:

    <to uri="switchyard://BpmsCamelService"/>

with:

    <to="bpms://startProcess?baseUrl=http://localhost:18080/business-central&deploymentId=com.redhat:Workshop:1.0&username=bpmsAdmin&password=your_password&processId=Workshop.Workshop"/>


Alternatively, the process variables can be passed in all at once with the following:

    <recipientList>
      <simple>
       bpms://startProcess?baseUrl=http://localhost:8080/business-central&amp;deploymentId=com.redhat:Workshop:1.0&amp;processId=Workshop.Workshop&amp;username=bpmsAdmin&amp;password=your_password&amp;var_firstName=${body.firstName}&amp;var_lastName=${body.lastName}
      </simple>
    </recipientList>

If you would like to pass the camel message payload (e.g., a Java object) into the BPMS process, add the *processVarName* parameter to the bpms url. For example, if you would like to pass a Person java object that is currently a message in the camel route into a process variable named p_person:
 
    processVarName=p_person


### Java

    BpmsClient client = new BpmsClient("http://localhost:18080/business-central",
      "com.redhat:Workshop:1.0", 
      "bpmsAdmin",
      "your_password");
		
      Map<String, String> processVariables = new HashMap<String,String>();
      processVariables.put("firstName", "John");
      processVariables.put("lastName", "Doe");
      processVariables.put("p_person", personObject);
      BpmsResponse result = client.startProcess("Workshop.Workshop", processVariables );

      if (result == null || !result.isSuccess()) {
        // Process failed to start
      } else {
        // Process was started successfully
      }
