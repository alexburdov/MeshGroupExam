package ru.alex.burdovitsin.mesh.model.jpa;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "email_data", schema = "public")
@ToString
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailData emailData = (EmailData) o;
        return Objects.nonNull(id) && Objects.equals(id, emailData.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
