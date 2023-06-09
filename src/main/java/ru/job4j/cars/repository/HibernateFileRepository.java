package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.File;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateFileRepository implements FileRepository {
    private final CrudRepository crudRepository;

    @Override
    public File save(File file) {
        crudRepository.run(session -> session.save(file));
        return file;
    }

    @Override
    public Optional<File> findById(int id) {
        return crudRepository.optional(
                "from File where id = :fId", File.class,
                Map.of("fId", id));
    }

    @Override
    public List<File> findAll() {
        return crudRepository.query("from File", File.class);
    }

    @Override
    public boolean delete(int fileId) {
        boolean rsl = false;
        try {
            crudRepository.runForBoolean("DELETE File WHERE id = :fId",
                    Map.of("fId", fileId)
            );
            rsl = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }
}
