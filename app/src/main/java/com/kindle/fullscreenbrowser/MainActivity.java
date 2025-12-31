package com.kindle.fullscreenbrowser;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private EditText urlInput;
    private LinearLayout urlBarContainer;
    private Button btnToggleUrlBar;
    private Button btnBack;
    private Button btnForward;
    private Button btnReload;
    private Button btnGo;

    private boolean isUrlBarVisible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 全画面表示を設定
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        setContentView(R.layout.activity_main);

        // ビューの初期化
        webView = findViewById(R.id.webview);
        urlInput = findViewById(R.id.url_input);
        urlBarContainer = findViewById(R.id.url_bar_container);
        btnToggleUrlBar = findViewById(R.id.btn_toggle_url_bar);
        btnBack = findViewById(R.id.btn_back);
        btnForward = findViewById(R.id.btn_forward);
        btnReload = findViewById(R.id.btn_reload);
        btnGo = findViewById(R.id.btn_go);

        // WebViewの設定
        setupWebView();

        // ボタンのクリックリスナー設定
        setupClickListeners();

        // デフォルトURLをロード
        String defaultUrl = getString(R.string.default_url);
        webView.loadUrl(defaultUrl);
        urlInput.setText(defaultUrl);
    }

    private void setupWebView() {
        WebSettings webSettings = webView.getSettings();

        // JavaScript有効化
        webSettings.setJavaScriptEnabled(true);

        // DOM Storage有効化
        webSettings.setDomStorageEnabled(true);

        // ズーム機能
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        // キャッシュ設定
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

        // ユーザーエージェント設定（デスクトップサイトを表示する場合はコメント解除）
        // webSettings.setUserAgentString("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36");

        // ビューポート設定
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        // WebViewClientを設定（リンクをアプリ内で開く）
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                urlInput.setText(url);
            }
        });

        // WebChromeClientを設定
        webView.setWebChromeClient(new WebChromeClient());
    }

    private void setupClickListeners() {
        // URL欄切替ボタン
        btnToggleUrlBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleUrlBar();
            }
        });

        // 移動ボタン
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadUrl();
            }
        });

        // URL入力欄のエンターキー処理
        urlInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER &&
                     event.getAction() == KeyEvent.ACTION_DOWN)) {
                    loadUrl();
                    return true;
                }
                return false;
            }
        });

        // 戻るボタン
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView.canGoBack()) {
                    webView.goBack();
                }
            }
        });

        // 進むボタン
        btnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView.canGoForward()) {
                    webView.goForward();
                }
            }
        });

        // 更新ボタン
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.reload();
            }
        });
    }

    private void toggleUrlBar() {
        if (isUrlBarVisible) {
            // URL欄を非表示にする
            urlBarContainer.setVisibility(View.GONE);
            btnToggleUrlBar.setText("▲");
            isUrlBarVisible = false;
        } else {
            // URL欄を表示する
            urlBarContainer.setVisibility(View.VISIBLE);
            btnToggleUrlBar.setText("▼");
            isUrlBarVisible = true;
        }
    }

    private void loadUrl() {
        String url = urlInput.getText().toString().trim();

        // URLにプロトコルがない場合は追加
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "https://" + url;
        }

        webView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        // WebViewで戻れる場合は戻る
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        webView.restoreState(savedInstanceState);
    }
}
