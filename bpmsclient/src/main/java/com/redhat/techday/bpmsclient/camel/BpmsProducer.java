package com.redhat.techday.bpmsclient.camel;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.impl.DefaultProducer;
import org.kie.api.runtime.process.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.techday.bpmsclient.BpmsClient;

/**
 * This class provides the ability to start a BPMS process from camel.
 *
 */

public class BpmsProducer extends DefaultProducer {

	private static final Logger LOGGER = LoggerFactory.getLogger(BpmsProducer.class);
	private static final String PROCESS_VARIABLE_PREFIX = "var_";

	public BpmsProducer(BpmsEndpoint bpmsEndpoint) {
		super(bpmsEndpoint);
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		BpmsEndpoint endpoint = (BpmsEndpoint) getEndpoint();

		Map<String, Object> allParameters = new HashMap<String, Object>();

		// Parameters can be obtained from the headers or query parameters
		allParameters.putAll(exchange.getIn().getHeaders());
		allParameters.putAll(endpoint.getParameters());

		String baseUrl = getParameter("baseUrl", allParameters, true);
		String deploymentId = getParameter("deploymentId", allParameters, true);
		String username = getParameter("username", allParameters, true);
		String password = getParameter("password", allParameters, true);
		String objectName = getParameter("processVarName",allParameters, false);
		
		// If an object name was specified, add it as a process variable, obtaining it from the camel body.
		if (!isNullOrEmpty(objectName)) {
			allParameters.put(PROCESS_VARIABLE_PREFIX + objectName, exchange.getIn().getBody());
		}

		boolean missingParameter = isNullOrEmpty(baseUrl) || isNullOrEmpty(deploymentId) || isNullOrEmpty(username)
				|| isNullOrEmpty(password);

		if (missingParameter) {
			LOGGER.error("One or more required parameters are missing. Cancelling operation.");
		} else {

			BpmsClient client = new BpmsClient(baseUrl, deploymentId, username, password);

			String bpmsAction = endpoint.getAction();

			if (bpmsAction.equalsIgnoreCase("startProcess")) {
				startBpmProcess(exchange, client, allParameters);
			} else {
				LOGGER.error("Unrecognized BPMS action \"" + bpmsAction + "\". No action will be taken.");
			}
		}
	}

	private void startBpmProcess(Exchange exchange, BpmsClient client, Map<String, Object> parameters) {

		Map<String, Object> processVariables = getProcessVariables(parameters);
		String processId = getParameter("processId", parameters, true);

		if (isNullOrEmpty(processId)) {
			LOGGER.error("Required parameter processId was not specified, process will NOT be started.");
		} else {

			 ProcessInstance response = null;
			 try {
				 response = client.startProcess(processId, processVariables);
			 } catch (Exception e) {
				 LOGGER.error("Failed to start BPMS process.",e);
			 }
			 
			if (response == null) {
				LOGGER.error("Call to start BPMS process failed.");
			} else {
				Message out = exchange.getOut();
				if (out != null) {
					out.setBody(response);
				} else {
					LOGGER.error("Unexpected error - output message in exchange was null, BPMS response will not be set in the exchange: " + response);
				}
			}
		}
	}

	private String getParameter(String parameter, Map<String, Object> parameters, boolean required) {
		String result = null;
		if (parameters.get(parameter) != null) {
			result = parameters.get(parameter).toString();
		}
		if (required && (result == null || result.isEmpty())) {
			LOGGER.error("Required parameter " + parameter + " was not provided.");
		}
		return result;
	}

	private Map<String, Object> getProcessVariables(Map<String, Object> parameters) {
		Map<String, Object> processVariables = new HashMap<String, Object>();

		for (Entry<String, Object> parameter : parameters.entrySet()) {
			if (parameter.getKey().startsWith(PROCESS_VARIABLE_PREFIX)) {
				String processVariable = parameter.getKey().toString().substring(PROCESS_VARIABLE_PREFIX.length());
				Object processVariableValue = parameter.getValue();
				LOGGER.debug("Found process variable: " + processVariable + " => " + processVariableValue);
				processVariables.put(processVariable, processVariableValue);
			}
		}
		return processVariables;
	}

	boolean isNullOrEmpty(Object object) {
		return object == null || object.toString() == null || object.toString().isEmpty();
	}

}