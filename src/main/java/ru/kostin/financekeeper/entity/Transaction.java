package ru.kostin.financekeeper.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn
    private Account source;

    @ManyToOne
    @JoinColumn
    private Account target;

    @Column
    private Date time;

    @Column
    private String description;

    @ManyToMany
    @JoinTable(
            name = "transactions_to_types",
            joinColumns = @JoinColumn(name = "transaction_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "type_id", referencedColumnName = "id")
    )
    List<Type> types;

    @ManyToOne
    @JoinColumn
    private User owner;
}
