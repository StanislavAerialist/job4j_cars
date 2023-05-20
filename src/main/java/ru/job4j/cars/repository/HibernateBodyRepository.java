package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Body;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateBodyRepository implements BodyRepository {
    private final CrudRepository crudRepository;

    @Override
    public Optional<Body> findById(int id) {
        return crudRepository.optional("from Body where id = :bId", Body.class, Map.of("bId", id)
        );
    }

    @Override
    public List<Body> findAll() {
        return crudRepository.query("from Body", Body.class);
    }
}
