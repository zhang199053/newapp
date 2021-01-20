package com.yh.Entity;

import java.util.List;

/**
 * Administrator  ：zhouyuru
 * 2020/10/30
 * Describe ：
 */
public class CustomerBusinessEntity {
    private Permission_list permission_list;
    private List<Fields_list> fields_list;
    private List<CustomerBean.Scene_list> scene_list;
    private List<BusinessList> list;
    private int page;
    private String info;
    private int status;

    public void setPermission_list(Permission_list permission_list) {
        this.permission_list = permission_list;
    }

    public Permission_list getPermission_list() {
        return permission_list;
    }

    public List<Fields_list> getFields_list() {
        return fields_list;
    }

    public void setFields_list(List<Fields_list> fields_list) {
        this.fields_list = fields_list;
    }

    public List<CustomerBean.Scene_list> getScene_list() {
        return scene_list;
    }

    public void setScene_list(List<CustomerBean.Scene_list> scene_list) {
        this.scene_list = scene_list;
    }

    public List<BusinessList> getList() {
        return list;
    }

    public void setList(List<BusinessList> list) {
        this.list = list;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
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

//    "list": [商机列表<array>
//-{
//        "name": "M_20190506-0001",商机名称 <string>
//                "business_id": "3",... <string>
//                "final_price": "1098000.00",商机最终价 <string>
//                "customer_id": "117",... <string>
//                "owner_role_id": "1",... <string>
//                "status_id": "100",... <string>
//                "create_time": "1557110236",创建时间 <string>
//                "status_type_id": "1",... <string>
//                "customer_name": "廖雪红",... <string>
//                "schedule": 100,完成度 <number>
//                "status_name": "完成收款",状态名称 <string>
//                -"permission": {...<object>
//                "edit": 1,... <number>
//                "view": 1,... <number>
//                "delete": 1... <number>
//        }
//    }
//],

    public class BusinessList {

        private String name;
        private String business_id;
        private String final_price;
        private String customer_id;
        private String owner_role_id;
        private String status_id;
        private String create_time;
        private String status_type_id;
        private String customer_name;
        private int schedule;
        private String status_name;
        private Object permission;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setBusiness_id(String business_id) {
            this.business_id = business_id;
        }

        public String getBusiness_id() {
            return business_id;
        }

        public void setFinal_price(String final_price) {
            this.final_price = final_price;
        }

        public String getFinal_price() {
            return final_price;
        }

        public void setCustomer_id(String customer_id) {
            this.customer_id = customer_id;
        }

        public String getCustomer_id() {
            return customer_id;
        }

        public void setOwner_role_id(String owner_role_id) {
            this.owner_role_id = owner_role_id;
        }

        public String getOwner_role_id() {
            return owner_role_id;
        }

        public void setStatus_id(String status_id) {
            this.status_id = status_id;
        }

        public String getStatus_id() {
            return status_id;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setStatus_type_id(String status_type_id) {
            this.status_type_id = status_type_id;
        }

        public String getStatus_type_id() {
            return status_type_id;
        }

        public void setCustomer_name(String customer_name) {
            this.customer_name = customer_name;
        }

        public String getCustomer_name() {
            return customer_name;
        }

        public void setSchedule(int schedule) {
            this.schedule = schedule;
        }

        public int getSchedule() {
            return schedule;
        }
        public void setStatus_name(String status_name) {
            this.status_name = status_name;
        }

        public String getStatus_name() {
            return status_name;
        }

        public Object getPermission() {
            return permission;
        }

        public void setPermission(Object permission) {
            this.permission = permission;
        }
    }

    public class Fields_list {
        private String name;
        private String field;
        //        设置字段
        private Object setting;

        private String form_type;
        private String input_tips;

        public Object getSetting() {
            return setting;
        }

        public void setSetting(Object setting) {
            this.setting = setting;
        }

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

}
