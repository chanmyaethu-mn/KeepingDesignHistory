package com.example.techfunmmr.keepingdesignhistory.viewholder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.techfunmmr.keepingdesignhistory.R;

import org.w3c.dom.Text;

/**
 * Created by techfunmmr on 2/15/2016.
 */
public class PersonViewHolder extends RecyclerView.ViewHolder {

    private CardView personCard;

    private ImageView personPhoto;

    private TextView personName;

    private TextView personPhoneNo;

    private TextView personEmail;

    private TextView personAddress;

    private TextView personId;


    public PersonViewHolder(View itemView) {
        super(itemView);
        personCard = (CardView) itemView.findViewById(R.id.person_card);
        personPhoto = (ImageView) itemView.findViewById(R.id.person_photo);
        personName = (TextView) itemView.findViewById(R.id.person_name);
        personPhoneNo = (TextView) itemView.findViewById(R.id.person_phone_no);
        personEmail = (TextView) itemView.findViewById(R.id.person_email);
        personAddress = (TextView) itemView.findViewById(R.id.person_address);
        personId = (TextView) itemView.findViewById(R.id.person_id);
    }

    public CardView getPersonCard() {
        return personCard;
    }

    public void setPersonCard(CardView personCard) {
        this.personCard = personCard;
    }

    public ImageView getPersonPhoto() {
        return personPhoto;
    }

    public void setPersonPhoto(ImageView personPhoto) {
        this.personPhoto = personPhoto;
    }

    public TextView getPersonName() {
        return personName;
    }

    public void setPersonName(TextView personName) {
        this.personName = personName;
    }

    public TextView getPersonPhoneNo() {
        return personPhoneNo;
    }

    public void setPersonPhoneNo(TextView personPhoneNo) {
        this.personPhoneNo = personPhoneNo;
    }

    public TextView getPersonEmail() {
        return personEmail;
    }

    public void setPersonEmail(TextView personEmail) {
        this.personEmail = personEmail;
    }

    public TextView getPersonAddress() {
        return personAddress;
    }

    public void setPersonAddress(TextView personAddress) {
        this.personAddress = personAddress;
    }

    public TextView getPersonId() {
        return personId;
    }

    public void setPersonId(TextView personId) {
        this.personId = personId;
    }
}
