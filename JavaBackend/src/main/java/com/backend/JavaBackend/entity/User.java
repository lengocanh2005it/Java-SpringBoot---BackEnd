package com.backend.JavaBackend.entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;


@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    String id;

    @Column
    String username;

    @Column
    String password;

    @Column
    String firstName;

    @Column
    String lastName;

    @Column
    LocalDate birthday;

}
