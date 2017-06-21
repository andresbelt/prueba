package org.oncreate.prueba.Utils;

import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by azulandres92 on 6/21/17.
 */

public class AndroidUtils {

    public static String getDate(long timeStamp){

       return new java.text.SimpleDateFormat("E, MMM dd").format(new java.util.Date (timeStamp*1000));
    }

    public static String getDateDayNum(long timeStamp){

        return new java.text.SimpleDateFormat("E").format(new java.util.Date (timeStamp*1000));
    }
}
