package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.support.test.InstrumentationRegistry;
import android.test.ApplicationTestCase;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

// Derived from http://marksunghunpark.blogspot.com/2015/05/how-to-test-asynctask-in-android.html
public class AsyncTaskTest extends ApplicationTestCase {

    private ArrayList<String> jokeList = null;
    private Exception mError = null;
    private CountDownLatch signal = null;

    public AsyncTaskTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        signal = new CountDownLatch(1);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        signal.countDown();
    }

    public void testAlbumGetTask() throws InterruptedException {

        com.udacity.gradle.builditbigger.EndpointsAsyncTask task = new com.udacity.gradle.builditbigger.EndpointsAsyncTask();
        task.setListener(new com.udacity.gradle.builditbigger.EndpointsAsyncTask.EndpointsGetTaskListener() {
            @Override
            public void onComplete(ArrayList<String> joke, Exception e) {
                jokeList = joke;
                mError = e;
                signal.countDown();
            }
        }).execute(InstrumentationRegistry.getContext());
        signal.await();

        // There should be no error message;
        assertNull(mError);

        // The asynctask should return an ArrayList of two strings - one for the joke questions,
        // one for the joke punchline.
        assertTrue(jokeList.size() == 2);
    }
}

