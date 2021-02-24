/**
 * Copyright 2020 bejson.com
 */
package com.yhj.Entity;

import java.io.Serializable;
import java.util.List;

//{
//        -"permission": {...<object>
//        "edit": 1,编辑权限 <number>
//        "view": 1,查看权限 <number>
//        "delete": 1删除权限 <number>
//        },
//        -"data": [...<array>
//        -{
//        "field": "owner_role_id",字段 <string>
//        "name": "负责人",字段名 <string>
//        "form_type": "user",表单类型 <string>
//        "val": "李浩",值 <string>
//        "id": "1",... <string>
//        "type": 1... <number>
//        }
//        ],
//        "info": "success",... <string>
//        "status": 1... <number>
//        }
public class CustomerDetailsTwoBean {
    private Object permission;
    private List<Data> data;
    private String info;
    private int status;

    public Object getPermission() {
        return permission;
    }

    public void setPermission(Object permission) {
        this.permission = permission;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public List<Data> getData() {
        return data;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public class Data implements Serializable {

        private String field;
        private String name;
        private String form_type;
        private List<AddCustomerEntity.SelectValue> setting;
        private String val;
        private String id;
        private int type;

        public void setField(String field) {
            this.field = field;
        }

        public String getField() {
            return field;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setForm_type(String form_type) {
            this.form_type = form_type;
        }

        public String getForm_type() {
            return form_type;
        }

        public void setVal(String val) {
            this.val = val;
        }

        public String getVal() {
            return val;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }

        public List<AddCustomerEntity.SelectValue> getSetting() {
            return setting;
        }

        public void setSetting(List<AddCustomerEntity.SelectValue> setting) {
            this.setting = setting;
        }
    }
}