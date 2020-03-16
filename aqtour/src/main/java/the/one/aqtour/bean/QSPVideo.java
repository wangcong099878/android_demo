package the.one.aqtour.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.LitePalSupport;

import java.util.List;

public class QSPVideo extends LitePalSupport implements Parcelable {

    public String collection;
    public String name;
    public String actors;
    public String url;
    public String cover;
    public String remark;
    public List<QSPVideoInfo> videoInfos;
    public String type;
    public String area;
    public String language;
    public String director;
    public String updateDate;
    public float score;
    public String introduce;
    public List<QSPSeries> series ;
    public List<QSPVideoSection> recommends ;


    public QSPVideo() {
    }

    public QSPVideo(String name, String actors, String url, String cover, String remark) {
        this.name = name;
        this.actors = actors;
        this.url = url;
        this.cover = cover;
        this.remark = remark;
    }

    public QSPSeries getFirstSeries(){
        if(null != series&& series.size()>0){
            return series.get(0);
        }
        return null;
    }

    public String getType(){
        if(null != videoInfos && videoInfos.size()>0){
            return videoInfos.get(0).value;
        }
        return null;
    }

    @Override
    public String toString() {
        return "QSPVideo{" +
                "name='" + name + '\'' +
                ", actors='" + actors + '\'' +
                ", url='" + url + '\'' +
                ", cover='" + cover + '\'' +
                ", remark='" + remark + '\'' +
                ", score='" + score + '\'' +
                ", videoInfos=" + videoInfos +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.actors);
        dest.writeString(this.url);
        dest.writeString(this.cover);
        dest.writeString(this.remark);
        dest.writeString(this.type);
        dest.writeString(this.area);
        dest.writeString(this.language);
        dest.writeString(this.director);
        dest.writeString(this.updateDate);
        dest.writeFloat(this.score);
    }

    protected QSPVideo(Parcel in) {
        this.name = in.readString();
        this.actors = in.readString();
        this.url = in.readString();
        this.cover = in.readString();
        this.remark = in.readString();
        this.type = in.readString();
        this.area = in.readString();
        this.language = in.readString();
        this.director = in.readString();
        this.updateDate = in.readString();
        this.score = in.readFloat();
    }

    public static final Creator<QSPVideo> CREATOR = new Creator<QSPVideo>() {
        @Override
        public QSPVideo createFromParcel(Parcel source) {
            return new QSPVideo(source);
        }

        @Override
        public QSPVideo[] newArray(int size) {
            return new QSPVideo[size];
        }
    };
}
