package ru.alex.burdovitsin.mesh.model;

import javax.persistence.*;

@Entity
@Table(name = "email_data")
public class EmailData {
    @Id
    @GeneratedValue(generator = "email_data_generator")
    @SequenceGenerator(
            name = "email_data_generator",
            sequenceName = "email_data_sequence"
    )
    private Long id;


    @Column(name = "USER_ID", insertable = false, updatable = false)
    private long userId;

    private String email;
}
