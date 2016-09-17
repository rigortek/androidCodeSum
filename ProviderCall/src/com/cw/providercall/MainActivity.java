package com.cw.providercall;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
    Button mSingleWeatherBt;
    Button mArrayListWeatherBt;
    MyOnClickListener mMyOnClickListener;
    Uri mUri = Uri.parse("content://cw.weather");

	public class MyOnClickListener implements OnClickListener {
        public MyOnClickListener(MainActivity activity) {
            // TODO Auto-generated constructor stub
            mMyActivity = activity;
        }
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch(v.getId()) {
            case R.id.single_weatehr_bt: 
                mMyActivity.getSingleWeather();
                break;
            case R.id.arraylist_weather_bt:
                mMyActivity.getArrayListWeather();
                break;
            default:
                break;
            }
        }
        
        private MainActivity mMyActivity;
    }
    
    public void getSingleWeather() {
        ContentResolver mContentResolver = getContentResolver();
        String method = "getSingleWeather";
        Bundle bundle = mContentResolver.call(mUri, method, null, null);
        Weather weather = bundle.getParcelable(method);
        if (null != weather) {
            Log.i("cw", "Weather : " + weather.toString());
        } else {
            Log.i("cw", "error getSingleWeather");
        }
    }
    
    public void getArrayListWeather() {
        ContentResolver mContentResolver = getContentResolver();
        String method = "getArrayListWeather";
        Bundle bundle = mContentResolver.call(mUri, method, null, null);
        ArrayList<Weather> weathers = bundle.getParcelableArrayList(method);
        if (null != weathers) {
            for (int i = 0; i < weathers.size(); ++i) {
                Weather weather = weathers.get(i);
                Log.i("cw", "Weather[" + i + "] : " + weather.toString());
            }
        } else {
            Log.e("cw", "error getArrayListWeather");
        }
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mMyOnClickListener = new MyOnClickListener(this);
        mSingleWeatherBt = (Button)findViewById(R.id.single_weatehr_bt);
        mArrayListWeatherBt = (Button)findViewById(R.id.arraylist_weather_bt);
        if (null != mSingleWeatherBt) {
            mSingleWeatherBt.setOnClickListener(mMyOnClickListener);
        }
        if (null != mArrayListWeatherBt) {
            mArrayListWeatherBt.setOnClickListener(mMyOnClickListener);
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
}
