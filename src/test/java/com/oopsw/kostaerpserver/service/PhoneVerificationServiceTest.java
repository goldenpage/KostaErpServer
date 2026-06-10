package com.oopsw.kostaerpserver.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.oopsw.kostaerpserver.repository.dao.UserInfoDAO;
import com.oopsw.kostaerpserver.vo.User;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;

class PhoneVerificationServiceTest {

    @Test
    void verifiesGeneratedCodeAndRequiresSamePhone() {
        PhoneVerificationService service = serviceWithPhoneCount(0);
        MockHttpSession session = new MockHttpSession();

        service.sendCode("010-1234-5678", session);
        String code = (String) session.getAttribute("phoneVerification.code");
        service.verify("01012345678", code, session);

        assertEquals("01012345678", service.requireVerified("010-1234-5678", session));
        assertThrows(
            IllegalArgumentException.class,
            () -> service.requireVerified("01099999999", session)
        );
    }

    @Test
    void rejectsAlreadyRegisteredPhone() {
        PhoneVerificationService service = serviceWithPhoneCount(1);

        assertThrows(
            IllegalArgumentException.class,
            () -> service.sendCode("01012345678", new MockHttpSession())
        );
    }

    @Test
    void rejectsResendDuringCooldown() {
        PhoneVerificationService service = serviceWithPhoneCount(0);
        MockHttpSession session = new MockHttpSession();

        service.sendCode("01012345678", session);

        assertThrows(
            IllegalArgumentException.class,
            () -> service.sendCode("01012345678", session)
        );
    }

    private PhoneVerificationService serviceWithPhoneCount(int phoneCount) {
        return new PhoneVerificationService(new UserInfoDaoStub(phoneCount), (phone, code) -> {
        });
    }

    private static class UserInfoDaoStub implements UserInfoDAO {

        private final int phoneCount;

        private UserInfoDaoStub(int phoneCount) {
            this.phoneCount = phoneCount;
        }

        @Override
        public int getPhoneCheck(String phone) {
            return phoneCount;
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
