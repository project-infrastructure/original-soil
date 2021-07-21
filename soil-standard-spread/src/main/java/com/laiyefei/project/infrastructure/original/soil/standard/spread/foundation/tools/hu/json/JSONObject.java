package com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.hu.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.utensil.hu.IHU;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.util.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @Author : leaf.fly(?)
 * @Create : 2020-04-04 19:11
 * @Desc : this is class named JSONObject for do JSONObject
 * @Version : v2.0.0.20200404
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public final class JSONObject extends LinkedHashMap<String, Object> implements IHU {
    private static final long serialVersionUID = 5649783212022708121L;
    private static final Pattern arrayNamePattern = Pattern.compile("(\\w+)((\\[\\d+\\])+)");
    private static final ObjectMapper objectMapper = new ObjectMapper();


    private interface EndArrayCallback<T> {
        T callback(final JSONArray arr, final int index);
    }


    public static class JSONArray extends ArrayList<Object> implements IHU {
        private static final long serialVersionUID = 2869255723111128209L;

        public JSONArray() {
            super();
        }

        public JSONArray(int size) {
            super(size);
        }

        @Override
        public String toString() {
            return JSONUtil.Marshal(this);
        }

        @Override
        public Object set(int index, Object element) {
            return super.set(index, transfer(element));
        }

        @Override
        public boolean add(Object element) {
            return super.add(transfer(element));
        }

        @Override
        public void add(int index, Object element) {
            super.add(index, transfer(element));
        }
    }


    public final Object value(final String name) {
        final int indexDot = name.indexOf('.');
        if (0 <= indexDot) {
            return toObj(name.substring(0, indexDot)).value(name.substring(indexDot + 1));
        }
        final Matcher matcher = arrayNamePattern.matcher(name);
        if (matcher.find()) {
            return endArray(matcher.group(1), matcher.group(2), (arr, index) -> elementAt(arr, index));
        }
        return get(name);
    }


    private final <T> T endArray(
            final String name,
            final String indexesStr,
            final EndArrayCallback<T> callback) {
        JSONArray endArr = toArr(name);
        final int[] indexes = parseIndexes(indexesStr);
        int i = 0;
        while (i < indexes.length - 1) {
            endArr = arrayAt(endArr, indexes[i++]);
        }
        return callback.callback(endArr, indexes[i]);
    }

    public final JSONObject toObj(final String name) {
        final Matcher matcher = arrayNamePattern.matcher(name);
        if (matcher.find()) {
            return endArray(matcher.group(1), matcher.group(2), new EndArrayCallback<JSONObject>() {
                public JSONObject callback(JSONArray arr, int index) {
                    return objAt(arr, index);
                }
            });
        } else {
            JSONObject obj = getObj(name);
            if (obj == null) {
                obj = new JSONObject();
                put(name, obj);
            }
            return obj;
        }
    }

    public final JSONArray toArr(final String name) {
        JSONArray arr = getArr(name);
        if (JudgeUtil.IsNull(arr)) {
            arr = new JSONArray();
            put(name, arr);
        }
        return arr;
    }

    public final JSONObject getObj(final String name) {
        return ObjectUtil.Cast(get(name));
    }

    public final String getString(final String name) {
        return AsString(get(name));
    }

    public final String getString(final String name, final String defaultValue) {
        return JudgeUtil.NVL(getString(name), defaultValue);
    }

    public final JSONArray getArr(final String name) {
        return ObjectUtil.Cast(get(name));
    }

    public static final Integer AsInt(final Object value) {
        if (JudgeUtil.IsNull(value)) {
            return IntegerUtil.EMPTY;
        }

        if (value instanceof Integer) {
            return ObjectUtil.Cast(value);
        }

        if (value instanceof Number) {
            final Number number = ObjectUtil.Cast(value);
            return number.intValue();
        }

        if (value instanceof String) {
            return Integer.valueOf(ObjectUtil.Cast(value));
        }

        if (value instanceof Boolean) {
            return ObjectUtil.Cast(value) ? IntegerUtil.ONE : IntegerUtil.ZERO;
        }

        return IntegerUtil.EMPTY;
    }

    public static final Long AsLong(final Object value) {
        if (JudgeUtil.IsNull(value)) {
            return LongUtil.EMPTY;
        }
        if (value instanceof Long) {
            return ObjectUtil.Cast(value);
        }
        if (value instanceof Number) {
            final Number number = ObjectUtil.Cast(value);
            return number.longValue();
        }
        if (value instanceof String) {
            return Long.valueOf(ObjectUtil.Cast(value));
        }
        if (value instanceof Boolean) {
            return ObjectUtil.Cast(value) ? LongUtil.ONE : LongUtil.ZERO;
        }
        return LongUtil.EMPTY;
    }

    public static final String AsString(final Object value) {
        if (JudgeUtil.IsNull(value)) {
            return StringUtil.EMPTY;
        }
        if (value instanceof String) {
            return (String) value;
        } else if (value != null) {
            return value.toString();
        } else {
            return null;
        }
    }

    public static final Boolean AsBool(final Object value) {
        if (JudgeUtil.IsNull(value)) {
            return BooleanUtil.EMPTY;
        }
        if (value instanceof Boolean) {
            return ObjectUtil.Cast(value);
        }
        if (value instanceof Number) {
            final Number number = ObjectUtil.Cast(value);
            return DoubleUtil.EMPTY != number.doubleValue();
        }
        if (value instanceof String) {
            return Boolean.valueOf(ObjectUtil.Cast(value));
        }
        return BooleanUtil.EMPTY;
    }


    private static final Object transfer(final Object value) {
        if (value instanceof JSONObject) {
            return value;
        }
        if (value instanceof Map) {
            return toObj((Map<String, Object>) value);
        }
        if (value instanceof Collection) {
            return toArr((Collection<Object>) value);
        }
        return value;
    }

    private static final JSONArray toArr(final Collection<Object> list) {
        final JSONArray arr = new JSONArray(list.size());
        for (final Object element : list) {
            arr.add(element);
        }
        return arr;
    }

    private static final JSONObject toObj(final Map<String, Object> map) {
        final JSONObject obj = new JSONObject();
        for (final Map.Entry<String, Object> ent : map.entrySet()) {
            obj.put(ent.getKey(), transfer(ent.getValue()));
        }
        return obj;
    }

    private static final int[] parseIndexes(final String s) {
        final List<Integer> list = new ArrayList<Integer>();
        final StringTokenizer st = new StringTokenizer(s, "[]");
        while (st.hasMoreTokens()) {
            final int index = Integer.valueOf(st.nextToken());
            if (index < 0) {
                throw new RuntimeException(String.format("Illegal index %1$d in \"%2$s\"", index, s));
            }
            list.add(index);
        }
        final int[] indexes = new int[list.size()];
        int i = 0;
        for (Integer tmp : list.toArray(new Integer[list.size()])) {
            indexes[i++] = tmp;
        }
        return indexes;
    }

    private static final JSONObject objAt(final JSONArray arr, final int index) {
        expand(arr, index);
        if (JudgeUtil.IsNull(arr.get(index))) {
            arr.set(index, new JSONObject());
        }
        return ObjectUtil.Cast(arr.get(index));
    }

    private static final void elementAt(final JSONArray arr, final int index, final Object value) {
        expand(arr, index).set(index, value);
    }


    private static final Object elementAt(final JSONArray arr, final int index) {
        return expand(arr, index).get(index);
    }


    private static JSONArray arrayAt(JSONArray arr, int index) {
        expand(arr, index);
        if (JudgeUtil.IsNull(arr.get(index))) {
            arr.set(index, new JSONArray());
        }
        return ObjectUtil.Cast(arr.get(index));
    }

    private static final JSONArray expand(final JSONArray arr, final int index) {
        while (arr.size() <= index) {
            arr.add(ObjectUtil.GetNULL());
        }
        return arr;
    }

}
