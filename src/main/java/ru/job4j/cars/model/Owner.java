package ru.job4j.cars.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "owner")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    private String name;

    @OneToOne
    @JoinColumn(name = "auto_user_id")
    private User user;
}
