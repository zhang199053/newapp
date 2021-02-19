package com.xyk.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Administrator  ：zhouyuru
 * 2020/11/5
 * Describe ：
 */
public class CustomerInput {
    public static List<Inputs> inpit = new ArrayList<>();

    public List<Inputs> getInpit() {
        return inpit;
    }

    public void setInpit(List<Inputs> inpit) {
        this.inpit = inpit;
    }

    public static class Inputs {
        //        字段id
        private String tv_field;
        //        字段名
        private String name;
        //        输入内容
        private String value;
        //        是否为空
        private String is_validate;

        public String getTv_field() {
            return tv_field;
        }

        public void setTv_field(String tv_field) {
            this.tv_field = tv_field;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getIs_validate() {
            return is_validate;
        }

        public void setIs_validate(String is_validate) {
            this.is_validate = is_validate;
        }
    }


}
