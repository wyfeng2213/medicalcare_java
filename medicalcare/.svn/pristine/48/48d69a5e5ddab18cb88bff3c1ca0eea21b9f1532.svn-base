package com.cmcc.common;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by chijr on 17/3/10.
 */
public class HttpUtils {

    private final static Logger logger = LoggerFactory.getLogger(HttpUtils.class);



    static public   String requestGet(String url,Map<String,String> params,Map<String,String> heads) {


        OkHttpClient okHttpClient = new OkHttpClient();


        Request.Builder reqBuild = new Request.Builder();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();

        if (params!=null){
            for (String key:params.keySet()){

                urlBuilder.addQueryParameter(key,params.get(key));
            }
        }


        reqBuild.url(urlBuilder.build());



        if (heads!=null){
            for (String key:heads.keySet()){

                reqBuild.addHeader(key,heads.get(key));
            }
        }



        Request req = reqBuild.build();

        try {
            Response response=okHttpClient.newCall(req).execute();
            //判断请求是否成功
            if(response.isSuccessful()){
                return response.body().string();
                //打印服务端返回结果
            }

        } catch (IOException e) {


            logger.error(e.toString());


        }

        return null;


    }


    static public String requestPostBody(String url,String json,Map<String,String> heads) throws ApiException{

        OkHttpClient okHttpClient = new OkHttpClient();

        MediaType JSONType=MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSONType,json);

        Request.Builder builder= new Request.Builder();

        builder.url(url);

        if (heads!=null){
            for (String key:heads.keySet()){

                builder.addHeader(key,heads.get(key));
            }
        }

        builder.post(body);

        Request req = builder.build();

        try {
            Response response=okHttpClient.newCall(req).execute();
            if(response.isSuccessful()){

                return response.body().string();
                //打印服务端返回结果

            }
            throw   new ApiException(response.code(),response.body().string());
        } catch (IOException e) {
            e.printStackTrace();

            throw   new ApiException(301,"网络错误");

        }



    }

    static public String requestPostForm(String url,Map<String,String> params,Map<String,String> heads){



        //OkHttpClient okHttpClient = new OkHttpClient();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).readTimeout(20, TimeUnit.SECONDS)

                .build();
        FormBody.Builder bodyBuilder = new FormBody.Builder();


        if (params!=null){
            for (String key:params.keySet()){
              bodyBuilder.add(key,params.get(key));
            }
        }

        RequestBody body = bodyBuilder.build();

        Request.Builder builder= new Request.Builder();

        if (heads!=null){
            for (String key:heads.keySet()){

                builder.addHeader(key,heads.get(key));
            }
        }
        builder.url(url);
        builder.post(body);

        Request req = builder.build();

        try {
            Response response=okHttpClient.newCall(req).execute();
            if(!response.isSuccessful()) {

            }


            return response.body().string();
                //打印服务端返回结果
        } catch (IOException e) {
            e.printStackTrace();


        }

        return null;

    }



}
