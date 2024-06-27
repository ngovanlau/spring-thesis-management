package com.thesisSpringApp.repository.repositoryimpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import com.thesisSpringApp.pojo.Thesis;
import com.thesisSpringApp.repository.StatsRepository;

@Repository
@Transactional
public class StatsRepositoryImpl implements StatsRepository {

	@Autowired
	private LocalSessionFactoryBean factory;

	@Override
	public List<Object[]> statsThesisByYear(int year) {
		Session session = factory.getObject().getCurrentSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root rThesis = criteriaQuery.from(Thesis.class);

		criteriaQuery.multiselect(rThesis.get("name"), rThesis.get("score"));

		List<Predicate> predicates = new ArrayList<>();

		if (year > 0) {
			predicates.add(criteriaBuilder.equal(
					criteriaBuilder.function("YEAR", Integer.class, rThesis.get("createDate")),
					year));
		}

		criteriaQuery.where(predicates.toArray(Predicate[]::new)); // nhung dieu kien where

		Query query = session.createQuery(criteriaQuery);

		return query.getResultList();
	}

	@Override
	public List<Object[]> statsFrequencyJoinedThesisByYear(int year) {
		Session session = factory.getObject().getCurrentSession();
//		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
//		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
//		Root rThesis = criteriaQuery.from(Thesis.class);
//		Root rThesisUser = criteriaQuery.from(ThesisUser.class);
//		Root rFaculty = criteriaQuery.from(Faculty.class);
//		Root rUser = criteriaQuery.from(User.class);
//		Join<Thesis, ThesisUser> thesisUserJoin = rThesis.join("thesisUserList");
//		Join<ThesisUser, User> userJoin = thesisUserJoin.join("userId");
//		Join<User, Faculty> facultyJoin = userJoin.join("facultyId");
//		criteriaQuery.multiselect(facultyJoin.get("name"),
//				criteriaBuilder.count(facultyJoin.get("id")));
//		List<Predicate> predicates = new ArrayList<>();
//		if (year > 0) {
//			predicates.add(criteriaBuilder.equal(criteriaBuilder.function("YEAR", Integer.class,rThesis.get("createDate")),year));
//		}
//		criteriaQuery.where(predicates.toArray(Predicate[]::new)); 
//		criteriaQuery.groupBy(facultyJoin.get("name"));

		String hql = "select f.name as name, count(f.id) as count " +
				"from User u " +
				"inner join u.facultyId f " +
				"inner join u.thesisUserList tu " +
				"inner join tu.thesisId t " +
				"where year(t.createDate) = :year and u.roleId.id = 2" +
				"group by f.name";


		Query query = session.createQuery(hql);
		query.setParameter("year", year);

		return query.getResultList();
	}

}
