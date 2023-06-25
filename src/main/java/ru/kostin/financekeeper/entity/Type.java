package ru.kostin.financekeeper.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "transaction_types")
@Getter
@Setter
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    private String type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Type type = (Type) o;
        return getId() != null && Objects.equals(getId(), type.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
