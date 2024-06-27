package com.thesisSpringApp.repository.repositoryimpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.thesisSpringApp.pojo.Thesis;
import com.thesisSpringApp.pojo.ThesisUser;
import com.thesisSpringApp.pojo.User;
import com.thesisSpringApp.repository.ThesisUserRepository;

@Repository
@Transactional
public class ThesisUserRepositoryImpl implements ThesisUserRepository {

	@Autowired
	private LocalSessionFactoryBean factoryBean;

	@Override
	public void saveThesisUser(ThesisUser thesisUser, Thesis thesis, User user) {
		Session session = factoryBean.getObject().getCurrentSession();
		thesisUser.setThesisId(thesis);
		thesisUser.setUserId(user);

		if (thesisUser.getId() != null && thesisUser.getId() > 0)
			session.update(thesisUser);
		else
			session.save(thesisUser);
	}

	@Override
	public List<ThesisUser> getUserByThesis(Thesis thesis) {
		Session session = factoryBean.getObject().getCurrentSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<ThesisUser> criteriaQuery = criteriaBuilder.createQuery(ThesisUser.class);
		Root rThesisUser = criteriaQuery.from(ThesisUser.class);

		criteriaQuery.select(rThesisUser);
		List<Predicate> predicates = new ArrayList<>();

		if (thesis != null)
			predicates.add(
					criteriaBuilder.equal(rThesisUser.get("thesisId"), thesis.getId()));

		criteriaQuery.where(predicates.toArray(Predicate[]::new));

		Query query = session.createQuery(criteriaQuery);

		return query.getResultList();
	}

	@Override
	public List<ThesisUser> getThesisByUser(User user) {
		Session session = factoryBean.getObject().getCurrentSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<ThesisUser> criteriaQuery = criteriaBuilder.createQuery(ThesisUser.class);
		Root rThesisUser = criteriaQuery.from(ThesisUser.class);
		criteriaQuery.select(rThesisUser);
		List<Predicate> predicates = new ArrayList<>();

		if (user != null)
			predicates.add(
					criteriaBuilder.equal(rThesisUser.get("userId"), user.getId()));

		criteriaQuery.where(predicates.toArray(Predicate[]::new));

		Query query = session.createQuery(criteriaQuery);
		return query.getResultList();

	}



	@Override
	public List<ThesisUser> getThesisUser() {
		Session session = factoryBean.getObject().getCurrentSession();
		Query query = session.createNamedQuery("ThesisUser.findAll");

		return query.getResultList();
	}


}
