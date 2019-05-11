package com.library.helpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class UtilsDate {

    private LocalDateTime _date;

    public static Date getDateTime(){

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Madrid"));
        Date currentDate = calendar.getTime();

        return currentDate;
    }

    /**
     * UtilsDate.getFormattedDate(dateCreated, "dd MM yyyy")
     * @param date
     * @param format
     * @return
     */
    public static String getFormattedDate(Date date, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Madrid"));
        return sdf.format(calendar.getTime());
    }

}
