package com.cw.webviewsummary;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyPagerAdapter extends PagerAdapter {

    private final Context mContext;

    private String []simulatorData = {"Beijing", "ShangHai", "ShenZhen"};
    private int [] layoutId = {R.layout.pageradaperitem_beijing, R.layout.pageradaperitem_shanghai, R.layout.pageradaperitem_shengzhen};

    public MyPagerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return simulatorData.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = LayoutInflater.from(mContext).inflate(layoutId[position], null);
        TextView tv = (TextView) view.findViewById(R.id.tv);
        tv.setText(simulatorData[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
