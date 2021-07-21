package com.example.afinally;


import android.animation.Animator;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class MainActivity extends AppCompatActivity {
     String ip_add;
     String po;
     Button but;

     EditText ip_address;
     EditText port;

    private ImageView bookIconImageView;
    private TextView bookITextView;
    private ProgressBar loadingProgressBar;
    private RelativeLayout rootView, afterAnimationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        findViewById(R.id.fb_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked_btn("https://www.facebook.com/roboclubiitkanpur/");
            }
        });
        findViewById(R.id.youtube_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked_btn("https://www.youtube.com/channel/UCWM9ZaOmev5otSwQqW3JICA");
            }
        });
        findViewById(R.id.git_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked_btn("https://github.com/RoboticsClubIITK");
            }
        });
        // this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ip_address = (EditText) findViewById(R.id.ip_address);
        port = (EditText) findViewById(R.id.port);
        but = (Button) findViewById(R.id.button);
        but.setOnClickListener((View v) -> {
            Intent intent = new Intent(this, SecondActivity.class);
            ip_add = ip_address.getText().toString();
            po=port.getText().toString();
            intent.putExtra( "Value",ip_add);
            intent.putExtra( "Value1",po);
            startActivity(intent);


        });
        initViews();
        new CountDownTimer(5000, 3000) {

            @Override
            public void onTick(long millisUntilFinished) {
                bookITextView.setVisibility(GONE);
                loadingProgressBar.setVisibility(GONE);
                rootView.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorSplashText));
                bookIconImageView.setImageResource(R.drawable.background_color_book);
                startAnimation();
            }

            @Override
            public void onFinish() {

            }
        }.start();

    }

    private void initViews() {
        bookIconImageView = findViewById(R.id.bookIconImageView);
        bookITextView = findViewById(R.id.bookITextView);
        loadingProgressBar = findViewById(R.id.loadingProgressBar);
        rootView = findViewById(R.id.rootView);
        afterAnimationView = findViewById(R.id.afterAnimationView);
    }

    private void startAnimation() {
        ViewPropertyAnimator viewPropertyAnimator = bookIconImageView.animate();
        viewPropertyAnimator.x(50f);
        viewPropertyAnimator.y(100f);
        viewPropertyAnimator.setDuration(3000);
        viewPropertyAnimator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                afterAnimationView.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
    public void clicked_btn(String url)
    {
        Intent intent =new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
