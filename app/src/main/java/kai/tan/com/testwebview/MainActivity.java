package kai.tan.com.testwebview;

import android.annotation.SuppressLint;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class MainActivity extends AppCompatActivity {

    private ImageView img;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Logger.addLogAdapter(new AndroidLogAdapter());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = (ImageView)findViewById(R.id.img);

        final WebView mWebView = (WebView) findViewById(R.id.webView);
        assert mWebView != null;
//        mWebView.getSettings().setDomStorageEnabled(true);//处理天猫商品不显示问题
//        mWebView.getSettings().setJavaScriptEnabled(true);
//        mWebView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                if (url.startsWith("http") || url.startsWith("file:///android_asset")) {
//                    view.loadUrl(url);
//                } else {
//                    if (url.startsWith("mqqwpa:") || url.startsWith("tencent:")) {
//
//                    } else if (url.startsWith("taobao:")) {
//                        return true;
//                    } else if (url.startsWith("tmall:")) {//屏蔽天猫
//                        return true;
//                    } else if (url.startsWith("alipays://")) {
//                        try {
//                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
//                        } catch (ActivityNotFoundException e) {
//                            e.printStackTrace();
//                            Toast.makeText(MainActivity.this, "请安装支付宝", Toast.LENGTH_SHORT).show();
//                        }
//
//                    } else {
//                        view.loadUrl(url);
//                    }
//                }
//                return true;
//            }
//        });

        mWebView.setWebViewClient(new WebViewClient() {
            //
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();// 接受所有网站的证书
                super.onReceivedSslError(view, handler, error);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mWebView.loadUrl(url);
                return true;
            }
        });
//        mWebView.loadUrl("https://api.qirexiaoshuo.com/my");
        mWebView.loadUrl("https://api.qirexiaoshuo.com:8443/my");

        float denstity = this.getApplicationContext().getResources().getDisplayMetrics().density;
        int denstityDpi = this.getApplicationContext().getResources().getDisplayMetrics().densityDpi;
        int widthPixels = this.getApplicationContext().getResources().getDisplayMetrics().widthPixels;
        int heightPixels = this.getApplicationContext().getResources().getDisplayMetrics().heightPixels;
        Logger.d("widthPixels:" + widthPixels + "-heightPixels:" + heightPixels);
        Logger.d("denstity:" + denstity);// A31u 1.5
        Logger.d("denstityDpi:" + denstityDpi);// A31u 240

        Logger.d("60dpTopx:" + (int)(60 * denstity + 0.5));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Logger.d("imgWidth:" + img.getWidth() + "-imgHeight:"+ img.getHeight());
    }
}