package com.xyk.Utils.UrlUtils;

/**
 * Des：
 *
 * @author zhouyuru
 * @date 2018/4/17
 */


public class Url {
    /**
     * 域名
     */
    public static String ip = "saas002.ymz666.com";
    //saas002.ymz666.com
    //url
    public static String DOMAIN_NAME = "http://" + ip + "/vue.php";
    //    获取全部的ip
    public static String DOMAIN_AppIp = DOMAIN_NAME + "?m=index&a=getAllIp";
    //    登陆
    public static String DOMAIN_Login = DOMAIN_NAME + "?m=user&a=login";
    //    获取账号个人中心数据
    public static String DOMAIN_User = DOMAIN_NAME + "?m=user&a=view";
    //    退出登陆
    public static String DOMAIN_Logout = DOMAIN_NAME + "?m=user&a=logout";
    //通信配值
    public static String DOMAIN_PhoneSys = DOMAIN_NAME + "?m=user&a=getUser";
    //    修改通信配置
    public static String DOMAIN_EditEcpInfo = DOMAIN_NAME + "?m=user&a=editEcpInfo";
    //    修改密码
    public static String DOMAIN_EditPassword = DOMAIN_NAME + "?m=user&a=editPassword";
    //客户数据
    public static String DOMAIN_Index = DOMAIN_NAME + "?m=customer&a=index";

    //话务统计
    public static String CALL_STATISTCAL ="?m=user&a=telcallTotalStatistics";
    /**
     * 录音列表
     */
    public static String AUDIO_Index = DOMAIN_NAME + "?m=call&a=getAppRecordList";
    //客户池数据
    public static String DOMAIN_Resource = DOMAIN_NAME + "?m=customer&a=index&content=resource";
    //客户详情
    public static String DOMAIN_Dynamic = DOMAIN_NAME + "?m=customer&a=dynamic";
    //    客户详情2
    public static String DOMAIN_Dynamic2 = DOMAIN_NAME + "?m=customer&a=view";
    //认领
    public static String DOMAIN_Receive = DOMAIN_NAME + "?m=customer&a=receive";
    //获取客户跟进日志
    public static String DOMAIN_Loglist = DOMAIN_NAME + "?m=index&a=loglist";
    //获取客户商机
    public static String DOMAIN_Business = DOMAIN_NAME + "?m=business&a=index";
    //获取合同
    public static String DOMAIN_Contract = DOMAIN_NAME + "?m=contract&a=index";
    //获取回款
    public static String DOMAIN_Finance = DOMAIN_NAME + "?m=finance&a=index";

    //跟进记录类型
    public static String DOMAIN_Logstatus = DOMAIN_NAME + "?m=log&a=logstatus";

    //添加跟进记录
    public static String DOMAIN_LogAdd = DOMAIN_NAME + "?m=log&a=add";

    //拨打电话 vpm11
    public static String DOMAIN_Call = DOMAIN_NAME + "?m=call&a=dialNumber";
    public static String DOMAIN_CallCMCC = DOMAIN_NAME + "?m=call&a=moveCall";
    public static String DOMAIN_AXBCALLDJ = DOMAIN_NAME + "?m=call&a=axbCallDj";
    public static String DOMAIN_CallAXB = DOMAIN_NAME + "?m=call&a=axbCall";
    public static String dialpanel_phone = "";


    //判断客户是否存在
    public static String DOMAIN_CallPermission = DOMAIN_NAME + "?m=customer&a=customerIsexist";

    //添加备注
    public static String DOMAIN_CallRemark = DOMAIN_NAME + "?m=customer&a=addCustomerRemark";
    //新增客户字段
    public static String DOMAIN_Fields = DOMAIN_NAME + "?m=index&a=fields";
    //统计数据
    public static String DOMAIN_TJ_Index = DOMAIN_NAME + "?m=index&a=index";
    //修改用户
    public static String DOMAIN_Edit = DOMAIN_NAME + "?m=customer&a=edit";
    //添加用户
    public static String DOMAIN_Add = DOMAIN_NAME + "?m=customer&a=add";
    //获取系统余额
    public static String DOMAIN_getSystemBalance = DOMAIN_NAME + "?m=user&a=getSystemBalance";
    //通话列表
    public static String DOMAIN_allTelRecordDetail = DOMAIN_NAME + "?m=call&a=allTelRecordDetail";
    //话务统计
    public static String DOMAIN_telcallTotalStatistics = DOMAIN_NAME + "?m=call&a=telcallTotalStatistics";
    //更新拨打统计
    public static String DOMAIN_updateCutomerField = DOMAIN_NAME + "?m=customer&a=updateCutomerField";

    public static void setIp(String ip) {
        Url.ip = ip;
        DOMAIN_NAME = "http://" + Url.ip + "/vue.php";
        //    获取全部的ip
        DOMAIN_AppIp = DOMAIN_NAME + "?m=index&a=getAllIp";
        //    登陆
        DOMAIN_Login = DOMAIN_NAME + "?m=user&a=login";
        //    获取账号个人中心数据
        DOMAIN_User = DOMAIN_NAME + "?m=user&a=view";
        //    退出登陆
        DOMAIN_Logout = DOMAIN_NAME + "?m=user&a=logout";
        //通信配值
        DOMAIN_PhoneSys = DOMAIN_NAME + "?m=user&a=getUser";
        //    修改通信配置
        DOMAIN_EditEcpInfo = DOMAIN_NAME + "?m=user&a=editEcpInfo";
        //    修改密码
        DOMAIN_EditPassword = DOMAIN_NAME + "?m=user&a=editPassword";
        //客户数据
        DOMAIN_Index = DOMAIN_NAME + "?m=customer&a=index";
        AUDIO_Index = DOMAIN_NAME + "?m=call&a=getAppRecordList";
        //客户池数据
        DOMAIN_Resource = DOMAIN_NAME + "?m=customer&a=index&content=resource";
        //客户详情
        DOMAIN_Dynamic = DOMAIN_NAME + "?m=customer&a=dynamic";
        //    客户详情2
        DOMAIN_Dynamic2 = DOMAIN_NAME + "?m=customer&a=view";
        //认领
        DOMAIN_Receive = DOMAIN_NAME + "?m=customer&a=receive";
        //获取客户跟进日志
        DOMAIN_Loglist = DOMAIN_NAME + "?m=index&a=loglist";
        //获取客户商机
        DOMAIN_Business = DOMAIN_NAME + "?m=business&a=index";
        //获取合同
        DOMAIN_Contract = DOMAIN_NAME + "?m=contract&a=index";
        //获取回款
        DOMAIN_Finance = DOMAIN_NAME + "?m=finance&a=index";
        //跟进记录类型
        DOMAIN_Logstatus = DOMAIN_NAME + "?m=log&a=logstatus";
        //添加跟进记录
        DOMAIN_LogAdd = DOMAIN_NAME + "?m=log&a=add";
        //拨打电话
        DOMAIN_Call = DOMAIN_NAME + "?m=call&a=dialNumber";
        DOMAIN_CallCMCC = DOMAIN_NAME + "?m=call&a=moveCall";
        DOMAIN_CallAXB = DOMAIN_NAME + "?m=call&a=axbCall";
        DOMAIN_AXBCALLDJ = DOMAIN_NAME + "?m=call&a=axbCallDj";

        DOMAIN_CallPermission= DOMAIN_NAME + "?m=customer&a=customerIsexist";
        //添加客户备注
        DOMAIN_CallRemark= DOMAIN_NAME + "?m=customer&a=addCustomerRemark";
        //新增客户字段
        DOMAIN_Fields = DOMAIN_NAME + "?m=index&a=fields";
        //统计数据
        DOMAIN_TJ_Index = DOMAIN_NAME + "?m=index&a=index";
        //修改客户
        DOMAIN_Edit = DOMAIN_NAME + "?m=customer&a=edit";
        //添加客户
        DOMAIN_Add = DOMAIN_NAME + "?m=customer&a=add";
        //获取系统话费余额
        DOMAIN_getSystemBalance = DOMAIN_NAME + "?m=user&a=getSystemBalance";
        //通话记录
        DOMAIN_allTelRecordDetail = DOMAIN_NAME + "?m=call&a=allTelRecordDetail";
        //话务统计
        DOMAIN_telcallTotalStatistics = DOMAIN_NAME + "?m=call&a=telcallTotalStatistics";
//        更新拨打次数
        DOMAIN_updateCutomerField = DOMAIN_NAME + "?m=customer&a=updateCutomerField";
    }
}
