package ru.alex.burdovitsin.mesh.model.jpa;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

import static ru.alex.burdovitsin.mesh.common.Constants.DEFAULT_BALANCE_STRING;

@Entity
@Table(name = "account", schema = "public")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "USER_ID", updatable = false)
    private long userId;

    @ColumnDefault(value = DEFAULT_BALANCE_STRING)
    @Column(name = "INIT_BALANCE", updatable = false)
    @Min(value = 0)
    private BigDecimal initBalance;

    @ColumnDefault(value = DEFAULT_BALANCE_STRING)
    @Min(value = 0)
    private BigDecimal balance;
}
