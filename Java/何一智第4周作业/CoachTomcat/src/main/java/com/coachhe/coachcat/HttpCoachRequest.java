package com.coachhe.coachcat;

import com.coachhe.servlet.CoachRequest;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.List;
import java.util.Map;

/**
 * @author CoachHe
 * @project Geekbang
 * @description CoachCat中对Servlet规范的默认实现
 * @date 2023/11/1 1:11
 */
public class HttpCoachRequest implements CoachRequest {

    private HttpRequest request;

    public HttpCoachRequest(HttpRequest request) {
        this.request = request;
    }

    @Override
    public String getUri() {
        return request.uri();
    }

    @Override
    public String getPath() {
        QueryStringDecoder decoder = new QueryStringDecoder(request.uri());
        return decoder.path();
    }

    @Override
    public String getMethod() {
        return request.method().name();
    }

    @Override
    public Map<String, List<String>> getParameters() {
        QueryStringDecoder decoder = new QueryStringDecoder(request.uri());
        return decoder.parameters();
    }

    @Override
    public List<String> getParameters(String name) {
        return getParameters().get(name);
    }

    @Override
    public String getParameter(String name) {
        List<String> parameters = getParameters(name);
        if (parameters == null || parameters.isEmpty()) {
            return null;
        }
        return parameters.get(0);
    }
}
