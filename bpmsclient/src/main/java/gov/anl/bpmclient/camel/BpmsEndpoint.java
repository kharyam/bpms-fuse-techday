package gov.anl.bpmclient.camel;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BpmsEndpoint extends DefaultEndpoint {

	private String action;
	private Map<String, Object> parameters;
	private static final Logger LOGGER = LoggerFactory.getLogger(BpmsEndpoint.class);

	/**
	 * Creates a new BPMS camel endpoint
	 * 
	 * @param uri
	 *          The endpoint uri
	 * @param action
	 *          The path of the uri, which maps to a bpms action (e.g.,
	 *          startProcess)
	 * @param params
	 *          Map of process variables
	 */
	public BpmsEndpoint(String uri, String action, Map<String, Object> params) {
		setEndpointUri(uri);
		this.action = action;
		copyAndClearParameters(params);
	}

	/**
	 * 
	 * Copy the parameters from the incoming collection then clear that
	 * collection. This serves as an indicator to the parent class that all
	 * parameters were handled, otherwise it will throw an exception.
	 * 
	 * @param params
	 *          Incoming parameters
	 */
	private void copyAndClearParameters(Map<String, Object> params) {
		this.parameters = new HashMap<String, Object>();
		this.parameters.putAll(params);
		params.clear();
	}

	@Override
	public Producer createProducer() throws Exception {
		return new BpmsProducer(this);
	}

	@Override
	public Consumer createConsumer(Processor processor) throws Exception {
		LOGGER.error("The BPMS endpoint only supports producers, not consumers.");
		return null;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

}