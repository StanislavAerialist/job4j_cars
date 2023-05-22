package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryTest {
    /*
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    private final SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final HibernateUserRepository userRepository = new HibernateUserRepository(crudRepository);

    @BeforeEach
    public void cleanDb() {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Test
    public void whenCreateUserThenFindByLogin() {
        User user = new User(1, "name", "login", "pass");
        userRepository.add(user);
        assertThat(user).isEqualTo(userRepository.findByLogin("login").get());
    }

    @Test
    public void whenUpdateUserThenFindById() {
        userRepository.add(new User(1, "name", "login", "pass"));
        User user2 = new User(1, "name2", "login2", "pass2");
        userRepository.update(user2);
        assertThat(user2.getLogin()).isEqualTo(userRepository.findById(1).get().getLogin());
    }

    @Test
    public void whenDeleteUser() {
        User user = new User(1, "name", "login", "pass");
        userRepository.deleteById(user.getId());
        assertThat(userRepository.findById(user.getId())).isEqualTo(Optional.empty());
    }

     */
}
