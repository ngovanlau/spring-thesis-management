package com.thesisSpringApp.repository.repositoryimpl;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.thesisSpringApp.pojo.Role;
import com.thesisSpringApp.repository.RoleRepository;

@Repository
@Transactional
public class RoleRepositoryImpl implements RoleRepository {

	@Autowired
	private LocalSessionFactoryBean factory;

	@Override
	public Role getRoleById(int id) {
		Session session = factory.getObject().getCurrentSession();
		Query query = session.getNamedQuery("Role.findById");
		query.setParameter("id", id);
		try {
			return (Role) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Role> getAllRoles() {
		Session session = factory.getObject().getCurrentSession();
		Query query = session.getNamedQuery("Role.findAll");
		return query.getResultList();
	}

	@Override
	public Role getRoleByName(String name) {
		Session session = factory.getObject().getCurrentSession();
		Query query = session.getNamedQuery("Role.findByName");
		query.setParameter("name", name);
		try {
			return (Role) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

}
