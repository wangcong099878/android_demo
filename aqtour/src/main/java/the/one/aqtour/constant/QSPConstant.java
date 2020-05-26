package the.one.aqtour.constant;

public class QSPConstant {

    public static final String BASE_URL = "https://www.qsptv.net";

    public static final int TYPE_VIDEO = 1;
    public static final int TYPE_IMAGE = 2;
    public static final int TYPE_NOVEL = 2;

    public static String getSearchUrl(String search,int page){
        return BASE_URL+"/vodsearch/"+search+"----------"+page+"---.html";
    }

}
