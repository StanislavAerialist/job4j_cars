package ru.job4j.cars.repository;

import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> add(User user);

    boolean update(User user);

    Optional<User> findById(int id);

    List<User> findAll();

    boolean deleteById(int id);

    Optional<User> findByLoginAndPassword(String login, String password);

}
