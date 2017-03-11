package com.pengjunwei.kingmath.mvp.recyclerview;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by WikiPeng on 2017/3/11 16:28.
 */
public class ViewTypeProvider implements IViewTypeProvider {
    private static ViewTypeProvider                mInstance;
    /**
     * 从数据类对应 viewholder
     */
    protected      HashMap<Class, Class>           classHashMap;
    /**
     * 从viewholder 对应layout
     */
    protected      HashMap<Class, ILayoutProvider> layoutHashMap;

    protected List<Class> classList;

    private ViewTypeProvider() {
        classList = new ArrayList<>();
        classHashMap = new HashMap<>();
        layoutHashMap = new HashMap<>();
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
        if (viewType >= 0 && viewType < classList.size()) {
            Class           clazz          = classList.get(viewType);
            ILayoutProvider layoutProvider = layoutHashMap.get(clazz);
            if (layoutProvider != null) {
                return layoutProvider.onCreateViewHolder(LayoutInflater.from(parent.getContext()), parent);
            }
        }

        return null;
    }

    @Override
    public void register(Class dataClass, Class viewHolderClass, ILayoutProvider layoutProvider) {
        classHashMap.put(dataClass, viewHolderClass);
        layoutHashMap.put(viewHolderClass, layoutProvider);
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
