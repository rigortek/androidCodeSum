package com.cw.providercall;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StatFs;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.params.HttpParams;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity {
    Button mSingleWeatherBt;
    Button mArrayListWeatherBt;
    Button mSip20000;
    Button mSip28000;
    Button mBtParseXml;
    Button mHttps;

    MyOnClickListener mMyOnClickListener;
    Uri mUri = Uri.parse("content://cw.weather");

	public class MyOnClickListener implements OnClickListener {
        public MyOnClickListener(MainActivity activity) {
            // TODO Auto-generated constructor stub
            mMyActivity = activity;
        }
        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: ");
            // TODO Auto-generated method stub
            switch(v.getId()) {
            case R.id.single_weatehr_bt: 
//                mMyActivity.getSingleWeather();
                getDirAvailble("/data", 1000);
                break;
            case R.id.arraylist_weather_bt:
                mMyActivity.getArrayListWeather();
                break;

                case R.id.sip20000: {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            requestNetworkSip2000();
                        }
                    }).start();
                    break;
                }

                case R.id.sip28000: {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            requestNetworkSip2800();
                        }
                    }).start();
                    break;
                }

                case R.id.parsexml: {
                    InputStream xmlData = getResources().openRawResource(R.raw.xmldata);
                    parseXMLWithPull(xmlData);
                    break;
                }
                case R.id.https: {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
//                            new HttpsClient().testIt();
//
//                            StringBuffer sb = new StringBuffer();
//                            HttpsClient.readData("https://www.baidu.com", sb);
//                            Log.d("http", sb.toString());
                        }
                    }).start();
                    break;
                }
            default:
                break;
            }
        }
        
        private MainActivity mMyActivity;
    }


    public static long getDirAvailble(String path, long reserveSize) {
        long avail = 0;
        StatFs fileStats = new StatFs(path);
        if (Build.VERSION.SDK_INT >= 25) {
            avail = fileStats.getAvailableBytes();
        } else {
            long size = fileStats.getBlockSize();// 鑾峰彇鍒嗗尯鐨勫ぇ灏�
            long blocks = fileStats.getAvailableBlocks();// 鑾峰彇鍙敤鍒嗗尯鐨勪釜鏁�
            avail = blocks * size;
        }
        if (reserveSize > 0) {
            avail -= reserveSize;
        }
        return avail;
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

    public void requestNetworkSip2000() {
        String url = "http://10.27.252.138:8080/gateway/v1/command/anonymityLogin";
        HashMap hashMap = new HashMap();
        hashMap.put("deviceNo", "90291049345");
        hashMap.put("macAddress", "dc2a140002c3");
        hashMap.put("sessionjson", "{\n" +
                "\t\"adminArea\": \"\",\n" +
                "\t\"androidSdkVersion\": \"16\",\n" +
//                "\t\"buildDate\": \"Wed Sep 19 00:36:43 CST 2018\",\n" +
                "\t\"buildType\": \"user\",\n" +
                "\t\"carrier\": \"\",\n" +
                "\t\"checkin\": {\n" +
                "\t\t\"hwModel\": \"1.1.00\",\n" +
                "\t\t\"hwVersion\": \"1.2.00\",\n" +
                "\t\t\"manufacturer\": \"02\",\n" +
                "\t\t\"osType\": \"0\",\n" +
                "\t\t\"product\": \"\"\n" +
                "\t},\n" +
                "\t\"checkinType\": \"\",\n" +
                "\t\"chipModel\": \"ELITE1000\",\n" +
                "\t\"cid\": \"\",\n" +
                "\t\"clientVersionCode\": \"3006\",\n" +
                "\t\"connectionMedia\": \"\",\n" +
                "\t\"country\": \"CN\",\n" +
//                "\t\"digest\": \"\",\n" +f f
//                "\t\"ifVersion\": \"1\",\n" +
//                "\t\"imei\": \"\",\n" +
//                "\t\"imsi\": \"\",\n" +
                "\t\"ip\": \"10.27.65.210\",\n" +
                "\t\"lastCheckinMsec\": \"\",\n" +
                "\t\"locale\": \"zh_CN\",\n" +
                "\t\"locality\": \"\",\n" +
                "\t\"mac\": \"dc2a140002c3\",\n" +
                "\t\"mccMnc\": \"\",\n" +
                "\t\"phoneType\": \"\",\n" +
                "\t\"radio\": \"\",\n" +
                "\t\"romVersion\": \"3.2.18\",\n" +
                "\t\"sdkVersion\": \"12.5\",\n" +
                "\t\"serialNo\": \"90291049345\",\n" +
                "\t\"swModel\": \"1.1.00\",\n" +
                "\t\"swVersion\": \"3.2.18\",\n" +
                "\t\"systemVersion\": \"08.02.00.00\",\n" +
//                "\t\"timeStamp\": \"946685022321696\",\n" +
//                "\t\"timeZone\": \"Asia/Shanghai\",\n" +
                "\t\"zip\": \"\"\n" +
                "}");

//        hashMap.put("sessionjson", "{\n" +
//                "\t\"adminArea\": \"\",\n" +
//                "\t\"androidSdkVersion\": \"16\",\n" +
//                "\t\"buildDate\": \"Wed Sep 19 00:36:43 CST 2018\",\n" +
//                "\t\"buildType\": \"user\",\n" +
//                "\t\"carrier\": \"\",\n" +
//                "\t\"checkin\": {\n" +
//                "\t\t\"hwModel\": \"1.1.00\",\n" +
//                "\t\t\"hwVersion\": \"1.2.00\",\n" +
//                "\t\t\"manufacturer\": \"02\",\n" +
//                "\t\t\"osType\": \"0\",\n" +
//                "\t\t\"product\": \"\"\n" +
//                "\t},\n" +
//                "\t\"checkinType\": \"\",\n" +
//                "\t\"chipModel\": \"ELITE1000\",\n" +
//                "\t\"cid\": \"\",\n" +
//                "\t\"clientVersionCode\": \"3006\",\n" +
//                "\t\"connectionMedia\": \"\",\n" +
//                "\t\"country\": \"CN\",\n" +
//                "\t\"digest\": \"\",\n" +
//                "\t\"ifVersion\": \"1\",\n" +
//                "\t\"imei\": \"\",\n" +
//                "\t\"imsi\": \"\",\n" +
//                "\t\"ip\": \"10.27.65.210\",\n" +
//                "\t\"lastCheckinMsec\": \"\",\n" +
//                "\t\"locale\": \"zh_CN\",\n" +
//                "\t\"locality\": \"\",\n" +
//                "\t\"mac\": \"dc2a140002c3\",\n" +
//                "\t\"mccMnc\": \"\",\n" +
//                "\t\"phoneType\": \"\",\n" +
//                "\t\"radio\": \"\",\n" +
//                "\t\"romVersion\": \"3.2.18\",\n" +
//                "\t\"sdkVersion\": \"12.5\",\n" +
//                "\t\"serialNo\": \"90291049345\",\n" +
//                "\t\"swModel\": \"1.1.00\",\n" +
//                "\t\"swVersion\": \"3.2.18\",\n" +
//                "\t\"systemVersion\": \"08.02.00.00\",\n" +
//                "\t\"timeStamp\": \"946685022321696\",\n" +
//                "\t\"timeZone\": \"Asia/Shanghai\",\n" +
//                "\t\"zip\": \"\"\n" +
//                "}");
        StringBuffer stringBuffer = new StringBuffer();
        Log.d("sip", "requestNetworkSip2000: " + readData(url, hashMap, stringBuffer));
    }

    public void requestNetworkSip2800() {
        String url = "http://10.27.252.138:8080/gateway/v1/command/anonymityLogin";
        HashMap hashMap = new HashMap();
        hashMap.put("deviceNo", "29863416485");
        hashMap.put("macAddress", "dc2a140002c3");
        hashMap.put("sessionjson", "{\n" +
                "\t\"checkin\": {\n" +
                "\t\t\"hwModel\": \"AC9V301\",\n" +
                "\t\t\"hwVersion\": \"3.1.00\",\n" +
                "\t\t\"manufacturer\": \"02\",\n" +
                "\t\t\"product\": \"zx2100\",\n" +
                "\t\t\"osType\": \"tvos\"\n" +
                "\t},\n" +
                "\t\"checkinType\": \"\",\n" +
                "\t\"digest\": \"ad9a936b1aa081289b92584ff0581f94\",\n" +
                "\t\"lastCheckinMsec\": \"\",\n" +
                "\t\"mac\": \"dc2a14e0033a\",\n" +
                "\t\"ip\": \"10.171.151.97\",\n" +
                "\t\"chipModel\": \"ZX2000\",\n" +
                "\t\"serialNo\": \"29863416485\",\n" +
                "\t\"romVersion\": \"5.0.00\",\n" +
                "\t\"systemVersion\": \"10.05.000\",\n" +
                "\t\"swModel\": \"2.2.00\",\n" +
                "\t\"swVersion\": \"5.0.00\",\n" +
                "\t\"androidSdkVersion\": \"22\",\n" +
                "\t\"sdkVersion\": \"\",\n" +
                "\t\"clientVersionCode\": \"100000\",\n" +
                "\t\"buildDate\": \"Fri Sep 21 02:43:10 CST 2018\",\n" +
                "\t\"buildType\": \"userdebug\",\n" +
                "\t\"locale\": \"zh\",\n" +
                "\t\"country\": \"CN\",\n" +
                "\t\"adminArea\": \"\",\n" +
                "\t\"locality\": \"US\",\n" +
                "\t\"timeZone\": \"GMT+08:00\",\n" +
                "\t\"zip\": \"\",\n" +
                "\t\"timeStamp\": \"1537517398\",\n" +
                "\t\"cid\": \"unknown\",\n" +
                "\t\"carrier\": \"\",\n" +
                "\t\"mccMnc\": \"\",\n" +
                "\t\"phoneType\": \"0\",\n" +
                "\t\"ifVersion\": \"1\",\n" +
                "\t\"radio\": \"unknown\",\n" +
                "\t\"connectionMedia\": \"\"\n" +
                "}");
        StringBuffer stringBuffer = new StringBuffer();
        Log.d("sip", "requestNetworkSip2800: " + readData(url, hashMap, stringBuffer));
    }

    // Read data from specify server address.
    public static boolean readData(final String serverUrl, final Map<String, String> paramters, StringBuffer netData) {
        Log.v("http", "readData: " + serverUrl);

        boolean result = false;
        if (null == netData) {
            return result;
        }

        HttpURLConnection connection;
        try {
            URL url = new URL(serverUrl);
            try {
                // 打开服务器
                connection = (HttpURLConnection) url.openConnection();
                // 设置输入输出流
                connection.setDoInput(true);
                connection.setDoOutput(true);
                // 设置请求的方法为Post
                connection.setRequestMethod("POST");
                // Post方式不能缓存数据，则需要手动设置使用缓存的值为false
                connection.setUseCaches(false);
                // 配置请求Content-Type
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                // 设置超时时间
                connection.setConnectTimeout(10000);    // 45s
                connection.setReadTimeout(20000);    // 120s
                connection.setRequestProperty("Connection", "keep-alive");

                // 连接数据库
                Log.v("http", ">>>>prepare http request.");

                /**写入参数**/
                OutputStream os = connection.getOutputStream();
                // 封装写给服务器的数据（这里是要传递的参数）
                DataOutputStream dos = new DataOutputStream(os);
                dos.writeBytes(reqParams(paramters));
                dos.flush();
                dos.close();

                int responseCode = connection.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    Log.e(TAG, "readData: error, responseCode: " + responseCode);
                    return result;
                }

                /**读服务器数据**/
                InputStream is = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    netData.append(line);
                }
                is.close();
                br.close();
                if (netData.length() > 0) {
                    result = true;
                }
            } catch (IOException e) {
                Log.e("error IOException: ", e.getMessage());
                return result;
            }
        } catch (MalformedURLException e) {
            Log.e("http", "error MalformedURLException: " + e.getMessage());
            return result;
        } catch (Exception e) {
            Log.e("http", "error Exception: " + e.getMessage());
            return result;
        }
        Log.v("", ">>>>normal exit readData.");

        return result;
    }

    private static String reqParams(final Map<String, String> params) {
        StringBuffer rtn = new StringBuffer();
        int index = 0;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (index > 0) {
                rtn.append("&");
            }
            try {
                String key = entry.getKey();
                String value = entry.getValue();
                if ((key != null) && (value != null)) {
                    rtn.append(key + "=" + URLEncoder.encode(value, "UTF-8"));
                }
            } catch (UnsupportedEncodingException e) {
                //不支持UTF-8 编码,则使用默认值
                rtn.append(entry.getKey() + "=" + entry.getValue());
            }
            index++;
        }
        return rtn.toString();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView verText = (TextView)findViewById(R.id.versionText);
        try {
            PackageInfo pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            int versionNumber = pinfo.versionCode;
            String versionName = pinfo.versionName;
            verText.setText("ver number: " + versionNumber + "ver name: " + versionName);
        } catch (PackageManager.NameNotFoundException e) {

        }

        registerObserver();

        mMyOnClickListener = new MyOnClickListener(this);
        mSingleWeatherBt = (Button)findViewById(R.id.single_weatehr_bt);
        mArrayListWeatherBt = (Button)findViewById(R.id.arraylist_weather_bt);
        mSip20000 = (Button)findViewById(R.id.sip20000);
        mSip28000 = (Button)findViewById(R.id.sip28000);
        mBtParseXml = (Button) findViewById(R.id.parsexml);
        mHttps = (Button) findViewById(R.id.https);

        if (null != mSingleWeatherBt) {
            mSingleWeatherBt.setOnClickListener(mMyOnClickListener);
        }
        if (null != mArrayListWeatherBt) {
            mArrayListWeatherBt.setOnClickListener(mMyOnClickListener);
        }
        if (null != mSip20000) {
            mSip20000.setOnClickListener(mMyOnClickListener);
        }
        if (null != mSip28000) {
            mSip28000.setOnClickListener(mMyOnClickListener);
        }

        if (null != mBtParseXml) {
            mBtParseXml.setOnClickListener(mMyOnClickListener);
        }

        if (null != mHttps) {
            mHttps.setOnClickListener(mMyOnClickListener);
        }
    }

    private void parseXMLWithPull(InputStream xmlData) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(xmlData, "utf-8");
            int eventType = xmlPullParser.getEventType();
            String id = "";
            String name = "";
            String version = "";
            while (eventType != XmlPullParser.END_DOCUMENT) {

                String nodeName = xmlPullParser.getName();

                switch (eventType) {
                    case XmlPullParser.START_TAG: {
                        if ("id".equals(nodeName)) {
                            id = xmlPullParser.nextText();
                        } else if ("name".equals(nodeName)) {
                            name = xmlPullParser.nextText();
                        } else if ("version".equals(nodeName)) {
                            version = xmlPullParser.nextText();
                        }
                        break;
                    }
                    case XmlPullParser.END_TAG:

                        break;
                }
                eventType = xmlPullParser.next();//进入下一个元素并触发相应事件
            }
        } catch (Exception e) {

        }
    }

    void registerObserver() {
	    ContentResolver contentResolver = getContentResolver();
	    contentResolver.registerContentObserver(mUri, true, new ContentObserver(null) {
            @Override
            public boolean deliverSelfNotifications() {
                return super.deliverSelfNotifications();
            }

            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                Log.d(TAG, "onChange: ");
            }

            @Override
            public void onChange(boolean selfChange, Uri uri) {
                super.onChange(selfChange, uri);
                Log.d(TAG, "onChange: ");
            }
        });
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
