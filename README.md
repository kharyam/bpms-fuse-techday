# Red Hat Tech Day Fuse BPMS Integration Demo

## Architecture
![Architecture](architecture.png)

## Sub Projects
### bpms
This folder contains a completed BPMS process that can be uploaded to business central
### bpmsclient
This maven project implements a custom camel component that can be used to call BPMS processes.
### datamodel
This maven project implements a datamodel, consisting of a single Person class.  A person has a first name, last name, organizaton and an active boolean attribute.
### personservice
This maven project implements a camel route, exposed as a restful service. The route takes in a person object (as json) and starts a process on BPMS, passing it the object.
### ui
This maven project implements a simple user interface that can be used to call the personservice.
