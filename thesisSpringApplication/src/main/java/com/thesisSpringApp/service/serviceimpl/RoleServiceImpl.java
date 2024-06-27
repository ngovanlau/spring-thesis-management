package com.thesisSpringApp.service.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thesisSpringApp.pojo.Role;
import com.thesisSpringApp.repository.RoleRepository;
import com.thesisSpringApp.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Role getRoleById(int id) {
		return roleRepository.getRoleById(id);
	}

	@Override
	public List<Role> getAllRoles() {
		return roleRepository.getAllRoles();
	}

	@Override
	public Role getRoleByName(String name) {
		return roleRepository.getRoleByName(name);
	}

}
