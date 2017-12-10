package in.ravi.springsample.restsecuritybasic.service;

import in.ravi.springsample.restsecuritybasic.dao.UserDao;
import in.ravi.springsample.restsecuritybasic.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao dao;

	public List<User> findAllUsers() {
		return dao.findAll();
	}
	
	public User findById(long id) {
		return dao.find(id);
	}
	
	public void saveUser(User user) {
		dao.persist(user);
	}

	public void updateUser(User user) {
		dao.update(user);
	}

	public void deleteUserById(long id) {
		dao.delete(id);
	}

	public boolean isUserExist(User user) {
		return findById(user.getId())!=null;
	}
	
}
