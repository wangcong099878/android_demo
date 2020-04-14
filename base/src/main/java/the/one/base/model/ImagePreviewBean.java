package the.one.base.model;

import android.os.Parcel;
import android.os.Parcelable;

import the.one.base.Interface.ImageSnap;

public class ImagePreviewBean implements ImageSnap, Parcelable {

    private String url;
    private String refer;
    private boolean isVideo = false;

    public ImagePreviewBean(String url) {
        this.url = url;
    }

    public ImagePreviewBean(String url, String refer) {
        this.url = url;
        this.refer = refer;
    }

    public ImagePreviewBean(String url, String refer, boolean isVideo) {
        this.url = url;
        this.refer = refer;
        this.isVideo = isVideo;
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
    public boolean isVideo() {
        return isVideo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.refer);
        dest.writeByte(this.isVideo ? (byte) 1 : (byte) 0);
    }

    protected ImagePreviewBean(Parcel in) {
        this.url = in.readString();
        this.refer = in.readString();
        this.isVideo = in.readByte() != 0;
    }

    public static final Parcelable.Creator<ImagePreviewBean> CREATOR = new Parcelable.Creator<ImagePreviewBean>() {
        @Override
        public ImagePreviewBean createFromParcel(Parcel source) {
            return new ImagePreviewBean(source);
        }

        @Override
        public ImagePreviewBean[] newArray(int size) {
            return new ImagePreviewBean[size];
        }
    };
}
