package com.oopsw.kostaerpserver.dto;

public record PhoneCheckResponse (
    boolean available,
    String message
){
}
