package co.develhope.loginDemo1.user.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
}
