package com.thesisSpringApp.repository.repositoryimpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
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

import com.thesisSpringApp.pojo.Score;
import com.thesisSpringApp.repository.ScoreRepository;

@Repository
@Transactional
public class ScoreRepositoryImpl implements ScoreRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public void saveAndUpdateScore(Score score) {
        Session session = factory.getObject().getCurrentSession();

        if (score.getId() != null && score.getId() > 0)
            session.update(score);
        else
            session.save(score);
    }

    @Override
    public Score getScore(int thesisId, int committeeUserId, int criteriaId) {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Score> criteriaQuery = criteriaBuilder.createQuery(Score.class);
        Root<Score> root = criteriaQuery.from(Score.class);

        criteriaQuery.select(root);

        List<Predicate> predicates = new ArrayList<>();
        if (thesisId > 0 && committeeUserId > 0 && criteriaId > 0) {
            predicates.add(criteriaBuilder.equal(root.get("thesisId"), thesisId));
            predicates.add(criteriaBuilder.equal(root.get("committeeUserId"), committeeUserId));
            predicates.add(criteriaBuilder.equal(root.get("criteriaId"), criteriaId));
        }

        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(Predicate[]::new)));

        Query query = session.createQuery(criteriaQuery);

        try {
            return (Score) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }


    }

    @Override
    public List<Score> getScoresByThesisId(int thesisId) {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Score> criteriaQuery = criteriaBuilder.createQuery(Score.class);
        Root<Score> root = criteriaQuery.from(Score.class);

        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("thesisId"), thesisId));

        Query query = session.createQuery(criteriaQuery);

        return (List<Score>) query.getResultList();
    }

    @Override
    public List<Score> getScoreOfCommitteeUser(int thesisId, int committeeUserId) {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Score> criteriaQuery = criteriaBuilder.createQuery(Score.class);
        Root<Score> root = criteriaQuery.from(Score.class);

        criteriaQuery.select(root);

        List<Predicate> predicates = new ArrayList<>();
        if (thesisId > 0 && committeeUserId > 0) {
            predicates.add(criteriaBuilder.equal(root.get("thesisId"), thesisId));
            predicates.add(criteriaBuilder.equal(root.get("committeeUserId"), committeeUserId));
        }

        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(Predicate[]::new)));

        Query query = session.createQuery(criteriaQuery);

        return (List<Score>) query.getResultList();
    }

	@Override
	public List<Score> getScoreOfCommitteeUserId(int committeeUserId) {
		Session session = factory.getObject().getCurrentSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Score> criteriaQuery = criteriaBuilder.createQuery(Score.class);
		Root<Score> root = criteriaQuery.from(Score.class);

		criteriaQuery.select(root);

		List<Predicate> predicates = new ArrayList<>();
		if (committeeUserId > 0) {
			predicates.add(criteriaBuilder.equal(root.get("committeeUserId"), committeeUserId));
		}

		criteriaQuery.where(criteriaBuilder.and(predicates.toArray(Predicate[]::new)));

		Query query = session.createQuery(criteriaQuery);

		return (List<Score>) query.getResultList();
	}
}
