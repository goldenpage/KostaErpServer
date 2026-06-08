package com.oopsw.kostaerpserver.dto.auth;

public record PhoneCheckResponse (
    boolean available,
    String message
){
}
