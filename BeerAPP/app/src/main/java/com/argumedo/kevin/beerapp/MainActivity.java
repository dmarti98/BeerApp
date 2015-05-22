package com.argumedo.kevin.beerapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;



public class MainActivity extends ActionBarActivity {
    Button beerWeek, surpriseMe, search;
    EditText searchQ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        beerWeek = (Button) findViewById(R.id.beerOfTheWeek);
        beerWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beerOfTheWeek();
            }
        });

        surpriseMe = (Button) findViewById(R.id.surpiseMe);
        surpriseMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                surpriseMe();
            }
        });

        searchQ = (EditText) findViewById(R.id.searchQ);

        search = (Button) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

    }

    public void search()
    {
        EditText editText = (EditText) findViewById(R.id.searchQ);
        String query = editText.getText().toString();

        Intent intent = new Intent(MainActivity.this, Search.class);
        intent.putExtra("query", query);
        startActivity(intent);

    }


    public void surpriseMe()
    {
        Intent intent = new Intent(MainActivity.this,SurpriseMe.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }


    public void beerOfTheWeek()
    {
        Intent intent = new Intent(MainActivity.this,BeerOfTheWeek.class);
        intent.putExtra("taco","taco");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
