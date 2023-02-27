package com.bmc206p14app.functions;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class ConvertImage {
    public static Bitmap StringToImage(String image){
        try {
                Bitmap bitmap = null;
                byte[] images = Base64.decode(image, Base64.DEFAULT);
                bitmap = BitmapFactory.decodeByteArray(images,0,images.length);
                return bitmap;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
}
