package com.cw.webviewsummary;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.LinearLayout;
import android.widget.Button;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.cw.providercall.IMyServiceInterface;

public class MainActivity extends Activity implements View.OnClickListener {

    private RelativeLayout activityMain;
    private LinearLayout topLinearLayout;
    private WebView webview1;

    private Dialog mDialog;
    private WebView mMessageWebview;

    IMyServiceInterface mIMyServiceInterface = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMain = (RelativeLayout) findViewById(R.id.activity_main);
        topLinearLayout = (LinearLayout) findViewById(R.id.topLinearLayout);
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        webview1 = (WebView) findViewById(R.id.webview1);
        webview1.setWebViewClient(mWebViewClient);

        //如果有缓存 就使用缓存数据 如果没有 就从网络中获取
        Toast.makeText(MainActivity.this,
                "getCacheMode : " + webview1.getSettings().getCacheMode(),
                Toast.LENGTH_LONG).show();
        // default cache mode : LOAD_DEFAULT
        webview1.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        // webview1.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        Toast.makeText(MainActivity.this,
                "getDatabaseEnabled : " + webview1.getSettings().getDatabaseEnabled(),
                Toast.LENGTH_LONG).show();
        // default database enable : enable
        webview1.getSettings().setDatabaseEnabled(true);
        webview1.getSettings().setAppCacheEnabled(true);
        webview1.loadUrl("http://www.baidu.com/");  // default load baidu

        Intent intent = new Intent();
        intent.setAction("com.cw.action.MYSERVICE");
        intent.setPackage("com.cw.providercall");
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.e("shsh", ">>>>>>>>>>>>>>>> onServiceConnected");
                mIMyServiceInterface = IMyServiceInterface.Stub.asInterface(service);;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.e("shsh", ">>>>>>>>>>>>>>>> onServiceDisconnected");
                mIMyServiceInterface = null;
            }
        }, Service.BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                //TODO implement
                webview1.loadUrl("https://www.baidu.com/");
                break;
            case R.id.button2:
                //TODO implement
                webview1.loadUrl("http://www.sina.com.cn/");
                break;
            case R.id.button3:
                //TODO implement
                webview1.loadUrl("https://www.sohu.com/");
                break;
            case R.id.button4:
                //TODO implement
/*                mDialog = new Dialog(this, R.style.DialogFullScreen);
                mDialog.setContentView(R.layout.message_dialog_layout);
                LayoutInflater myLayoutInflater = getLayoutInflater();
                RelativeLayout viewGroup = (RelativeLayout) findViewById(R.id.message_dialog_layout);
                RelativeLayout messagewebview = (RelativeLayout) myLayoutInflater.inflate(R.layout.message_dialog_layout, viewGroup);
                mMessageWebview = (WebView) messagewebview.findViewById(R.id.messagewebview);
                mDialog.show();
                mMessageWebview.loadUrl("https://www.sohu.com/");*/

                WebDialog myWebDialog = new WebDialog(this, "https://www.sohu.com/");
                myWebDialog.show();

                // start activity by explicit action
//                Intent intent = new Intent(java.lang.NullPointerException:"com.cw.providercall.action.IMPLICT");
//                try {
//                    startActivity(intent);
//                } catch (Exception e) {
//                    Log.e("TEST", e.getMessage());
//                }
                // TODO: 18-6-26 start activity by explict action + data 
                break;
        }
    }

    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Uri request = Uri.parse(url);
            Log.i("", "url:" + url + ", request.getAuthority():" + request.getAuthority());
            if (TextUtils.equals(request.getAuthority(), "www.baidu.com")
                    || TextUtils.equals(request.getAuthority(), "www.sina.com.cn")
                    || TextUtils.equals(request.getAuthority(), "www.sohu.com")) {
                // return false;
            } else {
                // return super.shouldOverrideUrlLoading(view, url);
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            Toast.makeText(MainActivity.this,
                    "getAction : " + event.getAction() + ", getKeyCode : " +  event.getKeyCode(),
                    Toast.LENGTH_SHORT).show();
            if (onKeyDown(event.getKeyCode(), event)) {
                return true;
            }
            return super.shouldOverrideKeyEvent(view, event);
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
/*        Toast.makeText(MainActivity.this,
                "getAction : " + event.getAction() + ", keyCode : " +  keyCode,
                Toast.LENGTH_SHORT).show();*/
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (webview1.canGoBack()) {
                webview1.goBack();
                return true;
            }
        }

        if (event.getAction() == KeyEvent.ACTION_DOWN  && null != mIMyServiceInterface) {
            Log.i("shsh", "getKeyCode: " + event.getKeyCode());
            try {
                if (event.getKeyCode() == KeyEvent.KEYCODE_0) {
                    mIMyServiceInterface.bindCall(0);
                } else if (event.getKeyCode() == KeyEvent.KEYCODE_1) {
                    mIMyServiceInterface.bindCall(1);
                } else if (event.getKeyCode() == KeyEvent.KEYCODE_2) {
                    mIMyServiceInterface.bindCall(2);
                } else if (event.getKeyCode() == KeyEvent.KEYCODE_3) {
                    mIMyServiceInterface.bindCall(3);
                } else if (event.getKeyCode() == KeyEvent.KEYCODE_4) {
                    mIMyServiceInterface.bindCall(4);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (Exception e) {
                // E/JavaBinder(21506): *** Uncaught remote exception!  (Exceptions are not yet supported across processes.
                // 对于不支持跨进程exception,此处catch无用,其它支持跨进程exception,此处catch有用
                e.printStackTrace();
            }
        }

        SignatureCheck.validateAppSignature(this);

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(MainActivity.this,
                "onBackPressed",
                Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }
}