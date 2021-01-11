package com.ymz.Entity;

/**
 * Administrator  ：zhouyuru
 * 2020/10/27
 * Describe ：
 */
public class PhoneSysEntity {


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
        private Ecpinfo ecpinfo;
        private String full_name;
        private String name;

        public void setEcpinfo(Ecpinfo ecpinfo) {
            this.ecpinfo = ecpinfo;
        }

        public Ecpinfo getEcpinfo() {
            return ecpinfo;
        }

        public void setFull_name(String full_name) {
            this.full_name = full_name;
        }

        public String getFull_name() {
            return full_name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

    }

    public class Ecpinfo {
        private String ecpid;
        private String accountSid;
        private String appid;
        private String token;

        public void setEcpid(String ecpid) {
            this.ecpid = ecpid;
        }

        public String getEcpid() {
            return ecpid;
        }

        public void setAccountSid(String accountSid) {
            this.accountSid = accountSid;
        }

        public String getAccountSid() {
            return accountSid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getAppid() {
            return appid;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }
    }

}
