package com.xyk.Entity;

import java.util.List;

public class Statisticalbean {


    /**
     * data : {"data":[{"tel_total":"1","connected_total":"0","duration":"0秒","total_minutes":"0分","full_name":"管理员","call_date":"2021-01-12","user_id":"1","id":1,"duration_per":"0秒","connected_data":"0(0%)","connected_per":"0%","total_minutes_per":"0分"}],"total":1}
     * info : Success
     * status : 1
     */

    private DataBeanX data;
    private String info;
    private int status;

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
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

    public static class DataBeanX {
        /**
         * data : [{"tel_total":"1","connected_total":"0","duration":"0秒","total_minutes":"0分","full_name":"管理员","call_date":"2021-01-12","user_id":"1","id":1,"duration_per":"0秒","connected_data":"0(0%)","connected_per":"0%","total_minutes_per":"0分"}]
         * total : 1
         */

        private int total;
        private List<DataBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * tel_total : 1
             * connected_total : 0
             * duration : 0秒
             * total_minutes : 0分
             * full_name : 管理员
             * call_date : 2021-01-12
             * user_id : 1
             * id : 1
             * duration_per : 0秒
             * connected_data : 0(0%)
             * connected_per : 0%
             * total_minutes_per : 0分
             */

            private String tel_total;
            private String connected_total;
            private String duration;
            private String total_minutes;
            private String full_name;
            private String call_date;
            private String user_id;
            private int id;
            private String duration_per;
            private String connected_data;
            private String connected_per;
            private String total_minutes_per;

            public String getTel_total() {
                return tel_total;
            }

            public void setTel_total(String tel_total) {
                this.tel_total = tel_total;
            }

            public String getConnected_total() {
                return connected_total;
            }

            public void setConnected_total(String connected_total) {
                this.connected_total = connected_total;
            }

            public String getDuration() {
                return duration;
            }

            public void setDuration(String duration) {
                this.duration = duration;
            }

            public String getTotal_minutes() {
                return total_minutes;
            }

            public void setTotal_minutes(String total_minutes) {
                this.total_minutes = total_minutes;
            }

            public String getFull_name() {
                return full_name;
            }

            public void setFull_name(String full_name) {
                this.full_name = full_name;
            }

            public String getCall_date() {
                return call_date;
            }

            public void setCall_date(String call_date) {
                this.call_date = call_date;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getDuration_per() {
                return duration_per;
            }

            public void setDuration_per(String duration_per) {
                this.duration_per = duration_per;
            }

            public String getConnected_data() {
                return connected_data;
            }

            public void setConnected_data(String connected_data) {
                this.connected_data = connected_data;
            }

            public String getConnected_per() {
                return connected_per;
            }

            public void setConnected_per(String connected_per) {
                this.connected_per = connected_per;
            }

            public String getTotal_minutes_per() {
                return total_minutes_per;
            }

            public void setTotal_minutes_per(String total_minutes_per) {
                this.total_minutes_per = total_minutes_per;
            }
        }
    }
}
