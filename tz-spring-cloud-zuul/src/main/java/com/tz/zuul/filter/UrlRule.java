package com.tz.zuul.filter;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * @author tz
 * @ClassName UrlRule
 * @Description
 * @Date 19:43 2020/1/11
 **/
@Slf4j
public class UrlRule extends AbstractLoadBalancerRule {


    public Server choose(ILoadBalancer lb, Object key) {
        log.info("choose urlFilter lb = [{}],key = [{}] ",lb,key);
        if (lb == null) {
            log.warn("no load balancer");
            return null;
        }

        Server server = null;
        int count = 0;
        while (server == null && count++ < 10) {
            List<Server> reachableServers = lb.getReachableServers();
            List<Server> allServers = lb.getAllServers();
            int upCount = reachableServers.size();
            int serverCount = allServers.size();

            if ((upCount == 0) || (serverCount == 0)) {
                log.warn("No up servers available from load balancer: " + lb);
                return null;
            }

            int nextServerIndex = ipUserHash(serverCount);
            log.info("选择服务器hash index=[{}]",nextServerIndex);
            server = allServers.get(nextServerIndex);
            log.info("选择服务 [{}]",server.getHostPort());

            if (server == null) {
                /* Transient. */
                Thread.yield();
                continue;
            }

            if (server.isAlive() && (server.isReadyToServe())) {
                return (server);
            }

            // Next.
            server = null;
        }

        if (count >= 10) {
            log.warn("No available alive servers after 10 tries from load balancer: "
                    + lb);
        }
        return server;

    }

    private int ipUserHash(int serverCount) {
//        String userTicket = getTicketFromCookie();
        String userIp = getRemoteAddr();
        try {
            userIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
        }
        int userHashCode = Math.abs((userIp).hashCode());
        return userHashCode%serverCount;
    }

    private String getRemoteAddr() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String remoteAddr = "0.0.0.0";
        if (request.getHeader("X-FORWARDED-FOR") != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
        } else {
            remoteAddr = request.getRemoteAddr();
        }
        return remoteAddr;
    }

    private String getTicketFromCookie() {
        String ticket = "";
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        //从cookie获取ticket
//        Cookie cookie = CookieUtil.getCookieByName(request,CookieUtil.COOKIE_TICKET_NAME);
//        if (cookie!=null) {
//            ticket = cookie.getValue()!=null?cookie.getValue():"";
//        }
        return ticket;
    }

    @Override
    public Server choose(Object key) {
        return choose(getLoadBalancer(), key);
    }

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {
        // TODO Auto-generated method stub

    }
    public static void main(String[] args) {
        String ticket = "";
        String localIp = "192.168.1.203";
        System.out.println(Math.abs((localIp).hashCode())%2);
        String localIp1 = "192.168.1.204";
        System.out.println(Math.abs((localIp1).hashCode())%2);
        String localIp2 = "192.168.1.203";
        System.out.println(Math.abs((localIp2).hashCode())%2);
    }


}
