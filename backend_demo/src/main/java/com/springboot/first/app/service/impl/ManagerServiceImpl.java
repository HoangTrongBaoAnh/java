package com.springboot.first.app.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.springboot.first.app.exception.ResourceNotFoundException;
import com.springboot.first.app.model.Employee;
import com.springboot.first.app.model.Manager;
import com.springboot.first.app.repository.EmployeeRepository;
import com.springboot.first.app.repository.ManagerRepository;
import com.springboot.first.app.service.ManagerService;

@Service
public class ManagerServiceImpl implements ManagerService{
	private ManagerRepository managerReposistory;
	private EmployeeRepository employeeRepository;
	
	public ManagerServiceImpl(ManagerRepository managerReposistory,EmployeeRepository employeeRepository) {
		super();
		this.managerReposistory = managerReposistory;
		this.employeeRepository = employeeRepository;
	}

	@Override
	public Manager saveManager(Manager manager) {
		//ManagerReposistory.save(manager);
		// TODO Auto-generated method stub
		return managerReposistory.save(manager);
	}

	@Override
	public List<Manager> getManagers() {
		// TODO Auto-generated method stub
		return managerReposistory.findAll();
	}

	@Override
	public Manager editManager(Manager manager, long id) {
		Manager manager2 = managerReposistory.findById(id).orElseThrow(()->new ResourceNotFoundException("Manager", "id", id));
		manager2.setPosition(manager.getPosition());
		// TODO Auto-generated method stub
		managerReposistory.save(manager2);
		return manager2;
	}

	@Override
	public void deleteManager(long id) {
		Manager manager = managerReposistory.findById(id).orElseThrow(()->new ResourceNotFoundException("Manager", "id", id));
		//manager.setEmployees(null);
		//manager.getEmployees().clear();
		//List<Employee> tEmployees = manager.getEmployees();
//		for(Employee employee : manager.getEmployees()) {
//			//manager.getEmployees().remove(employee);
//			Employee toBeRemoved = employee;
//			//manager.getEmployees().remove(toBeRemoved);
//			toBeRemoved.setManager(null);
//			//employeeRepository.save(toBeRemoved);
//			//EmployeeRepository employeeRepository+
//		}
		//manager.getEmployees().clear();
		
//		manager.setEmployees(tEmployees);
		managerReposistory.deleteById(id);
		// TODO Auto-generated method stub
		
	}

	@Override
	public Manager getSingleManager(long id) {
		// TODO Auto-generated method stub
		return managerReposistory.findById(id).orElseThrow(()->new ResourceNotFoundException("Manager", "id", id));
	}

}
