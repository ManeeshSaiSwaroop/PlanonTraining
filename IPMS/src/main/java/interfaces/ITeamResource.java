package interfaces;

import java.io.IOException;
import java.util.ArrayList;

import departmentManagement.Team;


public interface ITeamResource {
	public ArrayList<Team> getTeams() throws IOException;
	public Team getTeamByID(String teamID) throws IOException;
	public ArrayList<Team> getAllTeamsOfParticularDepartment(String departmentID) throws IOException;
}
