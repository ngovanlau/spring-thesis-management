package com.thesisSpringApp.repository.repositoryimpl;

import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.thesisSpringApp.pojo.ThesisStatus;
import com.thesisSpringApp.repository.ThesisStatusRepository;

@Repository
@Transactional
public class ThesisStatusRepositoryImpl implements ThesisStatusRepository {

	@Autowired
	private LocalSessionFactoryBean factory;

	@Override
	public ThesisStatus getThesisStatusById(int id) {
		Session session = factory.getObject().getCurrentSession();

		Query query = session.createNamedQuery("ThesisStatus.findById");

		query.setParameter("id", id);

		return (ThesisStatus) query.getSingleResult();
	}

}
