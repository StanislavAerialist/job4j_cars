package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.dto.PostCreateDto;
import ru.job4j.cars.dto.PostDto;
import ru.job4j.cars.model.*;
import ru.job4j.cars.repository.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class HibernatePostService implements PostService {
    private final PostRepository postRepository;
    private final FileService fileService;
    private final CarRepository carRepository;
    private final OwnerRepository ownerRepository;
    private final EngineRepository engineRepository;
    private final TransmissionRepository transmissionRepository;
    private final BodyRepository bodyRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private List<PostDto> postDtoBuilder(List<Post> posts) {
        return posts.stream().map(post ->
                new PostDto(post.getId(), post.getPrice(), post.getFile().getId(), post.isSold(),
                        bodyRepository.findById(post.getCar().getBody().getId()).get().getName(),
                        brandRepository.findById(post.getCar().getBrand().getId()).get().getName(),
                        categoryRepository.findById(post.getCar().getCategory().getId()).get().getName(),
                        transmissionRepository.findById(post.getCar().getTransmission().getId()).get().getName(),
                        engineRepository.findById(post.getCar().getEngine().getId()).get().getName(),
                        post.getCar().getName(), post.getDescription(), post.getUser(),
                        post.getCreated())).toList();
    }


    @Override
    public Optional<Post> add(PostCreateDto postDto, FileDto image) {
        var owner = new Owner();
        owner.setName(postDto.getUser().getLogin());
        owner.setUser(postDto.getUser());
        ownerRepository.save(owner);
        var car = new Car();
        car.setName(postDto.getCarName());
        car.setEngine(engineRepository.findById(postDto.getEngineId()).get());
        car.setTransmission(transmissionRepository.findById(postDto.getTransmissionId()).get());
        car.setBody(bodyRepository.findById(postDto.getBodyId()).get());
        car.setBrand(brandRepository.findById(postDto.getBrandId()).get());
        car.setCategory(categoryRepository.findById(postDto.getCategoryId()).get());
        car.setOwner(owner);
        car.setOwners(Set.of(owner));
        carRepository.save(car);
        var post = new Post();
        post.setPrice(postDto.getPrice());
        post.setSold(false);
        post.setDescription(postDto.getDescription());
        post.setUser(postDto.getUser());
        post.setCar(car);
        saveNewFile(post, image);
        return Optional.of(postRepository.save(post));
    }

    private void saveNewFile(Post post, FileDto image) {
        var file = fileService.save(image);
        post.setFile(file);
    }

    @Override
    public boolean update(Post post) {
        return postRepository.update(post);
    }

    @Override
    public boolean delete(int postId) {
        var post = postRepository.findById(postId).get();
        var isDeleted = postRepository.delete(postId);
        fileService.deleteById(post.getFile().getId());
        return isDeleted;
    }

    @Override
    public Optional<PostDto> findById(int postId) {
        return postDtoBuilder(List.of(postRepository.findById(postId).get())).stream().findFirst();
    }

    @Override
    public List<PostDto> findAll() {
        return postDtoBuilder(postRepository.findAll());
    }

    @Override
    public List<PostDto> findPostFromLastDay() {
        return postDtoBuilder(postRepository.findPostFromLastDay());
    }

    @Override
    public List<PostDto> findPostWithPhoto() {
        return postDtoBuilder(postRepository.findPostWithPhoto());
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
