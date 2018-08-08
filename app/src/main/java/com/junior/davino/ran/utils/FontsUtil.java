package com.junior.davino.ran.utils;

import android.content.Context;
import android.graphics.Typeface;

import java.lang.reflect.Field;
import java.util.Arrays;

public final class FontsUtil {

    public static final String[] FONTS = {"Arial","Calibri","TimesNewRoman","OpenDyslexic"};

    public static void setDefaultFont(Context context,String staticTypefaceFieldName, String fontAssetName) {
        final Typeface regular = Typeface.createFromAsset(context.getAssets(), fontAssetName);
        replaceFont(staticTypefaceFieldName, regular);
    }

    protected static void replaceFont(String staticTypefaceFieldName,
                                      final Typeface newTypeface) {
        try {
            final Field staticField = Typeface.class
                    .getDeclaredField(staticTypefaceFieldName);
            staticField.setAccessible(true);
            staticField.set(null, newTypeface);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static int fontNameToNumber(String fontName){
        return Arrays.asList(FONTS).indexOf(fontName);
    }

    public static String fontNumberToName(int fontOrder){
        return FONTS[fontOrder];
    }

}