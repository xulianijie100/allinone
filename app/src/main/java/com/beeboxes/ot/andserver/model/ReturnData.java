
package com.beeboxes.ot.andserver.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class ReturnData implements Serializable {

    @JSONField(name = "isSuccess")
    public boolean isSuccess;

    @JSONField(name = "errorCode")
    public int errorCode;

    @JSONField(name = "errorMsg")
    public String errorMsg;

    @JSONField(name = "data")
    public Object data;
}