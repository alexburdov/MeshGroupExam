package ru.alex.burdovitsin.mesh.model.jpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "email_data", schema = "public")
public class EmailData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter
    private Long id;


    @Column(name = "user_id", insertable = false, updatable = false)
    @Setter
    @Getter
    private long userId;

    @Setter
    @Getter
    private String email;
}
