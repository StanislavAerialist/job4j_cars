package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Category;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateCategoryRepository implements CategoryRepository {
    private final CrudRepository crudRepository;

    @Override
    public Optional<Category> findById(int id) {
        return crudRepository.optional("from Category where id = :bId", Category.class, Map.of("bId", id)
        );
    }

    @Override
    public List<Category> findAll() {
        return crudRepository.query("from Category", Category.class);
    }
}
