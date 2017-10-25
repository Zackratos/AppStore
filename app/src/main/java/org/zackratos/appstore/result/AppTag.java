package org.zackratos.appstore.result;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * Created by Administrator on 2017/10/22.
 */

public class AppTag implements Parcelable {

    /**
     * tagId : 278
     * link : sametag/278
     * tagName : 角色
     */

    private int tagId;
    private String link;
    private String tagName;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.tagId);
        dest.writeString(this.link);
        dest.writeString(this.tagName);
    }

    public AppTag() {
    }

    protected AppTag(Parcel in) {
        this.tagId = in.readInt();
        this.link = in.readString();
        this.tagName = in.readString();
    }

    public static final Parcelable.Creator<AppTag> CREATOR = new Parcelable.Creator<AppTag>() {
        @Override
        public AppTag createFromParcel(Parcel source) {
            return new AppTag(source);
        }

        @Override
        public AppTag[] newArray(int size) {
            return new AppTag[size];
        }
    };
}
