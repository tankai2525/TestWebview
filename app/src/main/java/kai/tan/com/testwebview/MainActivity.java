package kai.tan.com.testwebview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebView mWebView = (WebView) findViewById(R.id.webView);

        mWebView.getSettings().setDomStorageEnabled(true);//处理天猫商品不显示问题aaa
        mWebView.getSettings().setJavaScriptEnabled(true);
        //        mWebView.getSettings().setUseWideViewPort(true);//读取 "viewport"网页meta标签
        //        mWebView.getSettings().setBuiltInZoomControls(true);
        //        mWebView.getSettings().setSupportZoom(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http") || url.startsWith("file:///android_asset")) {
                    view.loadUrl(url);
                } else {
                    if (url.startsWith("mqqwpa:") || url.startsWith("tencent:")) {

                    } else if (url.startsWith("taobao:")) {
                        return true;
                    } else if (url.startsWith("tmall:")) {//屏蔽天猫
                        return true;
                    } else {
                        view.loadUrl(url);
                    }

                }
                return true;
            }
        });
        mWebView.loadUrl("https://m.taobao.com");
    }

    //dev分支操做
    //dev分支在操作
}
