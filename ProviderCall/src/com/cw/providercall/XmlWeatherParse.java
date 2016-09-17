package com.cw.providercall;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

public class XmlWeatherParse {
    
	public interface XmlParseResponse {
	    public static final int SUCCESS = 0;
	    public static final int FAIL = -1;
	    
		public void onParseEnd(int result);
	}
	
	public void registerObserver(XmlParseResponse response) {
		synchronized(mXmlParseResponseMap) {
			if (!mXmlParseResponseMap.containsKey(response)) {
//				Log.e("cw", "registerObserver" + response.toString());
				mXmlParseResponseMap.put(response, response);
			}
		}
	}
	public void unRegisterObserver(XmlParseResponse response) {
		synchronized(mXmlParseResponseMap) {
			if (mXmlParseResponseMap.containsKey(response)) {
//				Log.e("cw", "unRegisterObserver" + response.toString());
				mXmlParseResponseMap.remove(response);
			}
		}
	}
	
    public XmlWeatherParse(String url) {
        mUrl = url;
        mXmlParseResponseMap = new HashMap<XmlParseResponse, XmlParseResponse>();
    }
    
    public ArrayList<Weather> getArrayListWeather() {return mWeatherArrayList;}    
    
    public void syncParse() throws XmlPullParserException, IOException {
        XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
        XmlPullParser myParser = xmlFactoryObject.newPullParser();
        myParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);

        File file = new File(mUrl);
        Log.e("cw", "check file exists : " + mUrl);
        if (!file.exists()) {
            Log.e("cw", mUrl + " not exists !!!");
            return;
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        myParser.setInput(fileInputStream, "UTF-8"); //为Pull解释器设置要解析的XML数据
        
        parse(myParser);
        
        fileInputStream.close();
        
        Log.e("cw", "parse end : " + mUrl);
        
        // notify parse end
        synchronized(mXmlParseResponseMap) {
        	Iterator<XmlParseResponse> it = mXmlParseResponseMap.keySet().iterator();
        	while(it.hasNext()) {
        		XmlParseResponse key = (XmlParseResponse)it.next();
        		// XmlParseResponse value = mXmlParseResponseMap.get(key);
        		if (null != key) {
        			key.onParseEnd(XmlParseResponse.SUCCESS);
        		}
        	}
        	
//        	Iterator<Map.Entry<XmlParseResponse, XmlParseResponse>> iterator = mXmlParseResponseMap.entrySet().iterator();
//        	while (iterator.hasNext()) {
//        		Map.Entry<XmlParseResponse, XmlParseResponse> entry = iterator.next();
//        		entry.getKey();
//        		entry.getValue();
//        	}
        }
    }
    
    // implement XmlParseResponse if you care parse xml end.
    public void asyncParse() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    syncParse();
                } catch (XmlPullParserException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
         });
         thread.start();
    }
    
    private void parse(XmlPullParser pullParser) throws XmlPullParserException, IOException {
        Weather weather = null; 
        int event = pullParser.getEventType();
        
        while (event != XmlPullParser.END_DOCUMENT){
            switch (event) {
            case XmlPullParser.START_DOCUMENT:
                if (null == mWeatherArrayList) {
                    mWeatherArrayList = new ArrayList<Weather>();
                }
                break;    
            case XmlPullParser.START_TAG: {
                String name = pullParser.getName();
                if ("Weather".equals(name)) {
                    weather = new Weather();
                    int attributeCount = pullParser.getAttributeCount();
                    for (int i = 0; i < attributeCount; ++i) {
                        String curAttributeName = pullParser.getAttributeName(i);
                        String curAttributeValue = pullParser.getAttributeValue(i);
                        if ("forecastdate".equals(curAttributeName)){
                            weather.setForecastDate(curAttributeValue);
                        }
                        if ("minimum".equals(curAttributeName)){
                            weather.setMinTemp(curAttributeValue);
                        }                                
                        if ("maximum".equals(curAttributeName)){
                            weather.setMaxTemp(curAttributeValue);
                        }
                        if ("status_en".equals(curAttributeName)){
                            weather.setStatusEn(curAttributeValue);
                        }
                        if ("status_zh".equals(curAttributeName)){
                            weather.setStatusZh(curAttributeValue);
                        }
                        if ("picturename".equals(curAttributeName)){
                            weather.setPicName(curAttributeValue);
                        }
                    }
                }
            }
            break;
                
            case XmlPullParser.END_TAG:
                if ("Weather".equals(pullParser.getName())){
                    mWeatherArrayList.add(weather);
                    Log.i("cw", weather.toString());
                    weather = null;
                }
                break;
            }
            event = pullParser.next();
        }
     }
    
    private String mUrl;
    private ArrayList<Weather> mWeatherArrayList;
    private Map<XmlParseResponse, XmlParseResponse> mXmlParseResponseMap;
}
