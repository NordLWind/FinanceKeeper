package ru.kostin.financekeeper.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "bank_accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn
    private User owner;
}
