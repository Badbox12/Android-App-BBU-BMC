package com.bmc206p14app.functions;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Formatter;

public class EncryptPassword {
    public static String MD5(String password){
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.reset();
            digest.update(password.getBytes(StandardCharsets.UTF_8));
            
            return ConvertToHex(digest.digest());
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    private static String ConvertToHex(byte[] digest) {
        Formatter frm = new Formatter();
            // declaration // ArrayName
        for(byte b : digest){
            frm.format("%02x",b);
        }
        String result = frm.toString();
        frm.close();
        return result;
    }
}
