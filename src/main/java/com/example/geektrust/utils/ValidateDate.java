package com.example.geektrust.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static com.example.geektrust.constants.SubscriptionConstant.DATE_FORMAT;

public class ValidateDate {
    public static boolean validateSubscriptionDate(String strDate) {
        if (strDate.trim().equals("")) {
            return false;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            sdf.setLenient(false);
            try {
                sdf.parse(strDate);
            } catch (ParseException e) {
                return false;
            }
            return true;
        }
    }
}
