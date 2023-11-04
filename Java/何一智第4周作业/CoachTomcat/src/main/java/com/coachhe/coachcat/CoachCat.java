package com.coachhe.coachcat;

/**
 * @author coachhe
 * @project Geekbang
 * @date 2023/11/4 15:50
 * @description
 */
public class CoachCat {
    public static void main(String[] args) throws Exception {
        CoachCatServer server = new CoachCatServer("com.coachhe.webapp");
        server.start();
    }
}
