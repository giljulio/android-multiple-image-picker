package com.giljulio.imagepicker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.loopj.android.image.SmartImage;
import com.loopj.android.image.SmartImageTask;
import com.loopj.android.image.SmartImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Gil on 05/03/2014.
 */
public class SmarterImageView extends SmartImageView {


    private static final String TAG = SmarterImageView.class.getSimpleName();

    public SmarterImageView(Context context) {
        super(context);
    }

    public SmarterImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setImageFile(File file, int orientation) {
        setImage(new FileImage(file, orientation));
    }

    public void setImageFile(File file, int orientation, final Integer fallbackResource) {
        setImage(new FileImage(file, orientation), fallbackResource);
    }

    public void setImageFile(File file, int orientation, final Integer fallbackResource, final Integer loadingResource) {
        setImage(new FileImage(file, orientation), fallbackResource, loadingResource);
    }


    //Squares the thumbnail
    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec){
        setMeasuredDimension(widthMeasureSpec, widthMeasureSpec);
    }

}
