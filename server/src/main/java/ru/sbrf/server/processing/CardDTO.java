package ru.sbrf.server.processing;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@RequiredArgsConstructor
@Getter
public class CardDTO {

    private final int pinCode;
    private final String cardNum;
    private final Date expireDate;
    private final int cvcCode;
}
