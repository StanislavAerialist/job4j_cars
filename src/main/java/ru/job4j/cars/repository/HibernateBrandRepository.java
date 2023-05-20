package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Brand;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateBrandRepository implements BrandRepository {
    private final CrudRepository crudRepository;

    @Override
    public Optional<Brand> findById(int id) {
        return crudRepository.optional("from Brand where id = :bId", Brand.class, Map.of("bId", id)
        );
    }

    @Override
    public List<Brand> findAll() {
        return crudRepository.query("from Brand", Brand.class);
    }
}
