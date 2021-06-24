package ru.sbrf.server.processing.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cards")
@NoArgsConstructor
@Getter
@Setter
public class Card {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "PINCODE")
    private int pinCode;
    @Column(name = "CARDNUM")
    private String cardNum;
    @Column(name = "EXPIREDATE")
    private Date expireDate;
    @Column(name = "CVCCODE")
    private int cvcCode;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account_id;
}
