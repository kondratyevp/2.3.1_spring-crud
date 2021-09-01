package web.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<User> allUsers(){
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }
    public void add(User user){
        entityManager.persist(user);
    }

    public void delete(long id){
        entityManager.remove(getById(id));
    }
    public void update (int id, User updatedUser){
        User userToBeUpdated = getById(id);
        userToBeUpdated.setName(updatedUser.getName());
        userToBeUpdated.setLastName(updatedUser.getLastName());
        userToBeUpdated.setAge(updatedUser.getAge());
    }

    public User getById(long id){
        TypedQuery <User> query = entityManager.createQuery
                ("select u from User u where u.id=:id", User.class);
        query.setParameter("id", id);
        return query.getResultList().stream().findAny().orElse(null);
    }
}
