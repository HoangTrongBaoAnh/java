package com.springboot.first.app.service;

import java.util.List;

import com.springboot.first.app.DTO.employeeDTO;
import com.springboot.first.app.model.Employee;


public interface EmployeeService {
	Employee savEmployee(employeeDTO employee);
	
	List<Employee> getEmployees(int page);
	
	Employee getEmployeeById(long id);
	
	Employee editEmployee(employeeDTO employee,long id);
	
	void deleteEmployee(long id);

}
