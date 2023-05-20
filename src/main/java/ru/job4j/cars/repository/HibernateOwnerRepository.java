package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Owner;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateOwnerRepository implements OwnerRepository {
    private final CrudRepository crudRepository;

    @Override
    public Owner save(Owner owner) {
        crudRepository.run(session -> session.save(owner));
        return owner;
    }

    @Override
    public boolean update(Owner owner) {
        return crudRepository.runForBoolean(session -> {
            session.merge(owner);
            return true;
        });
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.runForBoolean("DELETE Owner WHERE id = :cId", Map.of("cId", id));
    }

    @Override
    public List<Owner> findAll() {
        return crudRepository.query("from Owner", Owner.class);
    }

    @Override
    public Optional<Owner> findById(int id) {
        return crudRepository.optional("from Owner where id = :fId", Owner.class, Map.of("fId", id));
    }
}
