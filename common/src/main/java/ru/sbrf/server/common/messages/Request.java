package ru.sbrf.server.common.messages;

import lombok.Value;

import java.util.Date;

@Value
public class Request {
    String cardNum;
    int pinCode;
}
