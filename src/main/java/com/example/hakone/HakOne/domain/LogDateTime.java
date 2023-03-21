package com.example.hakone.HakOne.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogDateTime {
    public String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }
}
