package com.example.quizapp;

import android.content.Context;
import android.content.SharedPreferences;

public class module {

    public static boolean isPlaySound = true;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Name = "app_IsPlaySound";


    public static boolean getIsPlaySound() {
        return isPlaySound;
    }


    public static void setIsPlaySound(boolean IsPlaySound) {
        isPlaySound = IsPlaySound;
    }
}
