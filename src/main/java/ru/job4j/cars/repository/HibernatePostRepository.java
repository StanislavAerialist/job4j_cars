package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Category;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernatePostRepository implements PostRepository {
    private final CrudRepository crudRepository;

    @Override
    public Post save(Post post) {
        crudRepository.run(session -> session.save(post));
        return post;
    }

    @Override
    public boolean update(Post post) {
        return crudRepository.runForBoolean(session -> {
            session.merge(post);
            return true;
        });
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.runForBoolean("DELETE Post WHERE id = :pId", Map.of("pId", id));
    }

    @Override
    public Optional<Post> findById(int id) {
        return crudRepository.optional(
                "from Post where id = :pId", Post.class,
                Map.of("pId", id));
    }

    @Override
    public List<Post> findAll() {
        return crudRepository.query("from Post", Post.class);
    }

    @Override
    public List<Post> findPostFromLastDay() {
        LocalDateTime lastDay = LocalDateTime.now().minusDays(1);
        return crudRepository.query("SELECT p FROM Post p WHERE p.created >= :lastDay",
                Post.class, Map.of("lastDay", lastDay));
    }

    @Override
    public List<Post> findPostWithPhoto() {
        return crudRepository.query("SELECT p FROM Post p JOIN FETCH p.file WHERE p.file.id is NOT NULL", Post.class);
    }

    @Override
    public List<Post> findPostByBrand(String brand) {
        return crudRepository.query("SELECT p FROM Post p JOIN FETCH p.car c WHERE c.brand = :brand",
                Post.class, Map.of("brand", brand));
    }

    @Override
    public List<Post> findPostBySold(boolean sold) {
        return crudRepository.query("SELECT p FROM Post p JOIN FETCH p.file where sold = :pSold",
                Post.class, Map.of("pSold", sold));
    }

    @Override
    public boolean setSold(int id) {
        boolean status = findById(id).get().isSold() ? Boolean.FALSE : Boolean.TRUE;
        return crudRepository.runForBoolean("UPDATE Post SET sold = :pSold WHERE id = :pId",
                Map.of("pSold", status, "pId", id));
    }

    @Override
    public List<Post> findPostByCategory(Category category) {
        return crudRepository.query(
                "SELECT p FROM Post p JOIN FETCH p.car с WHERE с.category = :category",
                Post.class, Map.of("category", category)
        );
    }
}
