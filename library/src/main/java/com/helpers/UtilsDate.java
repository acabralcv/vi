package com.helpers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UtilsDate {

    private LocalDateTime _date;

    public LocalDateTime getCurrentDate(String format){
        //"yyyy/MM/dd HH:mm:ss"
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        LocalDateTime now = LocalDateTime.now();
        this._date = now;
        return this._date;
    }

}
