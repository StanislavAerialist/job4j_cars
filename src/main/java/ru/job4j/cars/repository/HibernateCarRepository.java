package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateCarRepository implements CarRepository {
    private final CrudRepository crudRepository;

    @Override
    public Car save(Car car) {
        crudRepository.run(session -> session.save(car));
        return car;
    }

    @Override
    public boolean update(Car car) {
        return crudRepository.runForBoolean(session -> {
            session.merge(car);
            return true;
        });
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.runForBoolean("DELETE Car WHERE id = :cId", Map.of("cId", id));
    }

    @Override
    public Optional<Car> findById(int id) {
        return crudRepository.optional(
                "from Car c LEFT JOIN FETCH c.engine LEFT JOIN FETCH c.owner "
                        + "LEFT JOIN FETCH c.owners where c.id = :cId", Car.class,
                Map.of("cId", id));
    }

    @Override
    public List<Car> findAll() {
        return crudRepository.query("from Car", Car.class);
    }
}
