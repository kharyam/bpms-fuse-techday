package com.redhat.techday.datamodel;

import static org.junit.Assert.assertNotNull;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersonTest {
	
  private static final Logger LOGGER = LoggerFactory.getLogger(PersonTest.class);
  
	@Test
	public void testPerson() {
		
		Person person = new Person();
		person.setActive(true);
		person.setFirstName("John");
		person.setLastName("Doe");
		person.setOrganization("HR");

		String json = convertToJson(person);
		assertNotNull(json);

		LOGGER.info("Person as json: \n"+json);
	}

	private String convertToJson(Object obj) {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

		String json = null;

		try {
			json = ow.writeValueAsString(obj);
		} catch (Exception e) {
		  LOGGER.error("Failed to convert object to json: ",e);
		}

		return json;
	}

}