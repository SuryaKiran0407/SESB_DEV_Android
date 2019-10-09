package com.enstrapp.fieldtekpro.notifications;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.enstrapp.fieldtekpro.networkconnection.ConnectionDetector;
import com.enstrapp.fieldtekpro.networkconnectiondialog.Network_Connection_Dialog;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.Map;

public class Notifications_View_Attachements_Activity extends AppCompatActivity {

    WebView webView;
    ImageView back_imageview;
    TextView title_textview, no_data_textview;
    String url = "", filename = "", objtype = "", doc_id = "", filetype = "", file_mimetype = "";
    Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog();
    ConnectionDetector cd;
    Network_Connection_Dialog network_connection_dialog = new Network_Connection_Dialog();
    Boolean isInternetPresent = false;
    Map<String, String> Notif_Attatchment_status;
    Error_Dialog error_dialog = new Error_Dialog();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications_view_attachements_activity);

        webView = (WebView) findViewById(R.id.webView1);
        back_imageview = (ImageView) findViewById(R.id.back_imageview);
        title_textview = (TextView) findViewById(R.id.title_textview);
        no_data_textview = (TextView) findViewById(R.id.no_data_textview);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            url = extras.getString("url");
            filename = extras.getString("filename");
            filetype = extras.getString("filetype");
            doc_id = extras.getString("doc_id");
            objtype = extras.getString("objtype");
            title_textview.setText(filename);
            if (url != null && !url.equals("")) {
                if (filetype.contains("pdf")) {
                    new DownloadFileFromURL().execute(getString(R.string.ip_address_port_attach, url));
                } else if (filetype.contains("doc")) {
                    file_mimetype = "application/msword";
                    new DownloadDOCFileFromURL().execute(getString(R.string.ip_address_port_attach, url));
                } else if (filetype.contains("xls")) {
                    file_mimetype = "application/vnd.ms-excel";
                    new DownloadDOCFileFromURL().execute(getString(R.string.ip_address_port_attach, url));
                } else {
                    Display_Image();
                }
            } else {
                new GetAttachmentUri().execute(doc_id, objtype, filetype, filename);
            }
        } else {
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


    public void Display_Image() {
        custom_progress_dialog
                .show_progress_dialog(Notifications_View_Attachements_Activity.this,
                        getString(R.string.download));

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

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                custom_progress_dialog.dismiss_progress_dialog();
            }

            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
                no_data_textview.setVisibility(View.VISIBLE);
                webView.setVisibility(View.VISIBLE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl(getString(R.string.ip_address_port_attach, url));
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            custom_progress_dialog
                    .show_progress_dialog(Notifications_View_Attachements_Activity.this,
                            getString(R.string.download));
        }

        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                int lenghtOfFile = conection.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                OutputStream output = new FileOutputStream("/sdcard/" + filename + ".pdf");
                byte data[] = new byte[2048];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {
            String imagePath = Environment.getExternalStorageDirectory().toString() + "/" + filename + ".pdf";
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setAllowFileAccessFromFileURLs(true);
            webSettings.setAllowUniversalAccessFromFileURLs(true);
            webSettings.setBuiltInZoomControls(true);
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onLoadResource(WebView view, String url) {
                    super.onLoadResource(view, url);
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    custom_progress_dialog.dismiss_progress_dialog();
                }

                @TargetApi(android.os.Build.VERSION_CODES.M)
                @Override
                public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                    onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(),
                            req.getUrl().toString());
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
            File file = new File(imagePath);
            webView.loadUrl("file:///android_asset/pdfjs/web/viewer.html?file="
                    + file.getAbsolutePath() + "#zoom=page-width");
        }
    }


    class DownloadDOCFileFromURL extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            custom_progress_dialog
                    .show_progress_dialog(Notifications_View_Attachements_Activity.this,
                            getString(R.string.download));
        }

        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                int lenghtOfFile = conection.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                OutputStream output = new FileOutputStream("/sdcard/" + filename);
                byte data[] = new byte[2048];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {
            custom_progress_dialog.dismiss_progress_dialog();
            try {
                String imagePath = Environment.getExternalStorageDirectory().toString() + "/" + filename;
                File file = new File(imagePath);
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(file.getAbsoluteFile()), file_mimetype);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                } catch (Exception a) {
                    no_data_textview.setText("File cannot be opened. Please install Office.");
                    no_data_textview.setVisibility(View.VISIBLE);
                    webView.setVisibility(View.GONE);
                }

            } catch (ActivityNotFoundException a) {
                no_data_textview.setText("File cannot be opened. Please install Office.");
                no_data_textview.setVisibility(View.VISIBLE);
                webView.setVisibility(View.GONE);
            }
        }
    }

    private class GetAttachmentUri extends AsyncTask<String, Integer, Void> {
        protected void onPreExecute() {
            super.onPreExecute();
            custom_progress_dialog
                    .show_progress_dialog(Notifications_View_Attachements_Activity.this,
                            getString(R.string.download));
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                String DocId = params[0];
                String object_type = params[1];
                Notif_Attatchment_status = GetAttachmenturi
                        .DocTypeUri(Notifications_View_Attachements_Activity.this, DocId,
                                object_type);
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(final Void result) {
            super.onPostExecute(result);

            if (Notif_Attatchment_status.get("response_status") != null &&
                    !Notif_Attatchment_status.get("response_status").equals("")) {
                if (Notif_Attatchment_status.get("response_status")
                        .equalsIgnoreCase("Success")) {
                    try {
                        String url_data = Notif_Attatchment_status.get("response_data");
                        if (url_data.contains("ED9.enstrapp.com:8000")) {
                            url = url_data.replace("ED9.enstrapp.com:8000", getResources().getString(R.string.ip_address_port));
                        }
                        custom_progress_dialog.dismiss_progress_dialog();
                        if (filetype.contains("pdf")) {
                            new DownloadFileFromURL().execute(url);
                        } else if (filetype.contains("doc")) {
                            file_mimetype = "application/msword";
                            new DownloadDOCFileFromURL().execute(url);
                        } else if (filetype.contains("xls")) {
                            file_mimetype = "application/vnd.ms-excel";
                            new DownloadDOCFileFromURL().execute(url);
                        } else {
                            Display_Image();
                        }
                    } catch (Exception e) {
                    }
                } else {
                    custom_progress_dialog.dismiss_progress_dialog();
                    error_dialog.show_error_dialog(Notifications_View_Attachements_Activity.this,
                            getString(R.string.error_downloading));
                }
            } else {
                custom_progress_dialog.dismiss_progress_dialog();
                error_dialog.show_error_dialog(Notifications_View_Attachements_Activity.this,
                        getString(R.string.error_downloading));
            }
        }
    }

    public static String decode(String url) {
        try {
            String prevURL = "";
            String decodeURL = url;
            while (!prevURL.equals(decodeURL)) {
                prevURL = decodeURL;
                decodeURL = URLDecoder.decode(decodeURL, "UTF-8");
            }
            return decodeURL;
        } catch (UnsupportedEncodingException e) {
            return "Issue while decoding" + e.getMessage();
        }
    }
}
