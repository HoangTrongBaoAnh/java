package com.springboot.first.app.service;

import java.util.List;

import com.springboot.first.app.model.Manager;

public interface ManagerService {
	Manager saveManager(Manager manager);
	
	List<Manager> getManagers();
	
	Manager getSingleManager(long id);
	
	Manager editManager(Manager manager, long id);
	
	void deleteManager(long id);
}
