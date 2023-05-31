package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.dto.PostDto;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.*;

import java.util.List;

@Service
@AllArgsConstructor
public class PostMapper {
    public List<PostDto> toDtoList(List<Post> posts) {
        return posts.stream().map(this::toDto).toList();
    }

    public PostDto toDto(Post post) {
        return PostDto.builder().id(post.getId()).price(post.getPrice()).fileId(post.getFile().getId()).sold(post.isSold())
                .bodyName(post.getCar().getBody().getName()).brandName(post.getCar().getBrand().getName())
                .categoryName(post.getCar().getCategory().getName())
                .transmissionName(post.getCar().getTransmission().getName()).engineName(post.getCar().getEngine().getName())
                .carName(post.getCar().getName()).description(post.getDescription()).user(post.getUser())
                .created(post.getCreated()).build();
    }
}
