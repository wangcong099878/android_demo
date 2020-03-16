package the.one.aqtour.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 分类
 */
public class QSPCategory implements Parcelable {

    public String title;
    public String url;
    public int type;

    public QSPCategory() {
    }

    public QSPCategory(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public QSPCategory(String title, String url, int type) {
        this.title = title;
        this.url = url;
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.url);
    }

    protected QSPCategory(Parcel in) {
        this.title = in.readString();
        this.url = in.readString();
    }

    public static final Creator<QSPCategory> CREATOR = new Creator<QSPCategory>() {
        @Override
        public QSPCategory createFromParcel(Parcel source) {
            return new QSPCategory(source);
        }

        @Override
        public QSPCategory[] newArray(int size) {
            return new QSPCategory[size];
        }
    };
}
