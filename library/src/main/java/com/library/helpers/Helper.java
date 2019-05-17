package com.library.helpers;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.UUID;

public class Helper {

    public static int STATUS_ACTIVE = 1;
    public static int STATUS_DISABLED = 0;

    /**
     * Generate a unique GUUID
     * @param lenght
     * @return
     */
    public String genToken(Integer lenght) {

        SecureRandom secureRandom = new SecureRandom();
        byte[] token = new byte[lenght];
        secureRandom.nextBytes(token);

        return new BigInteger(1, token).toString(16); //hex encoding
    }


    public UUID getUUID() {

        return UUID.randomUUID();
    }
}