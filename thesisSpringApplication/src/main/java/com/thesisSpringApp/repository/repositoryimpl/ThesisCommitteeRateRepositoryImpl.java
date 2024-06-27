package com.thesisSpringApp.repository.repositoryimpl;


import com.thesisSpringApp.pojo.ThesisCommitteeRate;
import com.thesisSpringApp.repository.ThesisCommitteeRateRepository;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@Transactional
public class ThesisCommitteeRateRepositoryImpl implements ThesisCommitteeRateRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public ThesisCommitteeRate getThesisCommitteeRateByThesisId(int thesisId) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = s.getCriteriaBuilder();
        CriteriaQuery<ThesisCommitteeRate> criteriaQuery = criteriaBuilder.createQuery(ThesisCommitteeRate.class);
        Root<ThesisCommitteeRate> root = criteriaQuery.from(ThesisCommitteeRate.class);

        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("thesisId"), thesisId));
        Query query = s.createQuery(criteriaQuery);

        try {
            return (ThesisCommitteeRate) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<ThesisCommitteeRate> getCommitteeRatesByCommitteeId(int committeeId) {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<ThesisCommitteeRate> criteriaQuery = criteriaBuilder.createQuery(ThesisCommitteeRate.class);
        Root<ThesisCommitteeRate> root = criteriaQuery.from(ThesisCommitteeRate.class);

        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("committeeId"), committeeId));

        Query query = session.createQuery(criteriaQuery);

        return (List<ThesisCommitteeRate>) query.getResultList();
    }

    @Override
    public void saveAndUpdateThesisCommitteeRate(ThesisCommitteeRate thesisCommitteeRate) {
        Session session = factory.getObject().getCurrentSession();

        if (thesisCommitteeRate.getId() != null && thesisCommitteeRate.getId() > 0)
            session.update(thesisCommitteeRate);
        else
            session.save(thesisCommitteeRate);
    }
}
