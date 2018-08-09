package com.mickeywilliamson.jokedisplay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class JokeDisplayActivity extends AppCompatActivity {

    TextView tvJokeText;
    TextView tvJokeAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_display);

        ArrayList<String> joke = getIntent().getStringArrayListExtra("JOKE");

        tvJokeText = findViewById(R.id.jokeText);
        tvJokeText.setText(joke.get(0));

        tvJokeAnswer = findViewById(R.id.jokeAnswer);
        tvJokeAnswer.setText(joke.get(1));
    }
}
