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

import departmentManagement.Department;
import interfaces.IDepartmentResource;
import properties.ApplicationProperties;


@Path("DepartmentManagement")
@Produces(MediaType.APPLICATION_JSON)
public class DepartmentResource implements IDepartmentResource{

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Department> getDepartments() throws IOException  {
    	Properties properties = ApplicationProperties.getInstance().getApplicationProperties();
    	String jsonArray = properties.getProperty("departments");
    	Type type = TypeToken.getParameterized(ArrayList.class, Department.class).getType();
    	ArrayList<Department> departments = new Gson().fromJson(jsonArray, type);
    	return departments;
    }

    @GET
    @Path("/DepartmentID")
    @Produces(MediaType.APPLICATION_JSON)
	public Department getDepartmentByID(@QueryParam("departmentID") String departmentID) throws IOException {
    	Properties properties = ApplicationProperties.getInstance().getApplicationProperties();
    	String jsonArray = properties.getProperty("departments");
    	Type type = TypeToken.getParameterized(ArrayList.class, Department.class).getType();
    	ArrayList<Department> departments = new Gson().fromJson(jsonArray, type);
    	for(int i=0; i<departments.size(); i++) {
    		if(departments.get(i).getDepartmentID().equals(departmentID)) {
    			return departments.get(i);
    		}
    	}
    	return null;
	}
    
}
