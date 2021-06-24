package ru.sbrf.client.controller;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.sbrf.client.dto.BalanceDTO;
import ru.sbrf.client.exception.ATMInternalErrorException;
import ru.sbrf.client.service.ATMService;
import ru.sbrf.server.common.messages.Request;
import ru.sbrf.server.common.messages.Response;
import ru.sbrf.server.common.messages.RequestTypes;


@RestController
@AllArgsConstructor
@Log
public class ATMRestController {
    private ATMService atmService;

    @GetMapping("/ATMs")
    public String getATMsStatus() {
        return "1 ATM is ready.";
    }

    @GetMapping("/ATMs/{ATMId}/clients/balance/{cardNum}/{pinCode}")
    public BalanceDTO getClientBalance(
            @PathVariable("ATMId") Long ATMId,
            @PathVariable("cardNum") String cardNum,
            @PathVariable("pinCode") int pinCode) {


        log.info("ATMId=" + ATMId+ " cardNum=" + cardNum + " pinCode=" + pinCode);

        if (ATMId != 1) {
            throw new ATMInternalErrorException("ATM internal Error");
        }

        return atmService.getClientBalance(cardNum, pinCode);
    }
}
