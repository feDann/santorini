package it.polimi.ingsw.PSP11.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeStamp {

    public static String getTimeSTamp(){
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Calendar.getInstance().getTime());
        return "[" + timeStamp +"] ";
    }

}
