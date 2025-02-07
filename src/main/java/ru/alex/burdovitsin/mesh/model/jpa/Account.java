package ru.alex.burdovitsin.mesh.model.jpa;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.Objects;

import static ru.alex.burdovitsin.mesh.common.Constants.DEFAULT_BALANCE_STRING;

@Entity
@Table(name = "account", schema = "public")
@Setter
@Getter
@ToString
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id", updatable = false)
    private long userId;

    @ColumnDefault(value = DEFAULT_BALANCE_STRING)
    @Column(name = "init_balance", updatable = false)
    @Min(value = 0)
    private BigDecimal initBalance;

    @ColumnDefault(value = DEFAULT_BALANCE_STRING)
    @Min(value = 0)
    private BigDecimal balance;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.nonNull(id) && Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
