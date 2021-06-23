package ru.sbrf.client.service;

import lombok.extern.java.Log;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sbrf.client.dto.BalanceDTO;
import ru.sbrf.server.common.messages.Request;
import ru.sbrf.server.common.messages.RequestTypes;
import ru.sbrf.server.common.messages.Response;
// import com.alibaba.fastjson.JSON;

@Log
@Service
public class ATMService {

    public BalanceDTO getClientBalance(Long clientId, Long accountId, int PIN) {

        RestTemplate restTemplate = new RestTemplate();
//        HttpEntity<Request> request = new HttpEntity<>(new Request(1, "{\"clientId\":1,\"accountId\":0,\"pin\":123}", RequestTypes.JSON));
        HttpEntity<Request> request = new HttpEntity<>(new Request(1, "{\"clientId\":1,\"accountId\":1,\"pin\":30}", RequestTypes.JSON));

        log.info("request.toString()" + request.toString());

        Response response = restTemplate.postForObject(
                "http://127.0.0.1:8080/hosts/1/clients/"+ clientId, request, Response.class);
        log.info("responseEntityStr.getBody()" + response.getBalance());

        return new BalanceDTO(response.getBalance() );

//        TODO убрать потом.
//        ResponseEntity<String> responseEntityStr = restTemplate.
//                postForEntity("http://127.0.0.1:8080/hosts/1/clients/" + clientId,
//                        request, String.class);
//
//        String ResponseJson = responseEntityStr.getBody();
//        log.info("responseEntityStr.getBody()" + ResponseJson);
//
//        return new BalanceDTO(JSON.parseObject(ResponseJson, Response.class).getBalance() );
    }
}
