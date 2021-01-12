/**
 * Copyright 2020 bejson.com
 */
package com.xsl.Entity;

import java.util.Date;

//{
//        -"data": {数据集<object>
//        "name": "cd",客户名 <string>
//        "owner_role_id": "1",所属人id <string>
//        "update_time": "2020-10-19 10:17:24",更新时间 <string>
//        "get_time": "1603073844",领取或分配时间 <string>
//        "is_locked": "0",客户是否被锁定 <string>
//        "contacts_id": "0",... <string>
//        -"owner": {...<object>
//        "full_name": "李浩",负责人名称 <string>
//        "role_id": "1"负责人id <string>
//        },
//        "contacts_telephone": "",联系电话 <string>
//        "contacts_name": "",联系名称 <string>
//        "focus": 0... <number>
//        },
//        "info": "success",成功或失败信息 <string>
//        "status": 11：成功 <number>
//        }
public class CustomerDetailsBean {

    private Data data;
    private String info;
    private int status;

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
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

    public class Data {

        private String name;
        private String owner_role_id;
        private String update_time;
        private String get_time;
        private String is_locked;
        private String contacts_id;
        private Owner owner;
        private String contacts_telephone;
        private String contacts_name;
        private int focus;

        public String getUpdate_time() {
            return update_time;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setOwner_role_id(String owner_role_id) {
            this.owner_role_id = owner_role_id;
        }

        public String getOwner_role_id() {
            return owner_role_id;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }


        public void setGet_time(String get_time) {
            this.get_time = get_time;
        }

        public String getGet_time() {
            return get_time;
        }

        public void setIs_locked(String is_locked) {
            this.is_locked = is_locked;
        }

        public String getIs_locked() {
            return is_locked;
        }

        public void setContacts_id(String contacts_id) {
            this.contacts_id = contacts_id;
        }

        public String getContacts_id() {
            return contacts_id;
        }

        public void setOwner(Owner owner) {
            this.owner = owner;
        }

        public Owner getOwner() {
            return owner;
        }

        public void setContacts_telephone(String contacts_telephone) {
            this.contacts_telephone = contacts_telephone;
        }

        public String getContacts_telephone() {
            return contacts_telephone;
        }

        public void setContacts_name(String contacts_name) {
            this.contacts_name = contacts_name;
        }

        public String getContacts_name() {
            return contacts_name;
        }

        public void setFocus(int focus) {
            this.focus = focus;
        }

        public int getFocus() {
            return focus;
        }

    }

    public class Owner {

        private String full_name;
        private String role_id;

        public void setFull_name(String full_name) {
            this.full_name = full_name;
        }

        public String getFull_name() {
            return full_name;
        }

        public void setRole_id(String role_id) {
            this.role_id = role_id;
        }

        public String getRole_id() {
            return role_id;
        }

    }
}