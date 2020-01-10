
package com.beeboxes.ot.andserver.controller;

import android.util.Log;

import com.beeboxes.ot.andserver.model.UserInfo;
import com.yanzhenjie.andserver.annotation.GetMapping;
import com.yanzhenjie.andserver.annotation.PathVariable;
import com.yanzhenjie.andserver.annotation.PostMapping;
import com.yanzhenjie.andserver.annotation.RequestBody;
import com.yanzhenjie.andserver.annotation.RequestMapping;
import com.yanzhenjie.andserver.annotation.RestController;
import com.yanzhenjie.andserver.util.MediaType;

@RestController
@RequestMapping(path = "/hy")
public class UserController {

    @GetMapping(path = "/get/{userId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String info(@PathVariable(name = "userId") String userId) {
        Log.e("userId---",userId);
        return userId;
    }


    @PostMapping(path = "/postData")
    UserInfo jsonBody(@RequestBody UserInfo userInfo) {
        return userInfo;
    }
}