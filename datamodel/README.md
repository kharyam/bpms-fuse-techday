# Data Model
This project generates the domain data model used in this example. It should be used by both BPMS and FSW. Things to note:

* This is a kjar (e.g., there is a kmodule.xml file)

* This should be deployed to FSW (e.g., copied into the *$JBOSS_HOME*/standalone/deployments directory of FSW.

* Projects that depend on this (e.g., sy-personservice) have entries in their pom.xml that reference the deployment of this jar.

* The object(s) in this project must contain JAXB annotations in order to be sent to BPMS via the REST API.