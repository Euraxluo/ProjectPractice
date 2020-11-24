package com.wss.netty.pojo;

import javax.persistence.*;

@Table(name = "my_friends")
public class MyFriends {
    @Id
    private String id;

    @Column(name = "my_user_id")
    private String myUserId;

    @Column(name = "my_frend_user_id")
    private String myFrendUserId;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return my_user_id
     */
    public String getMyUserId() {
        return myUserId;
    }

    /**
     * @param myUserId
     */
    public void setMyUserId(String myUserId) {
        this.myUserId = myUserId;
    }

    /**
     * @return my_frend_user_id
     */
    public String getMyFrendUserId() {
        return myFrendUserId;
    }

    /**
     * @param myFrendUserId
     */
    public void setMyFrendUserId(String myFrendUserId) {
        this.myFrendUserId = myFrendUserId;
    }
}