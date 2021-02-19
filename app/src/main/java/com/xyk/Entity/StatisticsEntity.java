package com.xyk.Entity;

import java.util.List;

/**
 * Administrator  ：zhouyuru
 * 2020/11/2
 * Describe ：
 */
public class StatisticsEntity {
    private Datas data;
    private String info;
    private int status;

    public Datas getData() {
        return data;
    }

    public void setData(Datas data) {
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

    public class Datas {
        //        客户统计
        private Customer_count customer_count;
        //        联系人统计
        private Contacts_count contacts_count;
        //        商机统计
        private Business_count business_count;
        //        沟通日志
        private Log_count log_count;
        //        我的工作日志
        private Mylog_count mylog_count;
        //        收款总金额
        private String sum_price;
        //        每月收款金额
        private String sum_price_month;
        //        每周收款金额

        private String sum_price_week;
        //        每年收款金额

        private String sum_price_year;
        //        收款进度
        private double schedule;
        //        所有角色
        private List<All_role_array> all_role_array;

        private List<String> permission_list;

        public Customer_count getCustomer_count() {
            return customer_count;
        }

        public void setCustomer_count(Customer_count customer_count) {
            this.customer_count = customer_count;
        }

        public Contacts_count getContacts_count() {
            return contacts_count;
        }

        public void setContacts_count(Contacts_count contacts_count) {
            this.contacts_count = contacts_count;
        }

        public Business_count getBusiness_count() {
            return business_count;
        }

        public void setBusiness_count(Business_count business_count) {
            this.business_count = business_count;
        }

        public Log_count getLog_count() {
            return log_count;
        }

        public void setLog_count(Log_count log_count) {
            this.log_count = log_count;
        }

        public Mylog_count getMylog_count() {
            return mylog_count;
        }

        public void setMylog_count(Mylog_count mylog_count) {
            this.mylog_count = mylog_count;
        }

        public String getSum_price() {
            return sum_price;
        }

        public void setSum_price(String sum_price) {
            this.sum_price = sum_price;
        }

        public String getSum_price_month() {
            return sum_price_month;
        }

        public void setSum_price_month(String sum_price_month) {
            this.sum_price_month = sum_price_month;
        }

        public String getSum_price_week() {
            return sum_price_week;
        }

        public void setSum_price_week(String sum_price_week) {
            this.sum_price_week = sum_price_week;
        }

        public String getSum_price_year() {
            return sum_price_year;
        }

        public void setSum_price_year(String sum_price_year) {
            this.sum_price_year = sum_price_year;
        }

        public void setSchedule(double schedule) {
            this.schedule = schedule;
        }

        public double getSchedule() {
            return schedule;
        }

        public void setSchedule(int schedule) {
            this.schedule = schedule;
        }

        public List<All_role_array> getAll_role_array() {
            return all_role_array;
        }

        public void setAll_role_array(List<All_role_array> all_role_array) {
            this.all_role_array = all_role_array;
        }

        public List<String> getPermission_list() {
            return permission_list;
        }

        public void setPermission_list(List<String> permission_list) {
            this.permission_list = permission_list;
        }

        public class Business_count {
            private String week;
            private String month;
            private String year;
            private String day;

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }

            public String getMonth() {
                return month;
            }

            public void setMonth(String month) {
                this.month = month;
            }

            public String getYear() {
                return year;
            }

            public void setYear(String year) {
                this.year = year;
            }
        }

        public class Log_count {
            private String week;
            private String month;
            private String year;
            private String day;

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }

            public String getMonth() {
                return month;
            }

            public void setMonth(String month) {
                this.month = month;
            }

            public String getYear() {
                return year;
            }

            public void setYear(String year) {
                this.year = year;
            }
        }

        public class Mylog_count {
            private String week;
            private String month;
            private String year;
            private String day;

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }

            public String getMonth() {
                return month;
            }

            public void setMonth(String month) {
                this.month = month;
            }

            public String getYear() {
                return year;
            }

            public void setYear(String year) {
                this.year = year;
            }
        }

        public class All_role_array {
            private String sum_price;
            private String role;
            private Role_info role_info;

            private class Role_info {
                private String full_name;
                private String thumb_path;

                public String getFull_name() {
                    return full_name;
                }

                public void setFull_name(String full_name) {
                    this.full_name = full_name;
                }

                public String getThumb_path() {
                    return thumb_path;
                }

                public void setThumb_path(String thumb_path) {
                    this.thumb_path = thumb_path;
                }
            }
        }
    }

    public class Contacts_count {
        private String week;
        private String month;
        private String year;
        private String day;

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }
    }

    public class Customer_count {
        private String week;
        private String month;
        private String year;
        private String day;

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }
    }
}
