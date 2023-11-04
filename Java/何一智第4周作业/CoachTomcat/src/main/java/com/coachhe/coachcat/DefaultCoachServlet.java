package com.coachhe.coachcat;

import com.coachhe.servlet.CoachRequest;
import com.coachhe.servlet.CoachResponse;
import com.coachhe.servlet.CoachServlet;

/**
 * @author coachhe
 * @project Geekbang
 * @date 2023/11/4 15:00
 * @description CoachCat中对Servlet规范的默认实现
 */
public class DefaultCoachServlet extends CoachServlet {

    @Override
    public void doGet(CoachRequest request, CoachResponse response) throws Exception {
        // http://localhost:8080/aaa/bbb/oneservlet?name=xiong
        // path：/aaa/bbb/oneservlet?name=xiong
        String uri = request.getUri();
        String name = uri.substring(0, uri.indexOf("?"));
        response.write("404 - no this servlet: " + name);
    }

    @Override
    public void doPost(CoachRequest request, CoachResponse response) throws Exception {
        doGet(request, response);
    }
}
