package ru.alex.burdovitsin.mesh.model;

import javax.persistence.*;

@Entity
@Table(name = "phone_data")
public class PhoneData {
    @Id
    @GeneratedValue(generator = "phone_data_generator")
    @SequenceGenerator(
            name = "phone_data_generator",
            sequenceName = "phone_data_sequence"
    )
    private Long id;


    @Column(name = "USER_ID", insertable = false, updatable = false)
    private long userId;

    private String phone;
}
