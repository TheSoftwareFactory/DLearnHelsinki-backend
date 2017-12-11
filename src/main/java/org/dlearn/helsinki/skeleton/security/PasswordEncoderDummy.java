package org.dlearn.helsinki.skeleton.security;

import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Eero
 */
public class PasswordEncoderDummy implements PasswordEncoder {

    final int B_ITERATIONS = 12;
    final int SHA_STRENGTH = 256;

    BCrypt b;
    ShaPasswordEncoder sha;
    Set<CacheItem> cache;

    private class CacheItem implements Comparable<CacheItem> {

        private String hash;
        private long accessed;

        public CacheItem(String hash, long accessed) {
            this.hash = hash;
            this.accessed = accessed;
        }

        public long getAccessed() {
            return accessed;
        }

        public void setAccessed(long accessed) {
            this.accessed = accessed;
        }

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        @Override
        public int compareTo(CacheItem o) {
            int h = this.hash.compareTo(o.getHash());
            int a = Long.compareUnsigned(accessed, o.getAccessed());
            return a != 0 ? a * -1 : h; // recently accessed first
        }
    }

    public PasswordEncoderDummy() {
        sha = new ShaPasswordEncoder(SHA_STRENGTH);
        cache = new TreeSet(
                (Comparator<CacheItem>) (o1, o2) -> o2.compareTo(o1));
    }

    @Override
    public String encode(CharSequence raw) {
        return this.encode(raw, BCrypt.gensalt(B_ITERATIONS));
    }

    private String encode(CharSequence raw, String salt) {
        return BCrypt.hashpw(raw.toString(), salt);
    }

    private String encode_fast(CharSequence raw, String salt) {
        return sha.encodePassword(raw.toString(), salt);
    }

    private boolean match_fast(CharSequence raw, String encoded, String salt) {
        return sha.isPasswordValid(encoded, raw.toString(), salt);
    }

    @Override
    public boolean matches(CharSequence raw, String encoded) {
        // TODO: remove this, (left for plain text passwords)
        if (raw.toString().equals(encoded))
            return true;

        for (Iterator<CacheItem> iter = cache.iterator(); iter.hasNext();) {
            CacheItem c = iter.next();
            Date time = new Date();
            // Remove expired (>5min) hashes before trying to match them
            if (time.getTime() - c.getAccessed() > (5 * 60 * 1000)) {
                iter.remove();
                continue;
            }
            if (match_fast(encoded + raw, c.getHash(), encoded)) {
                c.setAccessed(time.getTime()); //accessed recently
                return true;
            }
        }

        // Slow hash check, uses BCrypt to check if raw is matched to encoded
        String slowHash = this.encode(raw, encoded);
        if (compareHashes(slowHash, encoded)) {
            String fastHash = this.encode_fast(encoded + raw, encoded);
            if (!cacheHasHash(fastHash)) {
                cache.add(new CacheItem(fastHash, new Date().getTime()));
            }
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

    private boolean cacheHasHash(String hash) {
        return cache.stream()
                .anyMatch((cacheItem) -> (cacheItem.getHash().equals(hash)));
    }
}
