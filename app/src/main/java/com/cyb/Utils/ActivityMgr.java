package com.cyb.Utils;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by Huang Mingfei on 2017/9/20 0020.
 */

public class ActivityMgr {
    private Stack<Activity> mActivites = new Stack<Activity>();
    private String tag = "";
    private static ActivityMgr instance;

    public static ActivityMgr getInstance() {
        if (instance == null) {
            instance = new ActivityMgr();
        }
        return instance;
    }

    private ActivityMgr() {
    }

    public void putActivity(Activity act) {
        if (act != null) {
            mActivites.add(act);
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            if (!activity.isFinishing()) {
                activity.finish();
                mActivites.remove(activity);
            }
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : mActivites) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                break;
            }
        }
    }

    public void removeActivity(Activity act) {
        if (act != null) {
            mActivites.remove(act);
            act.finish();
        }
    }

    public void clearActivity() {
        for (Activity act : mActivites) {
            if (act != null) {
                act.finish();
            }
        }
        mActivites.clear();
    }

    public Activity getCurrentActivity() {
        return mActivites.lastElement();
    }


    public void add(Activity activity) {
        if (activity != null) {
            mActivites.add(activity);
        }
    }

    public void removeAll() {
        for (Activity activity : mActivites) {
            if (activity != null) {
                activity.finish();
            }
        }
        mActivites.clear();
        this.tag = "";
    }

    /**
     * 获取当前堆栈顶部Activity的前一个activity
     */
    public Activity preActivity() {
        if (mActivites.size() == 1) {
            return null;
        }
        int index = mActivites.size() - 2;
        if (index < 0) {
            return null;
        } else {
            return mActivites.get(index);
        }
    }
}
