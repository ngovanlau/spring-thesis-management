package com.thesisSpringApp.service;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.thesisSpringApp.pojo.Role;
import com.thesisSpringApp.pojo.User;

public interface UserService extends UserDetailsService {

	User getCurrentLoginUser();

	public boolean authUser(String username, String password);

	List<User> getAllUsers();

	void saveUser(User user);

	void saveInitUserAndSendMail(User user) throws MessagingException;

	User getUserByUsername(String username);

	User getUserById(int id);

	void setCloudinaryField(User user);

	List<User> getUserByRoleName(Role role);

	User getUserByEmail(String email);

	User getUserByUniversityId(String uId);

	List<User> getUsersByThesisId(int thesisId);

	void deleteUser(User user);

	String generateRandomString(int length);

	User getUserByPhone(String phone);

	boolean isUserExistsByEmail(String value);

	boolean isUserExistsByPhone(String value);

	boolean isUserExistsByUniversityId(String value);

	List<User> getAllUsersPaginator(String p);

	Long countAllUser();

}
