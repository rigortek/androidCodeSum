package com.cw.providercall;

import java.util.Set;

import android.os.Parcel;
import android.os.Parcelable;

public class Weather implements Parcelable {
    
    public Weather() {}

    public void setForecastDate(String forecastDate) {mForecastDate = forecastDate;}
    public String getForecastDate() {return mForecastDate;}
    
    public void setMinTemp(String minTemp) {mMinTemp = minTemp;}
    public String getMinTemp() {return mMinTemp;}
    
    public void setMaxTemp(String maxTemp) {mMaxTemp = maxTemp;}
    public String getMaxTemp() {return mMaxTemp;}
    
    public void setStatusEn(String statusEn) {mStatusEn = statusEn;}
    public String getStatusEn() {return mStatusEn;}
    
    public void setStatusZh(String statusZh) {mStatusZh = statusZh;}
    public String getStatusZh() {return mStatusZh;}
    
    public void setPicName(String picName) {mPicName = picName;}
    public String getPicName() {return mPicName;}
    
    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel arg0, int arg1) {
        // TODO Auto-generated method stub
       arg0.writeString(mForecastDate);
       arg0.writeString(mMinTemp);
       arg0.writeString(mMaxTemp);
       arg0.writeString(mStatusEn);
       arg0.writeString(mStatusZh);
       arg0.writeString(mPicName);
    }
    
    
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return mForecastDate + " : " + mMinTemp + " : " + mMaxTemp
               + " : " +  mStatusEn + " : " + mStatusZh + " : " + mPicName;
        // return super.toString();
    }


    public static final Parcelable.Creator<Weather> CREATOR
        = new Parcelable.Creator<Weather>() {
        public Weather createFromParcel(Parcel in) {
            return new Weather(in);
        }
        public Weather[] newArray(int size) {
            return new Weather[size];
        }
    };
    
    private Weather(Parcel in) {
        mForecastDate = in.readString();
        mMinTemp = in.readString();
        mMaxTemp = in.readString();
        mStatusEn = in.readString();
        mStatusZh = in.readString();
        mPicName = in.readString();
    }
        
    private String mForecastDate;
    private String mMinTemp;
    private String mMaxTemp;
    private String mStatusEn;
    private String mStatusZh;
    private String mPicName;
}
