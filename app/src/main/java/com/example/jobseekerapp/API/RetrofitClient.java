package com.example.jobseekerapp.API;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.IOException;

public class RetrofitClient {

    private static Retrofit retrofit = null;
    private static final String BASE_URL = "https://fursaty.kicklance.com/";
    private static final String TOKEN = "NmNVeKL3hmU9GJGrSf3rzFYDlUAGSM3FOIrJc3pr";

    public static Retrofit getInstance() {
        if (retrofit == null) {

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request originalRequest = chain.request();
                            Request newRequest = originalRequest.newBuilder()
                                    .addHeader("Accept", "application/json")
                                    .addHeader("Authorization", "Bearer " + TOKEN)
                                    .build();
                            return chain.proceed(newRequest);
                        }
                    })
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
