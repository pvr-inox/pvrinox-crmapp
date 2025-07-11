package com.cinema.crm.modules.utils;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@SuppressWarnings("deprecation")
@Component
public class SoapClient {
	
	private final long OKHTTP_TIMEOUT = 190000;

    public String sendSoapRequest(String URL, String soapRequest) throws Exception {
        
        OkHttpClient client = new OkHttpClient.Builder().readTimeout(OKHTTP_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(OKHTTP_TIMEOUT, TimeUnit.MILLISECONDS).build();

        Request request = new Request.Builder()
                .url(URL)
                .post(RequestBody.create(MediaType.parse("application/soap+xml; charset=utf-8"), soapRequest))
                //.post(RequestBody.create(MediaType.parse("application/soap+xml; charset=utf-8"), soapRequest))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new Exception("SOAP Request failed: " + response.message());
            }
        }
    }
}
