package ru.job4j.cars.service;

import ru.job4j.cars.model.Transmission;

import java.util.List;
import java.util.Optional;

public interface TransmissionService {
    Optional<Transmission> findById(int id);
    List<Transmission> findAll();
}
