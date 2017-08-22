package kai.tan.com.testwebview;

import android.annotation.SuppressLint;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private ImageView img;
    private Toast mToast;

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Logger.addLogAdapter(new AndroidLogAdapter());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = (ImageView) findViewById(R.id.img);

        final WebView mWebView = (WebView) findViewById(R.id.webView);
        assert mWebView != null;
//        mWebView.getSettings().setDomStorageEnabled(true);//处理天猫商品不显示问题
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new JSObject(), "android");
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
        mWebView.loadUrl("file:///android_asset/no-wifi.html");

        float denstity = this.getApplicationContext().getResources().getDisplayMetrics().density;
        int denstityDpi = this.getApplicationContext().getResources().getDisplayMetrics().densityDpi;
        int widthPixels = this.getApplicationContext().getResources().getDisplayMetrics().widthPixels;
        int heightPixels = this.getApplicationContext().getResources().getDisplayMetrics().heightPixels;
        Logger.d("widthPixels:" + widthPixels + "-heightPixels:" + heightPixels);
        Logger.d("denstity:" + denstity);// A31u 1.5
        Logger.d("denstityDpi:" + denstityDpi);// A31u 240
        Logger.d("60dpTopx:" + (int) (60 * denstity + 0.5));

        try {
            String json = null;
            JSONObject jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    Toast toast;

    public void onClick(View view) {
        //主线程
        Logger.d("Thread:" + Thread.currentThread().getName());
        //这里会报android.view.ViewRootImpl$CalledFromWrongThreadException: Only the original thread that created a view hierarchy can touch its views.
        toast.setText("Thread:" + Thread.currentThread().getName());
        toast.show();
    }

    public class JSObject {

        @JavascriptInterface
        public void toast() {
            //Javabride线程
            Logger.d("Thread:" + Thread.currentThread().getName());
            toast = Toast.makeText(MainActivity.this, "Thread:" + Thread.currentThread().getName(), Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Logger.d("imgWidth:" + img.getWidth() + "-imgHeight:" + img.getHeight());
    }
}