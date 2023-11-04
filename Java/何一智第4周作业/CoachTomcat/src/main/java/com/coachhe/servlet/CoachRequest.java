package com.coachhe.servlet;

import java.util.List;
import java.util.Map;

/**
 * @author CoachHe
 * @project Geekbang
 * @description Servlet规范之请求规范
 * @date 2023/11/1 1:04
 */
public interface CoachRequest {
    // 获取URI，包含请求参数，即？之后的内容
    String getUri();
    // 获取请求路径，其不包含请求参数
    String getPath();
    // 获取请求方法（GET、POST等）
    String getMethod();
    // 获取所有参数
    Map<String, List<String>> getParameters();
    // 获取制定名称的请求参数
    List<String> getParameters(String name);
    // 获取制定名称的请求参数的第一个值
    String getParameter(String name);
}
