package com.cw.providercall;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class SmileContentProvider extends ContentProvider  implements XmlWeatherParse.XmlParseResponse {
    
	@Override
	public void onParseEnd(int result) {
		// TODO Auto-generated method stub
	    Log.i("cw", "onParseEnd, result : " + result);
	}
	
    @Override
    public int delete(Uri arg0, String arg1, String[] arg2) {
        // TODO Auto-generated method stub
        return 0;
    }
    
    @Override
    public String getType(Uri arg0) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public Uri insert(Uri arg0, ContentValues arg1) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public boolean onCreate() {
        // TODO Auto-generated method stub
        mWeather = new Weather();
        mWeather.setForecastDate("20160914");
        mWeather.setMaxTemp("30");
        mWeather.setMinTemp("18");
        mWeather.setStatusEn("Sun");
        mWeather.setStatusZh("Tai Yang");
        mWeather.setPicName("/data/data/com.cw.providercall/Sun.png");
        
//        mWeatherArrayList = new ArrayList<Weather>();
//        
//        Weather todayWeather = new Weather();
//        todayWeather.setForecastDate("20160915");
//        todayWeather.setMaxTemp("28");
//        todayWeather.setMinTemp("20");
//        todayWeather.setStatusEn("Rain");
//        todayWeather.setStatusZh("Yun");
//        todayWeather.setPicName("/data/data/com.cw.providercall/yun.png");
//        
//        Weather tommrowWeather = new Weather();
//        tommrowWeather.setForecastDate("20160916");
//        tommrowWeather.setMaxTemp("31");
//        tommrowWeather.setMinTemp("23");
//        tommrowWeather.setStatusEn("Rain");
//        tommrowWeather.setStatusZh("Yun");
//        tommrowWeather.setPicName("/data/data/com.cw.providercall/rain.png");
//        
//        mWeatherArrayList.add(todayWeather);
//        mWeatherArrayList.add(tommrowWeather);
        
        XmlWeatherParse xmlWeatherParse = new XmlWeatherParse("/data/data/com.cw.providercall/dvb/Common/MultiWeather.xml");
        xmlWeatherParse.registerObserver(this);
        try {
            xmlWeatherParse.syncParse();
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mWeatherArrayList = xmlWeatherParse.getArrayListWeather();
        xmlWeatherParse.unRegisterObserver(this);
        
        return false;
    }
    
    @Override
    public Cursor query(Uri arg0, String[] arg1, String arg2, String[] arg3,
            String arg4) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
        // TODO Auto-generated method stub
        return 0;
    }
    
    
    @Override
    public Bundle call(String method, String arg, Bundle extras) {
        // TODO Auto-generated method stub
        Log.i("cw", "call method : " + method);
        if (null != method && method.equals("getSingleWeather")) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("getSingleWeather", mWeather);
            return bundle;
        } else if (null != method && method.equals("getArrayListWeather")) {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("getArrayListWeather", mWeatherArrayList);
            return bundle;
        }
        return super.call(method, arg, extras);
    }

    private Weather mWeather;
    private ArrayList<Weather> mWeatherArrayList;
}
