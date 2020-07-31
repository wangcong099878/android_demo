package the.one.base.util;

public class SkinSpUtil {

    private static final String QMUI_SKIN_MANAGER ="qmui_skin_manager";

    public static void setQMUISkinManager(boolean open){
        SpUtil.getInstance().putBoolean(QMUI_SKIN_MANAGER,open);
    }

    public static boolean isQMUISkinManger(){
        return SpUtil.getInstance().getBoolean(QMUI_SKIN_MANAGER,false);
    }

}
