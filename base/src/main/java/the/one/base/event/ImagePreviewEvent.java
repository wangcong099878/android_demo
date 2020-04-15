package the.one.base.event;

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

import java.util.List;

import the.one.base.model.ImagePreviewBean;

/**
 * @author The one
 * @date 2020/4/15 0015
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class ImagePreviewEvent implements Parcelable {

    private List<ImagePreviewBean> previewBeans;
    private int position = 0;
    private boolean needDown;

    public ImagePreviewEvent(List<ImagePreviewBean> previewBeans) {
        this.previewBeans = previewBeans;
    }

    public ImagePreviewEvent(List<ImagePreviewBean> previewBeans, int position) {
        this.previewBeans = previewBeans;
        this.position = position;
    }

    public ImagePreviewEvent(List<ImagePreviewBean> previewBeans, int position, boolean needDown) {
        this.previewBeans = previewBeans;
        this.position = position;
        this.needDown = needDown;
    }

    public List<ImagePreviewBean> getPreviewBeans() {
        return previewBeans;
    }

    public void setPreviewBeans(List<ImagePreviewBean> previewBeans) {
        this.previewBeans = previewBeans;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isNeedDown() {
        return needDown;
    }

    public void setNeedDown(boolean needDown) {
        this.needDown = needDown;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.previewBeans);
        dest.writeInt(this.position);
        dest.writeByte(this.needDown ? (byte) 1 : (byte) 0);
    }

    protected ImagePreviewEvent(Parcel in) {
        this.previewBeans = in.createTypedArrayList(ImagePreviewBean.CREATOR);
        this.position = in.readInt();
        this.needDown = in.readByte() != 0;
    }

    public static final Parcelable.Creator<ImagePreviewEvent> CREATOR = new Parcelable.Creator<ImagePreviewEvent>() {
        @Override
        public ImagePreviewEvent createFromParcel(Parcel source) {
            return new ImagePreviewEvent(source);
        }

        @Override
        public ImagePreviewEvent[] newArray(int size) {
            return new ImagePreviewEvent[size];
        }
    };
}
