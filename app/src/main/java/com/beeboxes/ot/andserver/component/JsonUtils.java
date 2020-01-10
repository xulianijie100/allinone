
package com.beeboxes.ot.andserver.component;

import android.database.MatrixCursor;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.beeboxes.ot.andserver.model.ReturnData;
import com.beeboxes.ot.andserver.model.UserInfo;

import java.lang.reflect.Type;

public class JsonUtils {

    /**
     * Business is successful.
     *
     * @param data return data.
     * @return json.
     */
    public static String successfulJson(Object data) {
        ReturnData returnData = new ReturnData();
        returnData.isSuccess = true;
        returnData.errorCode = 200;
        returnData.data = data;
        UserInfo info = (UserInfo) data;
        Log.e("result==", info.toString());

        String msg = info.msg + " " + info.mUserName + " " + info.mUserId + " " + info.time;
        String[] columns = new String[]{"result"};
        MatrixCursor cursor = new MatrixCursor(columns);
        cursor.addRow(new Object[]{"{" +
                "'result': " + info.result + "," +
                "'message':'" + msg + "'" +
                "}"});

        return JSON.toJSONString(returnData);
    }

    /**
     * Business is failed.
     *
     * @param code    error code.
     * @param message message.
     * @return json.
     */
    public static String failedJson(int code, String message) {
        ReturnData returnData = new ReturnData();
        returnData.isSuccess = false;
        returnData.errorCode = code;
        returnData.errorMsg = message;
        return JSON.toJSONString(returnData);
    }

    /**
     * Converter object to json string.
     *
     * @param data the object.
     * @return json string.
     */
    public static String toJsonString(Object data) {
        return JSON.toJSONString(data);
    }

    /**
     * Parse json to object.
     *
     * @param json json string.
     * @param type the type of object.
     * @param <T>  type.
     * @return object.
     */
    public static <T> T parseJson(String json, Type type) {
        return JSON.parseObject(json, type);
    }
}