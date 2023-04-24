package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class PostRepository {
    private final CrudRepository crudRepository;

    public List<Post> findPostFromLastDay() {
        LocalDateTime lastDay = LocalDateTime.now().minusDays(1);
        return crudRepository.query("SELECT p FROM Post p WHERE p.created >= :lastDay",
                Post.class, Map.of("lastDay", lastDay));
    }

    public List<Post> findPostWithPhoto() {
        return crudRepository.query("SELECT p FROM Post p JOIN FETCH p.files WHERE size(files) > 0", Post.class);
    }

    public List<Post> findPostByBrand(String brand) {
        return crudRepository.query("SELECT p FROM Post p JOIN FETCH p.car WHERE p.car.name = :brand",
                Post.class, Map.of("brand", brand));
    }
}
