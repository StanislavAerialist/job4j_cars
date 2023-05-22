package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateUserRepository implements UserRepository {
    private final CrudRepository crudRepository;

    public Optional<User> add(User user) {
        Optional<User> rsl = Optional.empty();
        try {
            crudRepository.run(session -> session.persist(user));
            rsl = Optional.of(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    public boolean update(User user) {
        return crudRepository.runForBoolean(session -> {
            session.merge(user);
            return true;
        });
    }
        public boolean deleteById(int id) {
            return crudRepository.runForBoolean("delete from User where id = :fId",
                    Map.of("fId", id)
            );
        }

        public List<User> findAll() {
            return crudRepository.query("from User", User.class);
        }

        @Override
        public Optional<User> findById(int id) {
            return crudRepository.optional(
                    "from User where id = :tId", User.class, Map.of("tId", id));
        }

        public Optional<User> findByLoginAndPassword(String login, String password) {
            return crudRepository.optional(
                    "from User where login = :uLogin and password = :uPassword", User.class,
                    Map.of("uLogin", login, "uPassword", password)
            );
        }

    public Optional<User> findByLogin(String login) {
        return crudRepository.optional(
                "from User where login = :uLogin", User.class,
                Map.of("uLogin", login)
        );
    }
}