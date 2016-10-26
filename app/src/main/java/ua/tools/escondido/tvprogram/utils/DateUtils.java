package ua.tools.escondido.tvprogram.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by artem.shevchuk on 10/21/2016.
 */
public class DateUtils {

    public static String formatChannelAccessDate(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        return dateFormat.format(date);
    }
}
