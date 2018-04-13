package amal.com.api.volley;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import amal.com.api.R;
import amal.com.api.retrofit.model.ProfileRequest;

public class VolleyActivity extends AppCompatActivity {

    static String BASE_URL = "192.168.1.33:8080/sample/rest";
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);
        queue = Volley.newRequestQueue(this);

        //getFullProfile();

        //updateUser();

        //updateProfile();
        //updateProfile2();

        updateProfilePicture();
    }

    private void updateProfilePicture() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .encodedAuthority(BASE_URL)
                .appendPath("payment")
                .appendPath("updateProfilePicture");
        String myUrl = builder.build().toString();

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, myUrl,
           new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                try {
                    String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                    Log.e("HttpClient", "success! response: " + json);

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("HttpClient", "error: " + error.toString());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", "563246");
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                File file = new File("/storage/emulated/0/Download/Selection_002.png");
                byte[] b = new byte[(int) file.length()];
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    fileInputStream.read(b);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Map<String, DataPart> params = new HashMap<>();
                params.put("file", new DataPart("Selection_002.png", b, "image/jpeg"));

                return params;
            }
        };
        queue.add(multipartRequest);
    }

    private void updateProfile2() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .encodedAuthority(BASE_URL)
                .appendPath("payment")
                .appendPath("updateProfile");
        String myUrl = builder.build().toString();


        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("userId", "56834");
            jsonBody.put("name", "Mr.Amal");
            jsonBody.put("address", "address-line");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String mRequestBody = jsonBody.toString();


        ProfileRequest profile = new ProfileRequest();
        profile.setUserId(56293);
        profile.setName("name");
        profile.setAddress("address line");
        final Gson gson = new Gson();
        final String json = gson.toJson(profile);


        StringRequest request = new StringRequest(Request.Method.POST, myUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("HttpClient", "success! response: " + response.toString());
                        amal.com.api.model.Response res = gson.fromJson(response, amal.com.api.model.Response.class);

                        //Type type = new TypeToken<List<String>>(){}.getType();
                        //List<String> contactList = gson.fromJson(response, type);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("HttpClient", "error: " + error.toString());
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                return params;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return json == null ? null : json.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    uee.printStackTrace();
                    return null;
                }
            }
        };

        queue.add(request);
    }

    private void updateProfile() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .encodedAuthority(BASE_URL)
                .appendPath("payment")
                .appendPath("updateProfile");
        String myUrl = builder.build().toString();

        HashMap<String, String> params = new HashMap<>();
        params.put("userId", "567334");
        params.put("name", "MrAmal");
        params.put("address", "address");

        JsonObjectRequest request = new JsonObjectRequest(myUrl, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("HttpClient", "success! response: " + response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("HttpClient", "error: " + error.toString());
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };

        queue.add(request);
    }

    private void updateUser() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .encodedAuthority(BASE_URL)
                .appendPath("payment")
                .appendPath("updateProfileForm");
        String myUrl = builder.build().toString();
        StringRequest sr = new StringRequest(Request.Method.POST, myUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("HttpClient", "success! response: " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("HttpClient", "error: " + error.toString());
                    }
                })
        {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("userId", "56325");
                params.put("name", "Amal");
                params.put("address", "Addr");
                return params;
            }
        };

        queue.add(sr);
    }

    private void getFullProfile() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .encodedAuthority(BASE_URL)
                .appendPath("payment")
                .appendPath("fullProfile")
                .appendQueryParameter("id", "58796")
                .appendQueryParameter("token", "FH5868536HFR45CF452HFF");
                //.fragment("section-name");
        String myUrl = builder.build().toString();

        StringRequest sr = new StringRequest(Request.Method.GET, myUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("HttpClient", "success! response: " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("HttpClient", "error: " + error.toString());
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type","application/json");
                params.put("Country-Code","91");
                return params;
            }
        };
        queue.add(sr);
    }
}
