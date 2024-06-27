package com.thesisSpringApp.repository;

import java.util.List;

import com.thesisSpringApp.pojo.Role;
import com.thesisSpringApp.pojo.User;

public interface UserRepository {

	User getCurrentLoginUser();

	public boolean authUser(String username, String password);

	List<User> getAllUsers();

	void saveUser(User user);

	User getUserById(int id);

	List<User> getUserByRole(Role role);

	User getUserByUsername(String username);

	User getUserByEmail(String email);

	User getUserByUniversityId(String uId);

	List<User> getUsersByThesisId(int thesisId);

	void deleteUser(User user);

	User getUserByPhone(String phone);

	boolean isUserExistsByEmail(String email);

	boolean isUserExistsByPhone(String phone);

	boolean isUserExistsByUniversityId(String uid);

	List<User> getAllUsersPaginator(String p);

	Long countAllUser();
}
