package com.lailelaodi.pojo;

import java.util.Date;

/**
 * @ClassName MsgcodeBean
 * @Description TODO
 * @Author Euraxluo
 * @Date 18-12-26 下午2:59
 */
public class Mobielcode {
    private int id;
    private String Phone;
    private String code;
    private Date createtime;

    public Mobielcode() {
        super();
    }

    public Mobielcode(int id, String phone, String code, Date createtime) {
        this.id = id;
        Phone = phone;
        this.code = code;
        this.createtime = createtime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public int getId() {
        return id;
    }

    public String getPhone() {
        return Phone;
    }

    public String getCode() {
        return code;
    }

    public Date getCreatetime() {
        return createtime;
    }
}
