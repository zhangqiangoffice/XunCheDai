package com.minsheng.app.xunchedai.mine.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/17.
 * 人员实体类
 */
public class Person implements Serializable {
    private String name;//人员姓名
    private String position;//人员职位
    private String phone;//人员手机号
    private String day_achievement;//人员当日业绩
    private String month_achievement;//人员当月业绩
    private String total_achievement;//人员总业绩

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDay_achievement() {
        return day_achievement;
    }

    public void setDay_achievement(String day_achievement) {
        this.day_achievement = day_achievement;
    }

    public String getMonth_achievement() {
        return month_achievement;
    }

    public void setMonth_achievement(String month_achievement) {
        this.month_achievement = month_achievement;
    }

    public String getTotal_achievement() {
        return total_achievement;
    }

    public void setTotal_achievement(String total_achievement) {
        this.total_achievement = total_achievement;
    }
}
