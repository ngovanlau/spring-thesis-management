package com.thesisSpringApp.service;

import java.util.List;

import com.thesisSpringApp.pojo.Role;

public interface RoleService {

	Role getRoleById(int id);

	List<Role> getAllRoles();

	Role getRoleByName(String name);

}
