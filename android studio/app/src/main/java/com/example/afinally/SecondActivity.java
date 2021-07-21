package com.example.afinally;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

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
import java.util.Timer;
import java.util.TimerTask;

public class SecondActivity extends AppCompatActivity  {


     String ip_add;
     String ip_add1;
     String ip_add2;
     String po;
     String text;
     Button left;
     Button right;
     Button up;
     Button down;
     EditText ip_address;
     WebView myWebView;
     WebView myWebView1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ip_add =getIntent().getExtras().getString("Value");
        po=getIntent().getExtras().getString("Value1");

        ip_add2="http://"+ip_add+":"+po+"/"+"video_feed";
        ip_add1 ="http://"+ip_add+":"+po+"/"+"battery";

        myWebView = (WebView) findViewById(R.id.webView);
        /*myWebView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);*/
        myWebView.setInitialScale(1);
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setUseWideViewPort(true);
        WebSettings webSettings = myWebView.getSettings();
        myWebView.setWebViewClient(new WebViewClient());
        webSettings.setJavaScriptEnabled(true);
        myWebView.loadUrl(ip_add2);

       // myWebView1.setBackgroundColor(Color.TRANSPARENT);

        myWebView1 = (WebView) findViewById(R.id.webView1);
        myWebView1.setWebViewClient(new WebViewClient());
        WebSettings webSettings1 = myWebView1.getSettings();
        webSettings1.setJavaScriptEnabled(true);
        myWebView1.setBackgroundColor(0x00FFFFFF);
        myWebView1.loadUrl(ip_add1);



        up = (Button) findViewById(R.id.up);
        down = (Button) findViewById(R.id.down);
        right = (Button) findViewById(R.id.right);
        left = (Button) findViewById(R.id.left);

        up.setOnClickListener(new senddatatoserver());
        down.setOnClickListener(new senddatatoserver());
        right.setOnClickListener(new senddatatoserver());
        left.setOnClickListener(new senddatatoserver());

       }



    public class senddatatoserver implements View.OnClickListener{

        @Override
        public void onClick(View v) {

                switch (v.getId()){
                    case R.id.left:
                        text = left.getText().toString();
                        // do your code
                        break;

                    case R.id.right:
                        text = right.getText().toString();
                        // do your code
                        break;

                    case R.id.up:
                        text = up.getText().toString();
                        // do your code
                        break;

                    case R.id.down:
                        text = down.getText().toString();
                        // do your code
                        break;


                }

                JSONObject post_dict = new JSONObject();
                try {
                    post_dict.put("text", text);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (post_dict.length() > 0) {
                    new SendJsonDataToServer().execute(String.valueOf(post_dict));
                }

            }
    }



    //add background inline class here
    class SendJsonDataToServer extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... params) {
            String JsonResponse = null;
            String JsonDATA = params[0];
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL("http://"+ip_add+":"+po+"/button");
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

        @Override
    public void onBackPressed() {
        if(myWebView.canGoBack()) {
            myWebView.goBack();
        } else {
            super.onBackPressed();
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
