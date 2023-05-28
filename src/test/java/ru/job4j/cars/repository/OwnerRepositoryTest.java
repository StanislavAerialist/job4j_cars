package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Owner;
import ru.job4j.cars.model.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class OwnerRepositoryTest {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    private final SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final HibernateOwnerRepository ownerRepository = new HibernateOwnerRepository(crudRepository);
    private final HibernateUserRepository userRepository = new HibernateUserRepository(crudRepository);

    @BeforeEach
    public void cleanDb() {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.createQuery("DELETE FROM Owner").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Test
    public void whenSaveOwner() {
        User user = userRepository.add(new User(1, "name", "login", "pass")).get();
        Owner owner = new Owner(1, "OwnerName", user);
        Owner result = ownerRepository.save(owner);
        assertThat(result).isEqualTo(owner);
    }

    @Test
    public void whenUpdateOwnerThenFindId() {
        User user = userRepository.add(new User(1, "name", "login", "pass")).get();
        ownerRepository.save(new Owner(1, "OwnerName1", user));
        Owner owner2 = new Owner(1, "OwnerName2", user);
        ownerRepository.update(owner2);
        assertThat(owner2.getName()).isEqualTo(ownerRepository.findById(owner2.getId()).get().getName());
    }

    @Test
    public void whenDeleteOwner() {
        User user = userRepository.add(new User(1, "name", "login", "pass")).get();
        Owner owner = ownerRepository.save(new Owner(1, "OwnerName1", user));
        ownerRepository.delete(owner.getId());
        assertThat(ownerRepository.findById(owner.getId())).isEqualTo(Optional.empty());
    }


}
