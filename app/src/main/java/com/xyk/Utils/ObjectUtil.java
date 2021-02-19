package com.xyk.Utils;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Map;

/**
 * @author Administrator
 * @date 2018/9/7 0007
 * @des
 */
public class ObjectUtil {

    /**
     * 判断一个对象是否为空  true为空   ， false非空
     *
     * @param obj
     * @return
     */
    public static boolean isNullOrEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        }

        if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        }

        if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }

        if (obj instanceof Object[]) {
            Object[] object = (Object[]) obj;
            if (object.length == 0) {
                return true;
            }
            boolean empty = true;
            for (int i = 0; i < object.length; i++) {
                if (!isNullOrEmpty(object[i])) {
                    empty = false;
                    break;
                }
            }
            return empty;
        }

        return false;
    }


    public static String join(CharSequence delimiter, Iterable<? extends CharSequence> elements) {
        if (isNullOrEmpty(delimiter) || isNullOrEmpty(elements)) {
            throw new NullPointerException("分隔符和数组不能为null");
        } else {
            StringBuilder builder = new StringBuilder();
            for (CharSequence cs : elements) {
                builder.append(cs).append(delimiter);
            }
            if (builder.length() > delimiter.length()) {
                builder.delete(builder.length() - delimiter.length(), builder.length());//删除最后位数的分割符
            }
            return builder.toString();
        }
    }


    /**
     * 获取一个对象的泛型对象实例
     *
     * @param o
     * @param i
     * @param <T>
     * @return
     */
    public static <T> T getT(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }

}
