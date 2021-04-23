package com.helixo.stopwatch.util;

public class TimeFormatUtil {

    public static String toDisplayString(int timeHundreds) {
        int hundreds, seconds, minutes;

        String[] formatterArrayMillis = new String[2];
        String formattedSeconds, formattedMinutes;

        hundreds = timeHundreds % 100;
        String milliSecStr = Integer.toString(hundreds);
        formatterArrayMillis[0] = "0" + milliSecStr;
        formatterArrayMillis[1] = milliSecStr;

        seconds = (timeHundreds /= 100) % 60;
        minutes = (timeHundreds /= 60) % 60;

        formattedSeconds = Integer.toString(seconds / 10) + seconds % 10;
        formattedMinutes = Integer.toString(minutes / 10) + minutes % 10;

        int millSecDigitsCnt = milliSecStr.length();

		return formattedMinutes + ":" +
			formattedSeconds + "." +
			formatterArrayMillis[millSecDigitsCnt - 1];
    }

}