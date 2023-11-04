package com.coachhe.webapp;

import com.coachhe.servlet.CoachRequest;
import com.coachhe.servlet.CoachResponse;
import com.coachhe.servlet.CoachServlet;

/**
 * @author coachhe
 * @project Geekbang
 * @date 2023/11/4 15:52
 * @description
 */
public class NameServlet extends CoachServlet {
    @Override
    public void doGet(CoachRequest request, CoachResponse response) throws Exception {
        response.write("coachhe handsome");
    }

    @Override
    public void doPost(CoachRequest request, CoachResponse response) throws Exception {
        doGet(request, response);
    }
}
