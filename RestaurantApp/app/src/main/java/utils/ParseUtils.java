package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Swomfire on 21-Mar-18.
 */

public class ParseUtils {

    public static Date parseStringToDate(String dateString) {
        return new Date(dateString.substring(0, 9));
    }

    public static String parseDateToStringFormat(Date date) {
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }
}
