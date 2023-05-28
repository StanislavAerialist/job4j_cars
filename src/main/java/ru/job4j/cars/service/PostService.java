package ru.job4j.cars.service;

import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.dto.PostCreateDto;
import ru.job4j.cars.dto.PostDto;
import ru.job4j.cars.model.Category;
import ru.job4j.cars.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Optional<Post> add(PostCreateDto postDto, FileDto image);
    boolean update(Post post);
    boolean delete(int id);
    Optional<PostDto> findById(int id);
    List<PostDto> findAll();
    List<PostDto> findPostFromLastDay();
    List<PostDto> findPostWithPhoto();
    List<Post> findPostBySold(boolean sold);
    boolean setSold(int id);
}
