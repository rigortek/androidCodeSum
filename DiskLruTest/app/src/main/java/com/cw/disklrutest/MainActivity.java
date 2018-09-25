package com.cw.disklrutest;

import android.app.Activity;
import android.app.ListActivity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        try {
            mDiskLruCacheUtils = DiskLruCacheUtils.getInstance(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
        mUrlList.add("https://tse3.mm.bing.net/th?id=OIP.S2zUPROGkkbglmMVjdAXUwHaF7&pid=Api");
        mUrlList.add("https://tse4.mm.bing.net/th?id=OIP.Ox6qcM6JwZFDSY-01DKMXQHaFj&pid=Api");
        mUrlList.add("https://tse4.mm.bing.net/th?id=OIP.FM1COqIHCvPUpnObKo2qJQHaHa&pid=Api");


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_0:
            case KeyEvent.KEYCODE_1:
            case KeyEvent.KEYCODE_2:
                mDiskLruCacheUtils.saveBitmap2Disk(mUrlList.get(keyCode - KeyEvent.KEYCODE_0), -1, -1);
                Log.d(TAG, "onKeyDown: " + mUrlList.get(keyCode - KeyEvent.KEYCODE_0));
                break;

            case KeyEvent.KEYCODE_3:
            case KeyEvent.KEYCODE_4:
            case KeyEvent.KEYCODE_5:
                Bitmap bitmap = mDiskLruCacheUtils.getBitmapFromDisk(mUrlList.get(keyCode - KeyEvent.KEYCODE_3), -1, -1);
                Log.d(TAG, "onKeyDown: " + bitmap);
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    DiskLruCacheUtils mDiskLruCacheUtils;

    List<String> mUrlList = new ArrayList<String>();
}
