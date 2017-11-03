package com.example.android.miwak1;

import android.app.Activity;
import android.media.MediaPlayer;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.miwak1.Dubs;
import com.example.android.miwak1.R;

import java.util.ArrayList;

/**
 * Created by Aaron on 10/6/2017.
 */
//ArrayAdapter only processes ViewGroups with one TextView.
//Create a subclass of ArrayAdapter when processing ViewGroups with complex layouts.
public class DubsAdapter extends ArrayAdapter<Dubs> {
    //The ArrayAdapter has no default constructor. Your custom ArrayAdapter requires parameters, and
    //you must use the super keyword to describe the parameters' relationship to the superclass's
    //parameters.

    private int mResourceID;
    private int branch03;

    public DubsAdapter(Activity context, ArrayList<Dubs> dubsList, int colorResourceID) {

        super(context, 0, dubsList);
        mResourceID = colorResourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        //Assigning the ArrayList data to the correct position.
        Dubs currentWord = getItem(position);

        TextView nameTextView = (TextView) listItemView.findViewById(R.id.text1);
        nameTextView.setText(currentWord.getEng());

        TextView numberTextView = (TextView) listItemView.findViewById(R.id.text2);
        numberTextView.setText(currentWord.getMiw());


        ImageView iconView = (ImageView) listItemView.findViewById(R.id.image);
        if(currentWord.hasImage()) {
            iconView.setImageResource(currentWord.getImg());
            iconView.setVisibility(View.VISIBLE);
        } else {
            iconView.setVisibility(View.GONE);
        }

        View textContainer = listItemView.findViewById(R.id.text_container);
        int color = ContextCompat.getColor(getContext(), mResourceID);
        textContainer.setBackgroundColor(color);







        return listItemView;
    }
}

