package com.coachhe.coachcat;

import com.coachhe.servlet.CoachRequest;
import com.coachhe.servlet.CoachResponse;
import com.coachhe.servlet.CoachServlet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author coachhe
 * @project Geekbang
 * @date 2023/11/4 15:16
 * @description
 *  2）从nameToServletMap中查找是否存在该名称的key。若存在，则直接使用该实例，否则执 行第3）步
 *  3）从nameToClassNameMap中查找是否存在该名称的key，若存在，则获取到其对应的全限定 性类名，
 *   使用反射机制创建相应的servlet实例，并写入到nameToServletMap中，若不存在，则直 接访问默认Servlet
 */
public class CoachCatHandler extends ChannelInboundHandlerAdapter {

    // key为CoachServlet的简单类名，value为对应CoachServlet实例
    private Map<String, CoachServlet> nameToServletMap = new ConcurrentHashMap<>();
    // key为CoachServlet的简单类名，value为对应CoachServlet类的全限定类名
    private Map<String, String> nameToClassNameMap = new HashMap<>();

    public CoachCatHandler(Map<String, CoachServlet> nameToServletMap, Map<String, String> nameToClassNameMap) {
        this.nameToClassNameMap = nameToClassNameMap;
        this.nameToServletMap = nameToServletMap;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("1");
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            String uri = request.uri();
            // 从请求中解析出要访问的Servlet名称
            // aaa/bbb/twoServlet?name=aa
            String servletName = uri.substring(uri.lastIndexOf("/") + 1, uri.indexOf("?"));

            CoachServlet coachServlet = new DefaultCoachServlet();
            // 如果存在已经加载的Servlet，那么采用对应的Servlet进行处理
            if (nameToServletMap.containsKey(servletName)) {
                coachServlet = nameToServletMap.get(servletName);
            } else if (nameToClassNameMap.containsKey(servletName)) {
                // 否则有可能是对应的Servlet没有被加载，那么检查是否包含对应的Servlet，如果有则将其进行加载
                // double-check，双重检测锁
                if (nameToServletMap.get(servletName) == null) {
                    // 没有在运行期间被其他线程加载了，那么进行加载
                    synchronized (this) {
                        if (nameToServletMap.get(servletName) == null) {
                            // 获取当前Servlet的全限定类名
                            String className = nameToClassNameMap.get(servletName);
                            // 使用反射机制创建Servlet实例
                            coachServlet = (CoachServlet) Class.forName(className).newInstance();
                            // 将Servlet实例放入nameToServletMap
                            nameToServletMap.put(servletName, coachServlet);
                        }
                    }
                }
            } // 否则说明没有对应的Servlet方法实现，那么就用默认的Servlet处理了

            // 代码走到这里，servlet一定不为空
            CoachRequest req = new HttpCoachRequest(request);
            CoachResponse res = new HttpCoachResponse(request, ctx);
            // 根据不同请求类型，调用get或者post
            if (request.method().name().equalsIgnoreCase("GET")) {
                coachServlet.doGet(req, res);
            } else if (request.method().name().equalsIgnoreCase("POST")) {
                coachServlet.doPost(req, res);
            }

            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
