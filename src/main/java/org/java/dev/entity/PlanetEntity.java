package org.java.dev.entity;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Data
@Entity
@Table(name="Planet")
public class PlanetEntity {
        @Id
        @Column(name="id", length = 50)
        private String id;

        @Column(name = "name", length = 2, nullable = false)
        @Pattern(regexp = "[A-Z]*", message = "Incorrect value: Planet.name must be in format [A-Z]")
        private String  name;
}
