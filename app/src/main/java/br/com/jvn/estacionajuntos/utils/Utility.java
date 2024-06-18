package br.com.jvn.estacionajuntos.utils;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {
    public static boolean isValidEmail(CharSequence target){
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static boolean isValidPassword(String target){
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])" + //numeros
                "(?=.*[A-Z])" + //letras maiúsculas
                "(?=\\S+$)" + //sem espaço
                ".{4,24}$"; //tamanho?
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(target);

        return matcher.matches();
    }

    public static String convertMD5(String target){
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(target.getBytes());
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();
            for(byte b : digest){
                sb.append(String.format("%02x",b & 0xff));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            Log.i("convertMD5","falha ao converter: "+ ex.getMessage());
        }

        return null;
    }
}
