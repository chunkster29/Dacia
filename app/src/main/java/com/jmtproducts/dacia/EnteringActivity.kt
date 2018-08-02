package com.jmtproducts.dacia

import android.os.Bundle
import android.os.CountDownTimer
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View

import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds

class EnteringActivity : AppCompatActivity() {

    private var mInterstitialAd: InterstitialAd? = null
    private var mCountDownTimer: CountDownTimer? = null
    private var mTimerMilliseconds: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entering)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val adView = findViewById(R.id.adView) as AdView
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(this, "ca-app-pub-1204889035395038~9608695353")

        // Create the InterstitialAd and set the adUnitId.
        mInterstitialAd = InterstitialAd(this)
        // Defined in res/values/strings.xml
        mInterstitialAd!!.adUnitId = getString(R.string.ad_unit_id_1)

        mInterstitialAd!!.adListener = object : AdListener() {
            override fun onAdClosed() {

            }
        }

        // Request a new ad if one isn't already loaded, hide the button, and kick off the timer.
        if (!mInterstitialAd!!.isLoading && !mInterstitialAd!!.isLoaded) {
            //  AdRequest adRequest = new AdRequest.Builder().build();
            mInterstitialAd!!.loadAd(adRequest)
        }

        startGame()


    }

    private fun createTimer(milliseconds: Long) {
        // Create the game timer, which counts down to the end of the level
        // and shows the "retry" button.
        if (mCountDownTimer != null) {
            mCountDownTimer!!.cancel()
        }



        mCountDownTimer = object : CountDownTimer(milliseconds, 50) {
            override fun onTick(millisUnitFinished: Long) {
                mTimerMilliseconds = millisUnitFinished

            }

            override fun onFinish() {

                showInterstitial()

            }
        }
    }

    private fun showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (mInterstitialAd != null && mInterstitialAd!!.isLoaded) {
            mInterstitialAd!!.show()
        }
    }

    private fun startGame() {

        resumeGame(GAME_LENGTH_MILLISECONDS)
    }

    private fun resumeGame(milliseconds: Long) {
        // Create a new timer for the correct length and start it.

        mTimerMilliseconds = milliseconds
        createTimer(milliseconds)
        mCountDownTimer!!.start()
    }

    companion object {

        private val GAME_LENGTH_MILLISECONDS: Long = 3000
    }

}
