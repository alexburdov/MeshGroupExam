package ru.alex.burdovitsin.mesh.model.jpa;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "phone_data", schema = "public")
@Getter
@Setter
@ToString
public class PhoneData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "user_id", insertable = false, updatable = false)
    private long userId;

    private String phone;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneData phoneData = (PhoneData) o;
        return Objects.nonNull(id) && Objects.equals(id, phoneData.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
