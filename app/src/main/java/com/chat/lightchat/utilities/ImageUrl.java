package com.chat.lightchat.utilities;

import android.net.Uri;

public class ImageUrl {
    private static final String defaultImage = "https://icons-for-free.com/iconfiles/png/512/profile+profile+page+user+icon-1320186864367220794.png";

    public static String toString(Uri uri){
        if (uri != null){
            return uri.toString();
        }
        return "";
    }

    public static String getImage(String imgLink){
        if (imgLink.isEmpty()){
            return defaultImage;
        }
        return imgLink;
    }

    public static String getImage(Uri uri){
        if (uri == null){
            return defaultImage;
        }
        return uri.toString();
    }

}
