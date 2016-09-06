package com.minsheng.app.xunchedai.message.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/15.
 * 消息实体类
 */
public class Message implements Serializable {
    private String id;
    private int type;
    private String theme;
    private String content;
    private String time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
