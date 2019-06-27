package com.lailelaodi.pojo;

import java.util.Date;

/**
 * @ClassName Student
 * @Description TODO
 * @Author Euraxluo
 * @Date 18-12-2 下午4:19
 */
public class Student {
    private int sid;
    private String sname;
    private String gender;
    private String phone;
    private String hobby;
    private String info;
    private Date birthday;
    public Student() {
        super();
        // TODO Auto-generated constructor stub
    }
    public Student(int sid, String sname, String gender, String phone, String hobby, String info, Date birthday) {
        super();
        this.sid =sid;
        this.sname = sname;
        this.gender = gender;
        this.phone = phone;
        this.hobby = hobby;
        this.info = info;
        this.birthday = birthday;
    }
    public Student( String sname, String gender, String phone, String hobby, String info, Date birthday) {
        super();
        this.sname = sname;
        this.gender = gender;
        this.phone = phone;
        this.hobby = hobby;
        this.info = info;
        this.birthday = birthday;
    }
    public int getSid() {
        return sid;
    }

    public String getSname() {
        return sname;
    }

    public String getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    public String getHobby() {
        return hobby;
    }

    public String getInfo() {
        return info;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
