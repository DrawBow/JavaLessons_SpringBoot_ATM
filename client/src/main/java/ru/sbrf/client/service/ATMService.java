package ru.sbrf.client.service;

import lombok.extern.java.Log;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sbrf.client.dto.BalanceDTO;
import ru.sbrf.client.exception.ATMInternalErrorException;
import ru.sbrf.server.common.messages.ErrorsCode;
import ru.sbrf.server.common.messages.Request;
import ru.sbrf.server.common.messages.RequestTypes;
import ru.sbrf.server.common.messages.Response;

import java.util.HashMap;
import java.util.Map;
// import com.alibaba.fastjson.JSON;

@Log
@Service
public class ATMService {

    public BalanceDTO getClientBalance(String cardNum, int pinCode) {

        final String balanceUrl = "http://127.0.0.1:8080/hosts/1/clients/balance";

        Map<ErrorsCode, String> errMessages = new HashMap<ErrorsCode, String>() {{
            put(ErrorsCode.CARD_NOT_FOUND_OR_PIN_UNCORRECT, "Карточка не найдена или не корректный пинкод");
            put(ErrorsCode.DATE_EXPIRED, "Истек срок действия карты");
            put(ErrorsCode.UNCORRECT_CARD_NUMBER, "Не корректно введен номер карты: номер должен состоять из 16 цифр");
            put(ErrorsCode.UNCORRECT_PIN_CODE, "Не корректно введен пинкод: пинкод должен состоять из 4 цифр");
        }};


        Request request = new Request(cardNum, pinCode);
        log.info("request.toString()" + request.toString());

        RestTemplate restTemplate = new RestTemplate();
        Response response = restTemplate.postForObject(
                balanceUrl, request, Response.class);
        log.info("response.toString" + response.toString());

        if (response.getErrorsCode() != ErrorsCode.NOERROR) {
           throw new ATMInternalErrorException(errMessages.get(response.getErrorsCode()));
        }

        return new BalanceDTO(response.getBalance(), response.getIsoCode() );

    }
}
