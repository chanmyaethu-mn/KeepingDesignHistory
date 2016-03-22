package com.example.techfunmmr.keepingdesignhistory;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SearchResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    //DatabaseTable db = new DatabaseTable(this);

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Cursor c = null; //db.getWordMatches(query, null);
            if (null != c) {
                System.out.println("Cursor Count >>>> " + c.getCount());
                System.out.println("******############### "+c.getString(c.getColumnIndex("WORD")));
            }
            //process Cursor and display results
        }
    }
}
