package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Category;
import ru.job4j.cars.repository.HibernateCategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HibernateCategoryService implements CategoryService {
    private final HibernateCategoryRepository categoryRepository;

    @Override
    public Optional<Category> findById(int id) {
        return categoryRepository.findById(id);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
