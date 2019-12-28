package com.tz.authserver;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;

@SpringBootTest
public class AuthServerApplicationTests {

    @Test
    public void contextLoads() {
    }
    @Test
    public void createBCryptPw(){
        String hashpw = BCrypt.hashpw("123", BCrypt.gensalt());
        System.out.println(hashpw);
    }

}
