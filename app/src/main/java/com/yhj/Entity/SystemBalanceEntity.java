package com.yhj.Entity;

/**
 * Administrator  ：zhouyuru
 * 2020/11/25
 * Describe ：
 */
public class SystemBalanceEntity {
    private Datas data;
    private String info;
    private int status;

    public void setData(Datas data) {
        this.data = data;
    }

    public Datas getData() {
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

    public class Datas {
        private String remain_minutes;

        public String getRemain_minutes() {
            return remain_minutes;
        }

        public void setRemain_minutes(String remain_minutes) {
            this.remain_minutes = remain_minutes;
        }
    }
}
