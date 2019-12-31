package com.planon.IPMS;

import static io.restassured.RestAssured.get;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.CoreMatchers.equalTo;

import java.io.IOException;
import java.util.Properties;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import properties.ApplicationProperties;

public class TeamResourceTest {
	
	static Properties properties;

	@BeforeAll
	public static void init() throws IOException {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
		properties = ApplicationProperties.getInstance().getApplicationProperties();
	}
	
	@Nested
	class testGetTeams {
		@Test
		void testForAllTeams() throws IOException {
			assertEquals(get("IPMS/webapi/teams").asString(), properties.getProperty("teams"));
		}
	}
	
	@Nested
	class testGetTeamByID {
		@Test
		void testForTeam() {
			get("IPMS/webapi/teams/TeamID?teamID=1001").then().body("teamID", equalTo("1001")).body("teamName", equalTo("SRH"));
		}
	}
	
	@AfterAll
	public static void clean() {
		properties = null;
	}
}
