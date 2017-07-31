package com.example.safsaf.news;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<New>> {

    private static final String LOG_TAG = NewsActivity.class.getName();

    /**
     * URL for new data from the guardianapis dataset
     */
    private static final String GuardianApis_REQUEST_URL =
            "https://content.guardianapis.com/search?api-key=2eba40b9-74f4-4e2c-b54e-f1254228dfd0";

    /**
          * Constant value for the new loader ID. We can choose any integer.
          * This really only comes into play if you're using multiple loaders.
          */
    private static final int NEW_LOADER_ID = 1;
    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;

    /**
     * Adapter for the list of news
     */
    private NewAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);


        // Find a reference to the {@link ListView} in the layout
        ListView newListView = (ListView) findViewById(R.id.list);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
                newListView.setEmptyView(mEmptyStateTextView);

        // Create a new adapter that takes an empty list of news as input
        mAdapter = new NewAdapter(this, new ArrayList<New>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        newListView.setAdapter(mAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected new.
        newListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current new that was clicked on
                New currentNew = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newUri = Uri.parse(currentNew.getUrl());

                // Create a new intent to view the new URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });




        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getSupportLoaderManager();
        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(NEW_LOADER_ID, null, this);





    }

    @Override
    public Loader<List<New>> onCreateLoader(int i, Bundle bundle) {
                // Create a new loader for the given URL
        return new NewLoader(this,GuardianApis_REQUEST_URL);
            }

    @Override
   public void onLoadFinished(Loader<List<New>> loader, List<New> news) {

        // Set empty state text to display "No earthquakes found."
                mEmptyStateTextView.setText(R.string.no_News);
        // Clear the adapter of previous earthquake data
        mAdapter.clear();
        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (news != null && !news.isEmpty()) {
                        mAdapter.addAll(news);
        }
            }

    @Override
    public void onLoaderReset(Loader<List<New>> loader) {
// Loader reset, so we can clear out our existing data.
                mAdapter.clear();
    }







}
