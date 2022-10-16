package com.fitareq.techno71;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fitareq.techno71.databinding.ActivityBrowserBinding;

public class BrowserActivity extends AppCompatActivity implements NoInternetListener{

    private ActivityBrowserBinding binding;
    private WebView webView;
    private String USER_AGENT_ = "Mozilla/5.0 (Linux; Android 4.1.1; Galaxy Nexus Build/JRO03C) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19";
    private String URL;

    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBrowserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        URL = getIntent().getStringExtra(Utils.URL_KEY);

        receiver = new NetworkChangeReceiver();
        registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        //webView = new WebView(BrowserActivity.this);


        /*webView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        webView.setFitsSystemWindows(false); // your preferences
        webView.setVerticalScrollBarEnabled(false); // your preferences
        webView.setPadding(15, 15, 15, 15); // your preferences
        binding.layroot.addView(webView);*/

        webView = binding.webView;


        // Enabling some setting so that browser can work properly
        webView.getSettings().setUserAgentString(USER_AGENT_);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new mWebViewClient());
        webView.setWebChromeClient(new mChromeClient());


        loadUrl();

        binding.toolbar.setNavigationOnClickListener(view -> {
            finish();
        });

    }


    private void loadUrl(){
        if (!Utils.isNetworkAvailable(this)) {
            webView.setVisibility(View.GONE);
            binding.layNonet.setVisibility(View.VISIBLE);
        } else {
            webView.loadUrl(URL);
            webView.setVisibility(View.VISIBLE);
            binding.layNonet.setVisibility(View.GONE);
        }
    }

    @Override
    public void noInternet(boolean b) {
        if (b && binding.layNonet.getVisibility() == View.VISIBLE){
            loadUrl();
        }
    }

    private class mWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            URL = url;
            loadUrl();
            return true;
        }


    }


    private class mChromeClient extends WebChromeClient {
        // Defining some variables


        private View mCustomView;
        private CustomViewCallback mCustomViewCallback;
        protected FrameLayout mFullscreenContainer;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        mChromeClient() {
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            binding.progressBar.setProgress(newProgress);
            if (newProgress >= 100) {
                // Page loading finish

                binding.progressBar.setVisibility(View.GONE);

            } else {
                binding.progressBar.setVisibility(View.VISIBLE);
            }
        }


        public Bitmap getDefaultVideoPoster() {
            if (mCustomView == null) {
                return null;
            }
            return BitmapFactory.decodeResource(getApplicationContext().getResources(), 2130837573);
        }

        public void onHideCustomView() {
            ((FrameLayout) getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }

        public void onShowCustomView(View paramView, CustomViewCallback paramCustomViewCallback) {
            if (this.mCustomView != null) {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout) getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            getWindow().getDecorView().setSystemUiVisibility(3846 | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }


    // When user click bakpress button this method is called
    @Override
    public void onBackPressed() {

        // When landing in home screen
        if (!webView.canGoBack()) {
            finish();

        } else {
            webView.goBack();
        }


    } // end of onBackpressed method
}