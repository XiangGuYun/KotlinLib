package com.kotlinlib;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.kotlin_base.R;
import com.kotlinlib.utils.NetUtils;
import org.jetbrains.annotations.Nullable;

/**
 * 显示网页的通用Activity
 * 动态加载WebView
 * 需要初始化
 */
public class WebActivity extends KotlinActivity {

    public static final String WEB_URL = "web_url";
    private String webUrl;
    private WebView webView;
    private WebViewClient webViewClient;//网页客户端
    private WebSettings settings;//网页设置
    private FrameLayout fl_web;


    private void initWebView(int webContainerId) {
        webView = new WebView(this);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams
                .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        webView.setLayoutParams(lp);
        fl_web = findViewById(webContainerId);
        //有效果
        fl_web.addView(webView, 0);
        /*
        设置网页浏览器客户端
         */
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });
        /*
        设置网页视图客户端
         */
        initWebViewClient();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(webViewClient);
//        webView.addJavascriptInterface(new AndroidToJS(this), "postData");
        //AndroidtoJS类对象映射到js的postData对象
        webView.loadUrl(webUrl);
         /*
        设置支持JS
         */
        settings = webView.getSettings();
        // 设置可以支持缩放
        settings.setSupportZoom(true);
        // 设置出现缩放工具
        settings.setBuiltInZoomControls(false);
        //扩大比例的缩放
        settings.setUseWideViewPort(true);
        //自适应屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);//开启DOM缓存，关闭的话H5自身的一些操作是无效的
        /*
        设置缓存模式
         */
        if (NetUtils.Companion.netOK(this)) {//判断是否有网络链接
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            settings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
        }
        /*！
        把图片加载放在最后来加载渲染
         */
        settings.setBlockNetworkImage(false);
        /*
        设置渲染优先级
         */
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        /*
        支持多窗口
         */
        settings.setSupportMultipleWindows(true);
        /*
        开启 DOM storage API 功能
         */
        settings.setDomStorageEnabled(true);
        /*
        开启 Application Caches 功能
         */
        settings.setAppCacheEnabled(true);
    }

    private void initWebViewClient() {
        webViewClient = new WebViewClient() {
            @Override
            public void onLoadResource(WebView view, String url) {
                //开始加载
                super.onLoadResource(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url); //设置不用系统浏览器打开,直接显示在当前Webview
                return false;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String
                    failingUrl) {
//                super.onReceivedError(view, errorCode, description, failingUrl);
//                Log.d("Test", "网页错误");
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        };
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.setVisibility(View.GONE);
            webView.removeAllViews();
            webView.destroy();
        }
        fl_web.removeView(webView);
        super.onDestroy();
    }

    @Override
    protected void init(@Nullable Bundle bundle) {
        webUrl = getIntent().getStringExtra(WEB_URL);
        //重写init并调用initWebView();
    }
}






