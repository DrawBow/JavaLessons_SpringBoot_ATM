package ru.sbrf.server.processing;

import lombok.Value;

import java.util.List;

// @RequiredArgsConstructor
// @Getter
@Value
public class AccountDTO {
    int accountId;
    String accountNum;
    String isoCode;
    int balance;
    List<CardDTO> cardDTO;
}
