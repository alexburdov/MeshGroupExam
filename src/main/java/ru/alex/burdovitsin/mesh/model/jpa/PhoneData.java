package ru.alex.burdovitsin.mesh.model.jpa;

import javax.persistence.*;

@Entity
@Table(name = "phone_data", schema = "public")
public class PhoneData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;


    @Column(name = "USER_ID", insertable = false, updatable = false)
    private long userId;

    private String phone;
}
