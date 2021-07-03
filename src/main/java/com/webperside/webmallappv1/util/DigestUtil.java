package com.webperside.webmallappv1.util;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DigestUtil {

    public static String md5(String s) throws NoSuchAlgorithmException {
        byte[] digest = MessageDigest.getInstance("MD5").digest(s.getBytes());

        return DatatypeConverter.printHexBinary(digest).toUpperCase();
    }
}
