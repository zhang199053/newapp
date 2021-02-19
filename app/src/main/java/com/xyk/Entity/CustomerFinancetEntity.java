package com.xyk.Entity;

import java.util.List;

/**
 * Administrator  ：zhouyuru
 * 2020/10/30
 * Describe ：
 */
public class CustomerFinancetEntity {

    private Permission_list permission_list;
    private int receivingorder_money;
    private int un_receivables_money;
    private List<FinancetList> list;
    private int page;
    private String info;
    private int status;

    public Permission_list getPermission_list() {
        return permission_list;
    }

    public void setPermission_list(Permission_list permission_list) {
        this.permission_list = permission_list;
    }

    public int getReceivingorder_money() {
        return receivingorder_money;
    }

    public void setReceivingorder_money(int receivingorder_money) {
        this.receivingorder_money = receivingorder_money;
    }

    public int getUn_receivables_money() {
        return un_receivables_money;
    }

    public void setUn_receivables_money(int un_receivables_money) {
        this.un_receivables_money = un_receivables_money;
    }

    public List<FinancetList> getList() {
        return list;
    }

    public void setList(List<FinancetList> list) {
        this.list = list;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
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

    public class FinancetList {


        private String receivingorder_id;
        private String name;
        private String pay_time;
        private String money;
        private String receivables_id;
        private String status;
        private String owner_role_id;
        private String receipt_account;
        private String receipt_bank;
        private String company;
        private String description;
        private String status_name;
        private String owner_name;
        //        合同编号
        private String contract_number;

        public String getContract_number() {
            return contract_number;
        }

        public void setContract_number(String contract_number) {
            this.contract_number = contract_number;
        }

        public String getReceivingorder_id() {
            return receivingorder_id;
        }

        public void setReceivingorder_id(String receivingorder_id) {
            this.receivingorder_id = receivingorder_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getReceivables_id() {
            return receivables_id;
        }

        public void setReceivables_id(String receivables_id) {
            this.receivables_id = receivables_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getOwner_role_id() {
            return owner_role_id;
        }

        public void setOwner_role_id(String owner_role_id) {
            this.owner_role_id = owner_role_id;
        }

        public String getReceipt_account() {
            return receipt_account;
        }

        public void setReceipt_account(String receipt_account) {
            this.receipt_account = receipt_account;
        }

        public String getReceipt_bank() {
            return receipt_bank;
        }

        public void setReceipt_bank(String receipt_bank) {
            this.receipt_bank = receipt_bank;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getStatus_name() {
            return status_name;
        }

        public void setStatus_name(String status_name) {
            this.status_name = status_name;
        }

        public String getOwner_name() {
            return owner_name;
        }

        public void setOwner_name(String owner_name) {
            this.owner_name = owner_name;
        }
    }
}
