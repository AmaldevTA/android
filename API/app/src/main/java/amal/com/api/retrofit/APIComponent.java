package amal.com.api.retrofit;

import java.util.Map;

import amal.com.api.model.Response;
import amal.com.api.retrofit.model.ProfileRequest;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by amal on 31/3/18
 */

public interface APIComponent {
    @Headers("Content-Type: application/json")
    @GET("payment/{id}/profile")
    Call<Response> getProfile(@Path("id") int id, @Header("Country-Code") String code);

    @Headers("Content-Type: application/json")
    @GET("payment/fullProfile")
    Call<Response> getFullProfile(@Header("Country-Code") String code, @QueryMap Map<String, String> options);

    @Headers({"Content-Type: application/json"})
    @POST("payment/updateProfile")
    Call<Response> updateProfile(@Body ProfileRequest request);

    @FormUrlEncoded
    @POST("payment/updateProfileForm")
    Call<Response> updateUser(@Field("userId") Integer userId, @Field("name") String name, @Field("address") String address);

    @Multipart
    @POST("payment/updateProfilePicture")
    Call<Response> updateProfilePicture(@Part("id") RequestBody id, @Part MultipartBody.Part file);

}
