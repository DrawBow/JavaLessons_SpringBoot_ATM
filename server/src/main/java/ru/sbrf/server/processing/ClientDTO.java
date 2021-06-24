package ru.sbrf.server.processing;

import lombok.Value;

import java.util.List;

@Value
public class ClientDTO {

    String firstName;
    String lastName;
    List<AccountDTO> accountDTO;
}
