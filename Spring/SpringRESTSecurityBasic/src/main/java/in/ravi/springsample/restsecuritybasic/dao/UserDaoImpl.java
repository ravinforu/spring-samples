package in.ravi.springsample.restsecuritybasic.dao;

import in.ravi.springsample.restsecuritybasic.model.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component("userDao")
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void persist(User user) {
        em.persist(user);
    }

    @Override
    @Transactional
    public void update(User user) {
        em.merge(user);
    }

    @Override
    @Transactional
    public void delete(long id) {
        em.remove( find(id) );
    }

    @Override
    public User find(long id) {
        return em.find(User.class, id);
    }

    @Override
    public List<User> findAll() {
        return (List<User>) em.createNativeQuery("select * from my_users", User.class).getResultList();
    }
}
