package com.thesisSpringApp.repository.repositoryimpl;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.thesisSpringApp.pojo.Faculty;
import com.thesisSpringApp.repository.FacultyRepository;

@Repository
@Transactional
public class FacultyRepositoryImpl implements FacultyRepository {

	@Autowired
	private LocalSessionFactoryBean factoryBean;

	@Override
	public List<Faculty> findAllFaculties() {
		Session session = factoryBean.getObject().getCurrentSession();
		Query query = session.getNamedQuery("Faculty.findAll");
		return query.getResultList();
	}

	@Override
	public Faculty findFacultyById(int facultyId) {
		Session session = factoryBean.getObject().getCurrentSession();
		Query query = session.getNamedQuery("Faculty.findById");
		query.setParameter("id", facultyId);
		return (Faculty) query.getSingleResult();
	}

}
