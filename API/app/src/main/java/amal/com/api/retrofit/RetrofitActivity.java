package amal.com.api.retrofit;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import amal.com.api.R;
import amal.com.api.model.Response;
import amal.com.api.retrofit.model.ProfileRequest;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class RetrofitActivity extends AppCompatActivity {

    private APIComponent component;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        getComponent();

        //getProfile(536423, "91");

        //getFullProfile("91");

        //ProfileRequest request = new ProfileRequest();
        //request.setUserId(56293);
        //request.setName("name");
        //request.setAddress("address line");
        //updateProfile(request);

        //updateUser(56293, "name", "address line");

        updateProfilePicture("562436", "/storage/emulated/0/Download/Selection_002.png");
    }


    private APIComponent getComponent(){
        if(component == null){
            File file = new File(getCacheDir(), "responses");
            component = APIModule.getClient(file).create(APIComponent.class);
        }
        return component;
    }

    private void getProfile(int id, String code) {
        Call<Response> call = component.getProfile(id, code);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });
    }

    private void getFullProfile(String code) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("id", "58796");
        params.put("token", "FH5868536HFR45CF452HFF");
        Call<Response> call = component.getFullProfile(code, params);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });
    }

    private void updateProfile(ProfileRequest request) {
        Call<Response> call = component.updateProfile(request);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });
    }

    private void updateUser(int id, String name, String address) {
        Call<Response> call = component.updateUser(id, name,address);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });
    }

    private void updateProfilePicture(String id, String image_path) {

        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), id);

        File file = new File(image_path);
        RequestBody reqFile = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), reqFile);

        //RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        //MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), reqFile);

        Call<Response> call = component.updateProfilePicture(userId, body);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });
    }


}
