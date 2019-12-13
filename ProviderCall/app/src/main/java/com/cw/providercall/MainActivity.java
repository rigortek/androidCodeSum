package com.cw.providercall;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
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
import java.util.Map;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.cw.providercall.gson.Student;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity {
    Button mSingleWeatherBt;
    Button mArrayListWeatherBt;
    Button mSip4000;
    Button mSip5600;
    Button mFirePropertyChange;
    MyOnClickListener mMyOnClickListener;
    Uri mUri = Uri.parse("content://cw.weather");

    // monitor property change
    // 需要自己主动去触发firePropertyChange
    private static final String PROPERTYKEY = "cw.service.booted";
    private static final String PROPERTYKEYEX = "cw.service.booted.ex";
    String mValue;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    // 监听所有property
    private final PropertyChangeListener pclAll = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent event) {
            Log.d(TAG, "All " + event.getPropertyName() + " propertyChange from " + event.getOldValue() + " to " + event.getNewValue());
        }
    };
    // 监听单个property
    private final PropertyChangeListener pclSingle = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent event) {
            Log.d(TAG, "Single " + event.getPropertyName() + " propertyChange from " + event.getOldValue() + " to " + event.getNewValue());
        }
    };

    public void setValue(String newValue) {
        String oldValue = this.mValue;
        this.mValue = newValue;
        this.pcs.firePropertyChange(PROPERTYKEY, oldValue, newValue);
        this.pcs.firePropertyChange(PROPERTYKEYEX, oldValue, newValue);
    }

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
                mMyActivity.getSingleWeather();
                break;
            case R.id.arraylist_weather_bt:
                mMyActivity.getArrayListWeather();
                break;

                case R.id.sip4000: {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            requestNetworkSip4000();
                        }
                    }).start();
                    break;
                }

                case R.id.sip5600: {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            requestNetworkSip5600();
                        }
                    }).start();
                    break;
                }

                case R.id.firePropertyChange: {
                    setValue(TextUtils.isEmpty(mValue) || mValue.equals("0") ? "1" : "0");
                    break;
                }
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

    public void requestNetworkSip4000() {
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
//                "\t\"digest\": \"\",\n" +
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
        Log.d("sip", "requestNetworkSip4000: " + readData(url, hashMap, stringBuffer));
    }

    @Override
    protected void onResume() {
        super.onResume();

        Student.test();
    }

    public void requestNetworkSip5600() {
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
                "\t\"chipModel\": \"ZX4000\",\n" +
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
        Log.d("sip", "requestNetworkSip5600: " + readData(url, hashMap, stringBuffer));
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
                connection.setReadTimeout(40000);    // 120s
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
    protected void onDestroy() {
	    pcs.removePropertyChangeListener(pclAll);
	    pcs.removePropertyChangeListener(PROPERTYKEY, pclSingle);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pcs.addPropertyChangeListener(pclAll);  // 监听所有property
        pcs.addPropertyChangeListener(PROPERTYKEY, pclSingle); // 监听单个property

        TextView verText = (TextView)findViewById(R.id.versionText);
        try {
            PackageInfo pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            int versionNumber = pinfo.versionCode;
            String versionName = pinfo.versionName;
            verText.setText("ver number: " + versionNumber + "ver name: " + versionName);
        } catch (PackageManager.NameNotFoundException e) {

        }

        mMyOnClickListener = new MyOnClickListener(this);
        mSingleWeatherBt = (Button)findViewById(R.id.single_weatehr_bt);
        mArrayListWeatherBt = (Button)findViewById(R.id.arraylist_weather_bt);
        mSip4000 = (Button)findViewById(R.id.sip4000);
        mSip5600 = (Button)findViewById(R.id.sip5600);
        mFirePropertyChange = (Button) findViewById(R.id.firePropertyChange);

        if (null != mSingleWeatherBt) {
            mSingleWeatherBt.setOnClickListener(mMyOnClickListener);
        }
        if (null != mArrayListWeatherBt) {
            mArrayListWeatherBt.setOnClickListener(mMyOnClickListener);
        }
        if (null != mSip4000) {
            mSip4000.setOnClickListener(mMyOnClickListener);
        }
        if (null != mSip5600) {
            mSip5600.setOnClickListener(mMyOnClickListener);
        }
        if (null != mFirePropertyChange) {
            mFirePropertyChange.setOnClickListener(mMyOnClickListener);
        }
        registerContentObserver();
    }

    private void registerContentObserver() {
	    ContentResolver contentResolver = getContentResolver();
	    contentResolver.registerContentObserver(mUri, true, new ContentObserver(null) {
            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                Log.d(TAG, "onChange: " + selfChange);
            }

            @Override
            public void onChange(boolean selfChange, Uri uri) {
                super.onChange(selfChange, uri);
                Log.d(TAG, "onChange: " + selfChange + " : " + uri.toString()) ;
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
