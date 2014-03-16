package com.giljulio.imagepicker;

import android.net.Uri;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

/**
 * Created by Gil on 06/03/2014.
 */
public class SelectedImageMap extends HashMap<Uri, Image> {

    private Callback cb;

    public interface Callback {
        public void onAdd(Image image);
        public void onRemove(Image image);
    }

    public SelectedImageMap(Callback cb){
        this.cb = cb;
    }

    @Override
    public Image put(Uri key, Image image) {
        if(this.get(key) == null){
            super.put(key, image);
            cb.onAdd(image);
        }
        return image;
    }

    @Override
    public Image remove(Object key) {
        Image image = this.get(key);
        if(image != null){
            super.remove(key);
            cb.onRemove(image);
        }
        return image;
    }

}
