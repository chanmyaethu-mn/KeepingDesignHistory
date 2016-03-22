package com.example.techfunmmr.keepingdesignhistory;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.techfunmmr.keepingdesignhistory.constant.CommonConstant;
import com.example.techfunmmr.keepingdesignhistory.constant.TableInfoConstant;
import com.example.techfunmmr.keepingdesignhistory.dto.Person;
import com.example.techfunmmr.keepingdesignhistory.service.PersonService;
import com.example.techfunmmr.keepingdesignhistory.service.PersonServiceImpl;
import com.example.techfunmmr.keepingdesignhistory.util.BitmapUtils;
import com.example.techfunmmr.keepingdesignhistory.util.FetchPath;

import java.io.File;

public class PersonRegisterActivity extends AppCompatActivity {

    private EditText personName;

    private EditText personPhoneNo;

    private EditText personEmail;

    private EditText personAddress;

    private Button personRegisterBtn;

    private PersonService personService;

    private ImageView personPhoto;

    private Uri mImageCaptureUri;

    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_register);

        registerUIs();

        registerEvents();

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void registerUIs() {
        personName = (EditText) findViewById(R.id.person_name_et);
        personPhoneNo = (EditText) findViewById(R.id.person_phone_no_et);
        personEmail = (EditText) findViewById(R.id.person_email_et);
        personAddress = (EditText) findViewById(R.id.person_address_et);
        personPhoto = (ImageView) findViewById(R.id.person_photo);

        personRegisterBtn = (Button) findViewById(R.id.person_register_btn);
    }

    private void registerEvents() {
        setListenerPersonRegisterBtn();
        setListenerPersonPhoto();

    }

    private void setListenerPersonRegisterBtn() {
        personRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerPerson();
            }
        });
    }

    private void setListenerPersonPhoto() {
        personPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooserDialog();
            }
        });
    }

    private void openImageChooserDialog() {
        final String[] items = new String[]{"From Camera", "From Storage"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, items);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Select Image");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File file = new File(Environment.getExternalStorageDirectory(),
                            "tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
                    mImageCaptureUri = Uri.fromFile(file);

                    try {
                        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                        intent.putExtra("return-data", true);

                        startActivityForResult(intent, CommonConstant.PICK_FROM_CAMERA);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    dialog.cancel();
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK);

                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);

                    startActivityForResult(intent, CommonConstant.PICK_FROM_FILE);

                }
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;

        String path = "";

        if (requestCode == CommonConstant.PICK_FROM_FILE) {

            mImageCaptureUri = data.getData();
            if (mImageCaptureUri != null) {
                path = FetchPath.getPath(this, mImageCaptureUri);
            }

            if (path != null)
                bitmap = BitmapFactory.decodeFile(path);
        } else {
            path = mImageCaptureUri.getPath();
            bitmap = BitmapFactory.decodeFile(path);
        }

        personPhoto.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 225, 225, Boolean.FALSE));
    }

    private void registerPerson() {
        personService = new PersonServiceImpl(this);
        personService.addPerson(getPerson());

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private Person getPerson() {
        Person person = new Person();
        person.setPersonName(personName.getText().toString());
        person.setPersonPhoneNo(personPhoneNo.getText().toString());
        person.setPersonEmail(personEmail.getText().toString());
        person.setPersonAddress(personAddress.getText().toString());

        if (bitmap != null) {
            //person.setPersonPhoto(bitmap);
            person.setPersonPhoto(((BitmapDrawable)personPhoto.getDrawable()).getBitmap());
        } else {
            person.setPersonPhoto(BitmapFactory.decodeResource(getResources(), R.mipmap.default_profile));
        }
        return person;
    }
}
