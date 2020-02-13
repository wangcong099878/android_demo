package the.one.demo.constant;

public class MzituConstant {

    public static final String WELFARE_BASE_URL = "http://www.mzitu.com/";

    public static final String HOT = "hot";
    public static final String XING_GAN = "xinggan";
    public static final String JAPAN = "japan";
    public static final String TAIWAN = "taiwan";
    public static final String MM = "mm";
    public static final String ZIPAI = "zipai";
    public static final String JIEPAI = "jiepai";
    public static final String BEST = "best";
    public static String[] welfareTitle = { "推荐","火热","性感","清纯","日本","台湾","自拍","街拍"};

    public static String[] welfareUrl = { BEST,HOT, XING_GAN, MM, JAPAN, TAIWAN, ZIPAI,JIEPAI};

    public static boolean isNoDetail(String url){
        return url.equals(ZIPAI) || url.equals(JIEPAI);
    }
}
