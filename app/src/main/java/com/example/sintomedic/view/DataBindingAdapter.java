package com.example.sintomedic.view;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataBindingAdapter {

    @BindingAdapter("android:visibility")
    public static void setVisibility(View view, Boolean visibility) {
        view.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }


    @BindingConversion
    public static String formatDate(Date date) {
        if (date == null) {
            return "";
        }

        return new SimpleDateFormat("d MMM yyyy").format(date);
    }

}
