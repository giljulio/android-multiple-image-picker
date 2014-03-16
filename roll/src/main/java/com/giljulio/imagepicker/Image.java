package com.giljulio.imagepicker;

import android.net.Uri;

/**
 * Created by Gil on 04/03/2014.
 */
public class Image {

    public Uri mUri;
    public int mOrientation;
    public boolean mSelected;

    public Image(Uri uri, int orientation){
        mUri = uri;
        mOrientation = orientation;
    }

    public Image(Uri uri, int orientation, boolean selected){
        mUri = uri;
        mOrientation = orientation;
        mSelected = selected;
    }

}
