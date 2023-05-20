package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.HibernatePostRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HibernatePostService implements PostService {
    private final HibernatePostRepository postRepository;

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public boolean update(Post post) {
        return postRepository.update(post);
    }

    @Override
    public boolean delete(int id) {
        return postRepository.delete(id);
    }

    @Override
    public Optional<Post> findById(int id) {
        return postRepository.findById(id);
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> findPostFromLastDay() {
        return postRepository.findPostFromLastDay();
    }

    @Override
    public List<Post> findPostWithPhoto() {
        return postRepository.findPostWithPhoto();
    }

    @Override
    public List<Post> findPostByBrand(String brand) {
        return postRepository.findPostByBrand(brand);
    }

    @Override
    public List<Post> findPostBySold(boolean sold) {
        return postRepository.findPostBySold(sold);
    }

    @Override
    public boolean setSold(int id) {
        return postRepository.setSold(id);
    }
}
