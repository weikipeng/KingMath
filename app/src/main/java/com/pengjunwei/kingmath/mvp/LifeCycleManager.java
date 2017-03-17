package com.pengjunwei.kingmath.mvp;

import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WikiPeng on 2017/3/17 17:03.
 */
public class LifeCycleManager {

    protected List<ILifeCycleListener> lifeCycleListeners;

    protected void addLifeCycleListeners(ILifeCycleListener listener) {
        if (lifeCycleListeners == null) {
            lifeCycleListeners = new ArrayList<>();
        }

        lifeCycleListeners.add(listener);
    }

    protected void callLifeCycleListeners() {
        String fullClassName = Thread.currentThread().getStackTrace()[3].getClassName();
        String className     = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
        int    lineNumber    = Thread.currentThread().getStackTrace()[3].getLineNumber();
        String methodName    = Thread.currentThread().getStackTrace()[3].getMethodName(); // output : main


        StringBuilder stringBuilder = new StringBuilder(className);
        stringBuilder.append(" ===> ");
        stringBuilder.append(methodName);
        stringBuilder.append(" ===> ");
        stringBuilder.append("callLifeCycleListeners");
        stringBuilder.append("\t\t\t\t\t\tLine:");
        stringBuilder.append(lineNumber);

        Log.println(Log.ERROR, "wikipeng", stringBuilder.toString());

        if (lifeCycleListeners == null || lifeCycleListeners.size() == 0) {
            return;
        }
        for (ILifeCycleListener listener : lifeCycleListeners) {
            if (listener != null) {
                try {
                    Method method = ILifeCycleListener.class.getDeclaredMethod(methodName);
                    method.invoke(listener);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void clear(){
        if (lifeCycleListeners != null) {
            lifeCycleListeners.clear();
        }
    }
}
