package com.junior.davino.ran.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by davin on 26/02/2017.
 */

public class Util {

    public static String toTitleCase(String str) {

        if (str == null) {
            return null;
        }

        boolean space = true;
        StringBuilder builder = new StringBuilder(str);
        final int len = builder.length();

        for (int i = 0; i < len; ++i) {
            char c = builder.charAt(i);
            if (space) {
                if (!Character.isWhitespace(c)) {
                    // Convert to title case and switch out of whitespace mode.
                    builder.setCharAt(i, Character.toTitleCase(c));
                    space = false;
                }
            } else if (Character.isWhitespace(c)) {
                space = true;
            } else {
                builder.setCharAt(i, Character.toLowerCase(c));
            }
        }

        return builder.toString();
    }

    public static boolean isEqualStripAccentsIgnoreCase(String word1, String word2) {
        return StringUtils.stripAccents(word1).equalsIgnoreCase(StringUtils.stripAccents(word2));
    }

    public static boolean isPermissionOk(Activity activity, String permission) {
        int audioPermission = ContextCompat.checkSelfPermission(activity, permission);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (audioPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(permission);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(activity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
            return true;
        }

        return true;
    }

    public static Date getCurrentDateTime(){
        Calendar calendar = Calendar.getInstance();
        Date date =  calendar.getTime();
        return date;
    }

    public static String getCurrentDateTimeFormatted(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy_HH:mm");
        return df.format(getCurrentDateTime());
    }

}
