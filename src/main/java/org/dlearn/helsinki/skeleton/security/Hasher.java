package org.dlearn.helsinki.skeleton.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Hasher {
    // private final static PasswordEncoder HASHER = new BCryptPasswordEncoder(16);

    public static PasswordEncoder getHasher() {
        // return HASHER; requires token..
        return new PasswordEncoderDummy();
    }
}
