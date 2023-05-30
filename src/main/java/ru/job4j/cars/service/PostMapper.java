package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.dto.PostDto;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.*;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostMapper {
    private final BodyRepository bodyRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final TransmissionRepository transmissionRepository;
    private final EngineRepository engineRepository;

    public List<PostDto> toDtoList(List<Post> posts) {
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

    public Optional<PostDto> toDto(Post post) {
        return toDtoList(List.of(post)).stream().findFirst();
    }
}
