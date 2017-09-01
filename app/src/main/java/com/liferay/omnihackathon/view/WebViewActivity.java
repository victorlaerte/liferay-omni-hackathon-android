package com.liferay.omnihackathon.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.liferay.omnihackathon.R;

/**
 * @author Victor Oliveira
 */
public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_webview);

        Bundle extras = getIntent().getExtras();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        if (extras != null) {
            String url = extras.getString("URL", "");
            WebView webView = (WebView) findViewById(R.id.web_view);
            webView.setWebViewClient(new WebViewClient());
            WebSettings webSettings = webView.getSettings();

            webSettings.setJavaScriptEnabled(true);
            webView.loadUrl(url);
        }
    }

}
