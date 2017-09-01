package com.liferay.omnihackathon.view;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import com.liferay.omnihackathon.R;

/**
 * @author Victor Oliveira
 */
public class WebViewActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState,
        @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        setContentView(R.layout.activity_webview);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String url = extras.getString("URL", "");
            WebView webView = (WebView) findViewById(R.id.web_view);
            webView.loadUrl(url);
        }
    }
}
