package ai.remi.comm.util.http;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Startdis
 * @email startdis@dianjiu.cc
 * @desc OkHttpUtils
 */
@Component
public class OkHttpUtils {
    private static final Logger logger = LoggerFactory.getLogger(OkHttpUtils.class);
    //连接超时时间，默认10s
    private static final long CONNECT_TIMEOUT = 10;
    //设置新连接的默认读取超时时间，默认10s
    private static final long READ_TIMEOUT = 10;
    //设置新连接的默认写入超时，默认10s
    private static final long WRITE_TIMEOUT = 30;

    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    private static final String CONTENT_TYPE = "Content-Type";

    OkHttpClient httpClient;

    private OkHttpUtils(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                try {
                    String text = URLDecoder.decode(message, "utf-8");
                    logger.info("OKHttp-----", text);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    logger.error("OKHttp-----", message);
                }
            }
        });

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(interceptor)
                .sslSocketFactory(createSSLSocketFactory(), new TrustAllCerts())
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        return true;
                    }
                })
                .build();

    }

    private static class OkHttp3Holder {
        private static OkHttpUtils INSTANCE = new OkHttpUtils();
    }
    public static OkHttpUtils getInstance() {
        return OkHttp3Holder.INSTANCE;
    }

    /**
     * get请求，同步方式，获取网络数据，是在主线程中执行的，需要新起线程，将其放到子线程中执行
     *
     * @param url
     * @return
     */
    public Response getData(String url) {
        return getData(url, null);
    }

    /**
     * get请求，同步方式，获取网络数据，是在主线程中执行的，需要新起线程，将其放到子线程中执行
     *
     * @param url
     * @param headerMap
     * @return
     */
    public Response getData(String url, Map<String, String> headerMap) {
        //1.构造Request
        Request.Builder builder = new Request.Builder().get().url(url);
        addHeaders(headerMap,builder);
        Request request = builder.build();
        //2.将Request封装为Call
        Call call = httpClient.newCall(request);
        //3.执行Call，得到response
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * post方式
     *
     * @param url
     * @param bodyParams
     * @return
     */
    public Response postData(String url, Map<String, Object> bodyParams) {
        return postData(url, bodyParams, null);
    }

    /**
     * post方式
     *
     * @param url        请求url
     * @param bodyParams requestbody
     * @param headerMap  请求头信息
     * @return
     * @throws IOException
     */
    public Response postData(String url, Map<String, Object> bodyParams, Map<String, String> headerMap) {
        //1.构造RequestBody
        RequestBody body = setRequestBody(bodyParams, headerMap);
        //2.构造Request
        Request.Builder requestBuilder = new Request.Builder().post(body).url(url);
        addHeaders(headerMap, requestBuilder);
        Request request = requestBuilder.build();
        //3.将Request封装为Call
        Call call = httpClient.newCall(request);
        //4.执行Call，得到response
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * put请求方式
     *
     * @param url
     * @param bodyParams
     * @return
     */
    public Response putData(String url, Map<String, Object> bodyParams, Map<String, String> headerMap) {
        //1.构造RequestBody
        RequestBody body = setRequestBody(bodyParams, headerMap);
        //2.构造Request
        Request.Builder requestBuilder = new Request.Builder().put(body).url(url);
        addHeaders(headerMap, requestBuilder);
        Request request = requestBuilder.build();
        //3.将Request封装为Call
        Call call = httpClient.newCall(request);
        //4.执行Call，得到response
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * DELETE 请求
     * @param url
     * @param bodyParams
     * @return
     */
    public Response delData(String url, Map<String, Object> bodyParams, Map<String, String> headerMap) {
        //1.构造RequestBody
        RequestBody body = setRequestBody(bodyParams, headerMap);
        //2.构造Request
        Request.Builder requestBuilder = new Request.Builder().delete(body).url(url);
        addHeaders(headerMap, requestBuilder);
        Request request = requestBuilder.build();
        //3.将Request封装为Call
        Call call = httpClient.newCall(request);
        //4.执行Call，得到response
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * post的请求参数，构造RequestBody
     *
     * @param bodyParams
     * @return
     */
    private RequestBody setRequestBody(Map<String, Object> bodyParams, Map<String, String> headerMap) {
        String contentType = headerMap.get(CONTENT_TYPE);
        if ("application/x-www-form-urlencoded".equals(contentType)) {
            //表单提交 就是key-value 都是字符串型
            //转换
            Map<String, String> strBodyParamMap = new HashMap<>();
            if (bodyParams != null && !bodyParams.isEmpty()) {
                bodyParams.forEach((key, value) -> {
                    if (value != null) {
                        strBodyParamMap.put(key, (String) value);
                    }
                });
            }
            return buildRequestBodyByMap(strBodyParamMap);
        } else {
            //json
            return buildRequestBodyByJson(JSON.toJSONString(bodyParams));
        }

    }

    /**
     * 表单方式提交构建
     *
     * @param bodyParams
     * @return
     */
    private RequestBody buildRequestBodyByMap(Map<String, String> bodyParams) {
        RequestBody body = null;
        FormBody.Builder formEncodingBuilder = new FormBody.Builder();
        if (bodyParams != null) {
            Iterator<String> iterator = bodyParams.keySet().iterator();
            String key = "";
            while (iterator.hasNext()) {
                key = iterator.next().toString();
                formEncodingBuilder.add(key, bodyParams.get(key));
                logger.info(" 请求参数：{}，请求值：{} ", key, bodyParams.get(key));
            }
        }
        body = formEncodingBuilder.build();
        return body;
    }
    /**
     * json方式提交构建
     *
     * @param jsonStr
     * @return
     */
    private RequestBody buildRequestBodyByJson(String jsonStr) {
        return RequestBody.create(MEDIA_TYPE_JSON, jsonStr);
    }


    /**
     * 生成安全套接字工厂，用于https请求的证书跳过
     *
     * @return
     */
    public SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }
        return ssfFactory;
    }


    /**
     * 针对json post处理
     *
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    public String postJson(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = httpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    /**
     * JSON POST 异步
     *
     * @param url
     * @param json
     * @param callback
     * @throws IOException
     */
    public void postJsonAsyn(String url, String json, final OkHttp3Callback callback) throws IOException {
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
        //2 构造Request
        Request.Builder requestBuilder = new Request.Builder();
        Request request = requestBuilder.post(body).url(url).build();
        callBackHandler(request, callback);
    }

    /**
     * GET 异步
     *  @param headerMap
     * @param url
     * @param callback
     * @return
     */
    public void getDataAsyn(String url, Map<String, String> headerMap, final OkHttp3Callback callback) {
        //1 构造Request
        Request.Builder builder = new Request.Builder().get().url(url);
        addHeaders(headerMap, builder);
        Request request = builder.build();
        callBackHandler(request, callback);
    }
    /**
     * post方式
     *
     * @param url        请求url
     * @param bodyParams requestbody
     * @param headerMap  请求头信息
     * @return
     */
    public void postDataAsyn(String url, Map<String, Object> bodyParams, Map<String, String> headerMap, final OkHttp3Callback callback) {
        //1构造RequestBody
        RequestBody body = setRequestBody(bodyParams, headerMap);
        //2 构造Request
        Request.Builder requestBuilder = new Request.Builder().post(body).url(url);
        addHeaders(headerMap, requestBuilder);
        Request request = requestBuilder.build();
        callBackHandler(request, callback);
    }
    private void callBackHandler(Request request, OkHttp3Callback callback){
        // 1 将Request封装为Call
        Call call = httpClient.newCall(request);
        // 2 执行call
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.failed(call, e);
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                callback.success(call, response);
            }
        });
    }

    /**
     * 添加header信息
     *
     * @param headerMap
     * @param builder
     * @return
     */
    private static Request.Builder addHeaders(Map<String, String> headerMap, Request.Builder builder) {
        if (headerMap != null && !headerMap.isEmpty()) {
            headerMap.forEach((key, value) -> builder.addHeader(key, value));
        }
        return builder;
    }

    /**
     * 用于信任所有证书
     */
    class TrustAllCerts implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }


}

