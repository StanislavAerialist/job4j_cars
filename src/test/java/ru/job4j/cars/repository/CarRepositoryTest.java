package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.*;


import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.model.Owner;
import ru.job4j.cars.model.User;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CarRepositoryTest {
/*
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    private final SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final HibernateCarRepository carRepository = new HibernateCarRepository(crudRepository);
    private final HibernateEngineRepository engineRepository = new HibernateEngineRepository(crudRepository);
    private final HibernateOwnerRepository ownerRepository = new HibernateOwnerRepository(crudRepository);
    private final HibernateUserRepository userRepository = new HibernateUserRepository(sf);

    @BeforeEach
    public void cleanDb() {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery("DELETE FROM Car").executeUpdate();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.createQuery("DELETE FROM Engine").executeUpdate();
            session.createQuery("DELETE FROM Owner").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Test
    public void whenSaveCarThenFindById() {
        Engine engine = engineRepository.save(new Engine(1, "EngineName1"));
        User user = userRepository.create(new User(4, "login", "pass"));
        Owner owner = ownerRepository.save(new Owner(1, "OwnerName1", user));
        Car result = carRepository.save(new Car(1, "CarName1", engine, owner, Set.of(owner)));
        assertThat(result).isEqualTo(carRepository.findById(result.getId()).get());
    }

    @Test
    public void whenDeleteCar() {
        Engine engine = engineRepository.save(new Engine(1, "EngineName1"));
        User user = userRepository.create(new User(4, "login", "pass"));
        Owner owner = ownerRepository.save(new Owner(1, "OwnerName1", user));
        Car car = carRepository.save(new Car(1, "CarName1", engine, owner, Set.of(owner)));
        carRepository.delete(car.getId());
        assertThat(carRepository.findById(car.getId())).isEqualTo(Optional.empty());
    }

    @Test
    public void whenUpdateCar() {
        Engine engine = engineRepository.save(new Engine(1, "EngineName1"));
        User user = userRepository.create(new User(4, "login", "pass"));
        Owner owner = ownerRepository.save(new Owner(1, "OwnerName1", user));
        Car car1 = carRepository.save(new Car(1, "CarName1", engine, owner, Set.of(owner)));
        Car car2 = new Car(1, "CarName2", engine, owner, Set.of(owner));
        carRepository.update(car2);
        assertThat(carRepository.findById(car1.getId()).get().getName()).isEqualTo("CarName2");
    }
 */
}