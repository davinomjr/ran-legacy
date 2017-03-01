package com.junior.davino.ran.utils;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by davin on 28/02/2017.
 */

public class Toaster {

    Toast latestToast;

    public void showToastMessage(Activity activity, String message){
        showToastMessage(activity, message, Toast.LENGTH_LONG);
    }


    public void showToastMessage(Activity activity, String message, int lengthToastCode){
        if(latestToast != null){
            latestToast.cancel();
        }

        latestToast = Toast.makeText(activity, message, lengthToastCode);
        latestToast.show();
    }

}
