package com.poc.infinitescrolllist;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class APICalls {
    public static JSONObject get(String REQUEST_URL) {
        JSONObject jsonObject = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(REQUEST_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(30000);
            connection.setRequestMethod("GET");
            connection.connect();
            StringBuilder sb;
            int statusCode = connection.getResponseCode();
            if (statusCode == 200) {
                sb = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                jsonObject = new JSONObject(sb.toString());
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonObject;
    }
}
