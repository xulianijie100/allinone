
package com.beeboxes.ot.andserver.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class UserInfo implements Serializable {

    @JSONField(name = "msg")
    public String msg;
    @JSONField(name = "result")
    public String result;
    @JSONField(name = "userId")
    public String mUserId;
    @JSONField(name = "userName")
    public String mUserName;
    @JSONField(name = "time")
    public String time;

    @Override
    public String toString() {
        return "UserInfo{" +
                "msg='" + msg + '\'' +
                ", result='" + result + '\'' +
                ", mUserId='" + mUserId + '\'' +
                ", mUserName='" + mUserName + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}