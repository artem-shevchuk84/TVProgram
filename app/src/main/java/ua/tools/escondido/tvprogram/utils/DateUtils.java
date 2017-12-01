package ua.tools.escondido.tvprogram.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static final String DISPLAY_DATE_FORMAT = "EEE, d MMM ''yy";

    public static String formatChannelAccessDate(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy", new Locale("uk","UA"));
        return dateFormat.format(date);
    }
    public static String formatDate(String format, Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, new Locale("uk","UA"));
        return dateFormat.format(date);
    }

    public static Date getToday(){
        return new Date(System.currentTimeMillis() - 18000000);
    }
}
