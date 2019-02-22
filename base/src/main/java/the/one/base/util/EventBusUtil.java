package the.one.base.util;

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

import org.greenrobot.eventbus.EventBus;

import the.one.base.event.SuccessEvent;


/**
 * @author The one
 * @date 2018/12/12 0012
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class EventBusUtil {

    public static void register(Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    public static void unregister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    public static void sendEvent(Object event) {
        EventBus.getDefault().post(event);
    }

    public static void sendSuccessEvent() {
        sendSuccessEvent(0);
    }
    public static void sendSuccessEvent(int type) {
        sendEvent(new SuccessEvent(type));
    }
    public static void sendStickyEvent(Object event) {
        EventBus.getDefault().postSticky(event);
    }

    public static void removeStickyEvent(Object stickyEvent){
        EventBus.getDefault().removeStickyEvent(stickyEvent);
    }
}
