package com.giljulio.imagepicker;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.Locale;

public class MainActivity extends Activity implements ActionBar.TabListener, SelectedImageMap.Callback {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int RESULT_DONE = 40000, RESULT_CANCEL = 40001;

    public SelectedImageMap mSelectedImageMap;
    private LinearLayout mSelectedImagesContainer;
    private FrameLayout mFrameLayout;
    private TextView mSelectedImageEmptyMessage;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mSelectedImageEmptyMessage = (TextView)findViewById(R.id.selected_photos_empty);


        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }

        mFrameLayout = (FrameLayout) findViewById(R.id.selected_photos_container_frame);
        mSelectedImagesContainer = (LinearLayout) findViewById(R.id.selected_photos_container);
        mSelectedImageMap = new SelectedImageMap(this);

    }


    @Override
    public void onAdd(Image image) {
        View rootView = LayoutInflater.from(MainActivity.this).inflate(R.layout.listitem_thumbnail, null);
        SmarterImageView thumbnail = (SmarterImageView)rootView.findViewById(R.id.selected_photo);
        rootView.setTag(image.mUri);
        thumbnail.setImageFile(new File(image.mUri.toString()), image.mOrientation,
                android.R.color.transparent, android.R.color.darker_gray);
        mSelectedImagesContainer.addView(rootView, 0);

        int px = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60,
                getResources().getDisplayMetrics());
        thumbnail.setLayoutParams(new FrameLayout.LayoutParams(px, px));

        if(mSelectedImageMap.size() == 1){
            mSelectedImagesContainer.setVisibility(View.VISIBLE);
            mSelectedImageEmptyMessage.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRemove(Image image) {
        for(int i = 0; i < mSelectedImagesContainer.getChildCount(); i++){
            View childView = mSelectedImagesContainer.getChildAt(i);
            if(childView.getTag().equals(image.mUri)){
                mSelectedImagesContainer.removeViewAt(i);
                break;
            }
        }

        if(mSelectedImageMap.size() == 0){
            mSelectedImagesContainer.setVisibility(View.GONE);
            mSelectedImageEmptyMessage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch(position){
                case 0:
                    return new PhotoFragment();
                case 1:
                    return new ThumbnailFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return "Take a photo";//getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return "Gallery";//getString(R.string.title_section2).toUpperCase(l);
            }
            return null;
        }
    }
}
