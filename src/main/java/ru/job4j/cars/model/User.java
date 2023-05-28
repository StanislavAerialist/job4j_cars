package ru.job4j.cars.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "auto_user")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    private String name;
    @EqualsAndHashCode.Include
    private String login;
    private String password;
}