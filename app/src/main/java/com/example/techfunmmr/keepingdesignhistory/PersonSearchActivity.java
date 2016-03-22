package com.example.techfunmmr.keepingdesignhistory;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.techfunmmr.keepingdesignhistory.constant.CommonConstant;
import com.example.techfunmmr.keepingdesignhistory.dto.Person;
import com.example.techfunmmr.keepingdesignhistory.service.PersonService;
import com.example.techfunmmr.keepingdesignhistory.service.PersonServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class PersonSearchActivity extends AppCompatActivity {

    ListView personSearchListView;

    private List<Person> personList;

    private PersonService personService;

    //MenuItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_search);

        registerUis();
        //item.expandActionView();

        setListViewEvent();
    }

    private void setListViewEvent() {
        personSearchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String personId = ((TextView) view.findViewById(R.id.ac_person_id)).getText().toString();
                Intent intent = new Intent(PersonSearchActivity.this, PersonDetailActivity.class);
                intent.putExtra(CommonConstant.EXTRA_MESSAGE, personId);
                startActivity(intent);
            }
        });
    }

    private void registerUis() {
        personSearchListView = (ListView) findViewById(R.id.person_search_list);
        //item = (MenuItem) findViewById(R.id.search);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        MenuItem item = menu.findItem(R.id.search);

        item.expandActionView();

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) item.getActionView();//menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        // Getting personInformation.
        getPersonList();

        PersonAcSearchAdapter acSearchAdapter = new PersonAcSearchAdapter(this, personList);

        personSearchListView.setAdapter(acSearchAdapter);

        SearchView.OnQueryTextListener textListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Person> resList = getPersonMatches(newText);
                PersonAcSearchAdapter acSearchAdapter = new PersonAcSearchAdapter(PersonSearchActivity.this, resList);
                personSearchListView.setAdapter(acSearchAdapter);

                return true;
            }


        };

        searchView.setOnQueryTextListener(textListener);

        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return false;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Intent intent = new Intent(PersonSearchActivity.this, MainActivity.class);
                startActivity(intent);
                return false;
            }
        });

        return true;
    }

    private List<Person> getPersonMatches(String name) {
        List<Person> resList = new ArrayList<Person>();
        name = "(.*)" + name + "(.*)";
        for(Person person : personList) {
            if (person.getPersonName().toLowerCase().matches(name.toLowerCase())) {
                resList.add(person);
            }
        }
        return resList;
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
                //holder.cardView = (CardView) vi.findViewById(R.id.person_card);
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
}
