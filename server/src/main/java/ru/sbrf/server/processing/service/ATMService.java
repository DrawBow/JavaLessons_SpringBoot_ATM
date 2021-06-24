package ru.sbrf.server.processing.service;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class ATMService {

    private ClientService clientService;

    public ErrorsCode validate(String cardNo, int pinCode){
        String cardRegex = "\\d{16}";

        if (! cardNo.matches(cardRegex)){
            return ErrorsCode.UNCORRECT_CARD_NUMBER;
        }

        if (pinCode < 1000 | pinCode > 9999){
            return ErrorsCode.UNCORRECT_PIN_CODE;
        }

        return ErrorsCode.NOERROR;
    }

    public Response getCardBalance(String cardNo, int pinCode){
        ClientDTO client;
        AccountDTO account;
        CardDTO card;

        ErrorsCode errorsCode  = validate(cardNo,pinCode);
        if (! errorsCode.equals(ErrorsCode.NOERROR)) {
            return new Response(ErrorsCode.UNCORRECT_CARD_NUMBER);
        }

        // Метод возвращает нам клиента с единственым аккаунтом и карточкой
        try {
          client = clientService.getClientByCardNum(cardNo);
        } catch (CardNotFoundException e) {
            log.info("getCardBalance request card not found CardNum=" + cardNo);
            return new Response(ErrorsCode.CARD_NOT_FOUND_OR_PIN_UNCORRECT);
        }

        account = client.getAccountDTO().get(0);
        card = account.getCardDTO().get(0);

        if (card.getPinCode() != pinCode) {
            log.info("getCardBalance request pin uncorrect CardNum=" + cardNo + " pinCode=" + pinCode);
            return new Response(ErrorsCode.CARD_NOT_FOUND_OR_PIN_UNCORRECT);
        }

        if (card.getExpireDate().before(new Date())) {
            log.info("getCardBalance card date expired CardNum=" + cardNo + " date=" + card.getExpireDate().toString());
            return new Response(ErrorsCode.DATE_EXPIRED);
        }

        return new Response(ErrorsCode.NOERROR, account.getAccountNum(), account.getIsoCode(), account.getBalance());
    }
}
