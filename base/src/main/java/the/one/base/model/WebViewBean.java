package the.one.base.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author The one
 * @date 2018/11/6 0006
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class WebViewBean implements Parcelable {

    private String title;
    private String url;

    public WebViewBean(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    protected WebViewBean(Parcel in) {
        this.title = in.readString();
        this.url = in.readString();
    }

    public static final Creator<WebViewBean> CREATOR = new Creator<WebViewBean>() {
        @Override
        public WebViewBean createFromParcel(Parcel source) {
            return new WebViewBean(source);
        }

        @Override
        public WebViewBean[] newArray(int size) {
            return new WebViewBean[size];
        }
    };
}
