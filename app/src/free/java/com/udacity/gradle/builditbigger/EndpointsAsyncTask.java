package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.mickeywilliamson.jokedisplay.JokeDisplayActivity;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;
import java.util.ArrayList;

// Derived from https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/77e9910911d5412e5efede5fa681ec105a0f02ad/HelloEndpoints#2-connecting-your-android-app-to-the-backend
class EndpointsAsyncTask extends AsyncTask<Context, Void, ArrayList<String>> {
    private static MyApi myApiService = null;
    private Context context;
    private EndpointsGetTaskListener mListener = null;
    private Exception mError = null;
    private InterstitialAd mInterstitialAd;

    @Override
    protected ArrayList<String> doInBackground(Context... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        context = params[0];

        try {
            return (ArrayList<String>) myApiService.fetchJoke().execute().getData();
        } catch (IOException e) {
            ArrayList<String> errorList = new ArrayList<>();
            mError = e;
            errorList.add(e.getMessage());
            return errorList;
        }
    }

    @Override
    protected void onPostExecute(final ArrayList<String> result) {

        if (this.mListener != null) {
            this.mListener.onComplete(result, mError);
        }

        MobileAds.initialize(context,
                "ca-app-pub-3940256099942544~3347511713");

        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice("").build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mInterstitialAd.show();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                Intent intent = new Intent(context, JokeDisplayActivity.class);
                intent.putStringArrayListExtra(JokeDisplayActivity.KEY_JOKE, result);
                context.startActivity(intent);
            }
        });


    }

    public EndpointsAsyncTask setListener(EndpointsGetTaskListener listener) {
        this.mListener = listener;
        return this;
    }

    public static interface EndpointsGetTaskListener {
        public void onComplete(ArrayList<String> joke, Exception e);
    }
}
