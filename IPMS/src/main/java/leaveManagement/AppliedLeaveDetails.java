package leaveManagement;

import java.util.Date;

public class AppliedLeaveDetails {
	
	private String empId;
	private String leaveType;
	private Date from;
	private Date to;
	private String status;
	private String leaveDescription;
	public String getLeaveDescription() {
		return leaveDescription;
	}
	public void setLeaveDescription(String leaveDescription) {
		this.leaveDescription = leaveDescription;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	public Date getFrom() {
		return from;
	}
	public void setFrom(Date from) {
		this.from = from;
	}
	public Date getTo() {
		return to;
	}
	public void setTo(Date to) {
		this.to = to;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "AppliedLeave [empId=" + empId + ", leaveType=" + leaveType + ", from=" + from + ", to=" + to
				+ ", status=" + status + "]";
	}
	public AppliedLeaveDetails() {
		super();
		this.empId = empId;
		this.leaveType = leaveType;
		this.from = from;
		this.to = to;
		this.status = status;
	}
}

