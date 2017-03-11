package com.pengjunwei.kingmath.mvp.recyclerview;

import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by WikiPeng on 2017/3/11 16:28.
 */
public class ViewTypeProvider implements IViewTypeProvider {
    private static ViewTypeProvider      mInstance;
    protected      HashMap<Class, Class> classHashMap;
    protected      List<Class>           classList;

    private ViewTypeProvider() {
        classList = new ArrayList<>();
        classHashMap = new HashMap<>();
    }

    @Override
    public int getType(Class<?> typeClass) {
        Class clazz = null;
        if (classHashMap.containsKey(typeClass)) {
            clazz = classHashMap.get(typeClass);
        }
        return classList.indexOf(clazz);
    }

    @Override
    public BaseRecyclerViewHolder getViewHolder(ViewGroup parent, int viewType) {
        if (viewType > 0 && viewType < classList.size()) {
            Class clazz = classList.get(viewType);
            try {
                Object object = clazz.newInstance();
                if (object instanceof BaseRecyclerViewHolder) {
                    ((BaseRecyclerViewHolder) object).onCreateView(parent);
                    return (BaseRecyclerViewHolder) object;
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public void register(Class dataClass, Class viewHolderClass) {
        classHashMap.put(dataClass, viewHolderClass);
        if (!classList.contains(viewHolderClass)) {
            classList.add(viewHolderClass);
        }
    }

    public static IViewTypeProvider getInstance() {
        if (mInstance == null) {
            mInstance = new ViewTypeProvider();
        }
        return mInstance;
    }
}
