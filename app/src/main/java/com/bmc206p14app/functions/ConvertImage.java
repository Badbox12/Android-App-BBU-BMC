package com.bmc206p14app.functions;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

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

    public  static String ImageToString(Bitmap image){
        ByteArrayOutputStream byteArr = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG,100,byteArr);
        byte[] img = byteArr.toByteArray();
        return Base64.encodeToString(img,Base64.DEFAULT);
    }
}
