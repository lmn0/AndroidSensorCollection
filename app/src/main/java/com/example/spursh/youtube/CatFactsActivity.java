package com.example.spursh.youtube;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by spurs on 4/28/2016.
 */
class CatFactsActivity extends AsyncTask<Object, Void, JSONObject> {

    @Override
    protected JSONObject doInBackground(Object... params) {
        JSONObject jsonResponse = null;
        int responseCode;

        try {
            URL requestUrl = new URL("http://localhost:8083/api/mongoInsert");
            HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestProperty("Accept-Encoding", "identity");
            connection.connect();
            responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {

            } else {
                InputStream stream = connection.getInputStream();
                Reader reader = new InputStreamReader(stream);
                int contentLength = connection.getContentLength();
                if (contentLength > 0) {
                    char[] buffer = new char[contentLength ];
                    reader.read(buffer);

                    jsonResponse = new JSONObject(String.valueOf(buffer));
                } else {
                    String jsonData = isToString(stream);
                    jsonResponse = new JSONObject(jsonData);
                }


                connection.disconnect();
            }

        }
        catch (Exception e) {

        }


        return jsonResponse;
    }

    protected void onPostExecute(JSONObject data) {
        JSONObject mFactJSON = data;
    }

    protected String isToString(InputStream is) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[65536];

        try {
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            buffer.flush();
        } catch (IOException e) {

        }

        return buffer.toString();
    }
}
