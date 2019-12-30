package interfaces;

import java.io.IOException;
import java.util.ArrayList;

import departmentManagement.Department;

public interface IDepartmentResource {

	 public ArrayList<Department> getDepartments() throws IOException;
	 public Department getDepartmentByID(String departmentID) throws IOException;
}
