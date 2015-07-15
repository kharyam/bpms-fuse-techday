package com.redhat.techday.bpmclient.camel;

import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;

/**
 * Endpoint format:
 * bpms://startProcess?baseUrl=http://localhost:18080&deploymentId=group:artifact:version&username=bpmsUser&password=bpmsPassword&processId=process.id&processVarName=processvar
 *
 * Specifying processVarName will send the camel input message (e.g., a java
 * object) to bpms with the specified name as the process variable.
 *
 */
public class BpmsComponent extends DefaultComponent {

	@Override
	protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
		return new BpmsEndpoint(uri, remaining, parameters);
	}

}