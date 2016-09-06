package com.minsheng.app.xunchedai.utils;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/8/8.
 * share文件管理类
 */
public class PreferenceUtils {
    public static String STAFFID="staffid";//用户ID
    public static String USER_NAME="user_name";//用户名
    public static String PASSWORD="password";//登录密码
    public static String POSITION="position";
    public static String IS_LOGIN="is_login";//是否登录
    public static String IS_SIGNED="signed";//是否签名
    public static String BANK_NAME="bankname";//银行卡名称
    public static String BANK_NUM="banknum";//银行卡号
    public static String LOGIN_DATE="login_date";//登录日期


    //保存String信息
    public static void saveStr(Context context,String StrName,String string){
        SharedPreferences sp = context.getSharedPreferences("xunchedai", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(StrName, string);
        editor.commit();
    }

    //保存用户登录信息
    public static void saveLoginUser(Context context,String StrName,String string){
        SharedPreferences sp = context.getSharedPreferences("loginUser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(StrName, string);
        editor.commit();
    }

    //保存用户登录信息
    public static void saveLoginUserBool(Context context,String booleanName,boolean flag){
        SharedPreferences sp = context.getSharedPreferences("loginUser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(booleanName, flag);
        editor.commit();
    }

    //保存Boolean信息
    public static void saveBool(Context context,String booleanName,boolean flag){
        SharedPreferences sp = context.getSharedPreferences("xunchedai", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(booleanName, flag);
        editor.commit();
    }

    //获取String信息
    public static String loadStr(Context context,String StrName) {
        SharedPreferences sp = context.getSharedPreferences("xunchedai", Context.MODE_APPEND);
        String str=sp.getString(StrName, "");
        return str;
    }

    //获取Boolean信息
    public static boolean loadBool(Context context,String BoolanName) {
        SharedPreferences sp = context.getSharedPreferences("xunchedai", Context.MODE_APPEND);
        boolean flag=sp.getBoolean(BoolanName, false);
        return flag;
    }

    //获取String信息
    public static String loadUser(Context context,String StrName) {
        SharedPreferences sp = context.getSharedPreferences("loginUser", Context.MODE_APPEND);
        String str=sp.getString(StrName, "");
        return str;
    }

    //获取Boolean信息
    public static boolean loadUserBool(Context context,String BoolanName) {
        SharedPreferences sp = context.getSharedPreferences("loginUser", Context.MODE_APPEND);
        boolean flag=sp.getBoolean(BoolanName, false);
        return flag;
    }

    //清除用户缓存在本地的信息
    public static void clearLoginUser(Context context)
    {
        SharedPreferences sp = context.getSharedPreferences("loginUser", Context.MODE_APPEND);
        sp.edit().clear().commit();
    }

    //清除出来登录信息的其他缓存信息
    public static void clearXunchedai(Context context)
    {
        SharedPreferences sp = context.getSharedPreferences("xunchedai", Context.MODE_APPEND);
        sp.edit().clear();
    }
}
