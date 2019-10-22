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
 * @date 2019/10/22 0022
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class UpdateApkBean implements Parcelable {

    private String url;
    private String verName;
    private String updateLog;
    private long apkSize;
    private int verCode;
    private boolean isForce;
    private boolean isNewVer;

    public UpdateApkBean(IApkUpdate update) {
        this.url = update.getAppApkUrls();
        this.verName = update.getAppVersionName();
        this.updateLog = update.getAppUpdateLog();
        this.apkSize = update.getAppApkSize();
        this.verCode = update.getAppVersionCode();
        this.isForce = update.isForceUpdate();
        this.isNewVer = update.isNewVersion();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVerName() {
        return verName;
    }

    public void setVerName(String verName) {
        this.verName = verName;
    }

    public String getUpdateLog() {
        return updateLog;
    }

    public void setUpdateLog(String updateLog) {
        this.updateLog = updateLog;
    }

    public long getApkSize() {
        return apkSize;
    }

    public void setApkSize(long apkSize) {
        this.apkSize = apkSize;
    }

    public int getVerCode() {
        return verCode;
    }

    public void setVerCode(int verCode) {
        this.verCode = verCode;
    }

    public boolean isForce() {
        return isForce;
    }

    public void setForce(boolean force) {
        isForce = force;
    }

    public boolean isNewVer() {
        return isNewVer;
    }

    public void setNewVer(boolean newVer) {
        isNewVer = newVer;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.verName);
        dest.writeString(this.updateLog);
        dest.writeLong(this.apkSize);
        dest.writeInt(this.verCode);
        dest.writeByte(this.isForce ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isNewVer ? (byte) 1 : (byte) 0);
    }

    protected UpdateApkBean(Parcel in) {
        this.url = in.readString();
        this.verName = in.readString();
        this.updateLog = in.readString();
        this.apkSize = in.readLong();
        this.verCode = in.readInt();
        this.isForce = in.readByte() != 0;
        this.isNewVer = in.readByte() != 0;
    }

    public static final Creator<UpdateApkBean> CREATOR = new Creator<UpdateApkBean>() {
        @Override
        public UpdateApkBean createFromParcel(Parcel source) {
            return new UpdateApkBean(source);
        }

        @Override
        public UpdateApkBean[] newArray(int size) {
            return new UpdateApkBean[size];
        }
    };
}
