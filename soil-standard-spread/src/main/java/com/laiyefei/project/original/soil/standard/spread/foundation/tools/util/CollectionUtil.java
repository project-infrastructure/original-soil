package com.laiyefei.project.original.soil.standard.spread.foundation.tools.util;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.tools.util.IUtil;

import java.util.ArrayList;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-04-20 07:23
 * @Desc : this is class named CollectionUtil for do CollectionUtil
 * @Version : v1.0.0.20200420
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public abstract class CollectionUtil implements IUtil {
    private CollectionUtil() {
        throw new RuntimeException("error: CollectionUtil can no be an instance.");
    }

    public static final <T> ArrayList<T> ToArrayList(final T t) {
        final ArrayList<T> arrayList = new ArrayList<>();
        arrayList.add(t);
        return arrayList;
    }

}
