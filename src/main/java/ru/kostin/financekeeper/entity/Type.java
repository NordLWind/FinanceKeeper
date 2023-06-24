package ru.kostin.financekeeper.entity;

import javax.persistence.*;

@Entity
@Table(name = "transaction_types")
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    private String name;
}
