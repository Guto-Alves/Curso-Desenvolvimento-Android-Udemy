package com.gutotech.whatsapp.helper;

import android.util.Base64;

public class Base64Custom {

    public static String codificar(String text) {
        return Base64.encodeToString(text.getBytes(), Base64.DEFAULT).replaceAll("\\n|\\r", "");
    }

    public static String decodificar(String textoCodificado) {
        return Base64.decode(textoCodificado, Base64.DEFAULT).toString();
    }
}
