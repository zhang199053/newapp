package com.yh.Entity;

/**
 * Administrator  ：zhouyuru
 * 2020/10/27
 * Describe ：
 */
public class LoginEntity {

    //"img": "./Uploads/head/thumb_5caf434b67fc59813.png",头像 <string>
//"session_id": "505p80118nrm84rv9orvb5acp0",... <string>
//"token": "1500714767a651bb74f5f3ff90e8dc19",登录唯一标识token 值 <string>
//"admin": 1,... <number>
//"role_id": "1",角色id <string>
//"name": "李浩",姓名 <string>
//"sex": "1",用户性别1男2女 <string>
//"telephone": "16656235523",联系电话 <string>
//"department_id": "1",... <string>
//"department_name": "董事会",部门 <string>
//"role_name": "总管理员",岗位 <string>
//"system_name": "aaaa",系统名 <string>
//"info": "success",... <string>
//"status": 1... <number>
    private String img;
    private String session_id;
    private String token;
    private int admin;
    private String role_id;
    private String name;
    private String sex;
    private String telephone;
    private String department_id;
    private String department_name;
    private String role_name;
    private String system_name;
    private String info;
    private int status;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(String department_id) {
        this.department_id = department_id;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getSystem_name() {
        return system_name;
    }

    public void setSystem_name(String system_name) {
        this.system_name = system_name;
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
}
