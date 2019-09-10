package com.cw.webviewsummary;

import android.app.Fragment;
import android.os.Bundle;

public class MyFragment extends Fragment {
    private final String mString;

    public MyFragment() {
        super();
        Bundle bundle = getArguments();
        mString = null != bundle ? bundle.getString("title") : "";
    }
}
