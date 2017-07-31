package com.example.safsaf.news;

/**
 * Created by Safsaf on 7/22/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * An {@link NewAdapter} knows how to create a list item layout for each new
 * in the data source (a list of {@link New} objects).
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */
public class NewAdapter extends ArrayAdapter<New> {

    /**
     * Constructs a new {@link NewAdapter}.
     *
     * @param context of the app
     * @param news    is the list of news, which is the data source of the adapter
     */
    public NewAdapter(Context context, List<New> news) {
        super(context, 0, news);
    }

    /**
     * Returns a list item view that displays information about the new at the given position
     * in the list of news.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.new_list_item, parent, false);
        }

        // Find the new at the given position in the list of news
        New currentNew = getItem(position);

        // Find the TextView with view ID sectionName
        TextView sectionNameView = (TextView) listItemView.findViewById(R.id.sectionName);
        // Display the sectionName of the current new in that TextView
        sectionNameView.setText(currentNew.getSectionName());

        // Find the TextView with view ID webPublicationDate
        TextView webPublicationDateView = (TextView) listItemView.findViewById(R.id.webPublicationDate);
        // Display the webPublicationDate of the current new in that TextView
        webPublicationDateView.setText(currentNew.getWebPublicationDate());

        // Find the TextView with view ID webTitle
        TextView webTitleView = (TextView) listItemView.findViewById(R.id.webTitle);
        // Display the webTitle of the current new in that TextView
        webTitleView.setText(currentNew.getWebTitle());

        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }
}