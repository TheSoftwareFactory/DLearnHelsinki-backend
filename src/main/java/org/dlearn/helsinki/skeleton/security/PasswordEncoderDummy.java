package org.dlearn.helsinki.skeleton.security;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author Eero
 */
public class PasswordEncoderDummy implements PasswordEncoder {

    // Disables encoding
    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    // Password always matches
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return true;
    }

}