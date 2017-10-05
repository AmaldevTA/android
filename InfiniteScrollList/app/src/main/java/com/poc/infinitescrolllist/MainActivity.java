package com.poc.infinitescrolllist;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Activity activity;
    private Toolbar toolbar;
    private EditText searchBox;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private List<Artist> artistsList;

    private boolean loading = true;
    private int scrollOffset = 10;
    private String nextUrl = null;
    private int callCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = MainActivity.this;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        searchBox = (EditText) findViewById(R.id.search_box);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);

        artistsList = new ArrayList<>();
        adapter = new ArtistAdapter(activity, artistsList);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                try{
                    int visibleItemCount = layoutManager.getChildCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
                    int totalItemCount = layoutManager.getItemCount();

                    if (loading)
                    {
                        if ( (visibleItemCount + pastVisibleItems) >= (totalItemCount - scrollOffset))
                        {
                            loading = false;
                            if(nextUrl != null && !nextUrl.equals("null")){
                                new LoadVideoAsyncTask().execute(nextUrl);
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_search){
            callCount = 0;
            artistsList.clear();
            adapter.notifyDataSetChanged();
            nextUrl = null;
            new LoadVideoAsyncTask().execute("https://sample.code.com/v1/search?q="+searchBox.getText().toString().trim()+"&type=artist");
        }
        return super.onOptionsItemSelected(item);
    }

    class LoadVideoAsyncTask extends AsyncTask<String, Void, JSONObject> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(callCount == 0){
                callCount++;
                progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage("loading");
                progressDialog.show();
            }

        }

        @Override
        protected JSONObject doInBackground(String... params) {
            try {
                System.out.println(params[0]);
                return APICalls.get(params[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(JSONObject serverJSONObject) {
            super.onPostExecute(serverJSONObject);
            if(progressDialog != null && progressDialog.isShowing()){
                progressDialog.dismiss();
            }

            if (serverJSONObject != null){
                try {
                    JSONObject artists = serverJSONObject.getJSONObject("artists");
                    JSONArray items = artists.getJSONArray("items");
                    nextUrl = artists.optString("next");
                    for (int i = 0; i < items.length(); i++){
                        JSONObject artist = (JSONObject) items.get(i);
                        Artist obj = new Artist();
                        obj.setName(artist.optString("name"));
                        JSONArray images = artist.getJSONArray("images");
                        if(images.length() > 0){
                            obj.setImage(((JSONObject)images.get(images.length()-1)).optString("url"));
                        }
                        obj.setPopularity(artist.optInt("popularity"));
                        obj.setFollowers(artist.getJSONObject("followers").optInt("total"));
                        artistsList.add(obj);
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            loading = true;
        }
    }
}
