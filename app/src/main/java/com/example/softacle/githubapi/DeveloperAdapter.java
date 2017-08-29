package com.example.softacle.githubapi;


import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import com.bumptech.glide.*;



public class DeveloperAdapter extends ArrayAdapter<Developer> {




    public DeveloperAdapter(Activity context, List<Developer> developers) {

        // Here, I initialize the ArrayAdapter's internal storage for the context and the list.
        super(context, 0, developers);
    }

    //Handles the inflation of the list_item and assign apprioprate values from the developers class
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Gets the Word object from the ArrayAdapter at the appropriate position
        Developer developer = getItem(position);

        // Adapters recycle views to AdapterViews.
        // If this is a new View object we're getting, then inflate the layout.
        // If not, this view already has the layout inflated from a previous call to getView,
        // and we modify the View widgets as usual.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        //LinearLayout list_item = (LinearLayout) convertView.findViewById(R.id.list_item);
        TextView username = (TextView) convertView.findViewById(R.id.username);
        ImageView profile_photo = (ImageView) convertView.findViewById(R.id.profile_photo);
        username.setText(developer.getUsername());

        Uri baseUri = Uri.parse(developer.getImageResource());
        profile_photo.setImageURI(baseUri);

        Glide.with(getContext()).load(developer.getImageResource()).into(profile_photo);




        return convertView;

    }
}

