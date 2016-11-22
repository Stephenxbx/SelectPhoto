package demo.rc.com.selectphoto.utils;
/**
 * Copyright (c) 2013 An Yaming,  All Rights Reserved
 */

import android.os.Environment;

import java.io.File;

/**
 * 项目中心配置信息
 *
 * @author 亚明
 */
public class Confing {
    /**
     * 项目保存用户信息的sp的用户信息
     */
    public final static String SP_SaveUserInfo = "sp_userinfo";
    /**
     * 项目保存用户信息的sp的名字
     */
    public final static String SP_SaveUserInfo_Name = "name";
    /**
     * 项目保存用户信息的sp的名字
     */
    public final static String SP_SaveUserInfo_RealName = "realname";
    /**
     * 项目保存用户信息的sp的名字
     */
    public final static String SP_SaveUserInfo_UserType = "userType";
    /**
     * 项目保存用户信息的sp的名字
     */
    public final static String SP_SaveUserInfo_TheUserId = "theuserid";
    /**
     * 项目保存用户信息的sp的名字
     */
    public final static String SP_SaveUserInfo_Phone = "phone";
    /**
     * 项目保存用户信息的sp的名字
     */
    public final static String SP_SaveWehere_Location = "loaction";
    /**
     * 项目保存用户信息的sp的名字
     */
    public final static String SP_SaveWehere_New_Order = "neworder";
    /**
     * 项目保存用户信息的sp的名字
     */
    public final static String SP_SaveUserName = "username";
    /**
     * 项目保存用户信息的sp的名字
     */
    public final static String SP_SaveUserPwd = "userpassword";
    /**
     * 项目保存用户信息的sp的名字
     */
    public final static String SP_SaveToken = "token";
    /**
     * 项目保存用户信息的sp的名字
     */
    public final static String SP_WorkType = "work_type";
    /**
     * 项目保存用户信息的sp的名字
     */
    public final static String SP_Mobile = "mobile";
    /**
     * 项目保存用户信息的sp的名字
     */
    public final static String SP_Address = "address";
    /**
     * 项目保存用户信息的sp的名字
     */
    public final static String SP_AddressId = "addressid";
    /**
     * 项目保存用户信息的sp的名字
     */
   public final static String SP_AddressName = "addressname";
    /**
     * 项目保存用户信息的sp的名字
     */
    public final static String SP_Uid = "uid";
    /**
     * 项目保存用户信息的sp的名字
     */
    public final static String SP_Balance = "balance";
    /**
     * 项目保存用户信息的sp的名字
     */
    public final static String SP_ApkUrl = "apkurl";
    /**
     * 项目保存用户信息的sp的名字
     */
    public final static String SP_IsFirst = "isfirst";
    /**
     * 在sdcard建立项目应用的路径
     */
    public final static String productPath = Environment
            .getExternalStorageDirectory().getPath()
            + File.separator
            + "NotClosing" + File.separator;
    /**
     * 在sdcard图片的物理缓存路径
     */
    public final static String imgCache = productPath + "imgCache"
            + File.separator;

    /**
     * 使用支付宝是需要的回调地址
     */
    public static String PayNotifyUrl;
    /**
     * 使用支付宝是需要
     */
    public static String PayPartnerID;
    /**
     * 使用支付宝是需要
     */
    public static String PaySellerNo;
    /**
     * 使用支付宝是需要
     */
    public static String PayPrivateKey;
    /**
     * 支付宝（RSA）公钥 用签约支付宝账号
     */
    public static String RSA_ALIPAY_PUBLIC = "";
    /**
     * 验证码的可用时间
     */
    public static int ValidationCodeEffectionTime;
    /**
     * 使用分页加载时每页页数
     */
    public static int PageSize;
}
