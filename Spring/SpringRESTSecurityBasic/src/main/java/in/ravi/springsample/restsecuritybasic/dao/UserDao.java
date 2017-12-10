package in.ravi.springsample.restsecuritybasic.dao;

import in.ravi.springsample.restsecuritybasic.model.User;

import java.util.List;

public interface UserDao {
    void persist(User user);

    void update(User user);

    void delete(long id);

    User find(long id);

    List<User> findAll();
}
