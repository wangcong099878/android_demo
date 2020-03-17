package the.one.base.util;

import android.app.Activity;

import java.util.ArrayList;

public class ActivityListUtil {

    private static ActivityListUtil instence;
    private ArrayList<Activity> activityList;

    public ActivityListUtil() {
        activityList = new ArrayList<>();
    }

    public static ActivityListUtil getInstance() {
        if (instence == null) {
            instence = new ActivityListUtil();
        }
        return instence;
    }

    public void addActivityToList(Activity activity) {
        if (activity != null) {
            activityList.add(activity);
        }
    }

    public void removeActivityFromList(Activity activity) {
        if (activityList != null && activityList.size() > 0) {
            activityList.remove(activity);
        }
    }

    public void removeOthersActivityFromList(Activity keepActivity) {
        if (activityList != null && activityList.size() > 0) {
            for (int i = 0; i < activityList.size(); i++) {
                Activity activity = activityList.get(i);
                if (activity != null && !activity.isFinishing()
                        && !keepActivity.getComponentName().equals(activity.getComponentName())) {
                    activity.finish();
                }
            }
        }
    }

    public void cleanActivityList() {
        if (activityList != null && activityList.size() > 0) {
            for (int i = 0; i < activityList.size(); i++) {
                Activity activity = activityList.get(i);
                if (activity != null && !activity.isFinishing()) {
                    activity.finish();
                }
            }
        }

    }

}
