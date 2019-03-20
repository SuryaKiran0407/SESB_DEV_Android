/*
package com.arcs_orders;

import android.app.Dialog;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;




import com.bumptech.glide.Glide;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.enstrapp.fieldtekpro.notifications.Notif_EtDocs_Parcelable;
import com.enstrapp.fieldtekpro.orders.Orders_Create_Activity;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class Orders_Create_Attachments_Fragment extends android.support.v4.app.Fragment {


    Error_Dialog error_dialog = new Error_Dialog();
    private static final String IMAGE_DIRECTORY_NAME = "FIELDTEKPRO";
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    String path = "", filee_name = "";
    private List<Notif_EtDocs_Parcelable> attachments_list = new ArrayList<>();
    RecyclerView attachments_rv;
    TextView nofiles_textview;
    AttachmentsAdapter adapter;
    private static final int RESULT_LOAD_IMG = 2;
    private static final int REQUEST_CODE = 6384;
    TextView remove_tv, noData_tv;
    Dialog decision_dialog;
    Orders_Create_Activity ma;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    //String Zobjid=ma.zobjid;
    public Orders_Create_Attachments_Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.notifications_attachments_fragment, container, false);

        attachments_rv = (RecyclerView) rootView.findViewById(R.id.attachments_rv);
        noData_tv = (TextView) rootView.findViewById(R.id.nofiles_textview);

        ma = (Orders_Create_Activity) getActivity();
        //String Zobjid=ma.zobjid;
        attachments_rv.setVisibility(View.GONE);
        noData_tv.setVisibility(View.VISIBLE);
        DATABASE_NAME = getActivity().getString(R.string.database_name);
        App_db = getActivity().openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        return rootView;

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed())
            onResume();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint())
            return;
        Orders_Create_Activity mainActivity = (Orders_Create_Activity) getActivity();
        mainActivity.fab.show();
        mainActivity.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
                dialog.setContentView(R.layout.notifications_attachments_select_dialog);
                TextView camera_textview = (TextView) dialog.findViewById(R.id.camera_textview);
                TextView gallery_textview = (TextView) dialog.findViewById(R.id.gallery_textview);
                TextView file_textview = (TextView) dialog.findViewById(R.id.file_textview);
                dialog.show();
                camera_textview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (attachments_list.size() >= 5) {
                            error_dialog.show_error_dialog(getActivity(), "You cannot attach more than 5 files");
                        } else {
                            if (!isDeviceSupportCamera()) {
                                error_dialog.show_error_dialog(getActivity(), "Sorry! Your device doesn't support camera");
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
                            error_dialog.show_error_dialog(getActivity(), "You cannot attach more than 5 files");
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
                            error_dialog.show_error_dialog(getActivity(), "You cannot attach more than 5 files");
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
            }
        });
    }


    private boolean isDeviceSupportCamera() {
        if (getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
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
                            Uri selectedImage = getImageUri(getActivity(), photo);
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
                                final Dialog file_rename_dialog = new Dialog(getActivity());
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
                                            notif_etDocs_parcelable.setZobjid(ma.zobjid);
                                            notif_etDocs_parcelable.setZdoctype("Q");
                                            notif_etDocs_parcelable.setZdoctypeitem("QH");
                                            notif_etDocs_parcelable.setFilename(filee_name);
                                            notif_etDocs_parcelable.setFiletype(mimeType);
                                            notif_etDocs_parcelable.setFsize(String.valueOf(file_size));
                                            notif_etDocs_parcelable.setFilepath(path);
                                            notif_etDocs_parcelable.setContent(encodedImage);
                                            notif_etDocs_parcelable.setDocid("");
                                            notif_etDocs_parcelable.setDoctype("");
                                            notif_etDocs_parcelable.setObjtype("BUS2088");
                                            attachments_list.add(notif_etDocs_parcelable);
                                            App_db.beginTransaction();
                                            UUID uniqueKey_uuid = UUID.randomUUID();
                                            String sql11 = "Insert into Orders_Attachments (OBJTYPE,ZOBJID,ZDOCTYPE,ZDOCTYPE_ITEM,FILENAME,FILETYPE,FSIZE,CONTENT,DOC_ID,DOC_TYPE,file_path,object_id,jsa_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                            SQLiteStatement EtOrderDocs_statement = App_db.compileStatement(sql11);
                                            EtOrderDocs_statement.clearBindings();
                                            EtOrderDocs_statement.bindString(1, notif_etDocs_parcelable.getObjtype());
                                            EtOrderDocs_statement.bindString(2, notif_etDocs_parcelable.getZobjid());
                                            EtOrderDocs_statement.bindString(3, notif_etDocs_parcelable.getZdoctype());
                                            EtOrderDocs_statement.bindString(4, notif_etDocs_parcelable.getZdoctypeitem());
                                            EtOrderDocs_statement.bindString(5, notif_etDocs_parcelable.getFilename());
                                            EtOrderDocs_statement.bindString(6, notif_etDocs_parcelable.getFiletype());
                                            EtOrderDocs_statement.bindString(7, notif_etDocs_parcelable.getFsize());
                                            EtOrderDocs_statement.bindString(8, notif_etDocs_parcelable.getContent());
                                            EtOrderDocs_statement.bindString(9, notif_etDocs_parcelable.getDocid());
                                            EtOrderDocs_statement.bindString(10, notif_etDocs_parcelable.getDoctype());
                                            EtOrderDocs_statement.bindString(11, notif_etDocs_parcelable.getFilepath());
                                            EtOrderDocs_statement.execute();
                                            App_db.setTransactionSuccessful();
                                            App_db.endTransaction();

                                            Display_Attachments();

                                        }
                                    }
                                });
                            } else {

                            }
                        } catch (Exception e) {
                            Log.v("Camera issue", "" + e.getMessage());
                        }
                    } else if (resultCode == RESULT_CANCELED) {
                        Toast.makeText(getActivity(), "You have cancelled image capture", Toast.LENGTH_LONG).show();  // user cancelled Image capture
                    } else {
                        Toast.makeText(getActivity(), "Sorry! Failed to capture image", Toast.LENGTH_LONG).show(); // failed to capture image
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
                                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                                cursor.moveToFirst();
                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                final String file_path = cursor.getString(columnIndex);
                                cursor.close();
                                final File file = new File(file_path);
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
                                    final Dialog add_file_dialog = new Dialog(getActivity());
                                    add_file_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                    add_file_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    add_file_dialog.setCancelable(false);
                                    add_file_dialog.setCanceledOnTouchOutside(false);
                                    add_file_dialog.setContentView(R.layout.add_file_dialog);
                                    Notif_EtDocs_Parcelable notif_etDocs_parcelable = new Notif_EtDocs_Parcelable();
                                    notif_etDocs_parcelable.setZobjid(ma.zobjid);
                                    notif_etDocs_parcelable.setZdoctype("Q");
                                    notif_etDocs_parcelable.setZdoctypeitem("QH");
                                    notif_etDocs_parcelable.setFilename(filee_name);
                                    notif_etDocs_parcelable.setFiletype(mimeType);
                                    notif_etDocs_parcelable.setFsize(String.valueOf(file_size));
                                    notif_etDocs_parcelable.setFilepath(file_path);
                                    notif_etDocs_parcelable.setContent(encodedImage);
                                    notif_etDocs_parcelable.setDocid("");
                                    notif_etDocs_parcelable.setDoctype("");
                                    notif_etDocs_parcelable.setObjtype("BUS2088");
                                    attachments_list.add(notif_etDocs_parcelable);
                                    App_db.beginTransaction();
                                    UUID uniqueKey_uuid = UUID.randomUUID();
                                    String sql11 = "Insert into Orders_Attachments (OBJTYPE,ZOBJID,ZDOCTYPE,ZDOCTYPE_ITEM,FILENAME,FILETYPE,FSIZE,CONTENT,DOC_ID,DOC_TYPE,file_path,object_id,jsa_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                    SQLiteStatement EtOrderDocs_statement = App_db.compileStatement(sql11);
                                    EtOrderDocs_statement.clearBindings();
                                    EtOrderDocs_statement.bindString(1, notif_etDocs_parcelable.getObjtype());
                                    EtOrderDocs_statement.bindString(2, notif_etDocs_parcelable.getZobjid());
                                    EtOrderDocs_statement.bindString(3, notif_etDocs_parcelable.getZdoctype());
                                    EtOrderDocs_statement.bindString(4, notif_etDocs_parcelable.getZdoctypeitem());
                                    EtOrderDocs_statement.bindString(5, notif_etDocs_parcelable.getFilename());
                                    EtOrderDocs_statement.bindString(6, notif_etDocs_parcelable.getFiletype());
                                    EtOrderDocs_statement.bindString(7, notif_etDocs_parcelable.getFsize());
                                    EtOrderDocs_statement.bindString(8, notif_etDocs_parcelable.getContent());
                                    EtOrderDocs_statement.bindString(9, notif_etDocs_parcelable.getDocid());
                                    EtOrderDocs_statement.bindString(10, notif_etDocs_parcelable.getDoctype());
                                    EtOrderDocs_statement.bindString(11, notif_etDocs_parcelable.getFilepath());
                                    EtOrderDocs_statement.execute();
                                    App_db.setTransactionSuccessful();
                                    App_db.endTransaction();

                                    Display_Attachments();
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
                                final String path = FileUtils.getPath(getActivity(), uri);
                                final File file = new File(path);
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
                                    final Dialog add_file_dialog = new Dialog(getActivity());
                                    add_file_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                    add_file_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    add_file_dialog.setCancelable(false);
                                    add_file_dialog.setCanceledOnTouchOutside(false);
                                    add_file_dialog.setContentView(R.layout.add_file_dialog);

                                    Notif_EtDocs_Parcelable notif_etDocs_parcelable = new Notif_EtDocs_Parcelable();
                                    notif_etDocs_parcelable.setZobjid(ma.zobjid);
                                    notif_etDocs_parcelable.setZdoctype("Q");
                                    notif_etDocs_parcelable.setZdoctypeitem("QH");
                                    notif_etDocs_parcelable.setFilename(filee_name);
                                    notif_etDocs_parcelable.setFiletype(mimeType);
                                    notif_etDocs_parcelable.setFsize(String.valueOf(file_size));
                                    notif_etDocs_parcelable.setFilepath(path);
                                    notif_etDocs_parcelable.setContent(encodedImage);
                                    notif_etDocs_parcelable.setDocid("");
                                    notif_etDocs_parcelable.setDoctype("");
                                    notif_etDocs_parcelable.setObjtype("BUS2088");
                                    attachments_list.add(notif_etDocs_parcelable);
                                    App_db.beginTransaction();
                                    UUID uniqueKey_uuid = UUID.randomUUID();
                                    String sql11 = "Insert into Orders_Attachments (OBJTYPE,ZOBJID,ZDOCTYPE,ZDOCTYPE_ITEM,FILENAME,FILETYPE,FSIZE,CONTENT,DOC_ID,DOC_TYPE,file_path,object_id,jsa_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                    SQLiteStatement EtOrderDocs_statement = App_db.compileStatement(sql11);
                                    EtOrderDocs_statement.clearBindings();
                                    EtOrderDocs_statement.bindString(1, notif_etDocs_parcelable.getObjtype());
                                    EtOrderDocs_statement.bindString(2, notif_etDocs_parcelable.getZobjid());
                                    EtOrderDocs_statement.bindString(3, notif_etDocs_parcelable.getZdoctype());
                                    EtOrderDocs_statement.bindString(4, notif_etDocs_parcelable.getZdoctypeitem());
                                    EtOrderDocs_statement.bindString(5, notif_etDocs_parcelable.getFilename());
                                    EtOrderDocs_statement.bindString(6, notif_etDocs_parcelable.getFiletype());
                                    EtOrderDocs_statement.bindString(7, notif_etDocs_parcelable.getFsize());
                                    EtOrderDocs_statement.bindString(8, notif_etDocs_parcelable.getContent());
                                    EtOrderDocs_statement.bindString(9, notif_etDocs_parcelable.getDocid());
                                    EtOrderDocs_statement.bindString(10, notif_etDocs_parcelable.getDoctype());
                                    EtOrderDocs_statement.bindString(11, notif_etDocs_parcelable.getFilepath());
                                    EtOrderDocs_statement.execute();
                                    App_db.setTransactionSuccessful();
                                    App_db.endTransaction();
                                    Display_Attachments();

                                } else {
                                    error_dialog.show_error_dialog(getActivity(), "Please select correct format file. (TXT, PDF, PNG, DOC, DOCX, JPG, XLS, XLSX, DXF, DWF, DWG)");
                                }

                            } catch (Exception e) {
                                Log.v("file not found", "" + e.getMessage());
                            }
                        }
                    }
                }
                break;
        }
    }


    protected void Display_Attachments() {

        if (attachments_list.size() > 0) {
            //attachmentSizeDisplay(4);
            ma.updateTabDataCount();

            adapter = new AttachmentsAdapter(getActivity(), attachments_list);
            attachments_rv.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            attachments_rv.setLayoutManager(layoutManager);
            attachments_rv.setItemAnimator(new DefaultItemAnimator());
            attachments_rv.setAdapter(adapter);
            attachments_rv.setVisibility(View.VISIBLE);
            noData_tv.setVisibility(View.GONE);
        } else {
            attachments_rv.setVisibility(View.GONE);
            noData_tv.setVisibility(View.VISIBLE);
        }
    }


    private class AttachmentsAdapter extends RecyclerView.Adapter<AttachmentsAdapter.MyViewHolder> {
        private Context mContext;
        ConnectionDetector cd;


        Boolean isInternetPresent = false;
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
            protected LinearLayout Dele_layout;

            public MyViewHolder(View view) {
                super(view);
                filename_textview = (TextView) view.findViewById(R.id.filename_textview);
                checkbox = (CheckBox) view.findViewById(R.id.status_checkbox);
                file_objtype_textview = (TextView) view.findViewById(R.id.file_objtype_textview);
                file_size_textview = (TextView) view.findViewById(R.id.file_size_textview);
                file_path_textview = (TextView) view.findViewById(R.id.file_path_textview);
                pic_imageview = (ImageView) view.findViewById(R.id.pic_imageview);
                uuid_textview = (TextView) view.findViewById(R.id.uuid_textview);
                Dele_layout = (LinearLayout) view.findViewById(R.id.Dele_layout);
            }
        }

        public AttachmentsAdapter(Context mContext, List<Notif_EtDocs_Parcelable> list) {
            this.mContext = mContext;
            this.attachmentsList = list;
        }

        @Override
        public AttachmentsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_attachment_listdata, parent, false);
            return new AttachmentsAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final AttachmentsAdapter.MyViewHolder holder, final int position) {
            final Notif_EtDocs_Parcelable nep = attachmentsList.get(position);
            //holder.checkbox.setTag(position);
            holder.checkbox.setChecked((nep.isSelected() == true ? true : false));
            holder.filename_textview.setText(nep.getFilename());
            String object_type = nep.getObjtype();
            holder.Dele_layout.setTag(position);
            holder.file_objtype_textview.setVisibility(View.GONE);
            holder.Dele_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ConfirmDialogue(position);

                }
            });
            if (object_type.equalsIgnoreCase("EQUI")) {
                holder.file_objtype_textview.setText("Equipment");
                holder.file_objtype_textview.setBackgroundColor(getResources().getColor(R.color.footer_color));
            } else {
                holder.file_objtype_textview.setText("Notification");
                holder.file_objtype_textview.setBackgroundColor(getResources().getColor(R.color.dark_green));
            }
            String size = nep.getFsize();
            int int_size = Integer.parseInt(size);
            int size_mb = int_size / 1024;
            holder.file_size_textview.setText(size_mb + " KB");
            holder.file_path_textview.setText(nep.getFilepath());
            holder.uuid_textview.setText(nep.getUuid());
            String file_type = nep.getFiletype();
            if (file_type.contains("png") || file_type.contains("jpg") || file_type.contains("jpeg")) {
                try {
                    File image_file = new File(nep.getFilepath());
//                    Bitmap myBitmap = BitmapFactory.decodeFile(image_file.getAbsolutePath());
                    holder.pic_imageview.setBackground(getResources().getDrawable(R.drawable.ic_jpg_icon));
                } catch (Exception e) {
                }
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
        }

        @Override
        public int getItemCount() {
            return attachmentsList.size();
        }
    }

    private void ConfirmDialogue(final int position) {
        decision_dialog = new Dialog(getContext());
        decision_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        decision_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        decision_dialog.setCancelable(false);
        decision_dialog.setCanceledOnTouchOutside(false);
        decision_dialog.setContentView(R.layout.decision_dialog);
        ImageView imageview = (ImageView) decision_dialog.findViewById(R.id.imageView1);
        TextView description_textview = (TextView) decision_dialog.findViewById(R.id.description_textview);
        Glide.with(getContext()).load(R.drawable.error_dialog_gif).into(imageview);
        Button confirm = (Button) decision_dialog.findViewById(R.id.yes_button);
        Button cancel = (Button) decision_dialog.findViewById(R.id.no_button);
        description_textview.setText("Do you want to delete the selected attatchment?");
        decision_dialog.show();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decision_dialog.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decision_dialog.dismiss();

                // dele= Integer.toString( holder.Dele_layout.getTag(position));
                attachments_list.remove(position);
                ma.updateTabDataCount();
                adapter.notifyDataSetChanged();


            }
        });
        decision_dialog.show();

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
            cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
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


    public int attachmentSize() {
        if (attachments_list != null)
            return attachments_list.size();
        else
            return 0;
    }

}*/
