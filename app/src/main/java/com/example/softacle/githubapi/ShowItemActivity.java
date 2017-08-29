package com.example.softacle.githubapi;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class
ShowItemActivity extends AppCompatActivity {

    Developer developer = MainActivity.current_developer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_item);

        ImageView profile_photo = (ImageView) findViewById(R.id.show_photo);
        TextView username = (TextView) findViewById(R.id.show_username);
        TextView url = (TextView) findViewById(R.id.show_url);

        //Setting the text to appropriate resources
        url.setText(developer.getProfileUrl());
        username.setText(developer.getUsername());

        Glide.with(this).load(developer.getImageResource()).into(profile_photo);


    }

    public void shareProfileButton(View view){
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setText("checkout this awesome developer @"+developer.getUsername()+", "+developer.mProfileUrl)
                .setChooserTitle("Share Developer")
                .setSubject("Lagos Java Developer")
                .setType("text/plain")
                .createChooserIntent();

        //Check if intent is available to avoid app crashes
        if(shareIntent.resolveActivity(getPackageManager()) != null){
            startActivity(shareIntent);
        }
    }

    public  void launchInBrowser(View view){
        String url = developer.getProfileUrl();

        //parse te url string to uri using Uri.parse
        Uri webpage = Uri.parse(url);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, webpage);

        //Check if intent is available to avoid app crashes
        if(browserIntent.resolveActivity(getPackageManager()) != null){
            startActivity(browserIntent);
        }
    }

}
