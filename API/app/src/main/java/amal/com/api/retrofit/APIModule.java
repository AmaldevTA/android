package amal.com.api.retrofit;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *Created by amal on 31/3/18
 */

public class APIModule {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(File httpCacheDirectory) {

        if(retrofit == null){
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            int cacheSize = 20 * 1024 * 1024;
            Cache cache = new Cache(httpCacheDirectory, cacheSize);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .cache(cache)
                    .build();


            retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.43.223:8080/sample/rest/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }
}
