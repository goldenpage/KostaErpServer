package com.oopsw.kostaerpserver.dto.manager;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

public record RegistrationDocument(Resource resource,
                                   MediaType mediaType) {

}
