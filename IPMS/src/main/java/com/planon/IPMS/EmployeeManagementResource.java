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

import employeeManagement.EmployeeDetails;
import properties.ApplicationProperties;


@Path("EmployeeManagement")
public class EmployeeManagementResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("GetAllDetails/{requiredDetails}")
	public ArrayList<EmployeeDetails> getAllDetails(@PathParam("requiredDetails") String typeOfDetails) throws IOException {
		
		String JsonStringFromProperties = ApplicationProperties.getInstance().getApplicationProperties().getProperty("employeeDetails");
		Type type = TypeToken.getParameterized(ArrayList.class, EmployeeDetails.class).getType();
		ArrayList<EmployeeDetails> empDetails = new Gson().fromJson(JsonStringFromProperties, type);
		if (typeOfDetails.equals("3"))
			return empDetails;
		else if (typeOfDetails.equals("2")) {
			ArrayList<EmployeeDetails> empPersonalDetails = new ArrayList<EmployeeDetails>();
			for (int i = 0; i < empDetails.size(); i++) {
//	    		empPersonalDetails.add(i,employeeDetails.setEmpPersonalDetails(empDetails.get(i).getEmpName(),empDetails.get(i).getEmpEmailId(),empDetails.get(i).getEmpBloodGroup(),empDetails.get(i).getEmpDOB()));
				empPersonalDetails
						.add(new EmployeeDetails(empDetails.get(i).getEmpName(),empDetails.get(i).getEmpId(), empDetails.get(i).getEmpEmailId(),
								empDetails.get(i).getEmpBloodGroup(), empDetails.get(i).getEmpDOB()));
			}
			return empPersonalDetails;
		} else if (typeOfDetails.equals("1")) {
			ArrayList<EmployeeDetails> empProfessionalDetails = new ArrayList<EmployeeDetails>();
			for (int i = 0; i < empDetails.size(); i++) {
				empProfessionalDetails
						.add(new EmployeeDetails(empDetails.get(i).getEmpId(), empDetails.get(i).getEmpJoinDate(),
								empDetails.get(i).getEmpReportingTo(), empDetails.get(i).getEmpTeam(),
								empDetails.get(i).getEmpDesignation(), empDetails.get(i).getEmpDeptId()));
			}
			return empProfessionalDetails;
		} else
			return null;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("GetEmpDetails/{requiredDetails}/{id}")
	public EmployeeDetails getEmpDetails(@PathParam("requiredDetails") String typeOfDetails, @PathParam("id") String empid)
			throws IOException {
		ArrayList<EmployeeDetails> empDetails = getAllDetails(typeOfDetails);
		System.out.println(empDetails.toString());
		if (typeOfDetails.equals("3")) {
			for (int j = 0; j < empDetails.size(); j++) {
				if (empDetails.get(j).getEmpId().equalsIgnoreCase(empid)) {
					return new EmployeeDetails(empDetails.get(j).getEmpName(), empDetails.get(j).getEmpId(),
							empDetails.get(j).getEmpEmailId(), empDetails.get(j).getEmpBloodGroup(),
							empDetails.get(j).getEmpDOB(), empDetails.get(j).getEmpJoinDate(),
							empDetails.get(j).getEmpReportingTo(), empDetails.get(j).getEmpTeam(),
							empDetails.get(j).getEmpDesignation(), empDetails.get(j).getEmpDeptId());
				}
			}
		} else if (typeOfDetails.equals("2")) {
			for (int j = 0; j < empDetails.size(); j++) {
				if (empDetails.get(j).getEmpId().equalsIgnoreCase(empid)) {
					return new EmployeeDetails(empDetails.get(j).getEmpName(), empDetails.get(j).getEmpEmailId(),empDetails.get(j).getEmpId(),
							empDetails.get(j).getEmpBloodGroup(), empDetails.get(j).getEmpDOB());
				}
			}
		} else if (typeOfDetails.equals("1")) {
			for (int j = 0; j < empDetails.size(); j++) {
				if (empDetails.get(j).getEmpId().equalsIgnoreCase(empid)) {
					return new EmployeeDetails(empDetails.get(j).getEmpId(), empDetails.get(j).getEmpJoinDate(),
							empDetails.get(j).getEmpReportingTo(), empDetails.get(j).getEmpTeam(),
							empDetails.get(j).getEmpDesignation(), empDetails.get(j).getEmpDeptId());
				}
			}
		} 
		return null;
	}
}
