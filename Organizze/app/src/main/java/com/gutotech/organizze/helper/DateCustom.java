package com.gutotech.organizze.helper;

import java.text.SimpleDateFormat;

public class DateCustom {

    public static String dataAtual() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d/MM/yyyy");
        return simpleDateFormat.format(System.currentTimeMillis());
    }

    public static String mesAno(String data){
        String[] tokens= data.split("/");
        return tokens[1] + tokens[2];
    }
}
