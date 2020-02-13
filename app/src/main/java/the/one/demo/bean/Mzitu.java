package the.one.demo.bean;

//  ┏┓　　　┏┓
//┏┛┻━━━┛┻┓
//┃　　　　　　　┃
//┃　　　━　　　┃
//┃　┳┛　┗┳　┃
//┃　　　　　　　┃
//┃　　　┻　　　┃
//┃　　　　　　　┃
//┗━┓　　　┏━┛
//    ┃　　　┃                  神兽保佑
//    ┃　　　┃                  永无BUG！
//    ┃　　　┗━━━┓
//    ┃　　　　　　　┣┓
//    ┃　　　　　　　┏┛
//    ┗┓┓┏━┳┓┏┛
//      ┃┫┫　┃┫┫
//      ┗┻┛　┗┻┛


import android.os.Parcel;
import android.os.Parcelable;

import the.one.base.Interface.ImageSnap;

/**
 * @author The one
 * @date 2019/6/24 0024
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class Mzitu implements ImageSnap, Parcelable {

    private String url;
    private String link;
    private String refer;
    private String title;
    private String date;
    private int width;
    private int height;

    public Mzitu() {
    }

    public Mzitu(String url, String refer) {
        this.url = url;
        this.refer = refer;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setRefer(String refer) {
        this.refer = refer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String getImageUrl() {
        return url;
    }

    @Override
    public String getRefer() {
        return refer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.link);
        dest.writeString(this.refer);
        dest.writeString(this.title);
        dest.writeString(this.date);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
    }

    protected Mzitu(Parcel in) {
        this.url = in.readString();
        this.link = in.readString();
        this.refer = in.readString();
        this.title = in.readString();
        this.date = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
    }

    public static final Parcelable.Creator<Mzitu> CREATOR = new Parcelable.Creator<Mzitu>() {
        @Override
        public Mzitu createFromParcel(Parcel source) {
            return new Mzitu(source);
        }

        @Override
        public Mzitu[] newArray(int size) {
            return new Mzitu[size];
        }
    };
}
