package the.one.base.constant;

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

import android.content.Context;
import android.os.Build;

import com.qmuiteam.qmui.util.QMUIDeviceHelper;

/**
 * @author The one
 * @date 2019/8/20 0020
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class PhoneConstant {

    public static boolean isHaveVibrator = true;

    public static String[] bands = {"16th","16s"};

    public static void init(Context context){
        boolean isMeizu = QMUIDeviceHelper.isMeizu();
        boolean isBand = false;
        if(isMeizu){
            String brand = Build.MODEL;
            for (int i = 0; i < bands.length; i++) {
                if(brand.equals(bands[i])){
                    isBand = true;
                    break;
                }
            }
        }
        isHaveVibrator =  isMeizu && isBand;
    }

}
