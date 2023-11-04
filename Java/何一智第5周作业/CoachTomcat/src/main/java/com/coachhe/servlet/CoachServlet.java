package com.coachhe.servlet;

/**
 * @author CoachHe
 * @project Geekbang
 * @description 定义Servlet规范
 * @date 2023/11/1 1:08
 */
public abstract class CoachServlet {
    // 处理Http的get请求
    public abstract void doGet(CoachRequest request, CoachResponse response) throws Exception;
    // 处理Http的post请求
    public abstract void doPost(CoachRequest request, CoachResponse response) throws Exception;
}
