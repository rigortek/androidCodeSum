package com.china_liantong.lrutest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.LruCache;
import android.view.KeyEvent;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    // logt
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBitmapCache = new LruCache<String, Bitmap>(mCacheSize) {
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };

        mHandler = new LruHandler(this);

        mUrlList.add("http://5b0988e595225.cdn.sohucs.com/images/20180725/5006fd9cd35d4034aafb3a3f852d83d1.jpeg");
        mUrlList.add("http://img5.mtime.cn/CMS/News/2018/05/25/144244.88376230_620X620.jpg");
        mUrlList.add("http://cnews.chinadaily.com.cn/img/site1/20180628/509a4c1279f91c9e92e947.jpeg");
        mUrlList.add("http://www.xjbs.com.cn/_CMS_NEWS_IMG_/upload1/201807/20/1532053733310.jpg");

        new Thread(new TransferUrl2Bitmap(this, mUrlList)).start();
    }

    class TransferUrl2Bitmap implements Runnable {

        TransferUrl2Bitmap(Activity activity, List<String> params) {
            mActivity = new WeakReference<>(activity);
            mUrlList = params;
        }
        @Override
        public void run() {
            Log.d(TAG, "run: all decode start : " + mUrlList.size());
            for (int i = 0; i < mUrlList.size(); ++i) {

                try {
                    URL url = new URL(mUrlList.get(i));
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(input);
                    if (null != bitmap) {
                        Log.e(TAG, "run: decodeStream success: " + mUrlList.get(i));
                        mBitmapList.add(bitmap);
                    } else {
                        Log.e(TAG, "run: decodeStream fail: " + mUrlList.get(i));
                        mBitmapList.add(null);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Activity activity = mActivity.get();
            if (activity != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    if (activity.isDestroyed()) {
                        return;
                    }
                }
                if (activity.isFinishing()) {
                    return;
                }
            }

            Looper.prepare();
            mHandler.sendEmptyMessage(0);
            Log.d(TAG, "run: all decode finish");
        }

        WeakReference<Activity> mActivity;
        private List<String> mUrlList;
    }

    private class LruHandler extends Handler {

        public LruHandler(Activity activity) {
            mActivity = activity;
        }
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(mActivity, "all decode finished", Toast.LENGTH_LONG).show();
            super.handleMessage(msg);
        }

        private Activity mActivity;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_0:
            case KeyEvent.KEYCODE_1:
            case KeyEvent.KEYCODE_2:
            case KeyEvent.KEYCODE_3: {
                int index = keyCode - KeyEvent.KEYCODE_0;
                Bitmap bitmap = mBitmapList.get(index);
                if (null != bitmap) {
                    try {
                        mBitmapCache.put(URLEncoder.encode(mUrlList.get(index), Charset.defaultCharset().name()), bitmap);

                        Log.d(TAG, "onKeyDown: cache size: " + mBitmapCache.size());
                        Log.d(TAG, "onKeyDown: cache toString: " + mBitmapCache.toString());
                        Log.d(TAG, "onKeyDown: cache createCount: " + mBitmapCache.createCount());
                        Log.d(TAG, "onKeyDown: cache get: " + mBitmapCache.get(URLEncoder.encode(mUrlList.get(index), Charset.defaultCharset().name())));
//                        Log.d(TAG, "onKeyDown: cache remove: " + mBitmapCache.remove(URLEncoder.encode(mUrlList.get(index), Charset.defaultCharset().name())));
                        Log.d(TAG, "onKeyDown: cache size: " + mBitmapCache.size());

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }


                }
            }
            return true;

            default:
                break;

        }
        return super.onKeyDown(keyCode, event);
    }

    int mCacheSize = 40 * 1024 * 1024; // 40MiB

    LruCache<String, Bitmap> mBitmapCache;

    final List<String> mUrlList = new ArrayList<>();
    List<Bitmap> mBitmapList = new ArrayList<>();

    LruHandler mHandler;

}
