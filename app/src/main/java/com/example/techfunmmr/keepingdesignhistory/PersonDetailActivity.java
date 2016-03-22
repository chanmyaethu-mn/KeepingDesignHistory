package com.example.techfunmmr.keepingdesignhistory;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.techfunmmr.keepingdesignhistory.constant.CommonConstant;
import com.example.techfunmmr.keepingdesignhistory.dto.Person;
import com.example.techfunmmr.keepingdesignhistory.service.PersonService;
import com.example.techfunmmr.keepingdesignhistory.service.PersonServiceImpl;

public class PersonDetailActivity extends AppCompatActivity {

    private ImageView personPhoto;

    private EditText personName;

    private EditText personPhoneNo;

    private EditText personEmail;

    private EditText personAddress;

    private Button personUpdateBtn;

    private Button personDeleteBtn;

    private PersonService personService;

    private TextView personIdTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);

        Intent intent = getIntent();
        String personId = intent.getStringExtra(CommonConstant.EXTRA_MESSAGE);

        registerUIs();

        Person person = getPersonWithId(Integer.parseInt(personId));
        bindUIInitValues(person);

        registerEvents();

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void registerUIs() {
        personPhoto = (ImageView) findViewById(R.id.person_photo);
        personName = (EditText) findViewById(R.id.person_name_et);
        personPhoneNo = (EditText) findViewById(R.id.person_phone_no_et);
        personEmail = (EditText) findViewById(R.id.person_email_et);
        personAddress = (EditText) findViewById(R.id.person_address_et);
        personIdTextView = (TextView) findViewById(R.id.person_id);

        personUpdateBtn = (Button) findViewById(R.id.person_update_btn);
        personDeleteBtn = (Button) findViewById(R.id.person_delete_btn);
    }

    private void registerEvents() {
        setListenerPersonUpdateBtn();
        setListenerPersonDeleteBtn();
    }

    private void setListenerPersonUpdateBtn() {
        personUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePerson();
            }
        });
    }

    private void setListenerPersonDeleteBtn() {
        personDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePerson();
            }
        });
    }

    private void updatePerson() {
        personService = new PersonServiceImpl(this);
        int updateResult = personService.updatePerson(getPersonFromUi());
        if (updateResult > 0) {
            Toast.makeText(this, R.string.update_success, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.update_failed, Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void deletePerson() {
        String personId = personIdTextView.getText().toString();
        personService = new PersonServiceImpl(this);
        int deleteResult = personService.deletePerson(personId);
        if (deleteResult > 0) {
            Toast.makeText(this, R.string.delete_success, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.delete_failed, Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private Person getPersonFromUi() {
        Person person = new Person();
        person.setPersonPhoto(((BitmapDrawable)personPhoto.getDrawable()).getBitmap());
        person.setPersonId(Integer.valueOf(personIdTextView.getText().toString()));
        person.setPersonName(personName.getText().toString());
        person.setPersonPhoneNo(personPhoneNo.getText().toString());
        person.setPersonEmail(personEmail.getText().toString());
        person.setPersonAddress(personAddress.getText().toString());
        return person;
    }

    private Person getPersonWithId(int personId) {
        personService = new PersonServiceImpl(this);
        return personService.getPersonWithId(personId);
    }

    private void bindUIInitValues(Person person) {
        personName.setText(person.getPersonName());
        personPhoneNo.setText(person.getPersonPhoneNo());
        personEmail.setText(person.getPersonEmail());
        personAddress.setText(person.getPersonAddress());
        personIdTextView.setText(String.valueOf(person.getPersonId()));
        personPhoto.setImageBitmap(person.getPersonPhoto());
    }
}
