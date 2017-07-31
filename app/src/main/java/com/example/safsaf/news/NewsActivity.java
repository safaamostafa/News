package com.example.safsaf.news;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {

    private static final String LOG_TAG = NewsActivity.class.getName();

    /**
     * URL for new data from the guardianapis dataset
     */
    private static final String GuardianApis_REQUEST_URL =
            "https://content.guardianapis.com/search?api-key=2eba40b9-74f4-4e2c-b54e-f1254228dfd0";

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

        // Start the AsyncTask to fetch the new data
        NewAsyncTask task = new NewAsyncTask();
        task.execute(GuardianApis_REQUEST_URL);
    }


    /**
     * {@link AsyncTask} to perform the network request on a background thread, and then
     * update the UI with the list of news in the response.
     * <p>
     * AsyncTask has three generic parameters: the input type, a type used for progress updates, and
     * an output type. Our task will take a String URL, and return a New. We won't do
     * progress updates, so the second generic is just Void.
     * <p>
     * We'll only override two of the methods of AsyncTask: doInBackground() and onPostExecute().
     * The doInBackground() method runs on a background thread, so it can run long-running code
     * (like network activity), without interfering with the responsiveness of the app.
     * Then onPostExecute() is passed the result of doInBackground() method, but runs on the
     * UI thread, so it can use the produced data to update the UI.
     */
    private class NewAsyncTask extends AsyncTask<String, Void, List<New>> {

        /**
         * This method runs on a background thread and performs the network request.
         * We should not update the UI from a background thread, so we return a list of
         * {@link New}s as the result.
         */
        @Override
        protected List<New> doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<New> result = QueryUtils.fetchNewData(urls[0]);
            return result;
        }

        /**
         * This method runs on the main UI thread after the background work has been
         * completed. This method receives as input, the return value from the doInBackground()
         * method. First we clear out the adapter, to get rid of new data from a previous
         * query to google. Then we update the adapter with the new list of news,
         * which will trigger the ListView to re-populate its list items.
         */
        @Override
        protected void onPostExecute(List<New> data) {
            // Clear the adapter of previous new data
            mAdapter.clear();

            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
            }
        }

    }
}
