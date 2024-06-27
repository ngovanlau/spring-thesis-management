package com.thesisSpringApp.repository.repositoryimpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Validator;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.thesisSpringApp.pojo.Role;
import com.thesisSpringApp.pojo.User;
import com.thesisSpringApp.repository.UserRepository;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

	@Autowired
	private LocalSessionFactoryBean factory;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private Environment env;

	@Autowired
	private Validator validator;


	@Override
	public boolean authUser(String username, String password) {
		User user = this.getUserByUsername(username);

		return this.passwordEncoder.matches(password, user.getPassword());
	}

	@Override
	public User getCurrentLoginUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetails)
			return this.getUserByUsername((authentication.getName()));
		else
			throw new UsernameNotFoundException(
					"User not found with username: " + authentication.getName());
	}

	@Override
	public Long countAllUser() {
		Session session = factory.getObject().getCurrentSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root rUser = criteriaQuery.from(User.class);

		criteriaQuery.select(criteriaBuilder.count(rUser));
		Query query = session.createQuery(criteriaQuery);

		return (Long) query.getSingleResult();

	}


	@Override
	public List<User> getAllUsers() {
		Session session = factory.getObject().getCurrentSession();
		Query query = session.getNamedQuery("User.findAll");

		return query.getResultList();
	}

	@Override
	public List<User> getAllUsersPaginator(String p) {
		Session session = factory.getObject().getCurrentSession();
		Query query = session.getNamedQuery("User.findAll");

		if (p != null) {
			int pageSize = Integer.parseInt(env.getProperty("admin.pageSize").toString());
			int start = (Integer.parseInt(p) - 1) * pageSize;
			query.setFirstResult(start);
			query.setMaxResults(pageSize);
		}

		return query.getResultList();
	}

	@Override
	public void saveUser(User user) {
		Session session = factory.getObject().getCurrentSession();
		if (user.getId() != null && user.getId() > 0)
			session.update(user);
		else
			session.save(user);
	}

	@Override
	public User getUserById(int id) {
		Session session = factory.getObject().getCurrentSession();
		Query query = session.getNamedQuery("User.findById");
		query.setParameter("id", id);
		return (User) query.getSingleResult();
	}

	@Override
	public List<User> getUserByRole(Role role) {
		Session session = factory.getObject().getCurrentSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
		Root rUser = criteriaQuery.from(User.class);

		criteriaQuery.select(rUser);

		List<Predicate> predicates = new ArrayList<>();

		if (role != null) {
			predicates.add(criteriaBuilder.equal(rUser.get("roleId"), role.getId()));
		}

		criteriaQuery.where(predicates.toArray(Predicate[]::new));

		Query query = session.createQuery(criteriaQuery);

		return query.getResultList();

	}

	@Override
	public User getUserByUsername(String username) {
		Session session = factory.getObject().getCurrentSession();
		Query query = session.getNamedQuery("User.findByUsername");
		query.setParameter("username", username);
		try {
			return (User) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public User getUserByEmail(String email) {
		Session session = factory.getObject().getCurrentSession();
		Query query = session.getNamedQuery("User.findByEmail");
		query.setParameter("email", email);

		List<User> users = query.getResultList();
		if (users.isEmpty()) {
			return null;
		} else {
			return users.get(0);
		}
	}

	@Override
	public User getUserByUniversityId(String uId) {
		Session session = factory.getObject().getCurrentSession();
		Query query = session.getNamedQuery("User.findByUseruniversityid");
		query.setParameter("useruniversityid", uId);

		List<User> users = query.getResultList();
		if (users.isEmpty()) {
			return null;
		} else {
			return users.get(0);
		}
	}

	@Override
	public User getUserByPhone(String phone) {
		Session session = factory.getObject().getCurrentSession();
		Query query = session.getNamedQuery("User.findByPhone");
		query.setParameter("phone", phone);

		List<User> users = query.getResultList();
		if (users.isEmpty()) {
			return null;
		} else {
			return users.get(0);
		}
	}

	@Override
	public List<User> getUsersByThesisId(int thesisId) {
		Session session = factory.getObject().getCurrentSession();

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
		Root<User> root = criteriaQuery.from(User.class);

		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get("thesisId"), thesisId));

		Query query = session.createQuery(criteriaQuery);

		return (List<User>) query.getResultList();
	}

	@Override
	public void deleteUser(User user) {
		Session session = factory.getObject().getCurrentSession();
		session.delete(user);
	}

	@Override
	public boolean isUserExistsByEmail(String email) {
		User user = this.getUserByEmail(email);
		if (user != null)
			return true;
		return false;
	}

	@Override
	public boolean isUserExistsByPhone(String phone) {
		User user = this.getUserByPhone(phone);
		if (user != null)
			return true;
		return false;
	}

	@Override
	public boolean isUserExistsByUniversityId(String uid) {
		User user = this.getUserByUniversityId(uid);
		if (user != null)
			return true;
		return false;
	}



}
