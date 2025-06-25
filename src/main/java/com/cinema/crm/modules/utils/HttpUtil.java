
package com.cinema.crm.modules.utils;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
public class HttpUtil {

    static final Logger logger = LoggerFactory.getLogger("http");
    
    private final long OKHTTP_TIMEOUT1 = 12000;

    public String error(Exception e) {
        final StringBuffer bf = new StringBuffer();
        bf.append(e.toString());
        for (int i = 0; i < e.getStackTrace().length; i++) {
            bf.append("\n").append(e.getStackTrace()[i].toString());
        }
        bf.append("\n").append(new Date());
        return ":: ERROR::" + bf;
    }
    public String invoke(String URL, Map<String, String> HEADERS, String id, long OKHTTP_TIMEOUT) {
        try {
            logger.debug("REQUEST DATA URL :: {} HEADERS :: {} :: ID :: {}", URL, HEADERS, id);
            OkHttpClient client = new OkHttpClient.Builder().readTimeout(OKHTTP_TIMEOUT1, TimeUnit.MILLISECONDS)
                    .writeTimeout(OKHTTP_TIMEOUT1, TimeUnit.MILLISECONDS).build();

            Request request = null;
            if (ObjectUtils.isEmpty(HEADERS)) {
                request = new Request.Builder().url(URL).get().build();
            } else {
                request = new Request.Builder().url(URL).get().headers(Headers.of(HEADERS)).build();
            }
            Response response = client.newCall(request).execute();
            String result = response.body().string();
            logger.debug("RESPONSE DATA :: {} FOR ID :: {}", result, id);
            return result;
        } catch (Exception e) {
            logger.error("EXCEPTION OCCURED FOR ID :: {} :: {}", id, error(e));
            return null;
        }
    }

    public String invokePost(String URL, Map<String, String> HEADERS, String data, String mediaType, String id, long OKHTTP_TIMEOUT) {
        try {
            logger.debug("REQUEST DATA URL :: {} HEADERS :: {} :: ID :: {} DATA :: {}", URL, HEADERS, id, data);
        	OkHttpClient client = new OkHttpClient.Builder().readTimeout(OKHTTP_TIMEOUT1, TimeUnit.MILLISECONDS)
                    .writeTimeout(OKHTTP_TIMEOUT1, TimeUnit.MILLISECONDS).build();

            RequestBody body = RequestBody.create(data, MediaType.parse(mediaType));
            Request request = null;
            if (ObjectUtils.isEmpty(HEADERS)) {
                request = new Request.Builder().url(URL).post(body).build();
            } else {
                request = new Request.Builder().url(URL).post(body).headers(Headers.of(HEADERS)).build();
            }
            
            Response response = client.newCall(request).execute();
            String result = response.body().string();
            if(!ObjectUtils.isEmpty(URL) && (!URL.contains("getFood") && !URL.contains("seatlayout"))) {
            	logger.debug("RESPONSE DATA :: {} FOR ID :: {}", result, id);
            }
            return result;
        } catch (Exception e) {
            logger.error("EXCEPTION OCCURED FOR ID :: {} :: {}", id, error(e));
            return null;
        }
    }
    
    public String invokePatch(String URL, Map<String, String> HEADERS, String data, String mediaType, String id, long OKHTTP_TIMEOUT) {
        try {
            logger.debug("REQUEST DATA URL :: {} HEADERS :: {} :: ID :: {} DATA :: {}", URL, HEADERS, id, data);
        	OkHttpClient client = new OkHttpClient.Builder().readTimeout(OKHTTP_TIMEOUT, TimeUnit.MILLISECONDS)
                    .writeTimeout(OKHTTP_TIMEOUT1, TimeUnit.MILLISECONDS).build();

            RequestBody body = RequestBody.create(data, MediaType.parse(mediaType));
            Request request = null;
            if (ObjectUtils.isEmpty(HEADERS)) {
                request = new Request.Builder().url(URL).patch(body).build();
            } else {
                request = new Request.Builder().url(URL).patch(body).headers(Headers.of(HEADERS)).build();
            }
            
            Response response = client.newCall(request).execute();
            String result = response.body().string();
            logger.debug("RESPONSE DATA :: {} FOR ID :: {}", result, id);
            return result;
        } catch (Exception e) {
            logger.error("EXCEPTION OCCURED FOR ID :: {} :: {}", id, error(e));
            return null;
        }
    }
    
    public String invokePut(String URL, Map<String, String> HEADERS, String data, String mediaType, String id, long OKHTTP_TIMEOUT) {
        try {
            logger.debug("REQUEST DATA URL :: {} HEADERS :: {} :: ID :: {} DATA :: {}", URL, HEADERS, id, data);
        	OkHttpClient client = new OkHttpClient.Builder().readTimeout(OKHTTP_TIMEOUT1, TimeUnit.MILLISECONDS)
                    .writeTimeout(OKHTTP_TIMEOUT1, TimeUnit.MILLISECONDS).build();

            RequestBody body = RequestBody.create(data, MediaType.parse(mediaType));
            Request request = null;
            if (ObjectUtils.isEmpty(HEADERS)) {
                request = new Request.Builder().url(URL).put(body).build();
            } else {
                request = new Request.Builder().url(URL).put(body).headers(Headers.of(HEADERS)).build();
            }
            
            Response response = client.newCall(request).execute();
            String result = response.body().string();
            if(!ObjectUtils.isEmpty(URL) && (!URL.contains("getFood") && !URL.contains("seatlayout"))) {
            	logger.debug("RESPONSE DATA :: {} FOR ID :: {}", result, id);
            }
            return result;
        } catch (Exception e) {
            logger.error("EXCEPTION OCCURED FOR ID :: {} :: {}", id, error(e));
            return null;
        }
    }
}
