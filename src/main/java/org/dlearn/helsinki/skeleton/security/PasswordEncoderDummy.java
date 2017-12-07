package org.dlearn.helsinki.skeleton.security;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author Eero
 */
public class PasswordEncoderDummy implements PasswordEncoder {

    final int B_ITERATIONS = 10;
    BCrypt b;
    ShaPasswordEncoder sha;
    List<String> cache;

    public PasswordEncoderDummy() {
        b = new BCrypt();
        sha = new ShaPasswordEncoder();
        cache = new ArrayList();

        Timer timer = new Timer();
        
        // Doesn't work well with these http threads
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                cache = new ArrayList();
                Date d = new Date();
                System.out.println(d + ": Cache cleared");
            }
        };
        timer.scheduleAtFixedRate(task, 300000, 300000);
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return this.encode(rawPassword, BCrypt.gensalt(B_ITERATIONS));
    }

    public String encode(CharSequence rawPassword, String salt) {
        return BCrypt.hashpw(rawPassword.toString(), salt);
    }

    public String encode_fast(CharSequence rawPassword, String salt) {
        return sha.encodePassword(rawPassword.toString(), generateSalt(salt));
    }

    public boolean match_fast(CharSequence rawPassword, String encoded, String salt) {
        return sha.isPasswordValid(encoded, rawPassword.toString(), generateSalt(salt));
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {

        for (String hash : cache) {
            if (match_fast(encodedPassword + rawPassword, hash, encodedPassword)) {
                System.out.println("matched: " + cache);
                return true;
            }
        }
        String slowHash = this.encode(rawPassword, encodedPassword);
        if (compareHashes(slowHash, encodedPassword)) {
            cache.add(this.encode_fast(encodedPassword + rawPassword, encodedPassword));
            System.out.println("added cache pwd " + rawPassword);
            return true;
        } else {
            return false;
        }
    }

    private boolean compareHashes(String a, String b) {
        char[] caa = a.toCharArray();
        char[] cab = b.toCharArray();

        if (caa.length != cab.length) {
            return false;
        }

        byte ret = 0;
        for (int i = 0; i < caa.length; i++) {
            ret |= caa[i] ^ cab[i];
        }
        return ret == 0;
    }

    // Todo: implement something clever :) used only for stored passwords in RAM
    private String generateSalt(String salt) {
        return salt
                + salt.substring(6)
                + salt.substring(12)
                + salt.substring(20)
                + salt.substring(17)
                + "w!$kmBPDf7'9.gQ7";
    }
}
