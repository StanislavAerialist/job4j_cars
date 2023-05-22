package ru.job4j.cars.repository;

import ru.job4j.cars.model.Category;
import ru.job4j.cars.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Post save(Post post);
    boolean update(Post post);
    boolean delete(int id);
    Optional<Post> findById(int id);
    List<Post> findAll();
    List<Post> findPostFromLastDay();
    List<Post> findPostWithPhoto();
    List<Post> findPostByBrand(String brand);
    List<Post> findPostBySold(boolean sold);
    List<Post> findPostByCategory(Category category);
    boolean setSold(int id);
}
