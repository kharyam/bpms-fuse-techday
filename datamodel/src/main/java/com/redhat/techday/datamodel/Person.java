package com.redhat.techday.datamodel;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonProperty;

@XmlRootElement(name = "Person")
@XmlAccessorType(XmlAccessType.FIELD)
public class Person implements Serializable {

	private static final long serialVersionUID = -4536802274445773476L;

	@JsonProperty("firstName")
	@XmlElement(name = "firstName")
	private String firstName;

	@JsonProperty("lastName")
	@XmlElement(name = "lastName")
	private String lastName;

	@JsonProperty("active")
	@XmlElement(name = "active")
	private boolean active;

	@XmlElement(name = "organization")
	@JsonProperty("organization")
	private String organization;


	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

  @Override
  public String toString() {
    return "Person [firstName=" + firstName + ", lastName=" + lastName + ", active=" + active + ", organization="
        + organization + "]";
  }

}