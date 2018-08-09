package com.udacity.gradle.builditbigger.backend;

import com.mickeywilliamson.jokeprovider.Joke;

import java.util.ArrayList;
import java.util.Collections;

/** The object model for the data we are sending through endpoints */
public class JokeBean {

    private String[] myData;

    public JokeBean() {
        Joke joke = new Joke();
        setData(joke.getJokeText());
    }

    public ArrayList<String> getData() {
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, myData);
        return list;
    }

    public void setData(String[] data) {
        myData = data;
    }
}