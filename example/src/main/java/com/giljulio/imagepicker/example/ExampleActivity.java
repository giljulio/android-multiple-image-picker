package com.giljulio.imagepicker.example;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.giljulio.imagepicker.ui.ImagePickerActivity;

public class ExampleActivity extends Activity {

    private static final String TAG = ExampleActivity.class.getSimpleName();

    private static final int RESULT_IMAGE_PICKER = 9000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.example, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private TextView mActivityResultsTextView;


        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_example, container, false);

            mActivityResultsTextView = (TextView) rootView.findViewById(R.id.result);
            Button button = (Button)rootView.findViewById(R.id.pick_images);
            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
                    startActivityForResult(intent, RESULT_IMAGE_PICKER);
                }
            });
            return rootView;
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            Log.d(TAG, "RequestCode: " + requestCode + "  ResultCode: " + resultCode);
            switch (requestCode){
                case RESULT_IMAGE_PICKER:
                    if(resultCode == Activity.RESULT_OK){
                        Parcelable[] parcelableUris = data.getParcelableArrayExtra(ImagePickerActivity.TAG_IMAGE_URI);
                        Uri[] uris = new Uri[parcelableUris.length];
                        System.arraycopy(parcelableUris, 0, uris, 0, parcelableUris.length);
                        for(Uri uri : uris)
                            mActivityResultsTextView.setText( mActivityResultsTextView.getText() + " " + uri.getPath());
                    }
                    break;

                default:
                    super.onActivityResult(requestCode, resultCode, data);
                    break;
            }
        }
    }


}
