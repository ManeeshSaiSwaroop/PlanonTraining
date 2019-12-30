package employeeManagement;

import java.util.Date;

public class EmployeeDetails {
	private String empName;
	private String empId;
	private String empEmailId;
	private String empBloodGroup;
	private Date empDOB;
	private Date empJoinDate;
	private String empReportingTo;
	private String empTeam;
	private String empDesignation;
	private int empDeptId;

	public  EmployeeDetails(String empName,String empId, String empEmailId, String empBloodGroup, Date empDOB) {
		this.empName = empName;
		this.empEmailId = empEmailId;
		this.empBloodGroup = empBloodGroup;
		this.empDOB = empDOB;
		this.empId = empId;
	}

	public  EmployeeDetails(String empId,Date empJoinDate,String empReportingTo,String empTeam,String empDesignation,int empDeptId) {
		this.empId = empId;
		this.empJoinDate = empJoinDate;
		this.empReportingTo = empReportingTo;
		this.empTeam = empTeam;
		this.empDesignation = empDesignation;
		this.empDeptId = empDeptId;
	}

	public EmployeeDetails(String empName, String empId, String empEmailId, String empBloodGroup, Date empDOB,
			Date empJoinDate, String empReportingTo, String empTeam, String empDesignation, int empDeptId) {
		super();
		this.empName = empName;
		this.empId = empId;
		this.empEmailId = empEmailId;
		this.empBloodGroup = empBloodGroup;
		this.empDOB = empDOB;
		this.empJoinDate = empJoinDate;
		this.empReportingTo = empReportingTo;
		this.empTeam = empTeam;
		this.empDesignation = empDesignation;
		this.empDeptId = empDeptId;
	}


	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmpEmailId() {
		return empEmailId;
	}

	public void setEmpEmailId(String empEmailId) {
		this.empEmailId = empEmailId;
	}

	public String getEmpBloodGroup() {
		return empBloodGroup;
	}

	public void setEmpBloodGroup(String empBloodGroup) {
		this.empBloodGroup = empBloodGroup;
	}

	public Date getEmpDOB() {
		return empDOB;
	}

	public void setEmpDOB(Date empDOB) {
		this.empDOB = empDOB;
	}

	public Date getEmpJoinDate() {
		return empJoinDate;
	}

	public void setEmpJoinDate(Date empJoinDate) {
		this.empJoinDate = empJoinDate;
	}

	public String getEmpReportingTo() {
		return empReportingTo;
	}

	public void setEmpReportingTo(String empReportingTo) {
		this.empReportingTo = empReportingTo;
	}

	public String getEmpTeam() {
		return empTeam;
	}

	public void setEmpTeam(String empTeam) {
		this.empTeam = empTeam;
	}

	public String getEmpDesignation() {
		return empDesignation;
	}

	public void setEmpDesignation(String empDesignation) {
		this.empDesignation = empDesignation;
	}

	public int getEmpDeptId() {
		return empDeptId;
	}

	public void setEmpDeptId(int empDeptId) {
		this.empDeptId = empDeptId;
	}

	@Override
	public String toString() {
		return "employeeDetails [empName=" + empName + ", empId=" + empId + ", empEmailId=" + empEmailId
				+ ", empBloodGroup=" + empBloodGroup + ", empDOB=" + empDOB + ", empJoinDate=" + empJoinDate
				+ ", empReportingTo=" + empReportingTo + ", empTeam=" + empTeam + ", empDesignation=" + empDesignation
				+ ", empDeptId=" + empDeptId + "]";
	}
}

//private static employeeDetails employee = new employeeDetails();
//
//public static employeeDetails setEmpPersonalDetails(String empName, String empEmailId, String empBloodGroup, Date empDOB) {
//	employeeDetails employee = getInstance();
//	employee.setEmpName(empName);
//	employee.setEmpEmailId(empEmailId);
//	employee.setEmpBloodGroup(empBloodGroup);
//	employee.setEmpDOB(empDOB);
//	return employee;
//}
//
//public static employeeDetails setEmpProfessionalDetails(String empId,Date empJoinDate,String empReportingTo,String empTeam,String empDesignation,int empDeptId) {
//	employeeDetails employee = getInstance();
//	employee.setEmpId(empId);
//	employee.setEmpJoinDate(empJoinDate);
//	employee.setEmpReportingTo(empReportingTo);
//	employee.setEmpTeam(empTeam);	
//	employee.setEmpDesignation(empDesignation);
//	employee.setEmpDeptId(empDeptId);
//	return employee;
//}
//private employeeDetails() {
//}
//
//public static employeeDetails getInstance()
//{
//	return  employee;
//}

//Testing