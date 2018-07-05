package lcsd.com.whirlpool.activity;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.manager.ActivityManager;

import com.mingle.widget.ShapeLoadingDialog;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class WebActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_title;
    private String title, url;
    private com.tencent.smtt.sdk.WebView webview;
    private ShapeLoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        if (getIntent() != null) {
            title = getIntent().getStringExtra("title");
            url = getIntent().getStringExtra("url");
        }
        loadingDialog = new ShapeLoadingDialog.Builder(this)
                .loadText("加载中...")
                .build();
        loadingDialog.show();
        initView();
        initData();
    }

    private void initView() {
        webview = (com.tencent.smtt.sdk.WebView) findViewById(R.id.webview);
        tv_title = (TextView) findViewById(R.id.titlebar_title);
        tv_title.setText(title);
        findViewById(R.id.titlebar_back).setOnClickListener(this);
        findViewById(R.id.titlebar_home).setOnClickListener(this);
    }

    private void initData() {

        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().getLoadWithOverviewMode();
        webview.getSettings().getJavaScriptCanOpenWindowsAutomatically();
        webview.getSettings().setDomStorageEnabled(true);
        webview.loadUrl(url);

        webview.setWebViewClient(new WebViewClient() {
            // 网页跳转
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            // 网页加载结束
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadingDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_back:
                this.finish();
                break;
            case R.id.titlebar_home:
                ActivityManager.getActivityManager().finishAll();
                break;
        }
    }

    @Override
    protected boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {
            webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webview.destroy();
    }
}
