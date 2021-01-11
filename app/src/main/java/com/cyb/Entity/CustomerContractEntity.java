package com.cyb.Entity;

import java.util.List;

/**
 * Administrator  ：zhouyuru
 * 2020/10/30
 * Describe ：
 */
public class CustomerContractEntity {
    private Permission_list permission_list;
    private String contract_custo;
    private List<CustomerBean.Fields_list> fields_list;
    private List<Contract_Scene_list> scene_list;
    private List<ContractList> list;
    private int page;
    private String info;
    private int status;

    public void setPermission_list(Permission_list permission_list) {
        this.permission_list = permission_list;
    }

    public Permission_list getPermission_list() {
        return permission_list;
    }

    public List<CustomerBean.Fields_list> getFields_list() {
        return fields_list;
    }

    public void setFields_list(List<CustomerBean.Fields_list> fields_list) {
        this.fields_list = fields_list;
    }

    public String getContract_custo() {
        return contract_custo;
    }

    public void setContract_custo(String contract_custo) {
        this.contract_custo = contract_custo;
    }

    public List<Contract_Scene_list> getScene_list() {
        return scene_list;
    }

    public void setScene_list(List<Contract_Scene_list> scene_list) {
        this.scene_list = scene_list;
    }

    public List<ContractList> getList() {
        return list;
    }

    public void setList(List<ContractList> list) {
        this.list = list;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
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

    private class Contract_Scene_list {
        private String id;
        private String name;
        private String data;
        private String type;
        private String by;
        private String cut_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getBy() {
            return by;
        }

        public void setBy(String by) {
            this.by = by;
        }

        public String getCut_name() {
            return cut_name;
        }

        public void setCut_name(String cut_name) {
            this.cut_name = cut_name;
        }
    }

    public class ContractList {
        private String number;
        private String price;
        private String customer_id;
        private String contract_id;
        private String type;
        //        合同名称
        private String contract_name;
        private String owner_role_id;
        private String create_time;
        private String is_checked;
        private String customer_name;
        private String days;
        private int schedule;

        public String getContract_name() {
            return contract_name;
        }

        public void setContract_name(String contract_name) {
            this.contract_name = contract_name;
        }

        private CustomerBean.Custome_list.Permission permission;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(String customer_id) {
            this.customer_id = customer_id;
        }

        public String getContract_id() {
            return contract_id;
        }

        public void setContract_id(String contract_id) {
            this.contract_id = contract_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getOwner_role_id() {
            return owner_role_id;
        }

        public void setOwner_role_id(String owner_role_id) {
            this.owner_role_id = owner_role_id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getIs_checked() {
            return is_checked;
        }

        public void setIs_checked(String is_checked) {
            this.is_checked = is_checked;
        }

        public String getCustomer_name() {
            return customer_name;
        }

        public void setCustomer_name(String customer_name) {
            this.customer_name = customer_name;
        }

        public String getDays() {
            return days;
        }

        public void setDays(String days) {
            this.days = days;
        }

        public int getSchedule() {
            return schedule;
        }

        public void setSchedule(int schedule) {
            this.schedule = schedule;
        }

        public CustomerBean.Custome_list.Permission getPermission() {
            return permission;
        }

        public void setPermission(CustomerBean.Custome_list.Permission permission) {
            this.permission = permission;
        }
    }
}
