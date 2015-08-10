# Red Hat Tech Day Fuse BPMS Integration Demo


## Slides
* [BPMS Overview] (https://docs.google.com/a/redhat.com/presentation/d/1xomewEf58VkqVJxLJm7JHlKe6mYKFFC5aipY6rntwms/edit?usp=sharing)
* [Fuse Overview] (https://docs.google.com/a/redhat.com/presentation/d/1qKKFFf1Iy_LJl_Mzl1q3wZZ8wn0Ue3ZF6ro2OHn94EY/edit?usp=sharing)
* [Real Time Decision Server Demo Slides] (https://docs.google.com/a/redhat.com/presentation/d/1JgqTrfcrV_SxLFMj5rgi1cL3scOG6qJGlhwqVuwpAQs/edit?usp=sharing)
* [Workshop Sides](https://docs.google.com/a/redhat.com/presentation/d/1aWh4tB5n1u1Ng_ycxq-A3IYeuiA1bObTlN6ChZpp1do/edit?usp=sharing)

## Architecture
![Architecture](architecture.png)

## Sub Projects
### bpms
This folder contains a completed BPMS process that can be uploaded to business central. It also contains a lab (README.md) describing how to create the BPM Process for the workshop.
### bpmsclient
This maven project implements a custom camel component that can be used to call BPMS processes.
### datamodel
This maven project implements a datamodel, consisting of a single Person class.  A person has a first name, last name, organizaton and an active boolean attribute.
### personservice
This maven project implements a camel route, exposed as a restful service. The route takes in a person object (as json) and starts a process on BPMS, passing it the object. It has a dependency on the datamodel project (for the Person class) and the bpmsclient project, which it uses to call the BPMS process.
### ui
This maven project implements a simple user interface that can be used to call the personservice.
