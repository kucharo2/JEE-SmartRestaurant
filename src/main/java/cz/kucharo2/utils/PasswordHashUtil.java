package cz.kucharo2.utils;

import org.jboss.logging.Logger;

import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Copyright 2017 IEAP CTU
 * Author: Jakub Begera (jakub.begera@cvut.cz)
 */
public class PasswordHashUtil {

    private PasswordHashUtil() {
        // util class should never be instanced
    }

    private static Logger logger = Logger.getLogger(PasswordHashUtil.class.getName());

    public static String encrypt(@NotNull String passwordOriginal) {
        String password = "<|>?p!*&" + passwordOriginal + ".§";

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes("UTF-8"));

            byte byteData[] = md.digest();

            StringBuffer sb = new StringBuffer();
            for (byte aByteData : byteData) {
                sb.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            logger.error("Algorithm MD5 not found or UFT-8 is not supported", e);
            e.printStackTrace();
            return null;
        }
    }

}
