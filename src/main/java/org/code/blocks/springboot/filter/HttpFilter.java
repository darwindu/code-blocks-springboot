package org.code.blocks.springboot.filter;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 过滤器
 * 解决：http请求https场景，跨域问题；
 * 用户--(https)-->nginx--(http)-->后台
 * 直接使用spring过滤器，不存在改跨域问题
 */
@Component
@WebFilter(
    filterName = "HttpFilter",
    urlPatterns = {"/*"}
)
@Slf4j
@Order(FilterRegistrationBean.HIGHEST_PRECEDENCE)
public class HttpFilter implements Filter {

    //@Value("${code.blocks.cors.http.filter}")
    //private String corsHttpFilter;

    //private List<String> ALLOW_ORIGINS = new ArrayList<>();

    /*@Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if (StringUtils.isNotBlank(corsHttpFilter)) {
            String[] corsHttpFilters = corsHttpFilter.split(",");
            for (String s : corsHttpFilters) {
                ALLOW_ORIGINS.add(s);
            }
        }
        log.info("####httpxss filter init...", JsonUtils.objToJson(ALLOW_ORIGINS));
    }*/

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        log.info("httpxss filter is open");

        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;

        // 请求源：http://xx.test.xx.com
        String reqOrigin = req.getHeader(HttpHeaders.ORIGIN);
        // 请求域名，例如：xx.test.xx.com
        String reqHost = req.getHeader(HttpHeaders.HOST);
        // 服务接口强制增加：https协议，目的与请求源进行匹配
        String reqHostHttps = "https://" + reqHost;
        // 服务接口实际协议
        String reqScheme = req.getScheme();

        log.debug("httpxss request: url:{} method:{} reqOrigin:{} reqHost:{} reqScheme:{}", req.getRequestURL(), req.getMethod(), reqOrigin, reqHost, reqScheme);
        chain.doFilter(req, resp);

        boolean isPrintResponseHeader = false;
        // 域名一致，协议不一致，重新指定response
        if (StringUtils.equalsAny(req.getMethod(), HttpMethod.OPTIONS.toString(), HttpMethod.POST.toString()) &&
            StringUtils.isNotBlank(reqOrigin) && StringUtils.isNotBlank(reqHost) &&
            // 域名一致
            reqOrigin.contains(reqHost) &&
            // 协议不一致
            !StringUtils.equalsAny(reqOrigin, reqHostHttps)) {

            log.info("httpxss processing...");
            isPrintResponseHeader = true;

            // 处理ACCESS_CONTROL_ALLOW_ORIGIN属性
            //this.setCrosHeaderAllowOrigin(reqOrigin, resp);
            resp.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, reqOrigin);
            resp.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, Boolean.TRUE.toString());
            if (StringUtils.equalsAny(req.getMethod(), HttpMethod.OPTIONS.toString())) {
                //resp.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "clientid, clientversion, content-type, graytype, ngiid, trackid, unionid, userid, uuid");
                resp.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, RequestMethod.POST.toString());
            }
        }

        if (isPrintResponseHeader) {
            Collection<String> headerNames = resp.getHeaderNames();
            for(String headerName: headerNames) {
                log.debug("httpxss processed response-headers: {} :{}", headerName, resp.getHeaders(headerName));
            }
        }
    }

    /*private void setCrosHeaderAllowOrigin(String reqOrigin, HttpServletResponse resp) {
        if (reqOrigin == null) {
            return;
        }
        // 匹配算法：equals，命中源
        if (ALLOW_ORIGINS.contains(reqOrigin)) {
            resp.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, reqOrigin);
        }
    }*/

}
