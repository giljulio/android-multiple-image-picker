package com.giljulio.imagepicker;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.io.File;

/**
 * Created by Gil on 04/03/2014.
 */
public class ThumbnailFragment extends Fragment {

    private static final String TAG = ThumbnailFragment.class.getSimpleName();

    GridView mGalleryGridView;
    ImageGalleryAdapter mGalleryAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_thumbnail, container, false);

        Log.d(TAG, "onCreateView");
        mGalleryAdapter = new ImageGalleryAdapter(getActivity());
        mGalleryGridView = (GridView)rootView.findViewById(R.id.gallery_grid);


        final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID, MediaStore.Images.ImageColumns.ORIENTATION };
        final String orderBy = MediaStore.Images.Media._ID;
        Cursor imageCursor = getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);
        while(imageCursor.moveToNext()){
            Uri uri = Uri.parse(imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA)));
            int orientation = imageCursor.getInt(imageCursor.getColumnIndex(MediaStore.Images.ImageColumns.ORIENTATION));
            mGalleryAdapter.add(new Image(uri, orientation));
        }
        imageCursor.close();


        mGalleryGridView.setAdapter(mGalleryAdapter);
        mGalleryGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Image image = mGalleryAdapter.getItem(i);
                image.mSelected = !image.mSelected;
                if(image.mSelected){
                    ((MainActivity)getActivity()).mSelectedImageMap.put(image.mUri, image);
                    view.setBackgroundResource(android.R.color.holo_blue_light);
                    view.setPadding(15, 15, 15, 15);
                } else {
                    ((MainActivity)getActivity()).mSelectedImageMap.remove(image.mUri);
                    view.setPadding(0, 0, 0, 0);
                }
            }
        });

        return rootView;
    }

    class ViewHolder {
        SmarterImageView mThumbnail;
    }

    public class ImageGalleryAdapter extends ArrayAdapter<Image> {

        public ImageGalleryAdapter(Context context) {
            super(context, 0);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.gridview_thumbnail, null);
                holder = new ViewHolder();
                holder.mThumbnail = (SmarterImageView)convertView.findViewById(R.id.thumbnail_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }


            Image image = getItem(position);


            if(image.mSelected){
                convertView.setBackgroundResource(android.R.color.holo_blue_light);
                convertView.setPadding(15, 15, 15, 15);
            } else {
                convertView.setPadding(0, 0, 0, 0);
            }

            holder.mThumbnail.setImageFile(new File(image.mUri.toString()), image.mOrientation, android.R.color.transparent, android.R.color.darker_gray);


            return convertView;
        }
    }

}