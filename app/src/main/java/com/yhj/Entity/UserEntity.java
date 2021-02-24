package com.yhj.Entity;


import java.io.Serializable;
import java.util.Date;

/**
 * Administrator  ：zhouyuru
 * 2020/10/27
 * Describe ：
 */
public class UserEntity implements Serializable {
    private int is_edit;
    private UsersData data;
    private int status;
    private String info;

    public int getIs_edit() {
        return is_edit;
    }

    public void setIs_edit(int is_edit) {
        this.is_edit = is_edit;
    }

    public UsersData getData() {
        return data;
    }

    public void setData(UsersData data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public class UsersData  implements  Serializable{
        private String user_id;
        private String role_id;
        private String position_id;
        private String extid;
        private String login_name;
        private String user_name;
        private String status;
        private String weixinid;
        private String category_id;
        private String sex;
        private String address;
        private String email;
        private String img;
        private String thumb_path;
        private String telephone;
        private String hometown;
        private String customer_num;
        private Date birthday;
        private Date entry;
        private String introduce;
        private String office_tel;
        private String qq;
        private String dashboard;
        private String full_name;
        private String prefixion;
        private String number;
        private String type;
        private String ecpinfo;
        private String dial_type;
        private String role_name;
        private String parent_id;
        private String department_id;
        private String description;
        private String department_name;

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setRole_id(String role_id) {
            this.role_id = role_id;
        }

        public String getRole_id() {
            return role_id;
        }

        public void setPosition_id(String position_id) {
            this.position_id = position_id;
        }

        public String getPosition_id() {
            return position_id;
        }

        public void setExtid(String extid) {
            this.extid = extid;
        }

        public String getExtid() {
            return extid;
        }

        public void setLogin_name(String login_name) {
            this.login_name = login_name;
        }

        public String getLogin_name() {
            return login_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }

        public void setWeixinid(String weixinid) {
            this.weixinid = weixinid;
        }

        public String getWeixinid() {
            return weixinid;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getSex() {
            return sex;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAddress() {
            return address;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getEmail() {
            return email;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getImg() {
            return img;
        }

        public void setThumb_path(String thumb_path) {
            this.thumb_path = thumb_path;
        }

        public String getThumb_path() {
            return thumb_path;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setHometown(String hometown) {
            this.hometown = hometown;
        }

        public String getHometown() {
            return hometown;
        }

        public void setCustomer_num(String customer_num) {
            this.customer_num = customer_num;
        }

        public String getCustomer_num() {
            return customer_num;
        }

        public void setBirthday(Date birthday) {
            this.birthday = birthday;
        }

        public Date getBirthday() {
            return birthday;
        }

        public void setEntry(Date entry) {
            this.entry = entry;
        }

        public Date getEntry() {
            return entry;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setOffice_tel(String office_tel) {
            this.office_tel = office_tel;
        }

        public String getOffice_tel() {
            return office_tel;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getQq() {
            return qq;
        }

        public void setDashboard(String dashboard) {
            this.dashboard = dashboard;
        }

        public String getDashboard() {
            return dashboard;
        }

        public void setFull_name(String full_name) {
            this.full_name = full_name;
        }

        public String getFull_name() {
            return full_name;
        }

        public void setPrefixion(String prefixion) {
            this.prefixion = prefixion;
        }

        public String getPrefixion() {
            return prefixion;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getNumber() {
            return number;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public void setEcpinfo(String ecpinfo) {
            this.ecpinfo = ecpinfo;
        }

        public String getEcpinfo() {
            return ecpinfo;
        }

        public void setDial_type(String dial_type) {
            this.dial_type = dial_type;
        }

        public String getDial_type() {
            return dial_type;
        }

        public void setRole_name(String role_name) {
            this.role_name = role_name;
        }

        public String getRole_name() {
            return role_name;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setDepartment_id(String department_id) {
            this.department_id = department_id;
        }

        public String getDepartment_id() {
            return department_id;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public void setDepartment_name(String department_name) {
            this.department_name = department_name;
        }

        public String getDepartment_name() {
            return department_name;
        }
    }
}
