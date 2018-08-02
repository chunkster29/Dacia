package com.jmtproducts.dacia;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class ResultActivity extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;


    class Calculate {

        String precode;

        String result () {


            int i = '\005' * precode.charAt(0);
            int j = precode.charAt(1) + (-698 + i * 2);
            int k = precode.charAt(2);
            int m = -528 + (precode.charAt(3) + (j + 2 * (k * 5)));
            int n = ((m << 3) - m) % 100;
            String str = String.valueOf((int)Math.floor(n / 10) + 2 * (5 * (n % 10)) + 4 * (5 * (5 * (259 % j % 100))));

            if (str.length() == 3) {
                return "0" + str;
            }
            if (str.length() == 2) {
                return "00" + str;
            }
            if (str.length() == 1) {
                return "000" + str;
            }
            return str;

        }


    }


    public void getResult(String barcode) {

        String message;

        String code = barcode.substring(barcode.length() - 4);

        if (code.length() < 4 || (code.charAt(0) < (65)) || (code.charAt(0) > (122)) ||
                (code.charAt(1) > (65)) || (code.charAt(1) < (48)) ||
                (code.charAt(0) == (97) && code.charAt(1) == (48)) ||
                (code.charAt(0) == (65) && code.charAt(1) == (48))) {

            codeError();

        } else {

            if ((code.length() > 4) || (code.charAt(2) > (65)) ||
                    (code.charAt(2) < (48)) || (code.charAt(3) > (65)) ||
                    (code.charAt(3) < (48))) {

                codeError();

            } else {

                adTimer();

                Calculate myPrecode = new Calculate();


                myPrecode.precode = code.toUpperCase();

                message = myPrecode.result();

                System.out.println(myPrecode.result());

                TextView resultText;
                resultText = findViewById(R.id.resultText);

                resultText.setText(message);


            }
        }
    }

    public void codeError() {
        Toast.makeText(getApplicationContext(), "Please enter a valid number", Toast.LENGTH_LONG).show();
        Intent resultActivity = new Intent(ResultActivity.this, MainActivity.class);
        resultActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        ResultActivity.this.startActivity(resultActivity);
        finish();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);



        AdView adView = findViewById(R.id.adView);
        AdView adView1 = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        adView1.loadAd(adRequest);

        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(this, getString(R.string.mob_ads_id));

        // Create the InterstitialAd and set the adUnitId.
        mInterstitialAd = new InterstitialAd(this);
        // Defined in res/values/strings.xml
        mInterstitialAd.setAdUnitId(getString(R.string.ad_unit_id));

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {

            }
        });

        // Request a new ad if one isn't already loaded, hide the button, and kick off the timer.
        if (!mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()) {
            //   AdRequest adRequest = new AdRequest.Builder().build();
            mInterstitialAd.loadAd(adRequest);
        }

        String barcode = getIntent().getStringExtra("code");
        if (barcode.length() < 4) {
            codeError();
        } else {
            getResult(barcode);
        }
    }



    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    private void adTimer() {

        new CountDownTimer(5000, 1000) {

            public void onTick(long millisecondsUntilDone) {
                Log.i("Seconds Left!", String.valueOf(millisecondsUntilDone / 1000));
            }

            public void onFinish() {
                showInterstitial();
            }
        }.start();
    }

    public void onBackPressed() {
        Intent resultActivity = new Intent(ResultActivity.this, MainActivity.class);
        resultActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        ResultActivity.this.startActivity(resultActivity);
        finish();
    }

}