package com.cw.webviewsummary;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ViewPageActivity extends AppCompatActivity {

    public static final String TAG = "ViewPageActivity";

    private ViewPager mViewPager;
    private MyPagerAdapter mMyPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_page);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d(TAG, "onPageScrolled: postion:" + position + ", positionOffset:" + positionOffset + ", positionOffsetPixels:" + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected: position:" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d(TAG, "onPageScrollStateChanged: state:" + state);
            }
        });

        mMyPagerAdapter = new MyPagerAdapter(this);
        mViewPager.setAdapter(mMyPagerAdapter);
    }
}
