package com.techlabs.apdcl.retrofit;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

        private static final String BASE_URL = "http://103.8.43.36:2931/";
//    private static final String BASE_URL = "http://18.10.9.99:2941/";
//    private static final String BASE_URL = "http://192.168.1.14:2931/";
//    private static final String BASE_URL = "http://192.168.1.13:2931/";
//    private static final String BASE_URL = "http://192.168.1.14:1608/";
//    private static final String BASE_URL = "http://192.168.1.66:2985/";
//    private static final String BASE_URL = "http://10.0.0.14:2931/";
    ////    private static final String BASE_URL = "https://techlabsforce.in:2950/";


    public static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    .connectTimeout(5, TimeUnit.MINUTES)
                    .readTimeout(5, TimeUnit.MINUTES)
                    .writeTimeout(5, TimeUnit.MINUTES);

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(loggingInterceptor);

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

   /*public static Retrofit getClient() {
       if (retrofit == null) {

           try {

               TrustManager[] trustAllCerts = new TrustManager[]{
                       new X509TrustManager() {
                           @Override
                           public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {}

                           @Override
                           public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {}

                           @Override
                           public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                               return new java.security.cert.X509Certificate[]{};
                           }
                       }
               };

               SSLContext sslContext = SSLContext.getInstance("SSL");
               sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
               SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

               OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                       .sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0])
                       .hostnameVerifier((hostname, session) -> true)
                       .connectTimeout(5, TimeUnit.MINUTES)
                       .readTimeout(5, TimeUnit.MINUTES)
                       .writeTimeout(5, TimeUnit.MINUTES);

               HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
               loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
               httpClient.addInterceptor(loggingInterceptor);

               retrofit = new Retrofit.Builder()
                       .baseUrl(BASE_URL)
                       .client(httpClient.build())
                       .addConverterFactory(GsonConverterFactory.create())
                       .build();

           } catch (Exception e) {
               throw new RuntimeException(e);
           }
       }

       return retrofit;
   }*/

}
