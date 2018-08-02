package com.jmtproducts.dacia

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
//import kotlinx.android.synthetic.main.activity_main.*
//import kotlinx.android.synthetic.main.app_bar_main.*
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView


import android.provider.AlarmClock.EXTRA_MESSAGE
import com.jmtproducts.dacia.R.id.*
import java.sql.DriverManager.println

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, View.OnKeyListener, View.OnClickListener {

    override fun onClick(p0: View?) {
        if (p0!!.getId() == R.id.Info || p0.getId() == R.id.relativeLayout) {
            val imm = (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)

            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }

    override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {

        if (keyCode == KeyEvent.KEYCODE_ENTER) {

            //  getResult();
            val imm = (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)

            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            getResult()
        }

        return false
    }

    private fun getResult() {
        val message: String

        val code = findViewById<EditText>(R.id.Code)

        if (code.text.toString().length < 4) {

            message = "Please enter a valid number"
            Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()

        } else {

            /*  val resultActivity = Intent(this@MainActivity, ResultActivity::class.java)
              resultActivity.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
              resultActivity.putExtra("code", code.text.toString())
              this@MainActivity.startActivity(resultActivity)
              finish() */
        }

    }






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val textView = findViewById<TextView>(R.id.Info)
        // val relativeLayout = findViewById<RelativeLayout>(R.id.relativeLayout)
        textView.setOnClickListener(this)
        // relativeLayout.setOnClickListener(this)

        val code = findViewById<EditText>(R.id.Code)

        code.setOnKeyListener(this)

        findViewById<View>(R.id.btn_result).setOnClickListener { getResult() }



        val adView = findViewById<AdView>(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> {
                //   getbarcode(null)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_findcode -> {
                val i = Intent(applicationContext, HelpActivity::class)
                startActivity(i)
            }
            R.id.nav_gallery -> {
                val intent = Intent(this, RemovalActivity::class)

                startActivity(intent)
            }
            R.id.nav_slideshow -> {
                val intent = Intent(this, EnteringActivity::class)

                startActivity(intent)
            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}