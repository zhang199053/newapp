package com.yhj.Entity;

import java.util.List;

/**
 * Administrator  ：zhouyuru
 * 2020/10/26
 * Describe ：
 */
public class IpBean {
    private List<IP> data;
    private String info;
    private int status;

    public void setData(List<IP> data) {
        this.data = data;
    }

    public List<IP> getData() {
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

    public class IP {
        private int id;
        private String ip;
        private String domain_name;
        private String fronted_domain_name;
        private int status;
        private int type;
        private int is_edit;
        private int is_open;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getDomain_name() {
            return domain_name;
        }

        public void setDomain_name(String domain_name) {
            this.domain_name = domain_name;
        }

        public String getFronted_domain_name() {
            return fronted_domain_name;
        }

        public void setFronted_domain_name(String fronted_domain_name) {
            this.fronted_domain_name = fronted_domain_name;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getIs_edit() {
            return is_edit;
        }

        public void setIs_edit(int is_edit) {
            this.is_edit = is_edit;
        }

        public int getIs_open() {
            return is_open;
        }

        public void setIs_open(int is_open) {
            this.is_open = is_open;
        }
    }
}
