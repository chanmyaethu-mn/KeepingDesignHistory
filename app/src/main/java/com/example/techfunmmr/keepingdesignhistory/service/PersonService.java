package com.example.techfunmmr.keepingdesignhistory.service;

import android.content.Context;

import com.example.techfunmmr.keepingdesignhistory.dto.Person;

import java.util.List;

/**
 * Created by techfunmmr on 2/15/2016.
 */
public interface PersonService {

    long addPerson(Person person);

    int updatePerson(Person person);

    int deletePerson(String personId);

    List<Person> getPersonList();

    Person getPersonWithId(int personId);
}
