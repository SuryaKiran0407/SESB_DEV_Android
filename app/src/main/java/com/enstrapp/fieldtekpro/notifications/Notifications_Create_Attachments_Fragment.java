package com.enstrapp.fieldtekpro.notifications;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.enstrapp.fieldtekpro.FileUpload.FileUtils;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class Notifications_Create_Attachments_Fragment extends Fragment {

    Error_Dialog error_dialog = new Error_Dialog();
    private static final String IMAGE_DIRECTORY_NAME = "FIELDTEKPRO";
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    String path = "", filee_name = "";
    private List<Notif_EtDocs_Parcelable> attachments_list = new ArrayList<>();
    RecyclerView attachments_rv;
    AttachmentsAdapter adapter;
    private static final int RESULT_LOAD_IMG = 2;
    private static final int REQUEST_CODE = 6384;
    TextView noData_tv;
    boolean isSelected = false;
    Notifications_Create_Activity nca;
    int count = 0;

    public Notifications_Create_Attachments_Fragment() {
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
        nca = (Notifications_Create_Activity) this.getActivity();

        attachments_rv.setVisibility(View.GONE);
        noData_tv.setVisibility(View.VISIBLE);

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
        Notifications_Create_Activity mainActivity = (Notifications_Create_Activity) getActivity();
        mainActivity.fab.show();
        mainActivity.fab.setOnClickListener(new View.OnClickListener() {
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
                            nca.animateFab(false);
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
                    TextView camera_textview = dialog.findViewById(R.id.camera_textview);
                    TextView gallery_textview = dialog.findViewById(R.id.gallery_textview);
                    TextView file_textview = dialog.findViewById(R.id.file_textview);
                    dialog.show();
                    camera_textview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (attachments_list.size() >= 5) {
                                error_dialog.show_error_dialog(getActivity(),
                                        getString(R.string.attachment_morethan5));
                            } else {
                                if (!isDeviceSupportCamera()) {
                                    error_dialog.show_error_dialog(getActivity(),
                                            getString(R.string.attachment_camerasupport));
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
                                error_dialog.show_error_dialog(getActivity(),
                                        getString(R.string.attachment_morethan5));
                            } else {
                                dialog.dismiss();
                                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
                            }
                        }
                    });
                    file_textview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (attachments_list.size() >= 5) {
                                error_dialog.show_error_dialog(getActivity(),
                                        getString(R.string.attachment_morethan5));
                            } else {
                                Intent target = FileUtils.createGetContentIntent();
                                Intent intent = Intent.createChooser(target,
                                        getString(R.string.attachment_choosefile));
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
                            final String fileExtension = MimeTypeMap
                                    .getFileExtensionFromUrl(selectedUri.toString());
                            if (fileExtension.equalsIgnoreCase("txt") ||
                                    fileExtension.equalsIgnoreCase("pdf") ||
                                    fileExtension.equalsIgnoreCase("png") ||
                                    fileExtension.equalsIgnoreCase("doc") ||
                                    fileExtension.equalsIgnoreCase("docx") ||
                                    fileExtension.equalsIgnoreCase("png") ||
                                    fileExtension.equalsIgnoreCase("jpg") ||
                                    fileExtension.equalsIgnoreCase("xls") ||
                                    fileExtension.equalsIgnoreCase("xlsx") ||
                                    fileExtension.equalsIgnoreCase("dxf") ||
                                    fileExtension.equalsIgnoreCase("dwf") ||
                                    fileExtension.equalsIgnoreCase("dxf") ||
                                    fileExtension.equalsIgnoreCase("dwg")) {
                                final String mimeType = MimeTypeMap.getSingleton()
                                        .getMimeTypeFromExtension(fileExtension);
                                final int file_size = Integer
                                        .parseInt(String.valueOf(file.length()));
                                byte[] byteArray = null;
                                InputStream inputStream = new FileInputStream(image_Path);
                                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                byte[] b = new byte[4096 * 8];
                                int bytesRead = 0;
                                while ((bytesRead = inputStream.read(b)) != -1) {
                                    bos.write(b, 0, bytesRead);
                                }
                                byteArray = bos.toByteArray();
                                final String encodedImage = Base64
                                        .encodeToString(byteArray, Base64.DEFAULT);
                                final Dialog file_rename_dialog = new Dialog(getActivity());
                                file_rename_dialog.getWindow()
                                        .setBackgroundDrawableResource(android.R.color.transparent);
                                file_rename_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                file_rename_dialog.setCancelable(false);
                                file_rename_dialog.setCanceledOnTouchOutside(false);
                                file_rename_dialog.setContentView(R.layout.camera_file_rename);
                                final EditText file_rename = file_rename_dialog
                                        .findViewById(R.id.file_rename);
                                Button yes_button = file_rename_dialog.findViewById(R.id.yes_button);
                                Button no_button = file_rename_dialog.findViewById(R.id.no_button);
                                yes_button.setText(getString(R.string.ok));
                                no_button.setText(getString(R.string.cancel));
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
                                        if (!file_rename.getText().toString().equals("") &&
                                                file_rename.getText().toString() != null) {
                                            file_rename_dialog.dismiss();
                                            File mediaStorageDir = new File(Environment
                                                    .getExternalStoragePublicDirectory
                                                            (Environment.DIRECTORY_PICTURES),
                                                    IMAGE_DIRECTORY_NAME);
                                            filee_name = file.getName();

                                            File to = new File(mediaStorageDir,
                                                    file_rename.getText().toString() + ".jpg");
                                            if (file.exists()) {
                                                file.renameTo(to);
                                                File file2 =
                                                        new File(mediaStorageDir.getPath()
                                                                + File.separator +
                                                                file_rename.getText().toString()
                                                                + ".jpg");
                                                path = file2.getPath();
                                                filee_name = file_rename.getText().toString()
                                                        + ".jpg";
                                            }

                                            final Dialog add_file_dialog = new Dialog(getActivity());
                                            add_file_dialog.getWindow()
                                                    .setBackgroundDrawableResource(android.R.color.transparent);
                                            add_file_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                            add_file_dialog.setCancelable(false);
                                            add_file_dialog.setCanceledOnTouchOutside(false);
                                            add_file_dialog.setContentView(R.layout.add_file_dialog);
                                            final TextView favoritetextview = add_file_dialog
                                                    .findViewById(R.id.description_textview);
                                            Button equip_button = add_file_dialog
                                                    .findViewById(R.id.equip_button);
                                            Button notif_button = add_file_dialog
                                                    .findViewById(R.id.notif_button);
                                            Button cancel_button = add_file_dialog
                                                    .findViewById(R.id.cancel_button);
                                            favoritetextview.setText("Would like to add selected file for ?");
                                            equip_button.setText("Equipment");
                                            notif_button.setText("Notification");
                                            add_file_dialog.show();

                                            equip_button.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
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
                                                    attachments_list.add(notif_etDocs_parcelable);
                                                    add_file_dialog.dismiss();
                                                    Display_Attachments();
                                                }
                                            });

                                            notif_button.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
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
                                                    notif_etDocs_parcelable.setObjtype("BUS2038");
                                                    attachments_list.add(notif_etDocs_parcelable);
                                                    add_file_dialog.dismiss();
                                                    Display_Attachments();
                                                }
                                            });

                                            cancel_button.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    add_file_dialog.dismiss();
                                                    file_rename_dialog.dismiss();
                                                }
                                            });
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
                                    final Dialog add_file_dialog = new Dialog(getActivity());
                                    add_file_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                    add_file_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    add_file_dialog.setCancelable(false);
                                    add_file_dialog.setCanceledOnTouchOutside(false);
                                    add_file_dialog.setContentView(R.layout.add_file_dialog);
                                    final TextView favoritetextview = (TextView) add_file_dialog.findViewById(R.id.description_textview);
                                    Button equip_button = (Button) add_file_dialog.findViewById(R.id.equip_button);
                                    Button notif_button = (Button) add_file_dialog.findViewById(R.id.notif_button);
                                    Button cancel_button = (Button) add_file_dialog.findViewById(R.id.cancel_button);
                                    favoritetextview.setText("Would like to add selected file for ?");
                                    equip_button.setText("Equipment");
                                    notif_button.setText("Notification");
                                    add_file_dialog.show();
                                    equip_button.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
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
                                            notif_etDocs_parcelable.setDoctype("");
                                            notif_etDocs_parcelable.setObjtype("EQUI");
                                            attachments_list.add(notif_etDocs_parcelable);
                                            add_file_dialog.dismiss();
                                            Display_Attachments();
                                        }
                                    });

                                    notif_button.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
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
                                            notif_etDocs_parcelable.setDoctype("");
                                            notif_etDocs_parcelable.setObjtype("BUS2038");
                                            attachments_list.add(notif_etDocs_parcelable);
                                            add_file_dialog.dismiss();
                                            Display_Attachments();
                                        }
                                    });
                                    cancel_button.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            add_file_dialog.dismiss();
                                        }
                                    });
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
                                    final TextView favoritetextview = (TextView) add_file_dialog.findViewById(R.id.description_textview);
                                    Button equip_button = (Button) add_file_dialog.findViewById(R.id.equip_button);
                                    Button notif_button = (Button) add_file_dialog.findViewById(R.id.notif_button);
                                    Button cancel_button = (Button) add_file_dialog.findViewById(R.id.cancel_button);
                                    favoritetextview.setText("Would like to add selected file for ?");
                                    equip_button.setText("Equipment");
                                    notif_button.setText("Notification");
                                    add_file_dialog.show();
                                    equip_button.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
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
                                            attachments_list.add(notif_etDocs_parcelable);
                                            add_file_dialog.dismiss();
                                            Display_Attachments();
                                        }
                                    });

                                    notif_button.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
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
                                            notif_etDocs_parcelable.setObjtype("BUS2038");
                                            attachments_list.add(notif_etDocs_parcelable);
                                            add_file_dialog.dismiss();
                                            Display_Attachments();
                                        }
                                    });
                                    cancel_button.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            add_file_dialog.dismiss();
                                        }
                                    });
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
        nca.updateTabDataCount();
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
            String object_type = nep.getObjtype();
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
                    Bitmap myBitmap = BitmapFactory.decodeFile(image_file.getAbsolutePath());
                    holder.pic_imageview.setImageBitmap(myBitmap);
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


            holder.checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.checkbox.isChecked()) {
                        count = 0;
                        attachments_list.get(position).setSelected(true);
                        for (Notif_EtDocs_Parcelable oop : attachments_list) {
                            if (oop.isSelected()) {
                                count = count + 1;
                                isSelected = true;
                            }
                        }
                        if (count == 1)
                            nca.animateFab(true);
                    } else {
                        count = 0;
                        attachments_list.get(position).setSelected(false);
                        for (Notif_EtDocs_Parcelable oop : attachments_list) {
                            if (oop.isSelected()) {
                                count = count + 1;
                            }
                        }
                        if (count == 0) {
                            nca.animateFab(false);
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


}
