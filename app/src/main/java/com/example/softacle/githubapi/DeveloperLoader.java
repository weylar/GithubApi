package com.example.softacle.githubapi;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;


public class DeveloperLoader extends android.support.v4.content.AsyncTaskLoader<ArrayList<Developer>> {

    //Storing the github url from the main activity to github url variable
    URL GITHUB_URL = MainActivity.Github_REQUEST_URL;

    //to hold the list of already fetched items, so there wouldnt be the need to query anotertime
    public static ArrayList<Developer> fetched_developers = null;


    public DeveloperLoader(Context context) {
        super(context);

    }

    @Override
    public ArrayList<Developer> loadInBackground() {

        if (fetched_developers == null) {
            // Perform HTTP request to the URL and receive a JSON response back
            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(GITHUB_URL);
            } catch (IOException e) {
                // TODO Handle the IOException
            }


            // Extract relevant fields from the JSON response and create an {@link Event} object
            ArrayList<Developer> developers = extractDeveloper(jsonResponse);

            return developers;
        } else {
            return fetched_developers;
        }
    }

    // This is required to tell the loader to start doing the background load
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    //Make an HTTP request to the given URL and return a String as the response.
    private String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            jsonResponse = readFromStream(inputStream);
        } catch (IOException e) {
            // TODO: Handle the exception
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /*
//return a list of{@link Developer} object that has been built up from
//parsing a json response
*/
    public ArrayList<Developer> extractDeveloper(String jsonResponse) {

        //create an empty arraylist that we can start adding earthquake to
        ArrayList<Developer> developers = new ArrayList<>();

        /* try to parse the sample response, if there is an erroir
           //in the way it is array exception should be trhrown
         */
        try {
            //build up array of developers with the corresponding data
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            JSONArray developerArray = baseJsonResponse.getJSONArray("items");

            for (int i = 0; i < developerArray.length(); i++) {
                JSONObject currentDeveloper = developerArray.getJSONObject(i);

                //JSONObject properties = currentDeveloper.getJSONObject("properties");
                String username = currentDeveloper.getString("login");
                String photo = currentDeveloper.getString("avatar_url");
                String profile_url = currentDeveloper.getString("html_url");
                Developer developer = new Developer(username, photo, profile_url);
                developers.add(developer);

            }

        } catch (JSONException e) {
        }

        fetched_developers = developers;

        return developers;
    }


}



