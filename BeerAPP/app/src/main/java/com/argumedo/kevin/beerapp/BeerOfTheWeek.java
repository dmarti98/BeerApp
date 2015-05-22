package com.argumedo.kevin.beerapp;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class BeerOfTheWeek extends ActionBarActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beeroftheweek);
        startLoadTask(BeerOfTheWeek.this);
    }

    public void startLoadTask(Context c){
        if (isOnline()) {
            CallAPI task = new CallAPI();
            task.execute();

        } else {
            Toast.makeText(c, "Not online", Toast.LENGTH_LONG).show();
        }
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }


    private class CallAPI extends AsyncTask<String, String, String> {
        ArrayList<Beer> fBeer;
        HttpURLConnection urlConnection = null;
        ImageView image = (ImageView) findViewById(R.id.featuredPic);
        Bitmap bmImage = null;

        @Override
        protected String doInBackground(String... params) {
            String startURL = "https://api.brewerydb.com/v2/";
            String endURL = "key=e1afe81e104ba290bb7507cd693ead92&format=json";
            String dataString = startURL + "featured/?" + endURL;

            try {
                URL dataURL = new URL(dataString);

                urlConnection = (HttpURLConnection) dataURL.openConnection();
//                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                int status = urlConnection.getResponseCode();
                Log.i(status + "", status + "");

                InputStream inStream = urlConnection.getInputStream();
                BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));

                String response;
                StringBuilder sb = new StringBuilder();

                while ((response = bReader.readLine()) != null) {
                    sb = sb.append(response);
                }
                String fData = sb.toString();
                Log.d("data", fData);

                fBeer = Beer.getFeaturedBeer(fData);

                InputStream in = new java.net.URL(fBeer.get(0).getPic()).openStream();
                bmImage = BitmapFactory.decodeStream(in);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            } finally {
                if(bmImage == null)
                {
                    image.setImageDrawable(getResources().getDrawable(R.drawable.unavailable));
                }
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return "";
        }

        protected void onPostExecute(String result) {
            if(result != null)
            {
                TextView fName = (TextView) findViewById(R.id.featuredName);
                TextView fDesc = (TextView) findViewById(R.id.featuredDesc);

                if(fBeer != null)
                {
                    fName.setText(fBeer.get(0).getName() + " ABV(" + fBeer.get(0).getAbv() + "%)");
                    fDesc.setText(fBeer.get(0).getDescription());
                    if(bmImage == null)
                    {
                        image.setImageDrawable(getResources().getDrawable(R.drawable.unavailable));
                    }
                    else {
                        image.setImageBitmap(bmImage);
                    }
                }
                else
                {
                    fName.setText("Error");
                }

            }

        }
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
