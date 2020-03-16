package the.one.aqtour.m3u8downloader.utils;

import the.one.aqtour.m3u8downloader.M3U8Downloader;
import the.one.aqtour.m3u8downloader.server.EncryptM3U8Server;

public class M3U8PathUtil {

    public static String getLocalPath(EncryptM3U8Server m3u8Server,String url){
       return m3u8Server.createLocalHttpUrl(M3U8Downloader.getInstance().getM3U8Path(url));
    }

}
