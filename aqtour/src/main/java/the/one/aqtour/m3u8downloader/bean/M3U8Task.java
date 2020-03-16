package the.one.aqtour.m3u8downloader.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.LitePalSupport;

import the.one.aqtour.m3u8downloader.utils.MUtils;

/**
 * ================================================
 * 作    者：JayGoo
 * 版    本：
 * 创建日期：2017/11/22
 * 描    述: M3U8下载任务
 * ================================================
 */
public class M3U8Task extends LitePalSupport implements Parcelable {

    private int type;
    private String name;
    private String series;
    private String cover;
    private String url;
    private int state = M3U8TaskState.DEFAULT;
    private long speed;
    private float progress = 0;
    private M3U8 m3U8;
    private long createDate;

    private M3U8Task(){}

    public M3U8Task(String url){
        this.url = url;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof M3U8Task){
            M3U8Task m3U8Task = (M3U8Task)obj;
            if (url != null && url.equals(m3U8Task.getUrl()))return true;
        }
        return false;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName(){
        StringBuffer sb = new StringBuffer();
        sb.append(name).append(" ").append(series);
        return sb.toString();
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getFormatSpeed() {
        if (speed == 0)return "";
        return MUtils.formatFileSize(speed) + "/s";
    }

    public String getFormatTotalSize() {
        if (m3U8 == null)return "";
        return m3U8.getFormatFileSize();
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getSpeed() {
        return speed;
    }

    public void setSpeed(long speed) {
        this.speed = speed;
    }

    public M3U8 getM3U8() {
        return m3U8;
    }

    public void setM3U8(M3U8 m3U8) {
        this.m3U8 = m3U8;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);
        dest.writeString(this.name);
        dest.writeString(this.series);
        dest.writeString(this.cover);
        dest.writeString(this.url);
        dest.writeInt(this.state);
        dest.writeLong(this.speed);
        dest.writeFloat(this.progress);
        dest.writeLong(this.createDate);
    }

    protected M3U8Task(Parcel in) {
        this.type = in.readInt();
        this.name = in.readString();
        this.series = in.readString();
        this.cover = in.readString();
        this.url = in.readString();
        this.state = in.readInt();
        this.speed = in.readLong();
        this.progress = in.readFloat();
        this.createDate = in.readLong();
    }

    public static final Creator<M3U8Task> CREATOR = new Creator<M3U8Task>() {
        @Override
        public M3U8Task createFromParcel(Parcel source) {
            return new M3U8Task(source);
        }

        @Override
        public M3U8Task[] newArray(int size) {
            return new M3U8Task[size];
        }
    };
}
