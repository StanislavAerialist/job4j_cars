package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.*;


import ru.job4j.cars.model.*;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CarRepositoryTest {


    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    private final SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final HibernateCarRepository carRepository = new HibernateCarRepository(crudRepository);
    private final HibernateEngineRepository engineRepository = new HibernateEngineRepository(crudRepository);
    private final HibernateOwnerRepository ownerRepository = new HibernateOwnerRepository(crudRepository);
    private final HibernateUserRepository userRepository = new HibernateUserRepository(crudRepository);

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
        Car result = carRepository.save(new Car());
        assertThat(result).isEqualTo(carRepository.findById(result.getId()).get());
    }

    @Test
    public void whenDeleteCar() {
        Car car = carRepository.save(new Car());
        carRepository.delete(car.getId());
        assertThat(carRepository.findById(car.getId())).isEqualTo(Optional.empty());
    }


    @Test
    public void whenUpdateCar() {
        Car car = new Car();
        car.setName("name1");
        carRepository.save(car);
        car.setName("name2");
        carRepository.update(car);
        assertThat(carRepository.findById(car.getId()).get().getName()).isEqualTo("name2");
    }


}