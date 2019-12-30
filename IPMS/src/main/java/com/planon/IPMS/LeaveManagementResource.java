package com.planon.IPMS;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import leaveManagement.AppliedLeaveDetails;
import leaveManagement.EmployeeLeaveBalanceDetails;
import properties.ApplicationProperties;

@Path("LeaveManagement")
public class LeaveManagementResource {

	// get applied leave details of a particular employee based on his empId
	@Path("appliedLeave/{empId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public AppliedLeaveDetails getAppliedLeaveDetails(@PathParam("empId") String empId) {
		AppliedLeaveDetails AppliedLeaveDetails = new AppliedLeaveDetails();
		ArrayList<AppliedLeaveDetails> appliedleave = new ArrayList<AppliedLeaveDetails>();
		try {
			appliedleave = getAllAppliedLeaveDetails();
			for (int i = 0; i < appliedleave.size(); i++) {

				if (appliedleave.get(i).getEmpId().equalsIgnoreCase(empId)) {
					AppliedLeaveDetails.setEmpId(empId);
					AppliedLeaveDetails.setLeaveDescription(appliedleave.get(i).getLeaveDescription());
					AppliedLeaveDetails.setLeaveType(appliedleave.get(i).getLeaveType());
					AppliedLeaveDetails.setStatus(appliedleave.get(i).getStatus());
					AppliedLeaveDetails.setFrom(appliedleave.get(i).getFrom());
					AppliedLeaveDetails.setTo(appliedleave.get(i).getTo());
					break;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return AppliedLeaveDetails;
	}

	// get applied leave details of all the employees
	@Path("allAppliedLeave")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<AppliedLeaveDetails> getAllAppliedLeaveDetails() throws IOException {
		ArrayList<AppliedLeaveDetails> appliedleave = new ArrayList<AppliedLeaveDetails>();

		String jsonArray = ApplicationProperties.getInstance().getApplicationProperties().getProperty("appliedLeave");
		System.out.println(jsonArray);
		Type type = TypeToken.getParameterized(ArrayList.class, AppliedLeaveDetails.class).getType();
		appliedleave = new Gson().fromJson(jsonArray, type);

		return appliedleave;
	}

	// get the leave balance of a particular employee based on his empId
	@Path("leaveBalanceDetails/{empId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public EmployeeLeaveBalanceDetails getEmployeeLeaveBalanceDetails(@PathParam("empId") String empId) {
		EmployeeLeaveBalanceDetails EmployeeLeaveBalanceDetails = new EmployeeLeaveBalanceDetails();
		ArrayList<EmployeeLeaveBalanceDetails> leavedetails = new ArrayList<EmployeeLeaveBalanceDetails>();
		try {
			leavedetails = getAllEmployeesLeaveBalanceDetails();
			for (int i = 0; i < leavedetails.size(); i++) {

				if (leavedetails.get(i).getEmpId().equalsIgnoreCase(empId)) {
					EmployeeLeaveBalanceDetails.setEmpId(empId);
					EmployeeLeaveBalanceDetails.setLop(leavedetails.get(i).getLop());
					EmployeeLeaveBalanceDetails.setOptionalLeave(leavedetails.get(i).getOptionalLeave());
					EmployeeLeaveBalanceDetails.setOtherLeave(leavedetails.get(i).getOtherLeave());
					EmployeeLeaveBalanceDetails.setBalance(leavedetails.get(i).getBalance());

					break;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return EmployeeLeaveBalanceDetails;
	}

	// get leave balance details of all the employee
	@Path("allLeaveDetails")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<EmployeeLeaveBalanceDetails> getAllEmployeesLeaveBalanceDetails() throws IOException {
		ArrayList<EmployeeLeaveBalanceDetails> leavedetails = new ArrayList<EmployeeLeaveBalanceDetails>();
		String jsonArray = ApplicationProperties.getInstance().getApplicationProperties().getProperty("appliedLeave");
		Type type = TypeToken.getParameterized(ArrayList.class, EmployeeLeaveBalanceDetails.class).getType();
		leavedetails = new Gson().fromJson(jsonArray, type);
		return leavedetails;
	}
}
