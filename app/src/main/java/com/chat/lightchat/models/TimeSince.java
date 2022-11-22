package com.chat.lightchat.models;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.firebase.Timestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeSince {
//    public static final int LANGUAGE_ENGLISH = 1;
//    public static final int LANGUAGE_VIETNAM = 2;
//    private static final String before_vn = " trước";
//    private static final String before_en = " before";
//    Decrapted
//    public static String from(Timestamp pastTime){
//        Date past = pastTime.toDate();
//        Date now = Timestamp.now().toDate();
//        if (now.getYear() != past.getYear()){
//            return past.getDate()+"/"+past.getMonth()+"/"+past.getYear();
//        }
//        if (now.getMonth() != past.getMonth()){
//            return (now.getMonth()-past.getMonth())+" trước";
//        }
//        if (now.getDate() != past.getDate()){
//            return (now.getDate() - past.getDate())+" trước";
//        }
//        if (now.getHours() != past.getHours()){
//            return past.getHours() +":"+past.getMinutes();
//        }
//        if (now.getMinutes() +)
//
//
//        if (now.getDate() == past.getDate() && now.getMonth() == past.getMonth() && now.getYear() == past.getYear()){
//            return now.getHours()+":"+now.getMinutes();
//        }
//    }
    @SuppressLint({"SuspiciousIndentation", "DefaultLocale"})
    public static String from(Timestamp pastTime) {
        Date time = pastTime.toDate();
        long diff = Timestamp.now().getSeconds() - pastTime.getSeconds();
        long mins = diff / (60) % 60;
        long hours = diff / (60 * 60 )% 24;
        long days = diff/(60*60*24)%30;
        long months = diff/(60*60*24*30)%12;
        long years = diff/(60*60*24*30)%365;

        if (years > 0)
            return years+" năm trước";
        if (months > 0)
            return months+" tháng trước";
        if (days > 0)
            return days+" ngày trước";
        if (hours>8)
            return hours+" giờ trước";
        return String.format("%02d:%02d", time.getHours(), time.getMinutes());
//        return time.getHours()+":"+time.getMinutes();
//        if (mins>0)
//            return mins+" phút trước";
//        return diff+" giây trước";
    }
}
