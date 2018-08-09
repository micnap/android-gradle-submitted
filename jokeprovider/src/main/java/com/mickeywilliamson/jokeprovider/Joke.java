package com.mickeywilliamson.jokeprovider;

import java.util.Random;

public class Joke {

    // Cheesy jokes from https://thoughtcatalog.com/melanie-berliet/2016/04/50-short-corny-jokes-that-will-make-you-laugh-out-loud/.
    private String[][] jokes = {
            {"How do you befriend a squirrel?", "Just act like a nut."},
            {"Why did the jaguar eat the tightrope walker?", "It was craving a well-balanced meal."},
            {"What did the big bucket say to the smaller one?", "Lookin’ a little pail there."},
            {"Why do chicken coups always have two doors?", "With four, they’d be chicken sedans."},
            {"What did one hat say to the other?", "You stay here. I’ll go on ahead."},
            {"Why did the lifeguard kick the elephants out of the pool?", "They kept dropping their trunks."},
            {"What do you call a pony with a cough?", "A little hoarse."},
            {"What do you do if someone thinks an onion is the only food that can make them cry?", "Throw a coconut at their face."},
            {"How does a duck buy lipstick?", "She just puts it on her bill."},
            {"What do you call a snowman on a hot day?", "Puddle."}
    };

    public Joke() {}

    public String[] getJokeText() {
        Random r = new Random();
        return jokes[r.nextInt(jokes.length)];
    }
}
