package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Body;
import ru.job4j.cars.repository.HibernateBodyRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HibernateBodyService implements BodyService {

    private final HibernateBodyRepository bodyRepository;

    @Override
    public Optional<Body> findById(int id) {
        return bodyRepository.findById(id);
    }

    @Override
    public List<Body> findAll() {
        return bodyRepository.findAll();
    }
}
