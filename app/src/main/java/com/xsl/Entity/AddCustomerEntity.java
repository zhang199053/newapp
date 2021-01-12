package com.xsl.Entity;

import java.io.Serializable;
import java.util.List;

/**
 * Administrator  ：zhouyuru
 * 2020/11/2
 * Describe ：
 */
public class AddCustomerEntity {
    private List<Customer_list> data;
    private String info;
    private int status;

    public List<Customer_list> getData() {
        return data;
    }

    public void setData(List<Customer_list> data) {
        this.data = data;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public class Customer_list {
        private String is_main;
        private String field;
        private String name;
        private String form_type;
        private String default_value;
        private String max_length;
        private String is_unique;
        private String is_null;
        private String is_validate;
        private String in_add;
        private String input_tips;
        private List<SelectValue> setting;

        public String getIs_main() {
            return is_main;
        }

        public void setIs_main(String is_main) {
            this.is_main = is_main;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getForm_type() {
            return form_type;
        }

        public void setForm_type(String form_type) {
            this.form_type = form_type;
        }

        public String getDefault_value() {
            return default_value;
        }

        public void setDefault_value(String default_value) {
            this.default_value = default_value;
        }

        public String getMax_length() {
            return max_length;
        }

        public void setMax_length(String max_length) {
            this.max_length = max_length;
        }

        public String getIs_unique() {
            return is_unique;
        }

        public void setIs_unique(String is_unique) {
            this.is_unique = is_unique;
        }

        public String getIs_null() {
            return is_null;
        }

        public void setIs_null(String is_null) {
            this.is_null = is_null;
        }

        public String getIs_validate() {
            return is_validate;
        }

        public void setIs_validate(String is_validate) {
            this.is_validate = is_validate;
        }

        public String getIn_add() {
            return in_add;
        }

        public void setIn_add(String in_add) {
            this.in_add = in_add;
        }

        public String getInput_tips() {
            return input_tips;
        }

        public void setInput_tips(String input_tips) {
            this.input_tips = input_tips;
        }

        public List<SelectValue> getSetting() {
            return setting;
        }

        public void setSetting(List<SelectValue> setting) {
            this.setting = setting;
        }
    }

    public class SelectValue  implements Serializable {
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
}
