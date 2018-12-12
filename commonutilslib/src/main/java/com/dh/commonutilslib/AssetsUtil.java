package com.dh.commonutilslib;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AssetsUtil {


    public static byte[] readAssets(Context context, String fileName) {
        ByteArrayOutputStream output = null;
        InputStream is = null;
        try {
            output = new ByteArrayOutputStream();
            is = context.getAssets().open(fileName);
            byte[] buffer = new byte[2048];
            int length = -1;
            while ((length = is.read(buffer)) != -1) {
                output.write(buffer, 0, length);
            }
            return output.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
