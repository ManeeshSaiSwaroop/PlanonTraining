package projectManagement;

public class ProjectStatus {
	private int statusID;
	private String status;

	public int getStatusID() {
		return statusID;
	}

	public void setStatusID(int statusID) {
		this.statusID = statusID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ProjectStatus [statusID=" + statusID + ", status=" + status + "]";
	}

}
