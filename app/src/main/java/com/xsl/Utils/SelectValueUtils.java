package com.xsl.Utils;

import com.xsl.Entity.AddCustomerEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Administrator  ：zhouyuru
 * 2020/11/4
 * Describe ：
 */
public class SelectValueUtils {
    //    客户查询类型
    public static List<Select> list = new ArrayList<>();

    //    客户池子查询类型
    public static List<SelectPool> selectPools = new ArrayList<>();


    public static List<Select> getList() {
        return list;
    }

    public static void setList(List<Select> list) {
        SelectValueUtils.list = list;
    }


    public static List<SelectPool> getSelectPools() {
        return selectPools;
    }

    public static void setSelectPools(List<SelectPool> selectPools) {
        SelectValueUtils.selectPools = selectPools;
    }


    public static class Select {
        private String key;
        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class By {
        public String key;
        public String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class SelectPool {
        public String key;
        public String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class ByPool {
        public String key;
        public String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
