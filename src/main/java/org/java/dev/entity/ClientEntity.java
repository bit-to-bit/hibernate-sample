package org.java.dev.entity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="Client")
public class ClientEntity {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="id")
    private Long id;

    @Column(name = "name", length = 500, nullable = false)
    private String  name;
}
