package the.one.base.model;

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

/**
 * @author The one
 * @date 2019/10/15 0015
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class Download implements Parcelable {

    private String url;
    private String destFileDir;
    private String name;
    private boolean isUpdateApk = false;
    private boolean isImage =false;

    public Download(String url, String name) {
        this.name = name;
        this.url = url;
    }

    public Download(String url, String destFileDir, String name) {
        this.url = url;
        this.destFileDir = destFileDir;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDestFileDir() {
        return destFileDir;
    }

    public void setDestFileDir(String destFileDir) {
        this.destFileDir = destFileDir;
    }

    public boolean isUpdateApk() {
        return isUpdateApk;
    }

    public void setUpdateApk(boolean updateApk) {
        isUpdateApk = updateApk;
    }

    public boolean isImage() {
        return isImage;
    }

    public void setImage(boolean image) {
        isImage = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.destFileDir);
        dest.writeString(this.name);
        dest.writeByte(this.isUpdateApk ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isImage ? (byte) 1 : (byte) 0);
    }

    protected Download(Parcel in) {
        this.url = in.readString();
        this.destFileDir = in.readString();
        this.name = in.readString();
        this.isUpdateApk = in.readByte() != 0;
        this.isImage = in.readByte() != 0;
    }

    @Override
    public String toString() {
        return "Download{" +
                "url='" + url + '\'' +
                ", destFileDir='" + destFileDir + '\'' +
                ", name='" + name + '\'' +
                ", isUpdateApk=" + isUpdateApk +
                ", isImage=" + isImage +
                '}';
    }

    public static final Creator<Download> CREATOR = new Creator<Download>() {
        @Override
        public Download createFromParcel(Parcel source) {
            return new Download(source);
        }

        @Override
        public Download[] newArray(int size) {
            return new Download[size];
        }
    };
}
