package ru.job4j.cars.dto;

import lombok.*;
import ru.job4j.cars.model.User;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PostDto {
    @EqualsAndHashCode.Include
    private int id;
    private int price;
    private int fileId;
    private boolean sold;
    private String bodyName;
    private String brandName;
    private String categoryName;
    private String transmissionName;
    private String engineName;
    private String carName;
    private String description;
    private User user;
    private LocalDateTime created;
}