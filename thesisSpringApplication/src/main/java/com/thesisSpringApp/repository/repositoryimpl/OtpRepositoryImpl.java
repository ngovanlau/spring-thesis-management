package com.thesisSpringApp.repository.repositoryimpl;

import com.thesisSpringApp.pojo.Otp;
import com.thesisSpringApp.pojo.User;
import com.thesisSpringApp.repository.OtpRepository;
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

@Repository
@Transactional
public class OtpRepositoryImpl implements OtpRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public void saveOrUpdateOtp(Otp otp) {
        Session session = factory.getObject().getCurrentSession();

        if (otp.getId() != null && otp.getId() > 0) {
            session.update(otp);
        } else {
            session.save(otp);
        }
    }

    @Override
    public void deleteOtp(Otp otp) {
        Session session = factory.getObject().getCurrentSession();
        session.delete(otp);
    }

    @Override
    public Otp getOtpByUser(User user) {
        Session session = factory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Otp> criteriaQuery = criteriaBuilder.createQuery(Otp.class);
        Root<Otp> root = criteriaQuery.from(Otp.class);

        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("userId"), user.getId()));

        Query query = session.createQuery(criteriaQuery);

        try {
            return (Otp) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
