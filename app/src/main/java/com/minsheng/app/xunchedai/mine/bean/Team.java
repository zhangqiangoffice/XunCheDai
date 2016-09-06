package com.minsheng.app.xunchedai.mine.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/8/17.
 * 团队实体类
 */
public class Team implements Serializable{
    private String user_name;//登录姓名
    private String user_position;//登录职位
    private String user_duxunqu;//督训区
    private String user_day_achievement;//登录人的当日业绩
    private String user_month_achievement;//登录人的当月业绩
    private String user_total_achievement;//登录人的总业绩
    private String team_number;//团队总人数
    private String team_day_achievement;//团队当日业绩
    private String team_month_achievement;//团队当月总业绩
    private String team_total_achievement;//团队总业绩
    private List<Person>list;//人员集合

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_position() {
        return user_position;
    }

    public void setUser_position(String user_position) {
        this.user_position = user_position;
    }

    public String getUser_duxunqu() {
        return user_duxunqu;
    }

    public void setUser_duxunqu(String user_duxunqu) {
        this.user_duxunqu = user_duxunqu;
    }

    public String getUser_day_achievement() {
        return user_day_achievement;
    }

    public void setUser_day_achievement(String user_day_achievement) {
        this.user_day_achievement = user_day_achievement;
    }

    public String getUser_month_achievement() {
        return user_month_achievement;
    }

    public void setUser_month_achievement(String user_month_achievement) {
        this.user_month_achievement = user_month_achievement;
    }

    public String getUser_total_achievement() {
        return user_total_achievement;
    }

    public void setUser_total_achievement(String user_total_achievement) {
        this.user_total_achievement = user_total_achievement;
    }

    public String getTeam_number() {
        return team_number;
    }

    public void setTeam_number(String team_number) {
        this.team_number = team_number;
    }

    public String getTeam_day_achievement() {
        return team_day_achievement;
    }

    public void setTeam_day_achievement(String team_day_achievement) {
        this.team_day_achievement = team_day_achievement;
    }

    public String getTeam_month_achievement() {
        return team_month_achievement;
    }

    public void setTeam_month_achievement(String team_month_achievement) {
        this.team_month_achievement = team_month_achievement;
    }

    public String getTeam_total_achievement() {
        return team_total_achievement;
    }

    public void setTeam_total_achievement(String team_total_achievement) {
        this.team_total_achievement = team_total_achievement;
    }

    public List<Person> getList() {
        return list;
    }

    public void setList(List<Person> list) {
        this.list = list;
    }
}
