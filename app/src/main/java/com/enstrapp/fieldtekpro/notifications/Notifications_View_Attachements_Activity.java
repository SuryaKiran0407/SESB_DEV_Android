package com.enstrapp.fieldtekpro.notifications;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class Notifications_View_Attachements_Activity extends AppCompatActivity
{

    WebView webView;
    ImageView back_imageview;
    TextView title_textview, no_data_textview;
    String url = "", filename = "", filetype = "", file_mimetype = "";
    Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications_view_attachements_activity);

        webView = (WebView)findViewById(R.id.webView1);
        back_imageview = (ImageView)findViewById(R.id.back_imageview);
        title_textview = (TextView)findViewById(R.id.title_textview);
        no_data_textview = (TextView)findViewById(R.id.no_data_textview);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            url = extras.getString("url");
            filename = extras.getString("filename");
            filetype = extras.getString("filetype");
            title_textview.setText(filename);
            if (filetype.contains("pdf"))
            {
                new DownloadFileFromURL().execute(url);
            }
            else if (filetype.contains("doc"))
            {
                file_mimetype = "application/msword";
                new DownloadDOCFileFromURL().execute(url);
            }
            else if (filetype.contains("xls"))
            {
                file_mimetype = "application/vnd.ms-excel";
                new DownloadDOCFileFromURL().execute(url);
            }
            else
            {
                Display_Image();
            }
        }
        else
        {
            no_data_textview.setVisibility(View.VISIBLE);
            webView.setVisibility(View.VISIBLE);
        }

        back_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notifications_View_Attachements_Activity.this.finish();
            }
        });



    }


    public void Display_Image()
    {
        custom_progress_dialog.show_progress_dialog(Notifications_View_Attachements_Activity.this,getString(R.string.loading));

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.setInitialScale(1);

        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onLoadResource(WebView view, String url)
            {
                super.onLoadResource(view, url);
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                super.onPageStarted(view, url, favicon);
            }
            @Override
            public void onPageFinished(WebView view, String url)
            {
                super.onPageFinished(view, url);
                custom_progress_dialog.dismiss_progress_dialog();
            }
            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr)
            {
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
                no_data_textview.setVisibility(View.VISIBLE);
                webView.setVisibility(View.VISIBLE);
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl(url);
    }


    class DownloadFileFromURL extends AsyncTask<String, String, String>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(Notifications_View_Attachements_Activity.this,getString(R.string.loading));
        }
        @Override
        protected String doInBackground(String... f_url)
        {
            int count;
            try
            {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                int lenghtOfFile = conection.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                OutputStream output = new FileOutputStream("/sdcard/"+filename+".pdf");
                byte data[] = new byte[2048];
                long total = 0;
                while ((count = input.read(data)) != -1)
                {
                    total += count;
                    publishProgress(""+(int)((total*100)/lenghtOfFile));
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
            }
            catch (Exception e)
            {
            }
            return null;
        }
        protected void onProgressUpdate(String... progress)
        {
        }
        @Override
        protected void onPostExecute(String file_url)
        {
            String imagePath = Environment.getExternalStorageDirectory().toString() + "/"+filename+".pdf";
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setAllowFileAccessFromFileURLs(true);
            webSettings.setAllowUniversalAccessFromFileURLs(true);
            webSettings.setBuiltInZoomControls(true);
            webView.setWebViewClient(new WebViewClient()
            {
                @Override
                public void onLoadResource(WebView view, String url)
                {
                    super.onLoadResource(view, url);
                }
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon)
                {
                    super.onPageStarted(view, url, favicon);
                }
                @Override
                public void onPageFinished(WebView view, String url)
                {
                    super.onPageFinished(view, url);
                    custom_progress_dialog.dismiss_progress_dialog();
                }
                @TargetApi(android.os.Build.VERSION_CODES.M)
                @Override
                public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr)
                {
                    onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
                }
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url)
                {
                    view.loadUrl(url);
                    return true;
                }
            });
            File file = new File(imagePath);
            webView.loadUrl("file:///android_asset/pdfjs/web/viewer.html?file=" + file.getAbsolutePath() + "#zoom=page-width");
        }
    }


    class DownloadDOCFileFromURL extends AsyncTask<String, String, String>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(Notifications_View_Attachements_Activity.this,getString(R.string.loading));
        }
        @Override
        protected String doInBackground(String... f_url)
        {
            int count;
            try
            {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                int lenghtOfFile = conection.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                OutputStream output = new FileOutputStream("/sdcard/"+filename);
                byte data[] = new byte[2048];
                long total = 0;
                while ((count = input.read(data)) != -1)
                {
                    total += count;
                    publishProgress(""+(int)((total*100)/lenghtOfFile));
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
            }
            catch (Exception e)
            {
            }
            return null;
        }
        protected void onProgressUpdate(String... progress)
        {
        }
        @Override
        protected void onPostExecute(String file_url)
        {
            custom_progress_dialog.dismiss_progress_dialog();
            try
            {
                String imagePath = Environment.getExternalStorageDirectory().toString() + "/"+filename;
                File file = new File(imagePath);
                try
                {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(file.getAbsoluteFile()), file_mimetype);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                }
                catch (Exception a)
                {
                    no_data_textview.setText("File cannot be opened. Please install Office.");
                    no_data_textview.setVisibility(View.VISIBLE);
                    webView.setVisibility(View.GONE);
                }

            }
            catch (ActivityNotFoundException a)
            {
                no_data_textview.setText("File cannot be opened. Please install Office.");
                no_data_textview.setVisibility(View.VISIBLE);
                webView.setVisibility(View.GONE);
            }
        }
    }


}
