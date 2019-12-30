package com.planon.IPMS;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Properties;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import departmentManagement.Team;
import interfaces.ITeamResource;
import properties.ApplicationProperties;

@Path("teams")
@Produces(MediaType.APPLICATION_JSON)
public class TeamResource implements ITeamResource{
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Team> getTeams() throws IOException  {
		Properties properties = ApplicationProperties.getInstance().getApplicationProperties();
    	String jsonArray = properties.getProperty("teams");
    	Type type = TypeToken.getParameterized(ArrayList.class, Team.class).getType();
    	ArrayList<Team> teams = new Gson().fromJson(jsonArray, type);
    	return teams;
    }

    @GET
    @Path("/TeamID")
    @Produces(MediaType.APPLICATION_JSON)
	public Team getTeamByID(@QueryParam("teamID") String teamID) throws IOException {
    	Properties properties = ApplicationProperties.getInstance().getApplicationProperties();
    	String jsonArray = properties.getProperty("teams");
    	Type type = TypeToken.getParameterized(ArrayList.class, Team.class).getType();
    	ArrayList<Team> teams = new Gson().fromJson(jsonArray, type);
    	for(int i=0; i<teams.size(); i++) {
    		if(teams.get(i).getTeamID().equals(teamID)) {
    			return teams.get(i);
    		}
    	}
    	return null;
	}

    @GET
    @Path("/DepartmentID")
    @Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Team> getAllTeamsOfParticularDepartment(@QueryParam("departmentID") String departmentID) throws IOException {
    	Properties properties = ApplicationProperties.getInstance().getApplicationProperties();
    	String jsonArray = properties.getProperty("teams");
    	Type type = TypeToken.getParameterized(ArrayList.class, Team.class).getType();
    	ArrayList<Team> teams = new Gson().fromJson(jsonArray, type);
    	ArrayList<Team> respectiveTeams = new ArrayList<Team>();
    	for(int i=0; i<teams.size(); i++) {
    		if(teams.get(i).getDepartment().getDepartmentID().equals(departmentID)) {
    			respectiveTeams.add(teams.get(i));
    		}
    	}
    	return respectiveTeams;
	}
}
