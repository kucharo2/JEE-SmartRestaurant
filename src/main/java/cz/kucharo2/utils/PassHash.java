package cz.kucharo2.utils;

import javax.validation.constraints.NotNull;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Copyright 2017 IEAP CTU
 * Author: Jakub Begera (jakub.begera@cvut.cz)
 */
public class PassHash {

    public static String encrypt(@NotNull String passwordOriginal) {
        String password = "<|>?p!*&" + passwordOriginal + ".§";

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(password.getBytes());

            byte byteData[] = md.digest();

            StringBuffer sb = new StringBuffer();
            for (byte aByteData : byteData) {
                sb.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}
