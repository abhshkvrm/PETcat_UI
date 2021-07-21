package com.example.button_testing;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.net.Proxy.Type.HTTP;

public class MainActivity extends Activity {

    EditText AgeView;
    EditText LastNameView;
    EditText FirstNameView;
    String FirstName;
    String LastName;
    String Age;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Get reference to the defined views in layout file
        FirstNameView = (EditText) findViewById(R.id.FirstName);
        LastNameView = (EditText) findViewById(R.id.LastName);
        AgeView = (EditText) findViewById(R.id.Age);


    }

    public void senddatatoserver(View v )throws JSONException  {
        //function in the activity that corresponds to the layout button
        FirstName = FirstNameView.getText().toString();
        LastName = LastNameView.getText().toString();
        Age = AgeView.getText().toString();
        JSONObject post_dict = new JSONObject();
            try {
                post_dict.put("firstname", FirstName);
                post_dict.put("firstname", LastName);
                post_dict.put("firstname", Age);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (post_dict.length() > 0) {
                new SendJsonDataToServer().execute(String.valueOf(post_dict));
            }

    }
        //add background inline class here
        class SendJsonDataToServer extends AsyncTask<String,String,String>{
            @Override
            protected String doInBackground(String... params) {
                String JsonResponse = null;
                String JsonDATA = params[0];
                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL("http://www.google.com");
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setDoOutput(true);
                    // is output buffer writter
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setRequestProperty("Accept", "application/json");
                    //set headers and method
                    Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                    writer.write(JsonDATA);
                    // json data
                    writer.close();
                    InputStream inputStream = urlConnection.getInputStream();
                    //input stream
                    StringBuffer buffer = new StringBuffer();
                    if (inputStream == null) {
                        // Nothing to do.
                        return null;
                    }
                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String inputLine;
                    while ((inputLine = reader.readLine()) != null)
                        buffer.append(inputLine + "\n");
                    if (buffer.length() == 0) {
                        // Stream was empty. No point in parsing.
                        return null;
                    }
                    JsonResponse = buffer.toString();
                    //response data
                    Log.i("mytag", JsonResponse);
                    try {
                     //send to post execute
                        return JsonResponse;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return null;



                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (final IOException e) {
                            Log.e( "mytag", "Error closing stream", e);
                        }
                    }
                }
                return null;

            }


            @Override
            protected void onPostExecute(String s) {
            }

        }

        }



