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
    private final PostMapper postMapper;

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
        return Optional.of(postMapper.toDto(postRepository.findById(postId).get()));
    }

    @Override
    public List<PostDto> findAll() {
        return postMapper.toDtoList(postRepository.findAll());
    }

    @Override
    public List<PostDto> findPostFromLastDay() {
        return postMapper.toDtoList(postRepository.findPostFromLastDay());
    }

    @Override
    public List<PostDto> findPostWithPhoto() {
        return postMapper.toDtoList(postRepository.findPostWithPhoto());
    }

    @Override
    public List<PostDto> findPostBySold(boolean sold) {
        return postMapper.toDtoList(postRepository.findPostBySold(sold));
    }

    @Override
    public boolean setSold(int id) {
        return postRepository.setSold(id);
    }

}
