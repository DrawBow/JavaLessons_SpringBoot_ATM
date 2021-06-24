package ru.sbrf.server.processing.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import ru.sbrf.server.common.messages.ErrorsCode;
import ru.sbrf.server.common.messages.Response;
import ru.sbrf.server.processing.AccountDTO;
import ru.sbrf.server.processing.CardDTO;
import ru.sbrf.server.processing.ClientDTO;
import ru.sbrf.server.processing.exception.AccountNotFoundException;
import ru.sbrf.server.processing.exception.CardNotFoundException;
import ru.sbrf.server.processing.exception.ClientNotFoundException;

import java.util.Date;
import java.util.List;

@Log
@RequiredArgsConstructor
public class ATMService {
    private final List<ClientDTO> clients;

    public Response getCardBalance(String cardNo, int pinCode){
        ClientDTO client;
        AccountDTO account;
        CardDTO card;

        if (clients.stream().noneMatch(cli ->
                cli.getAccountDTO().stream().anyMatch(acc ->
                        acc.getCardDTO().stream().anyMatch(crd ->
                                crd.getCardNum().equalsIgnoreCase(cardNo))))) {
            log.info("getCardBalance request card not found CardNum=" + cardNo);
            return new Response(ErrorsCode.CARD_NOT_FIND_OR_PIN_UNCORRECT);
        }

        client = clients.stream().filter(cli ->
                cli.getAccountDTO().stream().anyMatch(acc ->
                        acc.getCardDTO().stream().anyMatch(crd ->
                                crd.getCardNum().equalsIgnoreCase(cardNo)))).findFirst().orElseThrow(ClientNotFoundException::new);

        account = client.getAccountDTO().stream().filter(acc ->
                acc.getCardDTO().stream().anyMatch(crd ->
                        crd.getCardNum().equalsIgnoreCase(cardNo))).findFirst().orElseThrow(AccountNotFoundException::new);

        card = account.getCardDTO().stream().filter(crd ->
                        crd.getCardNum().equalsIgnoreCase(cardNo)).findFirst().orElseThrow(CardNotFoundException::new);

        if (card.getPinCode() != pinCode) {
            log.info("getCardBalance request pin uncorrect CardNum=" + cardNo + " pinCode=" + pinCode);
            return new Response(ErrorsCode.CARD_NOT_FIND_OR_PIN_UNCORRECT);
        }

        if (card.getExpireDate().before(new Date())) {
            log.info("getCardBalance card date expired CardNum=" + cardNo + " date=" + card.getExpireDate().toString());
            return new Response(ErrorsCode.DATE_EXPIRED);
        }

        return new Response(ErrorsCode.NOERROR, account.getAccountNum(), account.getIsoCode(), account.getBalance());
    }
}
