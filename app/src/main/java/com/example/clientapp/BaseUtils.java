package com.example.clientapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class BaseUtils {
    public static boolean isValidBase64(String base64String) {
        if (base64String == null || base64String.isEmpty()) {
            return false;
        }

        try {
            // Decode the Base64 string into a byte array
            byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);

            // Attempt to create a Bitmap from the byte array
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

            // Check if the Bitmap is not null, indicating successful decoding
            return bitmap != null;
        } catch (Exception e) {
            // An exception occurred during decoding
            return false;
        }
    }
}
