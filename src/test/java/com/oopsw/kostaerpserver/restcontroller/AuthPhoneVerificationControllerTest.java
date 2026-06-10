package com.oopsw.kostaerpserver.restcontroller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.oopsw.kostaerpserver.advice.GlobalRestExceptionHandler;
import com.oopsw.kostaerpserver.advice.restcontroller.AuthRestController;
import com.oopsw.kostaerpserver.repository.dao.UserInfoDAO;
import com.oopsw.kostaerpserver.service.PhoneVerificationService;
import com.oopsw.kostaerpserver.vo.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class AuthPhoneVerificationControllerTest {

    private PhoneVerificationService phoneVerificationService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        phoneVerificationService = new PhoneVerificationService(new UserInfoDaoStub(), (phone, code) -> {
        });
        AuthRestController controller =
            new AuthRestController(null, phoneVerificationService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setControllerAdvice(new GlobalRestExceptionHandler())
            .build();
    }

    @Test
    void sendsAndVerifiesCodeInSameSession() throws Exception {
        MockHttpSession session = new MockHttpSession();

        mockMvc.perform(post("/api/auth/phone/code")
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"phone":"010-1234-5678"}
                    """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true));

        String code = (String) session.getAttribute("phoneVerification.code");

        mockMvc.perform(post("/api/auth/phone/verify")
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"phone":"01012345678","code":"%s"}
                    """.formatted(code)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void rejectsRegistrationBeforePhoneVerification() throws Exception {
        MockMultipartFile request = new MockMultipartFile(
            "request",
            "",
            MediaType.APPLICATION_JSON_VALUE,
            """
                {"bId":"1234567890","pw":"password","phone":"01012345678"}
                """.getBytes()
        );
        MockMultipartFile document = new MockMultipartFile(
            "document",
            "license.png",
            MediaType.IMAGE_PNG_VALUE,
            "image".getBytes()
        );

        mockMvc.perform(multipart("/api/auth/register")
                .file(request)
                .file(document)
                .session(new MockHttpSession()))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("휴대폰 인증을 완료해주세요."));
    }

    private static class UserInfoDaoStub implements UserInfoDAO {

        @Override
        public int getPhoneCheck(String phone) {
            return 0;
        }

        @Override
        public User login(String bId, String pw) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int register(User user) {
            throw new UnsupportedOperationException();
        }

        @Override
        public User checkMemberByVO(String bId, String name, String pw) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int setPw(String pw, String bId, String name, String phone) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getBidCheck(String bId) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int checkPwFindUser(String bId, String name, String phone) {
            throw new UnsupportedOperationException();
        }
    }
}
