package ua.tools.escondido.tvprogram.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static String formatChannelAccessDate(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy", new Locale("UA"));
        return dateFormat.format(date);
    }
    public static String formatDate(String format, Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, new Locale("UA"));
        return dateFormat.format(date);
    }
}
