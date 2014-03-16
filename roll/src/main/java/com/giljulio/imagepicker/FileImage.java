package com.giljulio.imagepicker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Log;

import com.loopj.android.image.SmartImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Gil on 05/03/2014.
 */
public class FileImage implements SmartImage {

    private static final String TAG = FileImage.class.getSimpleName();

    private File mFile;
    private int mOrientation;

    public FileImage(File file, int orientation){
        this.mFile = file;
        this.mOrientation = orientation;
    }

    @Override
    public Bitmap getBitmap(Context context) {

        Bitmap bitmap = decodeFile(mFile);
        if (mOrientation > 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(mOrientation);

            Log.d(TAG, "orientation: " + mOrientation);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }

        return bitmap;
    }

    private Bitmap decodeFile(File f){
        try {
            //Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            //The new size we want to scale to
            final int REQUIRED_SIZE=70;

            //Find the correct scale value. It should be the power of 2.
            int scale=1;
            while(o.outWidth/scale/2>=REQUIRED_SIZE && o.outHeight/scale/2>=REQUIRED_SIZE)
                scale*=2;

            //Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {}
        return null;
    }
}
