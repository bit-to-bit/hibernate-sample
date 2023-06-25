package org.java.dev.entity;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name="Planet")
public class PlanetEntity {
        @Id
        @Column(name="id", length = 50)
        private String id;

        @Column(name = "name", length = 200, nullable = false)
        private String  name;



}
