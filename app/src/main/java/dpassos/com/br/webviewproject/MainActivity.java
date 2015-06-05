package dpassos.com.br.webviewproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

//https://developer.chrome.com/multidevice/webview/gettingstarted
public class MainActivity extends ActionBarActivity {

    Handler handler;
    ProgressBar pb;
    WebView wv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new Handler();
        pb = (ProgressBar)findViewById(R.id.progressBar);
        wv = (WebView)findViewById(R.id.webView1);

        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(false);


        //If you've set your targetSdkVersion to 17 or higher,
        // you must add the @JavascriptInterface annotation to any
        // method that you want available to your JavaScript
        // (the method must also be public).
        // If you do not provide the annotation, the method
        // is not accessible by your web page when running on Android 4.2 or higher.

        JavaScriptInterface jsI = new JavaScriptInterface(this);
        wv.addJavascriptInterface(jsI, "JavaScriptInterface");

        //Evita abrir no browser padrao do sistema.
        wv.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                updateProgressBar(false);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        //abilitar funcoes extra js como alert
        wv.setWebChromeClient(new WebChromeClient());

        wv.loadUrl("file:///android_asset/index.html");
//        wv.loadUrl("http://www.google.com");

    }

    @Override
    public void onBackPressed() {
        updateProgressBar(false);
        if(wv.canGoBack()) {
            wv.goBack();
        } else {
            super.onBackPressed();
        }
    }

    //http://developer.android.com/guide/topics/ui/dialogs.html
    private void exibirDialog(){
        handler.post(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Olá Mundo")
                        .setTitle("Mensagem");
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }


    private void exibirConfirmDialog() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Deseja sair?");
                builder.setPositiveButton("Sair", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }



    public void updateProgressBar(final boolean visivel){
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(visivel){
                    pb.setVisibility(View.VISIBLE);
                }else{
                    pb.setVisibility(View.GONE);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class JavaScriptInterface {

        Context mContext;

        /** Instantiate the interface and set the context */
        JavaScriptInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void toast(String mensagem)
        {
            Toast.makeText(mContext, mensagem, Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public void vibrarAparelho(){
            Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            v.vibrate(1000);
        }

        @JavascriptInterface
        public String botaoNome(){
            return "Botão carregado via interface Java";
        }

        @JavascriptInterface
        public void updateProgressBarVisibility(final boolean visivel){
            updateProgressBar(visivel);
        }

        @JavascriptInterface
        public int dpToPx(int dp) {
            DisplayMetrics displayMetrics = getBaseContext().getResources().getDisplayMetrics();
            int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
            return px;
        }

        @JavascriptInterface
        public int pxToDp(int px) {
            DisplayMetrics displayMetrics = getBaseContext().getResources().getDisplayMetrics();
            int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
            return dp;
        }

        @JavascriptInterface
        public void dialog() {
            exibirDialog();
        }

        @JavascriptInterface
        public void sair() {
            exibirConfirmDialog();
        }

        @JavascriptInterface
        public void telaSpinner() {
            Intent it = new Intent(MainActivity.this, SpinnerActivity.class);
            startActivity(it);
        }
    }
}