package gov.anl.bpmclient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.services.client.api.RemoteRuntimeEngineFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * This class is responsible for making calls to BPMS via its REST API.
 * Refer to https://access.redhat.com/documentation/en-US/Red_Hat_JBoss_BPM_Suite/6.1/html/Development_Guide/sect-Remote_Java_API.html#The_REST_Remote_Java_RuntimeEngine_Factory
 * for more information on the remote runtime engine factory.
 */
public class BpmsClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(BpmsClient.class);
	private KieSession ksession;
	private RuntimeEngine engine;

	/**
	 * Constructs a new BPMS client.
	 * 
	 * @param baseUrl
	 *          The url of the BRMS server, e.g., https://mybpmserver.com:8443
	 * @param deploymentId
	 *          The project deployment id, e.g., com.mycompany:sampleBPMSApp:1.0
	 * @param username
	 *          The BPMS user id for making the remote call.
	 * @param password
	 *          The BPMS user password for making the remote call.
	 */
	public BpmsClient(String baseUrl, String deploymentId, String username, String password) {
		constructKieSession(baseUrl, deploymentId, username, password);
	}

	private void constructKieSession(String baseUrl, String deploymentId, String username, String password) {

    engine = RemoteRuntimeEngineFactory.newRestBuilder().addUrl(constructUrl(baseUrl))
        .addUserName(username)
        .addPassword(password)
        .addDeploymentId(deploymentId)
        .build();

    ksession = engine.getKieSession();

	}

	/**
	 * Starts a BPMS process with process variables.
	 * 
	 * @param processVariables
	 *          Map of process variables. null can be passed in if there are no
	 *          process variables.
	 * @return Results from BPMS
	 */
	public ProcessInstance startProcess(String processId, Map<String, Object> processVariables) {
		ProcessInstance processInstance = ksession.startProcess(processId, processVariables);
		int rulesFired = ksession.fireAllRules();
		if (rulesFired > 0 ) {
			LOGGER.info("Rules Fired: " + rulesFired);
		}
		return processInstance;
	}
	
	/**
	 * 
	 * Construct a new URL object from a String
	 * 
	 * @param fullUrl
	 *          URL as a string
	 * @return URL object representing the passed in String
	 */
	private URL constructUrl(String fullUrl) {
		URL url = null;
		try {
			url = new URL(fullUrl);
		} catch (MalformedURLException e) {
			LOGGER.error("Failed to contstruct url from " + fullUrl, e);
		}

		return url;
	}

}