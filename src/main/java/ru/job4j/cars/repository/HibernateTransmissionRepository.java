package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Transmission;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateTransmissionRepository {
    private final CrudRepository crudRepository;

    public Optional<Transmission> findById(int id) {
        return crudRepository.optional("from Transmission where id = :bId", Transmission.class, Map.of("bId", id)
        );
    }

    public List<Transmission> findAll() {
        return crudRepository.query("from Transmission", Transmission.class);
    }
}
