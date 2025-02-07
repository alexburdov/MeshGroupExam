package ru.alex.burdovitsin.mesh.model.jpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "phone_data", schema = "public")
@Getter
@Setter
public class PhoneData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "user_id", insertable = false, updatable = false)
    private long userId;

    private String phone;
}
