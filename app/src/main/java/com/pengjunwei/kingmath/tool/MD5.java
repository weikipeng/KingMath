package com.pengjunwei.kingmath.tool;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by wikipeng on 2017/4/22.
 */

public final class MD5 {
    /**
     * function md5 encryption for passwords
     *
     * @param password
     * @return passwordEncrypted
     */
    public static final String md5(final String password) {
        try {

            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(password.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2) h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
