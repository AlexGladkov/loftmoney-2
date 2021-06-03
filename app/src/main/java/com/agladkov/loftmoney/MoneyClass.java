package com.agladkov.loftmoney;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class MoneyClass implements Parcelable {

    String type;
    String count;

    public MoneyClass(String type, String count) {
        this.type = type;
        this.count = count;
    }

    protected MoneyClass(Parcel in) {
        type = in.readString();
        count = in.readString();
    }

    public static final Creator<MoneyClass> CREATOR = new Creator<MoneyClass>() {
        @Override
        public MoneyClass createFromParcel(Parcel in) {
            return new MoneyClass(in);
        }

        @Override
        public MoneyClass[] newArray(int size) {
            return new MoneyClass[size];
        }
    };

    public String getCount() {
        return count;
    }

    public String getType() {
        return type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(count);
    }
}
