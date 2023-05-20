package ru.job4j.cars.service;

import ru.job4j.cars.model.Owner;

import java.util.List;
import java.util.Optional;

public interface OwnerService {
    Owner save(Owner owner);
    boolean update(Owner owner);
    boolean delete(int id);
    List<Owner> findAll();
    Optional<Owner> findById(int id);
}
