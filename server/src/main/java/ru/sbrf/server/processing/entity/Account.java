package ru.sbrf.server.processing.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "accounts")
@NoArgsConstructor
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "ACCOUNTNUM")
    private String accountNum;
    @Column(name = "BALANCE")
    private int balance;
    @Column(name = "ISOCODE")
    private String isoCode;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client_id;

    @OneToMany(mappedBy = "account_id")
    private Set<Card> cards;
}
