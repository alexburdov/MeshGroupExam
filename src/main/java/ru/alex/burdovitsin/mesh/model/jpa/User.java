package ru.alex.burdovitsin.mesh.model.jpa;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "user", schema = "public")
@Setter
@Getter
public class User {

    private static final String ROLE_USER = "ROLE_USER";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 500)
    @Column(name = "name", unique = true, nullable = false)
    private String username;

    @Column(name = "DATE_OF_BIRTH")
    private java.sql.Date dateOfBird;

    @NotBlank
    @Size(min = 8, max = 500)
    private String password;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id", referencedColumnName = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Account account;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<EmailData> emailData;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<PhoneData> phoneData;
}
