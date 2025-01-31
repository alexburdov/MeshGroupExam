package ru.alex.burdovitsin.mesh.model.jpa;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.math.BigDecimal;

import static ru.alex.burdovitsin.mesh.common.Constants.DEFAULT_BALANCE_STRING;

@Entity
@Table(name = "account", schema = "public")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "USER_ID", insertable = false, updatable = false)
    private long userId;

    @ColumnDefault(value = DEFAULT_BALANCE_STRING)
    private BigDecimal balance;
}
