package tld.petbot.tgbot.Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateFormat {
    public static java.util.Date parseDate(String dateStr, SimpleDateFormat sdf) {
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
