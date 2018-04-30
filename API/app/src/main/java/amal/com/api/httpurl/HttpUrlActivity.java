package amal.com.api.httpurl;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import amal.com.api.R;

public class HttpUrlActivity extends AppCompatActivity {

    ProgressDialog mProgressDialog;
    String BASE_URL = "http://192.168.1.33:8080/sample/rest";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_url);

        //downloadFile("http://unec.edu.az/application/uploads/2014/12/pdf-sample.pdf");


        //Map<String, String> params = new LinkedHashMap<>();
        //params.put("id", "58796");
        //params.put("token", "FH5868536HFR45CF452HFF");
        //getFullProfile("91", params);

        /*raw data */
        //updateProfile();


        /*form-urlencoded */
        //Map<String, String> params = new HashMap<>();
        //params.put("userId", "56325");
        //params.put("name", "Amal");
        //params.put("address", "Addr");
        //updateUser(params);

        /*form-data file */
        //updateProfilePicture();

        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();
    }

    private void updateUser(Map<String, String> params) {

        try{
            StringBuilder result = new StringBuilder();
            boolean first = true;
            for(Map.Entry<String, String> entry : params.entrySet()){
                if (first)
                    first = false;
                else
                    result.append("&");
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }


            byte[] postData = result.toString().getBytes( StandardCharsets.UTF_8 );
            int postDataLength = postData.length;
            String request = BASE_URL + "/payment/updateProfileForm";
            URL url = new URL(request);
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength ));
            conn.setUseCaches(false);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.write(postData);

            conn.connect();
            int response = conn.getResponseCode();

            InputStream is = conn.getInputStream();

            StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            reader.close();

            Log.e("response" , sb.toString());
        }catch (IOException e){
            e.printStackTrace();
        }

    }


    private void updateProfilePicture() {
        String userID = "563348";
        String LINE_FEED = "\r\n";
        String boundary =  "*****";

        try {
            URL url = new URL(BASE_URL + "/payment/updateProfilePicture");
            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setUseCaches(false);
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setRequestMethod("POST");
            httpUrlConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);


            PrintWriter writer =  new PrintWriter(new OutputStreamWriter(httpUrlConnection.getOutputStream(),
                    "UTF-8"), true);
            writer.append("--" + boundary).append(LINE_FEED);
            writer.append("Content-Disposition: form-data; name=\"" + "id" + "\"")
                    .append(LINE_FEED);
            writer.append("Content-Type: text/plain; charset=" + "UTF-8").append(
                    LINE_FEED);
            writer.append(LINE_FEED);
            writer.append(userID).append(LINE_FEED);
            writer.flush();



            File uploadFile = new File("/storage/emulated/0/Download/Selection_002.png");

            String fileName = uploadFile.getName();
            writer.append("--" + boundary).append(LINE_FEED);
            writer.append("Content-Disposition: form-data; name=\"" + "file"
                            + "\"; filename=\"" + fileName + "\"")
                    .append(LINE_FEED);

            writer.append("Content-Type: "
                    + URLConnection.guessContentTypeFromName(fileName))
                    .append(LINE_FEED);
            writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
            writer.append(LINE_FEED);
            writer.flush();

            OutputStream outputStream = httpUrlConnection.getOutputStream();

            byte[] b = new byte[(int) uploadFile.length()];
            FileInputStream fileInputStream = new FileInputStream(uploadFile);
            fileInputStream.read(b);
            fileInputStream.close();
            outputStream.write(b);


            outputStream.flush();

            writer.append(LINE_FEED);
            writer.flush();

            writer.append(LINE_FEED).flush();
            writer.append("--" + boundary + "--").append(LINE_FEED);
            writer.close();


            Log.e("response code" , ""+httpUrlConnection.getResponseCode());

            InputStream responseStream = new BufferedInputStream(httpUrlConnection.getInputStream());

            BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(responseStream));

            String line;
            StringBuilder stringBuilder = new StringBuilder();

            while ((line = responseStreamReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            responseStreamReader.close();

            Log.e("response" , stringBuilder.toString());
            responseStream.close();
            httpUrlConnection.disconnect();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void updateProfile() {
        try {
            String url = BASE_URL + "/payment/updateProfile";
            URL object = new URL(url);

            HttpURLConnection con = (HttpURLConnection) object.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestMethod("POST");

            JSONObject parent = new JSONObject();

            parent.put("userId", "56324");
            parent.put("name", "amal");
            parent.put("address", "addr");

            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(parent.toString());
            wr.flush();


            StringBuilder sb = new StringBuilder();
            int HttpResult = con.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), "utf-8"));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                Log.e("response" , sb.toString());
            } else {
                System.out.println(con.getResponseMessage());
            }

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getFullProfile(String code, Map<String, String> params) {

        String url = BASE_URL + "/payment/fullProfile" + convertParamsToUrlValues(params);
        Log.e("URL" , url);

        InputStream is = null;
        HttpURLConnection conn = null;

        try {
            URL urlPath = new URL(url);

            conn = (HttpURLConnection) urlPath.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Country-Code", "91");

            conn.connect();
            int response = conn.getResponseCode();

            is = conn.getInputStream();

            StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            reader.close();

            Log.e("response" , sb.toString());

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            try {
                if (is != null) {
                    is.close();
                    conn.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    private String convertParamsToUrlValues(Map<String, String> params){
        Set<Map.Entry<String, String>> entrySet = params.entrySet();
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : entrySet){
            if(sb.length() > 0)
                sb.append("&");
            else
                sb.append("?");

            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
        }

        return sb.toString();
    }


    private void downloadFile(String url) {
        mProgressDialog = new ProgressDialog(HttpUrlActivity.this);
        mProgressDialog.setMessage("Downloading");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);

        final DownloadTask downloadTask = new DownloadTask(HttpUrlActivity.this);
        downloadTask.execute(url);

        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                downloadTask.cancel(true);
            }
        });

    }

    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "failed";
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                File file = new File(Environment.getExternalStorageDirectory() + File.separator + "download_file_path.ext");
                input = connection.getInputStream();
                output = new FileOutputStream(file);

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return "cancelled";
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));

                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                    ignored.printStackTrace();
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            mProgressDialog.dismiss();
            if (result != null)
                Toast.makeText(context,"Download error: "+result, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(context,"File downloaded", Toast.LENGTH_SHORT).show();
        }
    }
}
