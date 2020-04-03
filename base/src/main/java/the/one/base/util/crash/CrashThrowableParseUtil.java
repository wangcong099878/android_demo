package the.one.base.util.crash;

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

import java.io.PrintWriter;
import java.io.StringWriter;

import the.one.base.BaseApplication;

/**
 * @author The one
 * @date 2020/1/3 0003
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class CrashThrowableParseUtil {


    public static String parseCrash(Throwable ex) {
        StringBuilder builder = new StringBuilder();
        try {
            if (ex.getCause() != null) {
                ex = ex.getCause();
            }
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            StringBuilder builder1 = new StringBuilder();
            builder1.append(sw.toString())
                    .append("\n");
            pw.flush();
            StackTraceElement element = parseThrowable(ex);
            if (element == null) return builder1.toString();
            builder.append("Class:  ")
                    .append(element.getFileName())
                    .append("\n")
                    .append("Method:  ")
                    .append(element.getMethodName())
                    .append("\n")
                    .append("Line:  ")
                    .append(element.getLineNumber())
                    .append("\n")
                    .append("\n")
                    .append(builder1.toString());
        } catch (Exception e) {
            return ex.toString();
        }
        return builder.toString();
    }

    static StackTraceElement parseThrowable(Throwable ex) {
        if (ex == null || ex.getStackTrace() == null || ex.getStackTrace().length == 0) return null;
        StackTraceElement element;
        String packageName = BaseApplication.getInstance().getPackageName();
        for (StackTraceElement ele : ex.getStackTrace()) {
            if (ele.getClassName().contains(packageName)) {
                element = ele;
                return element;
            }
        }
        element = ex.getStackTrace()[0];
        return element;
    }

}
