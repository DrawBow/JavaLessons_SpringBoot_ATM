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

    private int pinCode;
    private String cardNum;
    private Date expireDate;
    private int cvcCode;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account_id;
}
