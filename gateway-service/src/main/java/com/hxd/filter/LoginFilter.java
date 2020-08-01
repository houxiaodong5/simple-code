package com.hxd.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * Created by Administrator on 2020/8/1/001.
 */
@Component
public class LoginFilter extends ZuulFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginFilter.class);

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否对请求做验证等相关处理：true->处理，走下面的run方法
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        LOGGER.info("请求进来了");
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/login")) {
            LOGGER.info("登录请求");
            return true;
        }
        return false;
    }

    /**
     * 处理请求
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        LOGGER.info("对请求做处理");
        LOGGER.error("登录失败，暂不支持QQ登录！");
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String token = request.getParameter("token");
       /* if (token == null || "".equals(token.trim())) {
            // 没有token，认为登录校验失败，进行拦截
            currentContext.setSendZuulResponse(false);
            // 返回401状态码。也可以考虑重定向到登录页
            currentContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }*/
        currentContext.addZuulResponseHeader("LoginCheck", "Failed");
        currentContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        currentContext.setResponseBody("{\"message\":\"operation failed\",\"reason\":\"Unauthorized\"}");
        currentContext.setSendZuulResponse(false);
        return null;
    }
}
