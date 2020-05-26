package the.one.aqtour.constant;

import rxhttp.wrapper.annotation.DefaultDomain;

public class QSPConstant {

    @DefaultDomain
    public static final String BASE_URL = "http://www.qsptv.net";

    public static final int TYPE_VIDEO = 1;
    public static final int TYPE_IMAGE = 2;
    public static final int TYPE_NOVEL = 2;

    public static String getSearchUrl(String search,int page){
        return "/vodsearch/"+search+"----------"+page+"---.html";
    }

}
