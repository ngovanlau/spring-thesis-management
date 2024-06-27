package com.thesisSpringApp.repository;

import java.util.List;

import com.thesisSpringApp.pojo.Role;

public interface RoleRepository {
	Role getRoleById(int id);

	Role getRoleByName(String name);

	List<Role> getAllRoles();
}
