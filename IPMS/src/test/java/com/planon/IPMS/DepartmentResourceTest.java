package com.planon.IPMS;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import properties.ApplicationProperties;

import static io.restassured.RestAssured.get;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Properties;

public class DepartmentResourceTest {
	
	static Properties properties;

	@BeforeAll
	public static void init() throws IOException {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
		properties = ApplicationProperties.getInstance().getApplicationProperties();
	}
	
	@Nested
	class testGetDepartments {
		@Test
		void testForAllDepartments() {
			assertEquals(get("IPMS/webapi/DepartmentManagement").asString(), properties.getProperty("departments"));
		}
	}
	
	@Nested
	class testGetDepartmentByID {
		
		@Test
		void testForDepartment() {
			get("IPMS/webapi/DepartmentManagement/DepartmentID?departmentID=1").then().body("departmentID", equalTo("1")).body("departmentName", equalTo("ICT"));
		}
	}
}
