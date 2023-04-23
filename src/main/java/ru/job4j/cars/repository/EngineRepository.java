package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class EngineRepository {
    private final CrudRepository crudRepository;

    public void save(Engine engine) {
        crudRepository.run(session -> session.persist(engine));
    }

    public boolean update(Engine engine) {
        return crudRepository.runForBoolean(session -> {
            session.merge(engine);
            return true;
        });
    }

    public boolean delete(int id) {
        return crudRepository.runForBoolean("DELETE Engine WHERE id = :cId", Map.of("cId", id));
    }

    public List<Car> findAll() {
        return crudRepository.query("from Engine", Car.class);
    }
}
