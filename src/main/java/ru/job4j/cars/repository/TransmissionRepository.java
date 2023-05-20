package ru.job4j.cars.repository;

import ru.job4j.cars.model.Transmission;

import java.util.List;
import java.util.Optional;

public interface TransmissionRepository {
    Optional<Transmission> findById(int id);
    List<Transmission> findAll();
}
