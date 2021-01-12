package com.xsl.Entity;

import java.util.List;

/**
 * Administrator  ：zhouyuru
 * 2020/10/30
 * Describe ：
 */

//{
//        -"list": [...<array>
//        -{
//        "log_id": "22",... <string>
//        "role_id": "1",... <string>
//        "category_id": "1",... <string>
//        "status_id": "2",跟进类型 <string>
//        "sign": "0",... <string>
//        "create_date": "2020-10-26 16:28:01",创建时间 <string>
//        "update_date": "1603700881",更新时间 <string>
//        "nextstep_time": "2020-10-26 16:27:00",下次联系时间 <string>
//        "subject": "",主题文本 <string>
//        "content": "asdfsaf",内容 <string>
//        "comment_id": "0",... <string>
//        "about_roles": "",新增相关人 <string>
//        "about_roles_name": "",新增相关人姓名 <string>
//        "status": "0",0未阅1已阅2已点评 <string>
//        -"owner": {客户所属人<object>
//        "role_id": "1",... <string>
//        "user_name": "李浩",所属人用户名 <string>
//        "thumb_path": "./Uploads/head/thumb_5caf434b67fc59813.png",... <string>
//        "role_name": "总管理员",所属人岗位名 <string>
//        "department_name": "董事会"所属人部门名称 <string>
//        },
//        "type": 1,... <number>
//        "status_name": "微信",跟进类型名称 <string>
//        "business_name": "M_20190506-0001",公司名称 <string>
//        "business_id": "3"... <string>
//        }
//        ],
//        "page": 0,... <number>
//        "info": "success",... <string>
//        "status": 1... <number>
//        }
public class CustomerLogEntity {
    private List<LogList> list;
    private int page;
    private String info;
    private int status;

    public List<LogList> getList() {
        return list;
    }

    public void setList(List<LogList> list) {
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

    public class LogList {

        private String log_id;
        private String role_id;
        private String category_id;
        private String status_id;
        private String sign;
        private String create_date;
        private String update_date;
        private String nextstep_time;
        private String subject;
        private String content;
        private String comment_id;
        private String about_roles;
        private String about_roles_name;
        private String status;
        private Owner owner;
        private int type;
        private String status_name;
        private String business_name;
        private String business_id;

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public String getNextstep_time() {
            return nextstep_time;
        }

        public void setNextstep_time(String nextstep_time) {
            this.nextstep_time = nextstep_time;
        }

        public void setLog_id(String log_id) {
            this.log_id = log_id;
        }

        public String getLog_id() {
            return log_id;
        }

        public void setRole_id(String role_id) {
            this.role_id = role_id;
        }

        public String getRole_id() {
            return role_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setStatus_id(String status_id) {
            this.status_id = status_id;
        }

        public String getStatus_id() {
            return status_id;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getSign() {
            return sign;
        }


        public void setUpdate_date(String update_date) {
            this.update_date = update_date;
        }

        public String getUpdate_date() {
            return update_date;
        }


        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getSubject() {
            return subject;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }

        public void setComment_id(String comment_id) {
            this.comment_id = comment_id;
        }

        public String getComment_id() {
            return comment_id;
        }

        public void setAbout_roles(String about_roles) {
            this.about_roles = about_roles;
        }

        public String getAbout_roles() {
            return about_roles;
        }

        public void setAbout_roles_name(String about_roles_name) {
            this.about_roles_name = about_roles_name;
        }

        public String getAbout_roles_name() {
            return about_roles_name;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }

        public void setOwner(Owner owner) {
            this.owner = owner;
        }

        public Owner getOwner() {
            return owner;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }

        public void setStatus_name(String status_name) {
            this.status_name = status_name;
        }

        public String getStatus_name() {
            return status_name;
        }

        public void setBusiness_name(String business_name) {
            this.business_name = business_name;
        }

        public String getBusiness_name() {
            return business_name;
        }

        public void setBusiness_id(String business_id) {
            this.business_id = business_id;
        }

        public String getBusiness_id() {
            return business_id;
        }

    }

    public class Owner {

        private String role_id;
        private String user_name;
        private String thumb_path;
        private String role_name;
        private String department_name;

        public void setRole_id(String role_id) {
            this.role_id = role_id;
        }

        public String getRole_id() {
            return role_id;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setThumb_path(String thumb_path) {
            this.thumb_path = thumb_path;
        }

        public String getThumb_path() {
            return thumb_path;
        }

        public void setRole_name(String role_name) {
            this.role_name = role_name;
        }

        public String getRole_name() {
            return role_name;
        }

        public void setDepartment_name(String department_name) {
            this.department_name = department_name;
        }

        public String getDepartment_name() {
            return department_name;
        }

    }
}
