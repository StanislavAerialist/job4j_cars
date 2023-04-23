package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Owner;

import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class OwnerRepository {
    private final CrudRepository crudRepository;

    public void save(Owner owner) {
        crudRepository.run(session -> session.persist(owner));
    }

    public boolean update(Owner owner) {
        return crudRepository.runForBoolean(session -> {
            session.merge(owner);
            return true;
        });
    }

    public boolean delete(int id) {
        return crudRepository.runForBoolean("DELETE Owner WHERE id = :cId", Map.of("cId", id));
    }

    public List<Car> findAll() {
        return crudRepository.query("from Owner", Car.class);
    }
}
