package com.example.techfunmmr.keepingdesignhistory.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.techfunmmr.keepingdesignhistory.PersonDetailActivity;
import com.example.techfunmmr.keepingdesignhistory.R;
import com.example.techfunmmr.keepingdesignhistory.constant.CommonConstant;
import com.example.techfunmmr.keepingdesignhistory.dto.Person;
import com.example.techfunmmr.keepingdesignhistory.util.BitmapUtils;
import com.example.techfunmmr.keepingdesignhistory.viewholder.PersonViewHolder;

import java.util.List;

/**
 * Created by techfunmmr on 2/15/2016.
 */
public class PersonAdapter extends RecyclerView.Adapter<PersonViewHolder> {

    List<Person> personList;
    Context context;

    public PersonAdapter(List<Person> personList, Context context) {
        this.personList = personList;
        this.context = context;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View personCardViewRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_card_view_row, parent, false);
        PersonViewHolder personViewHolder = new PersonViewHolder(personCardViewRow);
        return personViewHolder;
    }

    @Override
    public void onBindViewHolder(final PersonViewHolder holder, int position) {

        Person person = personList.get(position);
//        if ( null != person.getPersonPhoto()) {
//            holder.getPersonPhoto().setImageBitmap(person.getPersonPhoto());
//        } else {
//            holder.getPersonPhoto().setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.default_profile));
//        }

        holder.getPersonPhoto().setImageBitmap(person.getPersonPhoto());
        holder.getPersonName().setText(person.getPersonName());
        holder.getPersonPhoneNo().setText(person.getPersonPhoneNo());
        holder.getPersonEmail().setText(person.getPersonEmail());
        holder.getPersonAddress().setText(person.getPersonAddress());
        holder.getPersonId().setText(String.valueOf(person.getPersonId()));

        holder.getPersonCard().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                String personId = holder.getPersonId().getText().toString();
                Intent intent = new Intent(context, PersonDetailActivity.class);
                intent.putExtra(CommonConstant.EXTRA_MESSAGE, personId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }
}
