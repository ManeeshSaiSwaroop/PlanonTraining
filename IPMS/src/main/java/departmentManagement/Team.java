package departmentManagement;

public class Team {
	private String teamID;
	private String teamName;
	private Department department;
	private int totalNumberOfCompletedProjects;
	
	public String getTeamID() {
		return teamID;
	}
	public void setTeamID(String teamID) {
		this.teamID = teamID;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public int getTotalNumberOfCompletedProjects() {
		return totalNumberOfCompletedProjects;
	}
	public void setTotalNumberOfCompletedProjects(int totalNumberOfCompletedProjects) {
		this.totalNumberOfCompletedProjects = totalNumberOfCompletedProjects;
	}
	
	public Team()
	{
		
	}
}
