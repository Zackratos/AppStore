package org.zackratos.appstore.result;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * Created by Administrator on 2017/10/22.
 */

public class HdIcon implements Parcelable {

    /**
     * main : AppStore/0196e15194b904d9021c9f8f4e02237d5a1ee2215
     */

    private String main;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.main);
    }

    public HdIcon() {
    }

    protected HdIcon(Parcel in) {
        this.main = in.readString();
    }

    public static final Parcelable.Creator<HdIcon> CREATOR = new Parcelable.Creator<HdIcon>() {
        @Override
        public HdIcon createFromParcel(Parcel source) {
            return new HdIcon(source);
        }

        @Override
        public HdIcon[] newArray(int size) {
            return new HdIcon[size];
        }
    };
}
