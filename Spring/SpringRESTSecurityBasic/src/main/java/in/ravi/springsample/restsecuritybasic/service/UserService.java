package in.ravi.springsample.restsecuritybasic.service;

import in.ravi.springsample.restsecuritybasic.model.User;

import java.util.List;


public interface UserService {
	
	User findById(long id);

	void saveUser(User user);
	
	void updateUser(User user);
	
	void deleteUserById(long id);

	List<User> findAllUsers(); 

	boolean isUserExist(User user);
	
}
