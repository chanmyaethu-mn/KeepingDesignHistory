package com.example.techfunmmr.keepingdesignhistory;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.techfunmmr.keepingdesignhistory.adapter.PersonAdapter;
import com.example.techfunmmr.keepingdesignhistory.dto.Person;
import com.example.techfunmmr.keepingdesignhistory.service.PersonService;
import com.example.techfunmmr.keepingdesignhistory.service.PersonServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Person> personList;
    private RecyclerView personRecyclerView;
    private PersonAdapter personAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private FloatingActionButton personAddFab;

    private PersonService personService;

    private ListView personSearchAcList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Register UI in this Activity.
        registerUIs();

        // Getting personInformation.
        getPersonList();

        // Set LayoutManager to RecyclerView.
        layoutManager = new LinearLayoutManager(this);
        personRecyclerView.setLayoutManager(layoutManager);

        // Set PersonAdapter to RecyclerView.
        personAdapter = new PersonAdapter(personList, this);
        personRecyclerView.setAdapter(personAdapter);

        PersonAcSearchAdapter acSearchAdapter = new PersonAcSearchAdapter(this, personList);

        registerEvents();

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void registerUIs() {
        personRecyclerView = (RecyclerView) findViewById(R.id.person_list_rv);
        personRecyclerView.setHasFixedSize(true);

        personAddFab = (FloatingActionButton) findViewById(R.id.person_add_fab);

        //searchPersonAcTView = (AutoCompleteTextView) findViewById(R.id.person_search_ac);

        personSearchAcList = (ListView) findViewById(R.id.person_search_ac_list);
    }

    private void registerEvents() {
        setListenerPersonRegisterFabBtn();
    }
    private void setListenerPersonRegisterFabBtn() {
        personAddFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PersonRegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getPersonList() {
        personList = new ArrayList<Person>();
        personService = new PersonServiceImpl(this);
        personList = personService.getPersonList();
    }

    class PersonAcSearchAdapter extends ArrayAdapter {
        List<Person> myData;
        LayoutInflater inflater;

        public PersonAcSearchAdapter(Context context, List<Person> myData) {
            super(context, R.layout.person_search_ac_row, myData);
            inflater = LayoutInflater.from(context);
            this.myData = myData;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View vi = convertView;
            PersonSearchAcViewHolder holder = null;

            if (vi == null) {
                vi = inflater.inflate(R.layout.person_search_ac_row, parent, false);
                holder = new PersonSearchAcViewHolder();
                holder.personImageView = (ImageView) vi.findViewById(R.id.ac_person_photo);
                holder.personNameTView = (TextView) vi.findViewById(R.id.ac_person_name);
                holder.personIdTView = (TextView) vi.findViewById(R.id.ac_person_id);
                vi.setTag(holder);
            } else {
                holder = (PersonSearchAcViewHolder) vi.getTag();
            }
            Person person = myData.get(position);
            holder.personImageView.setImageBitmap(person.getPersonPhoto());
            holder.personNameTView.setText(person.getPersonName());
            holder.personIdTView.setText(String.valueOf(person.getPersonId()));

            return vi;
        }
    }

    static class PersonSearchAcViewHolder {
        ImageView personImageView;
        TextView personNameTView;
        TextView personIdTView;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        SearchView.OnQueryTextListener textListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(MainActivity.this, SearchResultsActivity.class);
                intent.putExtra(SearchManager.QUERY, query);
                intent.setAction(Intent.ACTION_SEARCH);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Intent intent = new Intent(MainActivity.this, PersonSearchActivity.class);
                intent.putExtra(SearchManager.QUERY, newText);
                startActivity(intent);
                return true;
            }


        };

        searchView.setOnQueryTextListener(textListener);

        return true;
    }
}
