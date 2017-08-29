package com.example.softacle.githubapi;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Developer>> {

    String Github_url_string = "https://api.github.com/search/users?q=location:lagos+language:java";
    static URL Github_REQUEST_URL;

    static Developer current_developer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //This is to check if phone is currently connected to the internet or not
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()) {
            //network is available go get the data
            getSupportLoaderManager().initLoader(1, null, MainActivity.this);

        }
        else {
            //if there is already fetched_developers
            if (DeveloperLoader.fetched_developers == null) {

                //hide progress bar
                ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
                progressBar.setVisibility(View.GONE);
                //internet is not available; make it known
                TextView empty_view = (TextView) findViewById(R.id.empty_view);
                //make it visible
                empty_view.setVisibility(View.VISIBLE);
                empty_view.setText("No internet connection");
                ListView listView = (ListView) findViewById(R.id.list_quake);
                listView.setEmptyView(findViewById(R.id.empty_view));

            }
        }


    }

    @Override
    public Loader<ArrayList<Developer>> onCreateLoader(int id, Bundle args) {

        //Parsing string to uri
        Uri baseUri = Uri.parse(Github_url_string);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        //Parsing uri string to URL
        try {
            Github_REQUEST_URL = new URL(uriBuilder.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();

        }

        return new DeveloperLoader(this);

    }

    //Everything that should occur after the loading should appear in this onLoadFinished method
    @Override
    public void onLoadFinished(Loader<ArrayList<Developer>> loader, ArrayList<Developer> data) {
        //Hide the progressbar
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        updateUi(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Developer>> loader) {
        updateUi(new ArrayList<Developer>());
    }

    //This is the update UI method
    private void updateUi(final ArrayList<Developer> developers) {
        ListView listView = (ListView) findViewById(R.id.list_quake);
        DeveloperAdapter developerAdapter = new DeveloperAdapter(this, developers);
        listView.setAdapter(developerAdapter);

        //What happens when the list item is clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //use the position of the item clicked on the listview to
                // get the corresponding url from the developer list
                Developer developer = developers.get(position);
                current_developer = developer;
                Intent developerIntent = new Intent(MainActivity.this, ShowItemActivity.class);
                startActivity(developerIntent);
            }
        });

        //show empty if result is empty
        if (developerAdapter.isEmpty() || developerAdapter == null) {
            TextView empty_view = (TextView) findViewById(R.id.empty_view);
            //make it visible
            empty_view.setVisibility(View.VISIBLE);
            listView.setEmptyView(findViewById(R.id.empty_view));
        }


    }


}

