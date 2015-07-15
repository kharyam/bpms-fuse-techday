package com.redhat.techday.datamodel;

import static org.junit.Assert.assertNotNull;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.junit.Test;

import com.redhat.techday.datamodel.Person;

public class PersonTest {
	
	@Test
	public void testPerson() {
		
		Person person = new Person();
		person.setActive(true);
		person.setFirstName("John");
		person.setLastName("Doe");
		person.setOrganization("HR");

		String json = convertToJson(person);
		assertNotNull(json);

		System.out.println(json);
	}

	private String convertToJson(Object obj) {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

		String json = null;

		try {
			json = ow.writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return json;
	}

}