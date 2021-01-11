/**
 * Copyright 2020 bejson.com
 */
package com.cyb.Entity;

import java.util.List;

/**
 * Auto-generated: 2020-10-28 10:25:35
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */

//客户池
public class CustomerBean {
    //不知道干嘛的
    public Permission_list permission_list;
    //    筛选框
    public List<Fields_list> fields_list;
    //    客户类型
    public List<Scene_list> scene_list;
    //    客户列表
    public List<Custome_list> list;
    //    当前页数
    private int page;
    //    状态信息
    private String info;
    private int status;

    public Permission_list getPermission_list() {
        return permission_list;
    }

    public void setPermission_list(Permission_list permission_list) {
        this.permission_list = permission_list;
    }

    public List<Fields_list> getFields_list() {
        return fields_list;
    }

    public void setFields_list(List<Fields_list> fields_list) {
        this.fields_list = fields_list;
    }

    public List<Scene_list> getScene_list() {
        return scene_list;
    }

    public void setScene_list(List<Scene_list> scene_list) {
        this.scene_list = scene_list;
    }

    public List<Custome_list> getList() {
        return list;
    }

    public void setList(List<Custome_list> list) {
        this.list = list;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
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

    public static class Fields_list {
        private String name;
        private String field;
        //        设置字段
        private List<SelectValues> setting;

        public List<SelectValues> getSetting() {
            return setting;
        }

        public void setSetting(List<SelectValues> setting) {
            this.setting = setting;
        }

        private String form_type;
        private String input_tips;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getField() {
            return field;
        }


        public void setForm_type(String form_type) {
            this.form_type = form_type;
        }

        public String getForm_type() {
            return form_type;
        }

        public void setInput_tips(String input_tips) {
            this.input_tips = input_tips;
        }

        public String getInput_tips() {
            return input_tips;
        }

    }

    public static class Scene_list {
        private String id;
        private String name;
        private String data;
        private String type;
        private String by;

        public String getBy() {
            return by;
        }

        public void setBy(String by) {
            this.by = by;
        }

        private String cut_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCut_name() {
            return cut_name;
        }

        public void setCut_name(String cut_name) {
            this.cut_name = cut_name;
        }
    }
//客户实体类
//
//"crm_okpkzz": "13728868187",手机号 <string>
//"name": "廖雪红",客户名 <string>
//"customer_id": "117",客户id <string>
//"owner_role_id": "1",... <string>
//"create_time": "1555223062",创建时间 <string>
//"customer_status": "未跟进",客户状态 <string>
//"dial_count": "0",拨打次数 <string>
//"industry": "项目1",姓名名称 <string>
//"owner_name": "李浩",真实名称 <string>
//-"permission": {...<object>
//            "edit": 1,... <number>
//            "view": 1,... <number>
//            "delete": 1... <number>
//    }

    public class Custome_list {
        private String crm_okpkzz;
        private String name;
        private String customer_id;
        private String owner_role_id;
        private String create_time;
        private String customer_status;
        private String dial_count;
        private String industry;
        private String owner_name;

        public String getCustomer_status() {
            return customer_status;
        }

        public void setCustomer_status(String customer_status) {
            this.customer_status = customer_status;
        }

        public String getDial_count() {
            return dial_count;
        }

        public void setDial_count(String dial_count) {
            this.dial_count = dial_count;
        }

        public String getIndustry() {
            return industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        public Object getPermission() {
            return permission;
        }

        public void setPermission(Object permission) {
            this.permission = permission;
        }

        //        是否具有编辑，查看，删除功能
        private Object permission;

        public String getCrm_okpkzz() {
            return crm_okpkzz;
        }

        public void setCrm_okpkzz(String crm_okpkzz) {
            this.crm_okpkzz = crm_okpkzz;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(String customer_id) {
            this.customer_id = customer_id;
        }

        public String getOwner_role_id() {
            return owner_role_id;
        }

        public void setOwner_role_id(String owner_role_id) {
            this.owner_role_id = owner_role_id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getOwner_name() {
            return owner_name;
        }

        public void setOwner_name(String owner_name) {
            this.owner_name = owner_name;
        }


        public void setPermission(Permission[] permission) {
            this.permission = permission;
        }

        public class Permission {
            private int edit;
            private int view;
            private int delete;

            public int getEdit() {
                return edit;
            }

            public void setEdit(int edit) {
                this.edit = edit;
            }

            public int getView() {
                return view;
            }

            public void setView(int view) {
                this.view = view;
            }

            public int getDelete() {
                return delete;
            }

            public void setDelete(int delete) {
                this.delete = delete;
            }
        }

        @Override
        public String toString() {
            return "Custome_list{" +
                    "crm_okpkzz='" + crm_okpkzz + '\'' +
                    ", name='" + name + '\'' +
                    ", customer_id='" + customer_id + '\'' +
                    ", owner_role_id='" + owner_role_id + '\'' +
                    ", create_time='" + create_time + '\'' +
                    ", customer_status='" + customer_status + '\'' +
                    ", dial_count='" + dial_count + '\'' +
                    ", industry='" + industry + '\'' +
                    ", owner_name='" + owner_name + '\'' +
                    ", permission=" + permission +
                    '}';
        }
    }

    public static class SelectValues {
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