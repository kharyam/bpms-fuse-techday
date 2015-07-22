# Data Model
This project generates the domain data model used in this example. It should be used by both BPMS and Fuse (Camel). Things to note:

* This is a kjar (e.g., there is a kmodule.xml file)

* Projects that depend on this (e.g., personservice) have entries in their pom.xml that reference the deployment of this jar.

* The object(s) in this project must contain JAXB annotations in order to be sent to BPMS via the REST API.
