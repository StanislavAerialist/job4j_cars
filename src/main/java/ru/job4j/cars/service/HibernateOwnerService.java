package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Owner;
import ru.job4j.cars.repository.HibernateOwnerRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HibernateOwnerService implements OwnerService {
    private final HibernateOwnerRepository ownerRepository;

    @Override
    public Owner save(Owner owner) {
        return ownerRepository.save(owner);
    }

    @Override
    public boolean update(Owner owner) {
        return ownerRepository.update(owner);
    }

    @Override
    public boolean delete(int id) {
        return ownerRepository.delete(id);
    }

    @Override
    public List<Owner> findAll() {
        return ownerRepository.findAll();
    }

    @Override
    public Optional<Owner> findById(int id) {
        return ownerRepository.findById(id);
    }
}
