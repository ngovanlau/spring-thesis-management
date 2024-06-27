package com.thesisSpringApp.repository.repositoryimpl;

import com.thesisSpringApp.pojo.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.thesisSpringApp.pojo.Paymentvnpaydetail;
import com.thesisSpringApp.repository.PaymentvnpaydetailRepository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class PaymentvnpaydetailRepositoryImpl implements PaymentvnpaydetailRepository {

    @Autowired
    private LocalSessionFactoryBean factoryBean;

    @Override
    public void saveVnPay(Paymentvnpaydetail paymentvnpaydetail) {
        Session session = factoryBean.getObject().getCurrentSession();

        if (paymentvnpaydetail.getId() != null && paymentvnpaydetail.getId() > 0)
            session.update(paymentvnpaydetail);
        else
            session.save(paymentvnpaydetail);
    }

    @Override
    public Paymentvnpaydetail getPaymentVNPayDetail(String orderId, User user) {
        Session session = factoryBean.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Paymentvnpaydetail> criteriaQuery = criteriaBuilder.createQuery(Paymentvnpaydetail.class);
        Root<Paymentvnpaydetail> root = criteriaQuery.from(Paymentvnpaydetail.class);

        criteriaQuery.select(root);
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(criteriaBuilder.like(root.get("orderId"), orderId));
        predicates.add(criteriaBuilder.equal(root.get("userId"), user.getId()));

        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(Predicate[]::new)));

        Query query = session.createQuery(criteriaQuery);
        try {
            return (Paymentvnpaydetail) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

}
