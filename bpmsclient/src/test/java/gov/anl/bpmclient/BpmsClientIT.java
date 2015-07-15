package gov.anl.bpmclient;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Ignore;
import org.junit.Test;
import org.kie.api.runtime.process.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.techday.bpmclient.BpmsClient;

public class BpmsClientIT {

	private static final Logger LOGGER = LoggerFactory.getLogger(BpmsClientIT.class);

	@Test
	//@Ignore
	public void testStartProcess() throws JsonGenerationException, JsonMappingException, IOException {
		BpmsClient client = new BpmsClient("http://localhost:8080/business-central",
				"com.redhat:Workshop:1.0", 
				"bpmsAdmin",
				"p@ssw0rd");
		
		Map<String, Object> processVariables = new HashMap<String,Object>();
		processVariables.put("p_employeeFirst", "John");
		processVariables.put("p_employeeLast", "Doe");
		 ProcessInstance result = client.startProcess("Workshop.Workshop", processVariables );

		 ObjectMapper mapper = new ObjectMapper();
		 
		 LOGGER.info("As json: " + mapper.writeValueAsString(result));
		  
		assertNotNull("Result was null", result);
		
		LOGGER.info(result.toString());
	}

}