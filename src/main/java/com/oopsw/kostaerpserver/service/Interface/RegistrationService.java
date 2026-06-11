package com.oopsw.kostaerpserver.service.Interface;

import com.oopsw.kostaerpserver.dto.auth.RegisterRequest;
import com.oopsw.kostaerpserver.dto.auth.RegistrationResponse;
import org.springframework.web.multipart.MultipartFile;

public interface RegistrationService {

    RegistrationResponse register(RegisterRequest request, MultipartFile file);
}
