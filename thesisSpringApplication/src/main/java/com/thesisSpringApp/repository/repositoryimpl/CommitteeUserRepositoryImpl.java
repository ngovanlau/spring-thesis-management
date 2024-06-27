package com.thesisSpringApp.repository.repositoryimpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.thesisSpringApp.pojo.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.thesisSpringApp.pojo.Committee;
import com.thesisSpringApp.pojo.CommitteeUser;
import com.thesisSpringApp.repository.CommitteeUserRepository;

@Repository
@Transactional
public class CommitteeUserRepositoryImpl implements CommitteeUserRepository {

	@Autowired
	private LocalSessionFactoryBean factoryBean;

	@Override
	public void saveCommitteeUser(CommitteeUser committeeUser) {
		Session session = factoryBean.getObject().getCurrentSession();
		if (committeeUser.getId() != null && committeeUser.getId() > 0)
			session.update(committeeUser);
		else
			session.save(committeeUser);
	}

	@Override
	public List<CommitteeUser> getCommitteeUserByCommittee(Committee committee) {
		Session session = factoryBean.getObject().getCurrentSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<CommitteeUser> criteriaQuery = criteriaBuilder
				.createQuery(CommitteeUser.class);
		Root rCommitteeUser = criteriaQuery.from(CommitteeUser.class);

		criteriaQuery.select(rCommitteeUser);
		List<Predicate> predicates = new ArrayList<>();

		if (committee != null)
			predicates.add(
					criteriaBuilder.equal(rCommitteeUser.get("committeeId"), committee.getId()));

		criteriaQuery.where(predicates.toArray(Predicate[]::new)); // nhung dieu kien where

		Query query = session.createQuery(criteriaQuery);

		return query.getResultList();
	}

	@Override
	public List<CommitteeUser> getAllUsersOfCommittee(int committeeId) {
		Session session = factoryBean.getObject().getCurrentSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<CommitteeUser> criteriaQuery = criteriaBuilder.createQuery(CommitteeUser.class);
		Root<CommitteeUser> root = criteriaQuery.from(CommitteeUser.class);

		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get("committeeId"), committeeId));

		Query query = session.createQuery(criteriaQuery);

		return (List<CommitteeUser>) query.getResultList();
	}

	@Override
	public CommitteeUser getCommitteeUser(int userId, int committeeId) {
		Session session = factoryBean.getObject().getCurrentSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<CommitteeUser> criteriaQuery = criteriaBuilder.createQuery(CommitteeUser.class);
		Root<CommitteeUser> root = criteriaQuery.from(CommitteeUser.class);

		criteriaQuery.select(root);
		List<Predicate> predicates = new ArrayList<>();

		if (userId > 0 && committeeId > 0) {
			predicates.add(criteriaBuilder.equal(root.get("userId"), userId));
			predicates.add(criteriaBuilder.equal(root.get("committeeId"), committeeId));
		}

		criteriaQuery.where(criteriaBuilder.and(predicates.toArray(Predicate[]::new)));

		Query query = session.createQuery(criteriaQuery);

		return (CommitteeUser) query.getSingleResult();
	}

	@Override
	public List<CommitteeUser> getCommitteeUserByUser(User user) {
		Session session = factoryBean.getObject().getCurrentSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<CommitteeUser> criteriaQuery = criteriaBuilder.createQuery(CommitteeUser.class);
		Root<CommitteeUser> root = criteriaQuery.from(CommitteeUser.class);

		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get("userId"), user.getId()));

		Query query = session.createQuery(criteriaQuery);
		return (List<CommitteeUser>) query.getResultList();
	}

}
