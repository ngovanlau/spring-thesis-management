package com.thesisSpringApp.repository.repositoryimpl;

import com.thesisSpringApp.pojo.Criteria;
import com.thesisSpringApp.repository.CriteriaRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class CriteriaRepositoryImpl implements CriteriaRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public Criteria getCriteriaById(int id) {
        Session session = factory.getObject().getCurrentSession();

        Query query = session.createNamedQuery("Criteria.findById");
        query.setParameter("id", id);

        return (Criteria) query.getSingleResult();
    }

    @Override
    public List<Criteria> getCriteriaList() {
        Session session = factory.getObject().getCurrentSession();

        Query query = session.createNamedQuery("Criteria.findAll");

        return (List<Criteria>) query.getResultList();
    }

    @Override
    public void saveAndUpdateCriteria(Criteria criteria) {
        Session session = factory.getObject().getCurrentSession();

        if ( criteria.getId() != null && criteria.getId() > 0)
            session.update(criteria);
        else
            session.save(criteria);
    }
}
