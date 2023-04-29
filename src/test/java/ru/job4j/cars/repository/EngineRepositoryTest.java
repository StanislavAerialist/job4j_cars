package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.junit.jupiter.api.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.cars.model.Engine;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class EngineRepositoryTest {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    private final SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final EngineRepository engineRepository = new EngineRepository(crudRepository);

    @AfterEach
    public void cleanDb() {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery("DELETE FROM Engine").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Test
    public void whenSaveEngine() {
        Engine engine = new Engine(1, "EngineName");
        Engine result = engineRepository.save(engine);
        assertThat(result).isEqualTo(engine);
    }

    @Test
    public void whenUpdateEngineThenFindById() {
        engineRepository.save(new Engine(1, "EngineName1"));
        Engine engine2 = new Engine(1, "EngineName2");
        engineRepository.update(engine2);
        assertThat(engine2.getName()).isEqualTo(engineRepository.findById(engine2.getId()).get().getName());
    }

    @Test
    public void whenDeleteEngine() {
        Engine engine = engineRepository.save(new Engine(1, "EngineName1"));
        engineRepository.delete(engine.getId());
        assertThat(engineRepository.findById(engine.getId())).isEqualTo(Optional.empty());
    }
}
