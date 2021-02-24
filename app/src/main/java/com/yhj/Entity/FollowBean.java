package com.yhj.Entity;

import java.util.List;

/**
 * Administrator  ：zhouyuru
 * 2020/10/30
 * Describe ：
 */
public class FollowBean {

    public List<States> list;
    private int status;
    private String  info;

    public List<States> getList() {
        return list;
    }

    public void setList(List<States> list) {
        this.list = list;
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

    public class States {
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
