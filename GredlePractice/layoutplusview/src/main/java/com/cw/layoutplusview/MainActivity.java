package com.cw.layoutplusview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);
        setContentView(R.layout.switch_systemui_layout);
    }

    public void onToggleClick(View v) {
        int currentVis = v.getSystemUiVisibility();
        int newView;
        switch(currentVis) {
            case View.SYSTEM_UI_FLAG_VISIBLE:
                Toast.makeText(this, "SYSTEM_UI_FLAG_VISIBLE", Toast.LENGTH_SHORT).show();
                newView = View.INVISIBLE;
                break;

            case View.INVISIBLE: // case View.SYSTEM_UI_FLAG_FULLSCREEN:
                Toast.makeText(this, "INVISIBLE", Toast.LENGTH_SHORT).show();
                newView = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
                break;

            case View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN:
                Toast.makeText(this, "SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN", Toast.LENGTH_SHORT).show();
                newView = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
                break;

            case View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION:
                Toast.makeText(this, "SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION", Toast.LENGTH_SHORT).show();
                newView = View.SYSTEM_UI_LAYOUT_FLAGS;
                break;

            case View.SYSTEM_UI_LAYOUT_FLAGS:
                Toast.makeText(this, "SYSTEM_UI_LAYOUT_FLAGS", Toast.LENGTH_SHORT).show();
                newView = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
                break;

            case View.SYSTEM_UI_FLAG_HIDE_NAVIGATION:
                Toast.makeText(this, "SYSTEM_UI_FLAG_HIDE_NAVIGATION", Toast.LENGTH_SHORT).show();
                newView = View.SYSTEM_UI_FLAG_LOW_PROFILE;
                break;

            case View.SYSTEM_UI_FLAG_LOW_PROFILE:
                Toast.makeText(this, "SYSTEM_UI_FLAG_LOW_PROFILE", Toast.LENGTH_SHORT).show();
                newView = View.SYSTEM_UI_FLAG_FULLSCREEN;
                break;
            default:
                Toast.makeText(this, "default", Toast.LENGTH_SHORT).show();
                newView = View.SYSTEM_UI_FLAG_FULLSCREEN;
                break;
        }
        v.setSystemUiVisibility(newView);
    }
}
