
package com.beeboxes.ot.andserver.component;

import com.yanzhenjie.andserver.annotation.Resolver;
import com.yanzhenjie.andserver.error.BasicException;
import com.yanzhenjie.andserver.framework.ExceptionResolver;
import com.yanzhenjie.andserver.framework.body.JsonBody;
import com.yanzhenjie.andserver.http.HttpRequest;
import com.yanzhenjie.andserver.http.HttpResponse;
import com.yanzhenjie.andserver.util.StatusCode;

@Resolver
public class AppExceptionResolver implements ExceptionResolver {

    @Override
    public void onResolve(HttpRequest request, HttpResponse response, Throwable e) {
        e.printStackTrace();
        if (e instanceof BasicException) {
            BasicException exception = (BasicException)e;
            response.setStatus(exception.getStatusCode());
        } else {
            response.setStatus(StatusCode.SC_INTERNAL_SERVER_ERROR);
        }
        String body = JsonUtils.failedJson(response.getStatus(), e.getMessage());
        response.setBody(new JsonBody(body));
    }
}