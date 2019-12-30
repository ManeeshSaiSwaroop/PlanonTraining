package com.planon.IPMS;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import projectManagement.Project;
import projectManagement.ProjectStatus;
import properties.ApplicationProperties;

@Path("ProjectManagement")
public class ProjectManagementResource {
	private List<Project> projectsList = null;
	private static Properties properties;
	ObjectMapper objectMapper = new ObjectMapper();
	private List<ProjectStatus> statusList = null;

	public ProjectManagementResource() throws IOException {
		properties = ApplicationProperties.getInstance().getApplicationProperties();
	}
	
	@Path("projects")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Project> getProjects() throws IOException {
		projectsList = objectMapper.readValue(properties.getProperty("projects"),
				new TypeReference<ArrayList<Project>>() {
				});

		return projectsList;
	}

	@Path("{ProjectID}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Project getProjectDetails(@PathParam("ProjectID") String ProjectID) throws IOException, ParseException {
		Project project = new Project();
		project.setProjectID(ProjectID);
		projectsList = objectMapper.readValue(properties.getProperty("projects"),
				new TypeReference<ArrayList<Project>>() {
				});

		for (Project projectIterator : projectsList) {
			if (projectIterator.getProjectID().equals(project.getProjectID())) {
				project = projectIterator;
				break;
			}
		}

		return project;
	}

	@Path("status")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Project> getProjectsByStatus(@QueryParam("statusId") int statusId) throws IOException {

		ProjectStatus projectStatus = new ProjectStatus();
		projectStatus.setStatusID(statusId);

		statusList = objectMapper.readValue(properties.getProperty("projectStatus"),
				new TypeReference<ArrayList<ProjectStatus>>() {
				});

//		for (ProjectStatus statusIterator : statusList) {
//			if (statusIterator.getStatusID() == statusId) {
//				projectStatus = statusIterator;
//				break;
//			}
//		}
		
		
		projectsList = objectMapper.readValue(properties.getProperty("projects"),
				new TypeReference<ArrayList<Project>>() {
				});

		List<Project> projectStatusList = new ArrayList<>();

		for (Project projectIterator : projectsList) {
			if (projectIterator.getProjectStatus().getStatusID() == statusId) {
				projectStatusList.add(projectIterator);
			}
		}

		return projectStatusList;

	}

}
