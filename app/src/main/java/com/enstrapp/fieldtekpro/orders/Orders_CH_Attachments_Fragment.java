package com.enstrapp.fieldtekpro.orders;

import android.app.Dialog;
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
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
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
import com.enstrapp.fieldtekpro.Authorizations.Authorizations;
import com.enstrapp.fieldtekpro.FileUpload.FileUtils;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.enstrapp.fieldtekpro.networkconnection.ConnectionDetector;
import com.enstrapp.fieldtekpro.networkconnectiondialog.Network_Connection_Dialog;
import com.enstrapp.fieldtekpro.notifications.Notif_EtDocs_Parcelable;
import com.enstrapp.fieldtekpro.notifications.Notifications_View_Attachements_Activity;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static android.view.View.GONE;

public class Orders_CH_Attachments_Fragment extends Fragment {

    TextView noData_tv;
    Error_Dialog error_dialog = new Error_Dialog();
    private static final String IMAGE_DIRECTORY_NAME = "FIELDTEKPRO";
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    String path = "", filee_name = "", image_Path = "";
    private List<Notif_EtDocs_Parcelable> attachments_list = new ArrayList<>();
    RecyclerView attachments_rv;
    AttachmentsAdapter adapter;
    private static final int RESULT_LOAD_IMG = 2;
    private static final int REQUEST_CODE = 6384;
    ArrayList<Notif_EtDocs_Parcelable> etdocs_parcablearray = new ArrayList<Notif_EtDocs_Parcelable>();
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    Network_Connection_Dialog network_connection_dialog = new Network_Connection_Dialog();
    int count = 0;
    boolean isSelected = false;
    Orders_Change_Activity ma;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater
                .inflate(R.layout.notifications_attachments_fragment, container, false);

        attachments_rv = (RecyclerView) rootView.findViewById(R.id.attachments_rv);
        noData_tv = (TextView) rootView.findViewById(R.id.nofiles_textview);
        ma = (Orders_Change_Activity) this.getActivity();

        attachments_list.clear();

        DATABASE_NAME = ma.getString(R.string.database_name);
        App_db = ma.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            etdocs_parcablearray = ma.ohp.getOrdrDocsPrcbls();
            if (etdocs_parcablearray != null && etdocs_parcablearray.size() > 0) {
                for (int i = 0; i < etdocs_parcablearray.size(); i++) {
                    Notif_EtDocs_Parcelable notif_etDocs_parcelable = new Notif_EtDocs_Parcelable();
                    notif_etDocs_parcelable.setZobjid(etdocs_parcablearray.get(i).getZobjid());
                    notif_etDocs_parcelable.setZdoctype(etdocs_parcablearray.get(i).getZdoctype());
                    notif_etDocs_parcelable
                            .setZdoctypeitem(etdocs_parcablearray.get(i).getZdoctypeitem());
                    notif_etDocs_parcelable.setFilename(etdocs_parcablearray.get(i).getFilename());
                    notif_etDocs_parcelable
                            .setFiletype(etdocs_parcablearray.get(i).getFiletype().toLowerCase());
                    notif_etDocs_parcelable.setFsize(etdocs_parcablearray.get(i).getFsize().trim());
                    notif_etDocs_parcelable.setFilepath(etdocs_parcablearray.get(i).getFilepath());
                    notif_etDocs_parcelable.setContentX(etdocs_parcablearray.get(i).getContentX());
                    notif_etDocs_parcelable.setDocid(etdocs_parcablearray.get(i).getDocid());
                    notif_etDocs_parcelable
                            .setDoctype(etdocs_parcablearray.get(i).getFiletype().toLowerCase());
                    notif_etDocs_parcelable.setObjtype(etdocs_parcablearray.get(i).getObjtype());
                    notif_etDocs_parcelable.setStatus(etdocs_parcablearray.get(i).getStatus());
                    String status = etdocs_parcablearray.get(i).getStatus();
                    if (status.equalsIgnoreCase("New")) {
                        try {
                            String file_path = etdocs_parcablearray.get(i).getFilepath();
                            byte[] byteArray = null;
                            InputStream inputStream = new FileInputStream(file_path);
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            byte[] b = new byte[4096 * 8];
                            int bytesRead = 0;
                            while ((bytesRead = inputStream.read(b)) != -1) {
                                bos.write(b, 0, bytesRead);
                            }
                            byteArray = bos.toByteArray();
                            String encodeddata = Base64.encodeToString(byteArray, Base64.DEFAULT);
                            notif_etDocs_parcelable.setContent(encodeddata);
                        } catch (Exception e) {
                            notif_etDocs_parcelable.setContent("");
                        }
                    } else {
                        notif_etDocs_parcelable.setContent(etdocs_parcablearray.get(i).getContent());
                    }
                    attachments_list.add(notif_etDocs_parcelable);
                }
            }
        }

        Display_Attachments();

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
        String xstatus = ma.ohp.getOrdrStatus();
        if (xstatus.equalsIgnoreCase("TECO")) {
            String auth_status = Authorizations.Get_Authorizations_Data(getActivity(), "W",
                    "U");
            if (auth_status.equalsIgnoreCase("true")) {
                if (ma.ohp.getOrdrId().startsWith("ORD")) {
                    ma.fab.hide();
                } else {
                    ma.fab.show();
                }
            } else {
                ma.fab.hide();
            }
        } else {
            String auth_status = Authorizations.Get_Authorizations_Data(getActivity(), "W",
                    "U");
            if (auth_status.equalsIgnoreCase("true")) {
                if (ma.ohp.getOrdrId().startsWith("ORD")) {
                    ma.fab.hide();
                } else {
                    ma.fab.show();
                }
            } else {
                ma.fab.hide();
            }
            ma.fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isSelected) {
                        final Dialog delete_decision_dialog = new Dialog(getActivity());
                        delete_decision_dialog.getWindow()
                                .setBackgroundDrawableResource(android.R.color.transparent);
                        delete_decision_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        delete_decision_dialog.setCancelable(false);
                        delete_decision_dialog.setCanceledOnTouchOutside(false);
                        delete_decision_dialog.setContentView(R.layout.decision_dialog);
                        TextView description_textview = delete_decision_dialog
                                .findViewById(R.id.description_textview);
                        description_textview.setText(getString(R.string.attachment_delete));
                        Button ok_button = delete_decision_dialog.findViewById(R.id.yes_button);
                        Button cancel_button = delete_decision_dialog.findViewById(R.id.no_button);
                        delete_decision_dialog.show();
                        ok_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ArrayList<Notif_EtDocs_Parcelable> rmoop = new ArrayList<>();
                                rmoop.addAll(attachments_list);

                                for (Notif_EtDocs_Parcelable oo : rmoop) {
                                    if (oo.isSelected()) {
                                        attachments_list.remove(oo);
                                    } else {
                                        oo.setSelected(false);
                                    }
                                }

                                ma.animateFab(false);
                                isSelected = false;

                                Display_Attachments();
                                delete_decision_dialog.dismiss();
                            }
                        });
                        cancel_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                delete_decision_dialog.dismiss();
                            }
                        });
                    } else {
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
                                    List<String> list = new ArrayList<String>();
                                    for (int i = 0; i < attachments_list.size(); i++) {
                                        String status = attachments_list.get(i).getStatus();
                                        if (status.equalsIgnoreCase("old")) {
                                        } else {
                                            list.add("new");
                                        }
                                    }
                                    if (list.size() >= 5) {
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
                                    List<String> list = new ArrayList<String>();
                                    for (int i = 0; i < attachments_list.size(); i++) {
                                        String status = attachments_list.get(i).getStatus();
                                        if (status.equalsIgnoreCase("old")) {
                                        } else {
                                            list.add("new");
                                        }
                                    }
                                    if (list.size() >= 5) {
                                        error_dialog.show_error_dialog(getActivity(), "You cannot attach more than 5 files");
                                    } else {
                                        if (!isDeviceSupportCamera()) {
                                            error_dialog.show_error_dialog(getActivity(), "Sorry! Your device doesn't support camera");
                                        } else {
                                            dialog.dismiss();
                                            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                            startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
                                        }
                                    }
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
                                    List<String> list = new ArrayList<String>();
                                    for (int i = 0; i < attachments_list.size(); i++) {
                                        String status = attachments_list.get(i).getStatus();
                                        if (status.equalsIgnoreCase("old")) {
                                        } else {
                                            list.add("new");
                                        }
                                    }
                                    if (list.size() >= 5) {
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
                }
            });
        }
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
                            image_Path = getRealPathFromURI(selectedImage);
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
                                            notif_etDocs_parcelable.setZobjid("");
                                            notif_etDocs_parcelable.setZdoctype("Q");
                                            notif_etDocs_parcelable.setZdoctypeitem("QH");
                                            notif_etDocs_parcelable.setFilename(filee_name);
                                            notif_etDocs_parcelable.setFiletype(mimeType);
                                            notif_etDocs_parcelable.setFsize(String.valueOf(file_size));
                                            notif_etDocs_parcelable.setFilepath(path);
                                            notif_etDocs_parcelable.setContent(encodedImage);
                                            notif_etDocs_parcelable.setDocid("");
                                            notif_etDocs_parcelable.setDoctype(fileExtension);
                                            notif_etDocs_parcelable.setObjtype("BUS2038");
                                            notif_etDocs_parcelable.setStatus("New");
                                            attachments_list.add(notif_etDocs_parcelable);
                                            try {
                                                App_db.beginTransaction();
                                                String sql11 = "Insert into Orders_Attachments (UUID, Object_id, Object_type, file_path, jsa_id,doctype) values(?,?,?,?,?,?);";
                                                SQLiteStatement statement11 = App_db.compileStatement(sql11);
                                                statement11.clearBindings();
                                                statement11.bindString(1,filee_name);
                                                statement11.bindString(2, ma.ohp.getOrdrId());
                                                statement11.bindString(3, "Orders");
                                                statement11.bindString(4, encodedImage);
                                                statement11.bindString(5, mimeType);
                                                statement11.bindString(6, fileExtension);
                                                statement11.execute();
                                                App_db.setTransactionSuccessful();
                                                App_db.endTransaction();
                                            } catch (Exception e) {
                                            }
                                            Display_Attachments();
                                        } else {
                                            error_dialog.show_error_dialog(getActivity(), "Please Enter Description");
                                        }
                                    }
                                });
                            } else {

                            }
                        } catch (Exception e) {
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
                                    notif_etDocs_parcelable.setZdoctype("Q");
                                    notif_etDocs_parcelable.setZdoctypeitem("QH");
                                    notif_etDocs_parcelable.setFilename(filee_name);
                                    notif_etDocs_parcelable.setFiletype(mimeType);
                                    notif_etDocs_parcelable.setFsize(String.valueOf(file_size));
                                    notif_etDocs_parcelable.setFilepath(file_path);
                                    notif_etDocs_parcelable.setContent(encodedImage);
                                    notif_etDocs_parcelable.setDocid("");
                                    notif_etDocs_parcelable.setDoctype(fileExtension);
                                    notif_etDocs_parcelable.setObjtype("BUS2038");
                                    notif_etDocs_parcelable.setStatus("New");
                                    attachments_list.add(notif_etDocs_parcelable);
                                    try {
                                        App_db.beginTransaction();
                                        String sql11 = "Insert into Orders_Attachments (UUID, Object_id, Object_type, file_path, jsa_id,doctype) values(?,?,?,?,?,?);";
                                        SQLiteStatement statement11 = App_db.compileStatement(sql11);
                                        statement11.clearBindings();
                                        statement11.bindString(1,filee_name);
                                        statement11.bindString(2, ma.ohp.getOrdrId());
                                        statement11.bindString(3, "Orders");
                                        statement11.bindString(4, encodedImage);
                                        statement11.bindString(5, mimeType);
                                        statement11.bindString(6, fileExtension);
                                        statement11.execute();
                                        App_db.setTransactionSuccessful();
                                        App_db.endTransaction();
                                    } catch (Exception e) {
                                    }
                                    Display_Attachments();
                                } else {
                                    error_dialog.show_error_dialog(getActivity(), "Please select correct format file. (TXT, PDF, PNG, DOC, DOCX, JPG, XLS, XLSX, DXF, DWF, DWG)");
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
                                File file = new File(path);
                                final String filee_name = file.getName();
                                Uri selectedUri = Uri.fromFile(file);
                                final String fileExtension = MimeTypeMap.getFileExtensionFromUrl(selectedUri.toString());
                                if (fileExtension.equalsIgnoreCase("txt") || fileExtension.equalsIgnoreCase("pdf") || fileExtension.equalsIgnoreCase("png") || fileExtension.equalsIgnoreCase("doc") || fileExtension.equalsIgnoreCase("docx") || fileExtension.equalsIgnoreCase("png") || fileExtension.equalsIgnoreCase("jpg") || fileExtension.equalsIgnoreCase("png") || fileExtension.equalsIgnoreCase("xls") || fileExtension.equalsIgnoreCase("xlsx") || fileExtension.equalsIgnoreCase("dxf") || fileExtension.equalsIgnoreCase("dwf") || fileExtension.equalsIgnoreCase("dxf") || fileExtension.equalsIgnoreCase("dwg")) {
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
                                    notif_etDocs_parcelable.setDoctype(fileExtension);
                                    notif_etDocs_parcelable.setObjtype("BUS2038");
                                    notif_etDocs_parcelable.setStatus("New");
                                    attachments_list.add(notif_etDocs_parcelable);
                                    try {
                                        App_db.beginTransaction();
                                        String sql11 = "Insert into Orders_Attachments (UUID, Object_id, Object_type, file_path, jsa_id,doctype) values(?,?,?,?,?,?);";
                                        SQLiteStatement statement11 = App_db.compileStatement(sql11);
                                        statement11.clearBindings();
                                        statement11.bindString(1,filee_name);
                                        statement11.bindString(2, ma.ohp.getOrdrId());
                                        statement11.bindString(3, "Orders");
                                        statement11.bindString(4, encodedImage);
                                        statement11.bindString(5, mimeType);
                                        statement11.bindString(6, fileExtension);
                                        statement11.execute();
                                        App_db.setTransactionSuccessful();
                                        App_db.endTransaction();
                                    } catch (Exception e) {
                                    }
                                    Display_Attachments();
                                } else {
                                    error_dialog.show_error_dialog(getActivity(), "Please select correct format file. (TXT, PDF, PNG, DOC, DOCX, JPG, XLS, XLSX, DXF, DWF, DWG)");
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
            adapter = new AttachmentsAdapter(getActivity(), attachments_list);
            attachments_rv.setHasFixedSize(true);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            mLayoutManager.setReverseLayout(true);
            mLayoutManager.setStackFromEnd(true);
            attachments_rv.setLayoutManager(mLayoutManager);
            attachments_rv.setItemAnimator(new DefaultItemAnimator());
            attachments_rv.setAdapter(adapter);
            attachments_rv.setVisibility(View.VISIBLE);
            noData_tv.setVisibility(View.GONE);
        } else {
            attachments_rv.setVisibility(View.GONE);
            noData_tv.setVisibility(View.VISIBLE);
        }
        ma.updateTabDataCount();
    }


    private class AttachmentsAdapter extends RecyclerView.Adapter<AttachmentsAdapter.MyViewHolder> {
        private Context mContext;
        private List<Notif_EtDocs_Parcelable> attachmentsList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            protected TextView filename_textview;
            protected TextView file_objtype_textview;
            protected TextView file_size_textview;
            protected TextView file_path_textview;
            protected ImageView pic_imageview, download_imageview;
            protected TextView uuid_textview, status_textview, content_textview;
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
                status_textview = (TextView) view.findViewById(R.id.status_textview);
                content_textview = (TextView) view.findViewById(R.id.content_textview);
                download_imageview = (ImageView) view.findViewById(R.id.download_imageview);
                layout = (LinearLayout) view.findViewById(R.id.layout);
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
            holder.status_textview.setText(nep.getStatus());
            if (holder.status_textview.getText().toString().equalsIgnoreCase("old")) {
                holder.checkbox.setVisibility(View.INVISIBLE);
                holder.file_size_textview.setVisibility(View.GONE);
            } else {
                holder.checkbox.setVisibility(View.VISIBLE);
                holder.file_size_textview.setVisibility(View.VISIBLE);
            }
            holder.content_textview.setText(nep.getContentX());
            holder.file_objtype_textview.setVisibility(View.GONE);
            /*String object_type = nep.getObjtype();
            if (object_type.equalsIgnoreCase("EQUI")) {
                holder.file_objtype_textview.setText("Equipment");
                holder.file_objtype_textview.setBackgroundColor(getResources().getColor(R.color.footer_color));
            } else {
                holder.file_objtype_textview.setText("Notification");
                holder.file_objtype_textview.setBackgroundColor(getResources().getColor(R.color.dark_green));
            }*/
            String size = nep.getFsize();
            int size_mb = 0;
            if (size != null && !size.equals("")) {
                int int_size = Integer.parseInt(size);
                size_mb = int_size / 1024;
            } else {
                size_mb = 0;
            }
            holder.file_size_textview.setText(size_mb + " KB");
            holder.file_path_textview.setText(nep.getFilepath());
            holder.uuid_textview.setText(nep.getUuid());
            final String file_type = nep.getDoctype();
            if (file_type.contains("png") || file_type.contains("jpg") || file_type.contains("jpeg")) {
                if (holder.status_textview.getText().toString().equalsIgnoreCase("old")) {
                    if (holder.content_textview.getText().toString() != null && !holder.content_textview.getText().toString().equals("")) {
                        Picasso.with(getActivity()).load(holder.content_textview.getText().toString()).placeholder(R.drawable.ic_loading_icon1).resize(60, 60).centerCrop().into(holder.pic_imageview);
                    } else {
                        int productImageId = getResources().getIdentifier("ic_jpg_icon", "drawable", getActivity().getPackageName());
                        Picasso.with(getContext()).load(productImageId).error(R.drawable.ic_jpg_icon).placeholder(R.drawable.ic_loading_icon1).resize(60, 60).centerCrop().into(holder.pic_imageview);
                    }
                } else {
                    try {
                        File image_file = new File(nep.getFilepath());
                        Picasso.with(getActivity()).load(image_file).placeholder(R.drawable.ic_loading_icon1).resize(60, 60).centerCrop().into(holder.pic_imageview);
                    } catch (Exception e) {
                    }
                }
            } else if (file_type.contains("pdf")) {
                int productImageId = getResources().getIdentifier("ic_pdf_icon", "drawable", getActivity().getPackageName());
                Picasso.with(getContext()).load(productImageId).error(R.drawable.ic_pdf_icon).placeholder(R.drawable.ic_loading_icon1).resize(60, 60).centerCrop().into(holder.pic_imageview);
            } else if (file_type.contains("doc") || file_type.contains("docx")) {
                int productImageId = getResources().getIdentifier("ic_doc_icon", "drawable", getActivity().getPackageName());
                Picasso.with(getContext()).load(productImageId).error(R.drawable.ic_doc_icon).placeholder(R.drawable.ic_loading_icon1).resize(60, 60).centerCrop().into(holder.pic_imageview);
            } else if (file_type.contains("xls") || file_type.contains("xlsx")) {
                int productImageId = getResources().getIdentifier("ic_xlx_icon", "drawable", getActivity().getPackageName());
                Picasso.with(getContext()).load(productImageId).error(R.drawable.ic_xlx_icon).placeholder(R.drawable.ic_loading_icon1).resize(60, 60).centerCrop().into(holder.pic_imageview);
            } else if (file_type.contains("txt")) {
                int productImageId = getResources().getIdentifier("ic_txt_icon", "drawable", getActivity().getPackageName());
                Picasso.with(getContext()).load(productImageId).error(R.drawable.ic_txt_icon).placeholder(R.drawable.ic_loading_icon1).resize(60, 60).centerCrop().into(holder.pic_imageview);
            } else if (file_type.contains("plain")) {
                int productImageId = getResources().getIdentifier("ic_txt_icon", "drawable", getActivity().getPackageName());
                Picasso.with(getContext()).load(productImageId).error(R.drawable.ic_txt_icon).placeholder(R.drawable.ic_loading_icon1).resize(60, 60).centerCrop().into(holder.pic_imageview);
            }

            if (holder.status_textview.getText().toString().equalsIgnoreCase("old")) {
                holder.download_imageview.setVisibility(View.VISIBLE);
            } else {
                holder.download_imageview.setVisibility(View.GONE);
            }

            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.status_textview.getText().toString().equalsIgnoreCase("old")) {
                        cd = new ConnectionDetector(getActivity());
                        isInternetPresent = cd.isConnectingToInternet();
                        if (isInternetPresent) {
                            final Dialog decision_dialog = new Dialog(getActivity());
                            decision_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            decision_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            decision_dialog.setCancelable(false);
                            decision_dialog.setCanceledOnTouchOutside(false);
                            decision_dialog.setContentView(R.layout.decision_dialog);
                            ImageView imageview = (ImageView) decision_dialog.findViewById(R.id.imageView1);
                            TextView description_textview = (TextView) decision_dialog.findViewById(R.id.description_textview);
                            Glide.with(getActivity()).load(R.drawable.error_dialog_gif).into(imageview);
                            Button confirm = (Button) decision_dialog.findViewById(R.id.yes_button);
                            Button cancel = (Button) decision_dialog.findViewById(R.id.no_button);
                            description_textview.setText("Do you want to Download the selected attatchment?");
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
                                    Intent viewattachments_intent = new Intent(getActivity(), Notifications_View_Attachements_Activity.class);
                                    viewattachments_intent.putExtra("url", holder.content_textview.getText().toString());
                                    viewattachments_intent.putExtra("filename", holder.filename_textview.getText().toString());
                                    viewattachments_intent.putExtra("filetype", file_type);
                                    viewattachments_intent.putExtra("doc_id", nep.getDocid());
                                    viewattachments_intent.putExtra("objtype", nep.getObjtype());
                                    startActivity(viewattachments_intent);
                                }
                            });
                            decision_dialog.show();
                        } else {
                            network_connection_dialog.show_network_connection_dialog(getActivity());
                        }
                    } else {

                    }
                }
            });


            holder.checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.checkbox.isChecked()) {
                        count = 0;
                        attachmentsList.get(position).setSelected(true);
                        for (Notif_EtDocs_Parcelable oop : attachmentsList) {
                            if (oop.isSelected()) {
                                count = count + 1;
                                isSelected = true;
                            }
                        }
                        if (count == 1)
                            ma.animateFab(true);
                    } else {
                        count = 0;
                        attachmentsList.get(position).setSelected(false);
                        for (Notif_EtDocs_Parcelable oop : attachmentsList) {
                            if (oop.isSelected()) {
                                count = count + 1;
                            }
                        }
                        if (count == 0) {
                            ma.animateFab(false);
                            isSelected = false;
                        }
                    }
                }
            });


        }

        @Override
        public int getItemCount() {
            return attachmentsList.size();
        }
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


    public int AttachmentSize() {
        if (attachments_list.size() > 0) {
            return attachments_list.size();
        } else {
            return 0;
        }
    }


    /*class DownloadFileFromURL extends AsyncTask<String, String, String>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            showDialog(progress_bar_type);
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
                OutputStream output = new FileOutputStream("/sdcard/"+selected_filename+".pdf");
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
            pDialog.setProgress(Integer.parseInt(progress[0]));
        }
        @Override
        protected void onPostExecute(String file_url)
        {
            dismissDialog(progress_bar_type);
            String imagePath = Environment.getExternalStorageDirectory().toString() + "/"+selected_filename+".pdf";
            image_dialog = new Dialog(Notifications_View_Attachments_Activity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
            image_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            image_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            image_dialog.setCancelable(true);
            image_dialog.setCanceledOnTouchOutside(false);
            image_dialog.getWindow().getAttributes().windowAnimations = R.style.ErrorDialog;
            image_dialog.setContentView(R.layout.view_attachment_image_dialog);
            final WebView webView1 = (WebView) image_dialog.findViewById(R.id.webView1);
            ImageView back_imageview = (ImageView) image_dialog.findViewById(R.id.back_imageview);
            //final PDFView pdfView = (PDFView) image_dialog.findViewById(R.id.pdfView);
            TextView image_id_textview = (TextView) image_dialog.findViewById(R.id.image_id_textview);
            final TextView textView1 = (TextView) image_dialog.findViewById(R.id.textView1);
            image_id_textview.setText(selected_filename);
            image_dialog.show();
            progressdialog1 = new ProgressDialog(Notifications_View_Attachments_Activity.this, ProgressDialog.THEME_HOLO_LIGHT);
            progressdialog1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressdialog1.setMessage(getResources().getString(R.string.loading));
            progressdialog1.setCancelable(false);
            progressdialog1.setCanceledOnTouchOutside(false);
            progressdialog1.show();
            WebSettings webSettings = webView1.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setAllowFileAccessFromFileURLs(true);
            webSettings.setAllowUniversalAccessFromFileURLs(true);
            webSettings.setBuiltInZoomControls(true);
            //webSettings.setWebChromeClient(new WebChromeClient());
            webView1.setWebViewClient(new WebViewClient(){
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
                    if (progressdialog1.isShowing() && progressdialog1 != null)
                    {
                        progressdialog1.dismiss();
                    }
                }

                @TargetApi(android.os.Build.VERSION_CODES.M)
                @Override
                public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr)
                {
                    // Redirect to deprecated method, so you can use it in all SDK versions
                    onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
            File file = new File(imagePath);
            webView1.loadUrl("file:///android_asset/pdfjs/web/viewer.html?file=" + file.getAbsolutePath() + "#zoom=page-width");
            back_imageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    image_dialog.dismiss();
                }
            });
        }
    }*/

}
