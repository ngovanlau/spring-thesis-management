package com.thesisSpringApp.repository.repositoryimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.thesisSpringApp.pojo.Score;
import com.thesisSpringApp.pojo.Thesis;
import com.thesisSpringApp.pojo.ThesisCommitteeRate;
import com.thesisSpringApp.pojo.ThesisUser;
import com.thesisSpringApp.repository.ThesisRepository;
import com.thesisSpringApp.service.ScoreService;
import com.thesisSpringApp.service.ThesisCommitteeRateService;
import com.thesisSpringApp.service.ThesisUserService;

@Repository
@Transactional
@PropertySource("classpath:config.properties")
public class ThesisRepositoryImpl implements ThesisRepository {

	private LocalSessionFactoryBean factory;
	private Environment env;
	private ThesisUserService thesisUserService;
	private ThesisCommitteeRateService committeeRateService;
	private ScoreService scoreService;

	@Autowired
	public ThesisRepositoryImpl(LocalSessionFactoryBean factory, Environment env,
			ThesisUserService thesisUserService, ThesisCommitteeRateService committeeRateService,
			ScoreService scoreService) {
		super();
		this.factory = factory;
		this.env = env;
		this.thesisUserService = thesisUserService;
		this.committeeRateService = committeeRateService;
		this.scoreService = scoreService;
	}

	@Override
	public void saveAndUpdateThesis(Thesis thesis) {
		Session session = factory.getObject().getCurrentSession();
		if (thesis.getId() != null && thesis.getId() > 0)
			session.update(thesis);
		else
			session.save(thesis);
	}


	@Override
	public Thesis getThesisById(int id) {
		Session session = factory.getObject().getCurrentSession();
		Query query = session.createNamedQuery("Thesis.findById");

		query.setParameter("id", id);

		return (Thesis) query.getSingleResult();
	}

	@Override
	public List<Thesis> getAllThesis(Map<String, String> params) {
		Session session = factory.getObject().getCurrentSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Thesis> criteriaQuery = criteriaBuilder.createQuery(Thesis.class);
		Root<Thesis> root = criteriaQuery.from(Thesis.class);

		criteriaQuery.select(root);
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("updateDate")));

		List<Predicate> predicates = new ArrayList<>();
		String search = params.get("search");
		if (search != null && !search.isEmpty()) {
			predicates.add(criteriaBuilder.like(root.get("name"), String.format("%%%s%%", search)));
		}

		criteriaQuery.where(predicates.toArray(Predicate[]::new));
		Query query = session.createQuery(criteriaQuery);

		String page = params.get("page");
		if (page != null && !page.isEmpty()) {
			int pageSize = Integer.parseInt(env.getProperty("theses.pageSize").toString());
			int start = (Integer.parseInt(page) - 1)  * pageSize;
			query.setFirstResult(start);
			query.setMaxResults(pageSize);
		}
		return (List<Thesis>) query.getResultList();
	}

	@Override
	public void deleteThesisById(int id) {
		Thesis thesis = this.getThesisById(id);
		Session session = factory.getObject().getCurrentSession();
		if (thesis != null) {
			List<ThesisUser> thesisUser = thesisUserService.getUserByThesis(thesis);
			if (thesisUser != null)
				for (int i = 0; i < thesisUser.size(); i++)
					session.delete(thesisUser.get(i));
			ThesisCommitteeRate thesisCommitteeRate = this.committeeRateService
					.getThesisCommitteeRateByThesisId(id);
			if (thesisCommitteeRate != null)
				session.delete(thesisCommitteeRate);
			List<Score> scores = scoreService.getScoresByThesisId(id);
			if (scores != null)
				for (int i = 0; i < scores.size(); i++)
					session.delete(scores.get(i));

		}
		session.delete(thesis);
	}

}
