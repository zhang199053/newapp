package com.ymz.Entity;

import java.util.List;

/**
 * Administrator  ：zhouyuru
 * 2020/11/27
 * Describe ：
 */
public class ConversationEntity {
    private Data data;
    private String info;
    private int status;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
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

    public class Data {
        private List<Datas> datas;
        private int total;

        public List<Datas> getDatas() {
            return datas;
        }

        public void setDatas(List<Datas> datas) {
            this.datas = datas;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }

    public class Datas {
        private String destination_number;
        private String is_connected;
        private String file_path;
        private String duration;
        private String realname;
        private String created_time;
        private String crm_type;
        private String user_id;
        private String name;

        public String getDestination_number() {
            return destination_number;
        }

        public void setDestination_number(String destination_number) {
            this.destination_number = destination_number;
        }

        public String getIs_connected() {
            return is_connected;
        }

        public void setIs_connected(String is_connected) {
            this.is_connected = is_connected;
        }

        public String getFile_path() {
            return file_path;
        }

        public void setFile_path(String file_path) {
            this.file_path = file_path;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getCreated_time() {
            return created_time;
        }

        public void setCreated_time(String created_time) {
            this.created_time = created_time;
        }

        public String getCrm_type() {
            return crm_type;
        }

        public void setCrm_type(String crm_type) {
            this.crm_type = crm_type;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
