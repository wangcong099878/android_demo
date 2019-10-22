package the.one.base.model;

/**
 * Created by cretin on 2017/4/20.
 */

public interface IApkUpdate {

    boolean isNewVersion();

    //获取版本号
    int getAppVersionCode();

    //是否强制更新 0 不强制更新 1 hasAffectCodes拥有字段强制更新 2 所有版本强制更新
    boolean isForceUpdate();

    //版本号 描述作用
    String getAppVersionName();

    //新安装包的下载地址
    String getAppApkUrls();

    //更新日志
    String getAppUpdateLog();

    //安装包大小 单位字节
    long getAppApkSize();
}
