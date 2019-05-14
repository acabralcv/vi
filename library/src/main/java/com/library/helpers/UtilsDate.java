package com.library.helpers;

import javax.annotation.Nullable;
import javax.validation.constraints.Null;
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

    protected static LocalDateTime _date;
    protected static String timeZone = "Europe/Madrid"; //mudar para cabo verde

    public static Date getDateTime(){

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(timeZone));
        Date currentDate = calendar.getTime();

        return currentDate;
    }

    /**
     * UtilsDate.getFormattedDate(dateCreated, "dd MM yyyy")
     * @param date
     * @return
     */
    public static String getFormattedDate(@Nullable Date date){

        if(date == null)
            return "";

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(timeZone));
        return sdf.format(calendar.getTime());
    }

    /**
     * UtilsDate.getFormattedDate(dateCreated, "dd MM yyyy")
     * @param date
     * @param format
     * @return
     */
    public static String getFormattedDate(@Nullable Date date, @Nullable String format){

        if(date == null)
            return "";

        if(format == null ||  format.isEmpty())
            format = "dd/MM/yyyy";

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(timeZone));
        return sdf.format(calendar.getTime());
    }

}
