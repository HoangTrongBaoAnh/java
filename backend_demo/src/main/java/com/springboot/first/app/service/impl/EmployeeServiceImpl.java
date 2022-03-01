package com.springboot.first.app.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springboot.first.app.DTO.employeeDTO;
import com.springboot.first.app.exception.ResourceNotFoundException;
import com.springboot.first.app.model.Employee;
import com.springboot.first.app.model.Manager;
import com.springboot.first.app.repository.EmployeeRepository;
import com.springboot.first.app.repository.ManagerRepository;
import com.springboot.first.app.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	private EmployeeRepository employeeRepository;
	private ManagerRepository managerRepository;
	
	public EmployeeServiceImpl(EmployeeRepository employeeRepository, ManagerRepository managerRepository) {
		super();
		this.employeeRepository = employeeRepository;
		this.managerRepository = managerRepository;
	}

	@Override
	public Employee savEmployee(employeeDTO employee) {
		// TODO Auto-generated method stub
		Employee nEmployee = new Employee();
		nEmployee.setFirstName(employee.getFirstName());
		nEmployee.setLastName(employee.getLastName());
		nEmployee.setEmail(employee.getEmail());
		
		Manager tManager = managerRepository.findById(employee.getManager_id()).orElseThrow(()->new ResourceNotFoundException("employee", "id", employee.getManager_id()));
		nEmployee.setManager(tManager);
		return employeeRepository.save(nEmployee);
	}

	@Override
	public List<Employee> getEmployees(int page) {
		// TODO Auto-generated method stub
		Pageable paging = PageRequest.of(page, 2);
		Page<Employee> ePage = employeeRepository.findAll(paging);
		List<Employee> employee = ePage.getContent();
		return employee;
	}

	@Override
	public Employee editEmployee(employeeDTO employee, long id) {
		Employee temp = employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("employee", "id", id));
		temp.setFirstName(employee.getFirstName());
		temp.setEmail(employee.getEmail());
		temp.setLastName(employee.getLastName());
		Manager tManager = temp.getManager();
		if(employee.getManager_id() != 0) {
			tManager = managerRepository.findById(employee.getManager_id()).orElseThrow(()->new ResourceNotFoundException("employee", "id", employee.getManager_id()));
			temp.setManager(tManager);

		}
		employeeRepository.save(temp);
		return temp;
		// TODO Auto-generated method stub
		//return null;
	}

	@Override
	public Employee getEmployeeById(long id) {
		// TODO Auto-generated method stub
//		Optional<Employee> employee = employeeRepository.findById(id);
//		if(employee.isPresent()) {
//			return employee.get();
//		}
//		throw new ResourceNotFoundException("employee", "id", id);
		
		return employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("employee", "id", id));
	}

	@Override
	public void deleteEmployee(long id) {
		// TODO Auto-generated method stub
		 employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("employee", "id", id));
		 employeeRepository.deleteById(id);

	}

}
