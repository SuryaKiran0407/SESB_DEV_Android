package com.enstrapp.fieldtekpro.orders;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.enstrapp.fieldtekpro.FileUpload.FileUtils;
import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.enstrapp.fieldtekpro.networkconnection.ConnectionDetector;
import com.enstrapp.fieldtekpro.networkconnectiondialog.Network_Connection_Dialog;
import com.enstrapp.fieldtekpro.notifications.Notif_EtDocs_Parcelable;
import com.enstrapp.fieldtekpro.notifications.Notifications_View_Attachements_Activity;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrdersConfirmAttachment_Activity extends AppCompatActivity implements View.OnClickListener {

    Error_Dialog error_dialog = new Error_Dialog();
    private static final String IMAGE_DIRECTORY_NAME = "FIELDTEKPRO";
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    String path = "", filee_name = "", ordrId = "", wapinr = "";
    private List<Notif_EtDocs_Parcelable> attachments_list = new ArrayList<>();
    RecyclerView attachments_rv;
    AttachmentsAdapter adapter;
    private static final int RESULT_LOAD_IMG = 2;
    private static final int REQUEST_CODE = 6384;
    FloatingActionButton fab;
    ImageButton back_ib;
    Button cancel_bt, save_bt;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    Error_Dialog errorDialog = new Error_Dialog();
    ArrayList<Notif_EtDocs_Parcelable> etdocs_parcablearray = new ArrayList<Notif_EtDocs_Parcelable>();
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    Network_Connection_Dialog network_connection_dialog = new Network_Connection_Dialog();
    Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_attachment_activity);

        Bundle extras = getIntent().getExtras();

        DATABASE_NAME = OrdersConfirmAttachment_Activity.this.getString(R.string.database_name);
        App_db = OrdersConfirmAttachment_Activity.this
                .openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        attachments_rv = findViewById(R.id.attachments_rv);
        fab = findViewById(R.id.fab);
        back_ib = findViewById(R.id.back_ib);
        cancel_bt = findViewById(R.id.cancel_bt);
        save_bt = findViewById(R.id.save_bt);

        if (extras != null) {
            ordrId = extras.getString("ordrId");
            wapinr = extras.getString("wapinr");
            if (ordrId != null && !ordrId.equals("")) {
                Display_Attachments();
            } else if (wapinr != null && !wapinr.equals("")) {
                Cursor cursor = null;
                try {
                    cursor = App_db.rawQuery("select * from DUE_ORDERS_EtDocs where Zobjid = ?",
                            new String[]{wapinr});
                    if (cursor != null && cursor.getCount() > 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                Notif_EtDocs_Parcelable notif_etDocs_parcelable = new Notif_EtDocs_Parcelable();
                                notif_etDocs_parcelable.setZobjid(cursor.getString(2));
                                notif_etDocs_parcelable.setZdoctype(cursor.getString(3));
                                notif_etDocs_parcelable.setZdoctypeitem(cursor.getString(4));
                                notif_etDocs_parcelable.setFilename(cursor.getString(5));
                                notif_etDocs_parcelable.setFiletype(cursor.getString(6));
                                notif_etDocs_parcelable.setFsize(cursor.getString(7));
                                notif_etDocs_parcelable.setContent(cursor.getString(8));
                                notif_etDocs_parcelable.setDocid(cursor.getString(9));
                                notif_etDocs_parcelable.setDoctype(cursor.getString(10));
                                notif_etDocs_parcelable.setObjtype(cursor.getString(11));
                                notif_etDocs_parcelable.setStatus("old");
                                attachments_list.add(notif_etDocs_parcelable);
                            } while (cursor.moveToNext());
                        }
                    }
                } catch (Exception e) {
                } finally {
                    if (cursor != null)
                        cursor.close();
                }
                try {
                    cursor = App_db.rawQuery("select * from Orders_Attachments where Object_id" +
                            " = ?", new String[]{wapinr});
                    if (cursor != null && cursor.getCount() > 0) {
                        if (cursor.moveToFirst()) {
                            do {

                                try {
                                    String file_path = cursor.getString(4);
                                    File file = new File(file_path);
                                    final String filee_name = file.getName();
                                    Uri selectedUri = Uri.fromFile(file);
                                    final String fileExtension = MimeTypeMap.getFileExtensionFromUrl(selectedUri.toString());
                                    if (fileExtension.equalsIgnoreCase("txt") || fileExtension.equalsIgnoreCase("pdf") || fileExtension.equalsIgnoreCase("pdf") || fileExtension.equalsIgnoreCase("png") || fileExtension.equalsIgnoreCase("doc") || fileExtension.equalsIgnoreCase("docx") || fileExtension.equalsIgnoreCase("png") || fileExtension.equalsIgnoreCase("jpg") || fileExtension.equalsIgnoreCase("png") || fileExtension.equalsIgnoreCase("xls") || fileExtension.equalsIgnoreCase("xlsx") || fileExtension.equalsIgnoreCase("dxf") || fileExtension.equalsIgnoreCase("dwf") || fileExtension.equalsIgnoreCase("dxf") || fileExtension.equalsIgnoreCase("dwg")) {
                                        final String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension);
                                        final int file_size = Integer.parseInt(String.valueOf(file.length()));
                                        byte[] byteArray = null;
                                        InputStream inputStream = new FileInputStream(file_path);
                                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                        byte[] b = new byte[4096 * 8];
                                        int bytesRead = 0;
                                        while ((bytesRead = inputStream.read(b)) != -1) {
                                            bos.write(b, 0, bytesRead);
                                        }
                                        byteArray = bos.toByteArray();
                                        final String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

                                        Notif_EtDocs_Parcelable notif_etDocs_parcelable = new Notif_EtDocs_Parcelable();
                                        notif_etDocs_parcelable.setZobjid(wapinr);
                                        notif_etDocs_parcelable.setZdoctype("W");
                                        notif_etDocs_parcelable.setZdoctypeitem("WH");
                                        notif_etDocs_parcelable.setFilename(filee_name);
                                        notif_etDocs_parcelable.setFiletype(mimeType);
                                        notif_etDocs_parcelable.setFsize(String.valueOf(file_size));
                                        notif_etDocs_parcelable.setFilepath(file_path);
                                        notif_etDocs_parcelable.setContent(encodedImage);
                                        notif_etDocs_parcelable.setDocid("");
                                        notif_etDocs_parcelable.setDoctype("");
                                        notif_etDocs_parcelable.setObjtype("WCA");
                                        notif_etDocs_parcelable.setStatus("NEW");
                                        attachments_list.add(notif_etDocs_parcelable);
                                    } else {
                                        error_dialog.show_error_dialog(OrdersConfirmAttachment_Activity.this,
                                                "Please select correct format file. (TXT, PDF, PNG, DOC, DOCX, JPG, XLS, XLSX, DXF, DWF, DWG)");
                                    }

                                } catch (Exception e) {
                                }
                            } while (cursor.moveToNext());
                        }
                    }
                } catch (Exception e) {
                } finally {
                    if (cursor != null)
                        cursor.close();
                }
                Display_Attachments();
            }
        }

        fab.setOnClickListener(this);
        back_ib.setOnClickListener(this);
        cancel_bt.setOnClickListener(this);
        save_bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.back_ib):
                onBackPressed();
                break;

            case (R.id.cancel_bt):
                onBackPressed();
                break;

            case (R.id.save_bt):
                if (attachments_list.size() > 0) {
                    for (int i = 0; i < attachments_list.size(); i++) {
                        try {
                            if (ordrId != null && !ordrId.equals("")) {
                            } else {
                                ordrId = wapinr;
                            }
                            String image_path1 = attachments_list.get(i).getFilepath();
//                            JSONObject jsonobject = new JSONObject(image_path1);
                            File imgFile = new File(image_path1);
                            if (imgFile.exists()) {
                                App_db.beginTransaction();
                                UUID uniqueKey_uuid = UUID.randomUUID();
                                String sql11 = "Insert into Orders_Attachments (UUID, Object_id," +
                                        " Object_type, file_path, jsa_id) values(?,?,?,?,?);";
                                SQLiteStatement statement11 = App_db.compileStatement(sql11);
                                statement11.clearBindings();
                                statement11.bindString(1, uniqueKey_uuid.toString());
                                statement11.bindString(2, ordrId);
                                statement11.bindString(3, "Orders");
                                statement11.bindString(4, imgFile.toString());
                                statement11.bindString(5, "");
                                statement11.execute();
                                App_db.setTransactionSuccessful();
                                App_db.endTransaction();
                            }
                        } catch (Exception e) {
                        }
                    }
                    OrdersConfirmAttachment_Activity.this.finish();
                } else {
                    error_dialog.show_error_dialog(OrdersConfirmAttachment_Activity.this,
                            "Please add atleast one attachment");
                }
                break;

            case (R.id.fab):
                final BottomSheetDialog dialog = new BottomSheetDialog(OrdersConfirmAttachment_Activity.this);
                dialog.setContentView(R.layout.notifications_attachments_select_dialog);
                TextView camera_textview = dialog.findViewById(R.id.camera_textview);
                TextView gallery_textview = dialog.findViewById(R.id.gallery_textview);
                TextView file_textview = dialog.findViewById(R.id.file_textview);
                dialog.show();
                camera_textview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (attachments_list.size() >= 5) {
                            error_dialog.show_error_dialog(OrdersConfirmAttachment_Activity.this, "You cannot attach more than 5 files");
                        } else {
                            if (!isDeviceSupportCamera()) {
                                error_dialog.show_error_dialog(OrdersConfirmAttachment_Activity.this, "Sorry! Your device doesn't support camera");
                            } else {
                                dialog.dismiss();
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                            }
                        }
                    }
                });
                gallery_textview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (attachments_list.size() >= 5) {
                            error_dialog.show_error_dialog(OrdersConfirmAttachment_Activity.this, "You cannot attach more than 5 files");
                        } else {
                            dialog.dismiss();
                            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
                        }
                    }
                });
                file_textview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (attachments_list.size() >= 5) {
                            error_dialog.show_error_dialog(OrdersConfirmAttachment_Activity.this, "You cannot attach more than 5 files");
                        } else {
                            Intent target = FileUtils.createGetContentIntent();
                            Intent intent = Intent.createChooser(target, "Choose File");
                            try {
                                dialog.dismiss();
                                startActivityForResult(intent, REQUEST_CODE);
                            } catch (ActivityNotFoundException e) {
                            }
                        }
                    }
                });
                break;
        }
    }

    private boolean isDeviceSupportCamera() {
        if (OrdersConfirmAttachment_Activity.this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true; // this device has a camera
        } else {
            return false; // no camera on this device
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAMERA_CAPTURE_IMAGE_REQUEST_CODE:
                if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
                    if (resultCode == RESULT_OK) {
                        try {
                            Bitmap photo = (Bitmap) data.getExtras().get("data");
                            Uri selectedImage = getImageUri(OrdersConfirmAttachment_Activity.this, photo);
                            String image_Path = getRealPathFromURI(selectedImage);
                            final File file = new File(image_Path);
                            Uri selectedUri = Uri.fromFile(file);
                            final String fileExtension = MimeTypeMap.getFileExtensionFromUrl(selectedUri.toString());
                            if (fileExtension.equalsIgnoreCase("txt") || fileExtension.equalsIgnoreCase("pdf") || fileExtension.equalsIgnoreCase("png") || fileExtension.equalsIgnoreCase("doc") || fileExtension.equalsIgnoreCase("docx") || fileExtension.equalsIgnoreCase("png") || fileExtension.equalsIgnoreCase("jpg") || fileExtension.equalsIgnoreCase("xls") || fileExtension.equalsIgnoreCase("xlsx") || fileExtension.equalsIgnoreCase("dxf") || fileExtension.equalsIgnoreCase("dwf") || fileExtension.equalsIgnoreCase("dxf") || fileExtension.equalsIgnoreCase("dwg")) {
                                final String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension);
                                final int file_size = Integer.parseInt(String.valueOf(file.length()));
                                byte[] byteArray = null;
                                InputStream inputStream = new FileInputStream(image_Path);
                                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                byte[] b = new byte[4096 * 8];
                                int bytesRead = 0;
                                while ((bytesRead = inputStream.read(b)) != -1) {
                                    bos.write(b, 0, bytesRead);
                                }
                                byteArray = bos.toByteArray();
                                final String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
                                final Dialog file_rename_dialog = new Dialog(OrdersConfirmAttachment_Activity.this);
                                file_rename_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                file_rename_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                file_rename_dialog.setCancelable(false);
                                file_rename_dialog.setCanceledOnTouchOutside(false);
                                file_rename_dialog.setContentView(R.layout.camera_file_rename);
                                final EditText file_rename = (EditText) file_rename_dialog.findViewById(R.id.file_rename);
                                Button yes_button = (Button) file_rename_dialog.findViewById(R.id.yes_button);
                                Button no_button = (Button) file_rename_dialog.findViewById(R.id.no_button);
                                yes_button.setText("Ok");
                                no_button.setText("Cancel");
                                file_rename_dialog.show();
                                no_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        file_rename_dialog.dismiss();
                                    }
                                });
                                yes_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (!file_rename.getText().toString().equals("") && file_rename.getText().toString() != null) {
                                            file_rename_dialog.dismiss();
                                            File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), IMAGE_DIRECTORY_NAME);
                                            filee_name = file.getName();

                                            File to = new File(mediaStorageDir, file_rename.getText().toString() + ".jpg");
                                            if (file.exists()) {
                                                file.renameTo(to);
                                                File file2 = new File(mediaStorageDir.getPath() + File.separator + file_rename.getText().toString() + ".jpg");
                                                path = file2.getPath();
                                                filee_name = file_rename.getText().toString() + ".jpg";
                                            }

                                            Notif_EtDocs_Parcelable notif_etDocs_parcelable = new Notif_EtDocs_Parcelable();
                                            notif_etDocs_parcelable.setZobjid("");
                                            notif_etDocs_parcelable.setZdoctype("Q");
                                            notif_etDocs_parcelable.setZdoctypeitem("QH");
                                            notif_etDocs_parcelable.setFilename(filee_name);
                                            notif_etDocs_parcelable.setFiletype(mimeType);
                                            notif_etDocs_parcelable.setFsize(String.valueOf(file_size));
                                            notif_etDocs_parcelable.setFilepath(path);
                                            notif_etDocs_parcelable.setContent(encodedImage);
                                            notif_etDocs_parcelable.setDocid("");
                                            notif_etDocs_parcelable.setDoctype("");
                                            notif_etDocs_parcelable.setObjtype("EQUI");
                                            notif_etDocs_parcelable.setStatus("NEW");
                                            attachments_list.add(notif_etDocs_parcelable);

                                            Display_Attachments();

                                        } else {
                                            error_dialog.show_error_dialog(OrdersConfirmAttachment_Activity.this, "Please Enter Description");
                                        }
                                    }
                                });
                            } else {

                            }
                        } catch (Exception e) {
                        }
                    } else if (resultCode == RESULT_CANCELED) {
                        Toast.makeText(OrdersConfirmAttachment_Activity.this, "You have cancelled image capture", Toast.LENGTH_LONG).show();  // user cancelled Image capture
                    } else {
                        Toast.makeText(OrdersConfirmAttachment_Activity.this, "Sorry! Failed to capture image", Toast.LENGTH_LONG).show(); // failed to capture image
                    }
                }
                break;
            case RESULT_LOAD_IMG:
                if (requestCode == RESULT_LOAD_IMG) {
                    if (resultCode == RESULT_OK) {
                        if (data != null && !data.equals("")) {
                            try {
                                Uri selectedImage = data.getData();
                                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                                Cursor cursor = OrdersConfirmAttachment_Activity.this.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                                cursor.moveToFirst();
                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                final String file_path = cursor.getString(columnIndex);
                                cursor.close();
                                File file = new File(file_path);
                                final String filee_name = file.getName();
                                Uri selectedUri = Uri.fromFile(file);
                                final String fileExtension = MimeTypeMap.getFileExtensionFromUrl(selectedUri.toString());
                                if (fileExtension.equalsIgnoreCase("txt") || fileExtension.equalsIgnoreCase("pdf") || fileExtension.equalsIgnoreCase("pdf") || fileExtension.equalsIgnoreCase("png") || fileExtension.equalsIgnoreCase("doc") || fileExtension.equalsIgnoreCase("docx") || fileExtension.equalsIgnoreCase("png") || fileExtension.equalsIgnoreCase("jpg") || fileExtension.equalsIgnoreCase("png") || fileExtension.equalsIgnoreCase("xls") || fileExtension.equalsIgnoreCase("xlsx") || fileExtension.equalsIgnoreCase("dxf") || fileExtension.equalsIgnoreCase("dwf") || fileExtension.equalsIgnoreCase("dxf") || fileExtension.equalsIgnoreCase("dwg")) {
                                    final String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension);
                                    final int file_size = Integer.parseInt(String.valueOf(file.length()));
                                    byte[] byteArray = null;
                                    InputStream inputStream = new FileInputStream(file_path);
                                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                    byte[] b = new byte[4096 * 8];
                                    int bytesRead = 0;
                                    while ((bytesRead = inputStream.read(b)) != -1) {
                                        bos.write(b, 0, bytesRead);
                                    }
                                    byteArray = bos.toByteArray();
                                    final String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

                                    Notif_EtDocs_Parcelable notif_etDocs_parcelable = new Notif_EtDocs_Parcelable();
                                    notif_etDocs_parcelable.setZobjid("");
                                    notif_etDocs_parcelable.setZdoctype("W");
                                    notif_etDocs_parcelable.setZdoctypeitem("WH");
                                    notif_etDocs_parcelable.setFilename(filee_name);
                                    notif_etDocs_parcelable.setFiletype(mimeType);
                                    notif_etDocs_parcelable.setFsize(String.valueOf(file_size));
                                    notif_etDocs_parcelable.setFilepath(file_path);
                                    notif_etDocs_parcelable.setContent(encodedImage);
                                    notif_etDocs_parcelable.setDocid("");
                                    notif_etDocs_parcelable.setDoctype("");
                                    notif_etDocs_parcelable.setObjtype("EQUI");
                                    notif_etDocs_parcelable.setStatus("NEW");
                                    attachments_list.add(notif_etDocs_parcelable);

                                    Display_Attachments();

                                } else {
                                    error_dialog.show_error_dialog(OrdersConfirmAttachment_Activity.this, "Please select correct format file. (TXT, PDF, PNG, DOC, DOCX, JPG, XLS, XLSX, DXF, DWF, DWG)");
                                }
                            } catch (Exception e) {
                            }
                        }
                    }
                }
                break;
            case REQUEST_CODE:
                if (requestCode == REQUEST_CODE) {
                    if (resultCode == RESULT_OK) {
                        if (data != null && !data.equals("")) {
                            try {
                                final Uri uri = data.getData();
                                final String path = FileUtils.getPath(OrdersConfirmAttachment_Activity.this, uri);
                                File file = new File(path);
                                final String filee_name = file.getName();
                                Uri selectedUri = Uri.fromFile(file);
                                final String fileExtension = MimeTypeMap.getFileExtensionFromUrl(selectedUri.toString());
                                if (fileExtension.equalsIgnoreCase("txt") || fileExtension.equalsIgnoreCase("pdf") || fileExtension.equalsIgnoreCase("pdf") || fileExtension.equalsIgnoreCase("png") || fileExtension.equalsIgnoreCase("doc") || fileExtension.equalsIgnoreCase("docx") || fileExtension.equalsIgnoreCase("png") || fileExtension.equalsIgnoreCase("jpg") || fileExtension.equalsIgnoreCase("png") || fileExtension.equalsIgnoreCase("xls") || fileExtension.equalsIgnoreCase("xlsx") || fileExtension.equalsIgnoreCase("dxf") || fileExtension.equalsIgnoreCase("dwf") || fileExtension.equalsIgnoreCase("dxf") || fileExtension.equalsIgnoreCase("dwg")) {
                                    final String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension);
                                    final int file_size = Integer.parseInt(String.valueOf(file.length()));
                                    byte[] byteArray = null;
                                    InputStream inputStream = new FileInputStream(path);
                                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                    byte[] b = new byte[4096 * 8];
                                    int bytesRead = 0;
                                    while ((bytesRead = inputStream.read(b)) != -1) {
                                        bos.write(b, 0, bytesRead);
                                    }
                                    byteArray = bos.toByteArray();
                                    final String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

                                    Notif_EtDocs_Parcelable notif_etDocs_parcelable = new Notif_EtDocs_Parcelable();
                                    notif_etDocs_parcelable.setZobjid("");
                                    notif_etDocs_parcelable.setZdoctype("Q");
                                    notif_etDocs_parcelable.setZdoctypeitem("QH");
                                    notif_etDocs_parcelable.setFilename(filee_name);
                                    notif_etDocs_parcelable.setFiletype(mimeType);
                                    notif_etDocs_parcelable.setFsize(String.valueOf(file_size));
                                    notif_etDocs_parcelable.setFilepath(path);
                                    notif_etDocs_parcelable.setContent(encodedImage);
                                    notif_etDocs_parcelable.setDocid("");
                                    notif_etDocs_parcelable.setDoctype("");
                                    notif_etDocs_parcelable.setObjtype("EQUI");
                                    notif_etDocs_parcelable.setStatus("NEW");
                                    attachments_list.add(notif_etDocs_parcelable);
                                    Display_Attachments();

                                } else {
                                    error_dialog.show_error_dialog(OrdersConfirmAttachment_Activity.this,
                                            "Please select correct format file. (TXT, PDF, PNG, DOC, DOCX, JPG, XLS, XLSX, DXF, DWF, DWG)");
                                }

                            } catch (Exception e) {
                            }
                        }
                    }
                }
                break;
        }
    }

    protected void Display_Attachments() {
        if (attachments_list.size() > 0) {
            adapter = new AttachmentsAdapter(OrdersConfirmAttachment_Activity.this, attachments_list);
            attachments_rv.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(OrdersConfirmAttachment_Activity.this);
            attachments_rv.setLayoutManager(layoutManager);
            attachments_rv.setItemAnimator(new DefaultItemAnimator());
            attachments_rv.setAdapter(adapter);
            attachments_rv.setVisibility(View.VISIBLE);
        }
    }

    private class AttachmentsAdapter extends RecyclerView.Adapter<AttachmentsAdapter.MyViewHolder> {
        private Context mContext;
        private List<Notif_EtDocs_Parcelable> attachmentsList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            protected TextView filename_textview;
            protected TextView file_objtype_textview;
            protected TextView file_size_textview;
            protected TextView file_path_textview;
            protected ImageView pic_imageview;
            protected TextView uuid_textview;
            protected CheckBox checkbox;
            protected LinearLayout layout;

            public MyViewHolder(View view) {
                super(view);
                filename_textview = (TextView) view.findViewById(R.id.filename_textview);
                checkbox = (CheckBox) view.findViewById(R.id.status_checkbox);
                file_objtype_textview = (TextView) view.findViewById(R.id.file_objtype_textview);
                file_size_textview = (TextView) view.findViewById(R.id.file_size_textview);
                file_path_textview = (TextView) view.findViewById(R.id.file_path_textview);
                pic_imageview = (ImageView) view.findViewById(R.id.pic_imageview);
                uuid_textview = (TextView) view.findViewById(R.id.uuid_textview);
                layout = view.findViewById(R.id.layout);
            }
        }

        public AttachmentsAdapter(Context mContext, List<Notif_EtDocs_Parcelable> list) {
            this.mContext = mContext;
            this.attachmentsList = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_attachment_listdata, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final Notif_EtDocs_Parcelable nep = attachmentsList.get(position);
            holder.checkbox.setTag(position);
            holder.checkbox.setChecked((nep.isSelected() == true ? true : false));
            holder.filename_textview.setText(nep.getFilename());
            holder.file_objtype_textview.setVisibility(View.GONE);
            holder.layout.setTag(position);
            String object_type = nep.getObjtype();
            /*if (object_type.equalsIgnoreCase("EQUI")) {
                holder.file_objtype_textview.setText("Equipment");
                holder.file_objtype_textview.setBackgroundColor(getResources().getColor(R.color.footer_color));
            } else {
                holder.file_objtype_textview.setText("Notification");
                holder.file_objtype_textview.setBackgroundColor(getResources().getColor(R.color.dark_green));
            }*/
            String size = nep.getFsize();
            int int_size = Integer.parseInt(size.trim());
            int size_mb = int_size / 1024;
            holder.file_size_textview.setText(size_mb + " KB");
            holder.file_path_textview.setText(nep.getFilepath());
            holder.uuid_textview.setText(nep.getUuid());
            final String file_type = nep.getFiletype();
            if (file_type.contains("png") || file_type.contains("jpg") || file_type.contains("jpeg")) {
                try {
                    File image_file = new File(nep.getFilepath());
                    Bitmap myBitmap = BitmapFactory.decodeFile(image_file.getAbsolutePath());
                    holder.pic_imageview.setImageBitmap(myBitmap);
                } catch (Exception e) {
                }
            } else if (file_type.contains("PDF")) {
                holder.pic_imageview.setBackground(getResources().getDrawable(R.drawable.ic_pdf_icon));
            } else if (file_type.contains("pdf")) {
                holder.pic_imageview.setBackground(getResources().getDrawable(R.drawable.ic_pdf_icon));
            } else if (file_type.contains("doc") || file_type.contains("docx")) {
                holder.pic_imageview.setBackgroundResource(R.drawable.ic_doc_icon);
            } else if (file_type.contains("xls") || file_type.contains("xlsx")) {
                holder.pic_imageview.setBackgroundResource(R.drawable.ic_xlx_icon);
            } else if (file_type.contains("txt")) {
                holder.pic_imageview.setBackgroundResource(R.drawable.ic_txt_icon);
            } else if (file_type.contains("plain")) {
                holder.pic_imageview.setBackgroundResource(R.drawable.ic_txt_icon);
            }

            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (attachmentsList.get((Integer) v.getTag()).getStatus().equalsIgnoreCase("old")) {
                        cd = new ConnectionDetector(OrdersConfirmAttachment_Activity.this);
                        isInternetPresent = cd.isConnectingToInternet();
                        if (isInternetPresent) {
                            Intent viewattachments_intent = new Intent(OrdersConfirmAttachment_Activity.this, Notifications_View_Attachements_Activity.class);
                            viewattachments_intent.putExtra("url", attachmentsList.get((Integer) v.getTag()).getContent());
                            viewattachments_intent.putExtra("filename", holder.filename_textview.getText().toString());
                            viewattachments_intent.putExtra("filetype", file_type.toLowerCase());
                            startActivity(viewattachments_intent);
                        } else {
                            network_connection_dialog.show_network_connection_dialog(OrdersConfirmAttachment_Activity.this);
                        }
                    } else {
                        String file_path = holder.file_path_textview.getText().toString();
                        File file = new File(file_path);
                        Display_Image(file, holder.filename_textview.getText().toString());
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return attachmentsList.size();
        }
    }


    public void Display_Image(File file, String filename) {
        custom_progress_dialog.show_progress_dialog(OrdersConfirmAttachment_Activity.this, getString(R.string.loading));

        final Dialog decision_dialog = new Dialog(OrdersConfirmAttachment_Activity.this, R.style.AppTheme);
        decision_dialog.setCancelable(true);
        decision_dialog.setCanceledOnTouchOutside(true);
        decision_dialog.setContentView(R.layout.notifications_view_attachements_activity);
        final WebView webView = (WebView) decision_dialog.findViewById(R.id.webView1);
        final TextView title_textview = (TextView) decision_dialog.findViewById(R.id.title_textview);
        title_textview.setText(filename);
        ImageView back_imageview = (ImageView) decision_dialog.findViewById(R.id.back_imageview);

        decision_dialog.show();

        back_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decision_dialog.dismiss();
            }
        });

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

            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        //File file1 = new File("/sdcard/abc123.pdf");
        webView.loadUrl("file:///android_asset/pdfjs/web/viewer.html?file=" + file.getAbsolutePath() + "#zoom=page-width");
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = OrdersConfirmAttachment_Activity.this.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public List<Notif_EtDocs_Parcelable> getAttachmentsData() {
        return attachments_list;
    }

    private void getAttachment(String Wapinr) {
        attachments_list = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = App_db.rawQuery("select * from DUE_ORDERS_EtDocs where Zobjid = ?", new String[]{});
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        Notif_EtDocs_Parcelable odp = new Notif_EtDocs_Parcelable();
                        odp.setZobjid(cursor.getString(2));
                        odp.setZdoctype(cursor.getString(3));
                        odp.setZdoctypeitem(cursor.getString(4));
                        odp.setFilename(cursor.getString(5));
                        odp.setFiletype(cursor.getString(6));
                        odp.setFsize(cursor.getString(7));
                        odp.setContent(cursor.getString(8));
                        odp.setDocid(cursor.getString(9));
                        odp.setDoctype(cursor.getString(10));
                        odp.setObjtype(cursor.getString(11));
                        attachments_list.add(odp);
                    } while (cursor.moveToNext());
                }
            }
        } catch (Exception e) {

        }
    }

}
