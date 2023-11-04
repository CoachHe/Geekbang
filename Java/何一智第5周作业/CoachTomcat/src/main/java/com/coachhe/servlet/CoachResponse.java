package com.coachhe.servlet;

/**
 * @author CoachHe
 * @project Geekbang
 * @description Servlet规范之响应规范
 * @date 2023/11/1 1:07
 */
public interface CoachResponse {
    // 将响应写入Channel
    void write(String content) throws Exception;
}
