package com.lailelaodi.pojo;

import java.util.Date;

/**
 * @ClassName phone
 * @Description TODO
 * @Author Euraxluo
 * @Date 18-12-27 下午3:32
 */
public class Phone {
    private int id;
    private String phoneName;
    private String theme;
    private String url;
    private int nums;
    private int isShow;
    private Date createTime;
    private int isdelte;

    public Phone() {
    }

    public Phone(int id, String phoneName, String theme, String url, int nums, int isShow, Date createTime, int isdelte) {
        this.id = id;
        this.phoneName = phoneName;
        this.theme = theme;
        this.url = url;
        this.nums = nums;
        this.isShow = isShow;
        this.createTime = createTime;
        this.isdelte = isdelte;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }

    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setIsdelte(int isdelte) {
        this.isdelte = isdelte;
    }

    public int getId() {
        return id;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public String getTheme() {
        return theme;
    }

    public String getUrl() {
        return url;
    }

    public int getNums() {
        return nums;
    }

    public int getIsShow() {
        return isShow;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public int getIsdelte() {
        return isdelte;
    }
}
