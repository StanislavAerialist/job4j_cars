package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryTest {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    private final SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    private final UserRepository userRepository = new UserRepository(sf);

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
        User user = new User(1, "login", "pass");
        userRepository.create(user);
        assertThat(user).isEqualTo(userRepository.findByLogin("login").get());
    }

    @Test
    public void whenUpdateUserThenFindById() {
        userRepository.create(new User(1, "login", "pass"));
        User user2 = new User(1, "login2", "pass2");
        userRepository.update(user2);
        assertThat(user2.getLogin()).isEqualTo(userRepository.findById(1).get().getLogin());
    }

    @Test
    public void whenDeleteUser() {
        User user = new User(1, "login", "pass");
        userRepository.delete(user.getId());
        assertThat(userRepository.findById(user.getId())).isEqualTo(Optional.empty());
    }
}
