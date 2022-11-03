package org.code.blocks.springboot.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.code.blocks.common.exception.BaseException;
import org.code.blocks.common.util.JsonUtils;
import org.code.blocks.springboot.config.InitConfig;
import org.code.blocks.springboot.config.PropertitesConfig;

@Slf4j
public class HttpClientUtils {

    // SpringUtil取值举例
    static PropertitesConfig propertitesConfig = (PropertitesConfig)SpringUtils.getBean("propertitesConfig");
    // SpringUtil取值举例
    public static int httpRequestTimeOut_ = propertitesConfig.getHttpRequestTimeOut();

    //最大等待时间;
    private static int httpRequestTimeOut = InitConfig.propertitesConfig.getHttpRequestTimeOut();

    //最大连接数;
    private static int httpMaxActive = InitConfig.propertitesConfig.getHttpMaxActive();

    //连接检查间隔时间
    private static int httpValidateAfterInactivity = InitConfig.propertitesConfig.getHttpValidateAfterInactivity();

    private static final String httpProxyHost = InitConfig.propertitesConfig.getHttpProxyHost();

    private static final int httpProxyPort = InitConfig.propertitesConfig.getHttpProxyPort();

    private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;

    static {
        // 设置连接池
        connMgr = new PoolingHttpClientConnectionManager();
        // 设置连接池大小
        connMgr.setMaxTotal(httpMaxActive);
        connMgr.setDefaultMaxPerRoute(httpMaxActive);
        connMgr.setValidateAfterInactivity(httpValidateAfterInactivity);

        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(httpRequestTimeOut);
        // 设置读取超时
        configBuilder.setSocketTimeout(httpRequestTimeOut);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(httpRequestTimeOut);
        requestConfig = configBuilder.build();
    }

    public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {

        //todo 改为TLS1？
        SSLContext sc = SSLContext.getInstance("SSLv3");

        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sc.init(null, new TrustManager[]{trustManager}, null);
        return sc;
    }

    public static CloseableHttpClient createHttpClient(String proxyHost, int proxyPort, String proxyUsername, String proxyPassword) {
        try {
            HttpHost proxy = null;
            if (StringUtils.isNotEmpty(proxyHost)) {
                proxy = new HttpHost(proxyHost, proxyPort, "http");
            }

            CredentialsProvider credsProvider = new BasicCredentialsProvider();
            if (StringUtils.isNotEmpty(proxyUsername) && StringUtils.isNotEmpty(proxyPassword)) {
                credsProvider.setCredentials(
                        new AuthScope(proxyHost, proxyPort),
                        new UsernamePasswordCredentials(proxyUsername, proxyPassword));
            }

            //采用绕过验证的方式处理https请求
            SSLContext sslcontext = createIgnoreVerifySSL();

            // 设置协议http和https对应的处理socket链接工厂的对象
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", new SSLConnectionSocketFactory(sslcontext))
                    .build();
            PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            HttpClients.custom().setConnectionManager(connManager);

            //创建自定义的httpclient对象
            CloseableHttpClient client = null;
            if (proxy == null) {
                client = HttpClients.custom().setConnectionManager(connManager).build();
            } else {
                client = HttpClients.custom().setConnectionManager(connManager)
                        .setProxy(proxy)
                        .setDefaultCredentialsProvider(credsProvider)
                        .build();
            }

            return client;
        } catch (Exception e) {
            log.error("onWarning:create http client error" + e);
            throw new BaseException(e);
        }
    }


    /**
     * 发送GET请求（HTTP）,不传入参数
     * @param url
     * @param isSSL 是否需要https校验
     * @return
     */
    public static String doGet(String url, boolean isSSL) {
        log.debug("doGet start. url:{}", url);

        // 创建默认的HttpClient实例.
        CloseableHttpClient httpClient;
        if(isSSL) {
            httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConn()).setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
        } else {
            httpClient = createHttpClient(httpProxyHost, httpProxyPort, "", "");
        }
        HttpGet httpget = null;
        CloseableHttpResponse httpResponse = null;
        try {
            // 定义一个get请求方法
            httpget = new HttpGet(url);

            // 执行get请求，返回response服务器响应对象, 其中包含了状态信息和服务器返回的数据
            httpResponse = httpClient.execute(httpget);
            log.info("doGet response: {}", httpResponse);

            // 使用响应对象, 获得状态码, 处理内容
            int statusCode = httpResponse.getStatusLine().getStatusCode();

            String result = "";
            if (statusCode >= 200 && statusCode < 300) {
                // 使用响应对象获取响应实体
                HttpEntity entity = httpResponse.getEntity();
                if (entity != null) {
                    // 将响应实体转为字符串
                    result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                    log.debug("doGet result : " + result);
                }
            } else {
                log.warn("onWarning:doGet fail. statusCode:{}", statusCode);
            }
            EntityUtils.consume(httpResponse.getEntity());
            httpResponse.close();
            return result;
        } catch (Exception e) {
            log.warn("onWarning:doGet fail. statusCode", e);
            throw new BaseException(e);
        } finally {
            httpget.releaseConnection();
            if (httpResponse != null) {
                try {
                    EntityUtils.consume(httpResponse.getEntity());
                    httpResponse.close();
                } catch (IOException e) {
                    log.error("onError:doGet consume fail : ", e);
                    throw new BaseException(e);
                }
            }
        }
    }


    /**
     * 发送 SSL POST请（HTTPS），K-V形式
     * @param url
     * @param params
     * @param isSSL 是否需要https校验
     * @return
     */
    public static String doPost(String url, Object params, boolean isSSL) {

        log.debug("doPost start. url:{},params:{}", url, JsonUtils.objToJson(params));
        CloseableHttpClient httpClient;
        if(isSSL) {
            httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConn()).setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
        } else {
            httpClient = createHttpClient(httpProxyHost, httpProxyPort, "", "");
        }
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        String httpStr = null;

        try {
            httpPost.setConfig(requestConfig);

            // 建立一个NameValuePair数组，用于存储欲传送的参数
            httpPost.addHeader("Content-type", "application/json; charset=utf-8");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setEntity(new StringEntity(JsonUtils.objToJson(params), Charset.forName("UTF-8")));//发送的参数

            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            String result = null;
            if (statusCode >= 200 && statusCode < 300) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    result = EntityUtils.toString(response.getEntity(), "UTF-8");
                    log.debug("doPost result : " + result);
                }
            }
            EntityUtils.consume(response.getEntity());
            response.close();
            return result;
        } catch (Exception e) {
            log.error("onWarning:doPost fail", e);
            throw new BaseException(e);
        } finally {
            httpPost.releaseConnection();
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                    response.close();
                } catch (IOException e) {
                    log.error("onWarning:doPost consume fail : ", e);
                    throw new BaseException(e);
                }
            }
        }
    }

    /**
     * @Description: 创建SSL安全连接
     */
    private static SSLConnectionSocketFactory createSSLConn() {
        log.debug("createSSLConn start...");

        SSLConnectionSocketFactory sslsf = null;
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();

            sslsf = new SSLConnectionSocketFactory(sslContext, new MyHostnameVerifier());
            log.debug("createSSLConn end...");
            return sslsf;
        } catch (GeneralSecurityException e) {
            log.error("Onwarning: createSSLConn fail:", e);
            throw new BaseException(e);
        }
    }


}

class MyHostnameVerifier implements HostnameVerifier {
    @Override
    public boolean verify(String s, SSLSession sslSession) {
        return false;
    }
}