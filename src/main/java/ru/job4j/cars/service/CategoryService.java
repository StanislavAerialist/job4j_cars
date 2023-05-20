package ru.job4j.cars.service;

import ru.job4j.cars.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Optional<Category> findById(int id);
    List<Category> findAll();
}
