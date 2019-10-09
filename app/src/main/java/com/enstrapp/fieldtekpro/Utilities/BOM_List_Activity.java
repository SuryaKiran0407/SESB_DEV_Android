package com.enstrapp.fieldtekpro.Utilities;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.enstrapp.fieldtekpro.BarcodeScanner.Barcode_Scanner_Activity;
import com.enstrapp.fieldtekpro.InitialLoad_Rest.REST_BOM;
import com.enstrapp.fieldtekpro.Initialload.BOM;
import com.enstrapp.fieldtekpro.Initialload.LoadSettings;
import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.enstrapp.fieldtekpro.networkconnection.ConnectionDetector;
import com.enstrapp.fieldtekpro.networkconnectiondialog.Network_Connection_Dialog;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class BOM_List_Activity extends Fragment {

    RecyclerView bom_list_recycleview;
    private Adapter adapter;
    private List<BOM_List_Object> bom_list = new ArrayList<>();
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";
    SwipeRefreshLayout swiperefreshlayout;
    Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog();
    public SearchView search;
    TextView no_data_bomlist_textview, searchview_textview;
    FloatingActionButton scan_fab_button, refresh_fab_button, sort_fab_button;
    private static final int ZXING_CAMERA_PERMISSION = 1;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    Network_Connection_Dialog network_connection_dialog = new Network_Connection_Dialog();
    Dialog decision_dialog;
    FloatingActionMenu floatingActionMenu;
    SharedPreferences FieldTekPro_SharedPref;
    SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    String BOM_Refresh_status = "", BOMITEM_status = "", BOM_status = "", sort_selected = "", cloud_search_status = "", bom_header_id = "", bom_header_desc = "", bom_header_plant = "", username = "", password = "", device_serial_number = "", device_id = "", device_uuid = "", url_host_ipaddress = "", GET_LIST_OF_EQBOMS_Response = "";
    Error_Dialog error_dialog = new Error_Dialog();
    ImageView cloud_search_imagview;

    public BOM_List_Activity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.utilities_bom_list_activity, container, false);

        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = getActivity().openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);

        /* Initializing Shared Preferences */
        FieldTekPro_SharedPref = getActivity().getSharedPreferences("FieldTekPro_SharedPreferences", Context.MODE_PRIVATE);
        FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();
        /* Initializing Shared Preferences */

        /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
        device_id = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        device_serial_number = Build.SERIAL;
        String androidId = "" + Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) device_id.hashCode() << 32) | device_serial_number.hashCode());
        device_uuid = deviceUuid.toString();
        /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */

        username = FieldTekPro_SharedPref.getString("FieldTekPro_Username", null);
        password = FieldTekPro_SharedPref.getString("FieldTekPro_Password", null);

        bom_list_recycleview = (RecyclerView) rootView.findViewById(R.id.bom_list_recycleview);
        swiperefreshlayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefreshlayout);
        search = (SearchView) rootView.findViewById(R.id.search);
        no_data_bomlist_textview = (TextView) rootView.findViewById(R.id.no_data_bomlist_textview);
        scan_fab_button = (FloatingActionButton) rootView.findViewById(R.id.scan_fab_button);
        refresh_fab_button = (FloatingActionButton) rootView.findViewById(R.id.refresh_fab_button);
        sort_fab_button = (FloatingActionButton) rootView.findViewById(R.id.sort_fab_button);
        floatingActionMenu = (FloatingActionMenu) rootView.findViewById(R.id.floatingactionmenu);
        cloud_search_imagview = (ImageView) rootView.findViewById(R.id.cloud_search_imagview);

        int id = search.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        search.setQueryHint("Search...");
        Typeface myCustomFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/metropolis_medium.ttf");
        searchview_textview = (TextView) search.findViewById(id);
        searchview_textview.setTextColor(getResources().getColor(R.color.white));
        search.setBaselineAligned(false);
        searchview_textview.setTypeface(myCustomFont);
        searchview_textview.setTextSize(16);
        EditText searchEditText = (EditText) search.findViewById(id);
        searchEditText.setTextColor(getResources().getColor(R.color.white));
        searchEditText.setHintTextColor(getResources().getColor(R.color.dark_grey2));

        floatingActionMenu.setClosedOnTouchOutside(false);

        new Get_BOM_List_Data().execute();

        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cd = new ConnectionDetector(getActivity());
                isInternetPresent = cd.isConnectingToInternet();
                if (isInternetPresent) {
                    decision_dialog = new Dialog(getActivity());
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
                    description_textview.setText(getString(R.string.refresh_text));
                    decision_dialog.show();
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            decision_dialog.dismiss();
                            swiperefreshlayout.setRefreshing(false);
                        }
                    });
                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            decision_dialog.dismiss();
                            String webservice_type = getString(R.string.webservice_type);
                            if(webservice_type.equalsIgnoreCase("odata"))
                            {
                                new Get_LoadSettings_Data().execute();
                            }
                            else
                            {
                                new Get_BOM_REST_Data().execute();
                            }
                        }
                    });
                }
                else
                {
                    //showing network error and navigating to wifi settings.
                    swiperefreshlayout.setRefreshing(false);
                    network_connection_dialog.show_network_connection_dialog(getActivity());
                }
            }
        });

        swiperefreshlayout.setColorSchemeResources(R.color.red, R.color.lime, R.color.colorAccent, R.color.red, R.color.blue, R.color.black, R.color.orange);

        scan_fab_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionMenu.close(true);
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);
                } else {
                    Intent intent = new Intent(getActivity(), Barcode_Scanner_Activity.class);
                    startActivityForResult(intent, 0);
                }
            }
        });

        refresh_fab_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cd = new ConnectionDetector(getActivity());
                isInternetPresent = cd.isConnectingToInternet();
                if (isInternetPresent) {
                    floatingActionMenu.close(true);
                    decision_dialog = new Dialog(getActivity());
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
                    description_textview.setText(getResources().getString(R.string.refresh_text));
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
                            String webservice_type = getString(R.string.webservice_type);
                            if(webservice_type.equalsIgnoreCase("odata"))
                            {
                                new Get_LoadSettings_Data().execute();
                            }
                            else
                            {
                                new Get_BOM_REST_Data().execute();
                            }
                        }
                    });
                }
                else
                {
                    //showing network error and navigating to wifi settings.
                    network_connection_dialog.show_network_connection_dialog(getActivity());
                }
            }
        });

        sort_fab_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionMenu.close(true);
                if (bom_list.size() > 0) {
                    final Dialog dialog = new Dialog(getActivity(), R.style.AppThemeDialog_Dark);
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.utilities_bom_sort_dialog);
                    dialog.setCancelable(true);
                    dialog.setCanceledOnTouchOutside(false);
                    TextView sort_clear_textview = (TextView) dialog.findViewById(R.id.sort_clear_textview);
                    final RadioGroup sort_radioGroup = (RadioGroup) dialog.findViewById(R.id.sort_radioGroup);
                    final RadioButton description_radiobutton1 = (RadioButton) dialog.findViewById(R.id.description_radiobutton1);
                    final RadioButton description_radiobutton2 = (RadioButton) dialog.findViewById(R.id.description_radiobutton2);
                    final RadioButton plant_radiobutton1 = (RadioButton) dialog.findViewById(R.id.plant_radiobutton1);
                    final RadioButton plant_radiobutton2 = (RadioButton) dialog.findViewById(R.id.plant_radiobutton2);
                    final RadioButton bom_number_radiobutton1 = (RadioButton) dialog.findViewById(R.id.bom_number_radiobutton1);
                    final RadioButton bom_number_radiobutton2 = (RadioButton) dialog.findViewById(R.id.bom_number_radiobutton2);
                    String BOM_Sort_Status = sort_selected;
                    if (BOM_Sort_Status != null && !BOM_Sort_Status.equals("")) {
                        if (BOM_Sort_Status.equalsIgnoreCase("description_sort1")) {
                            description_radiobutton1.setChecked(true);
                        } else if (BOM_Sort_Status.equalsIgnoreCase("description_sort2")) {
                            description_radiobutton2.setChecked(true);
                        } else if (BOM_Sort_Status.equalsIgnoreCase("plant_sort1")) {
                            plant_radiobutton1.setChecked(true);
                        } else if (BOM_Sort_Status.equalsIgnoreCase("plant_sort2")) {
                            plant_radiobutton2.setChecked(true);
                        } else if (BOM_Sort_Status.equalsIgnoreCase("bom_number_sort1")) {
                            bom_number_radiobutton1.setChecked(true);
                        } else if (BOM_Sort_Status.equalsIgnoreCase("bom_number_sort2")) {
                            bom_number_radiobutton2.setChecked(true);
                        }
                    } else {
                        description_radiobutton1.setChecked(false);
                        description_radiobutton2.setChecked(false);
                        plant_radiobutton1.setChecked(false);
                        plant_radiobutton2.setChecked(false);
                        bom_number_radiobutton1.setChecked(false);
                        bom_number_radiobutton2.setChecked(false);
                    }
                    dialog.show();
                    sort_clear_textview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            description_radiobutton1.setChecked(false);
                            description_radiobutton2.setChecked(false);
                            plant_radiobutton1.setChecked(false);
                            plant_radiobutton2.setChecked(false);
                            bom_number_radiobutton1.setChecked(false);
                            bom_number_radiobutton2.setChecked(false);
                            Collections.sort(bom_list, new Comparator<BOM_List_Object>() {
                                @Override
                                public int compare(BOM_List_Object rhs, BOM_List_Object lhs) {
                                    return rhs.getBom().compareTo(lhs.getBom());
                                }
                            });
                            adapter = new Adapter(getActivity(), bom_list);
                            bom_list_recycleview.setHasFixedSize(true);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                            bom_list_recycleview.setLayoutManager(layoutManager);
                            bom_list_recycleview.setItemAnimator(new DefaultItemAnimator());
                            bom_list_recycleview.setAdapter(adapter);
                            search.setOnQueryTextListener(listener);
                            no_data_bomlist_textview.setVisibility(View.GONE);
                            bom_list_recycleview.setVisibility(View.VISIBLE);
                            dialog.dismiss();
                            sort_selected = "";
                        }
                    });
                    sort_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            if (checkedId == R.id.description_radiobutton1) {
                                sort_selected = "description_sort1";
                                Collections.sort(bom_list, new Comparator<BOM_List_Object>() {
                                    @Override
                                    public int compare(BOM_List_Object rhs, BOM_List_Object lhs) {
                                        return rhs.getBomDesc().compareTo(lhs.getBomDesc());
                                    }
                                });
                                adapter = new Adapter(getActivity(), bom_list);
                                bom_list_recycleview.setHasFixedSize(true);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                bom_list_recycleview.setLayoutManager(layoutManager);
                                bom_list_recycleview.setItemAnimator(new DefaultItemAnimator());
                                bom_list_recycleview.setAdapter(adapter);
                                search.setOnQueryTextListener(listener);
                                no_data_bomlist_textview.setVisibility(View.GONE);
                                bom_list_recycleview.setVisibility(View.VISIBLE);
                                dialog.dismiss();
                            } else if (checkedId == R.id.description_radiobutton2) {
                                sort_selected = "description_sort2";
                                Collections.sort(bom_list, new Comparator<BOM_List_Object>() {
                                    @Override
                                    public int compare(BOM_List_Object rhs, BOM_List_Object lhs) {
                                        return lhs.getBomDesc().compareTo(rhs.getBomDesc());
                                    }
                                });
                                adapter = new Adapter(getActivity(), bom_list);
                                bom_list_recycleview.setHasFixedSize(true);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                bom_list_recycleview.setLayoutManager(layoutManager);
                                bom_list_recycleview.setItemAnimator(new DefaultItemAnimator());
                                bom_list_recycleview.setAdapter(adapter);
                                search.setOnQueryTextListener(listener);
                                no_data_bomlist_textview.setVisibility(View.GONE);
                                bom_list_recycleview.setVisibility(View.VISIBLE);
                                dialog.dismiss();
                            } else if (checkedId == R.id.plant_radiobutton1) {
                                sort_selected = "plant_sort1";
                                Collections.sort(bom_list, new Comparator<BOM_List_Object>() {
                                    @Override
                                    public int compare(BOM_List_Object rhs, BOM_List_Object lhs) {
                                        return rhs.getPlant().compareTo(lhs.getPlant());
                                    }
                                });
                                adapter = new Adapter(getActivity(), bom_list);
                                bom_list_recycleview.setHasFixedSize(true);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                bom_list_recycleview.setLayoutManager(layoutManager);
                                bom_list_recycleview.setItemAnimator(new DefaultItemAnimator());
                                bom_list_recycleview.setAdapter(adapter);
                                search.setOnQueryTextListener(listener);
                                no_data_bomlist_textview.setVisibility(View.GONE);
                                bom_list_recycleview.setVisibility(View.VISIBLE);
                                dialog.dismiss();
                            } else if (checkedId == R.id.plant_radiobutton2) {
                                sort_selected = "plant_sort2";
                                Collections.sort(bom_list, new Comparator<BOM_List_Object>() {
                                    @Override
                                    public int compare(BOM_List_Object rhs, BOM_List_Object lhs) {
                                        return lhs.getPlant().compareTo(rhs.getPlant());
                                    }
                                });
                                adapter = new Adapter(getActivity(), bom_list);
                                bom_list_recycleview.setHasFixedSize(true);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                bom_list_recycleview.setLayoutManager(layoutManager);
                                bom_list_recycleview.setItemAnimator(new DefaultItemAnimator());
                                bom_list_recycleview.setAdapter(adapter);
                                search.setOnQueryTextListener(listener);
                                no_data_bomlist_textview.setVisibility(View.GONE);
                                bom_list_recycleview.setVisibility(View.VISIBLE);
                                dialog.dismiss();
                            } else if (checkedId == R.id.bom_number_radiobutton1) {
                                sort_selected = "bom_number_sort1";
                                Collections.sort(bom_list, new Comparator<BOM_List_Object>() {
                                    @Override
                                    public int compare(BOM_List_Object rhs, BOM_List_Object lhs) {
                                        return rhs.getBom().compareTo(lhs.getBom());
                                    }
                                });
                                adapter = new Adapter(getActivity(), bom_list);
                                bom_list_recycleview.setHasFixedSize(true);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                bom_list_recycleview.setLayoutManager(layoutManager);
                                bom_list_recycleview.setItemAnimator(new DefaultItemAnimator());
                                bom_list_recycleview.setAdapter(adapter);
                                search.setOnQueryTextListener(listener);
                                no_data_bomlist_textview.setVisibility(View.GONE);
                                bom_list_recycleview.setVisibility(View.VISIBLE);
                                dialog.dismiss();
                            } else if (checkedId == R.id.bom_number_radiobutton2) {
                                sort_selected = "bom_number_sort2";
                                Collections.sort(bom_list, new Comparator<BOM_List_Object>() {
                                    @Override
                                    public int compare(BOM_List_Object rhs, BOM_List_Object lhs) {
                                        return lhs.getBom().compareTo(rhs.getBom());
                                    }
                                });
                                adapter = new Adapter(getActivity(), bom_list);
                                bom_list_recycleview.setHasFixedSize(true);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                bom_list_recycleview.setLayoutManager(layoutManager);
                                bom_list_recycleview.setItemAnimator(new DefaultItemAnimator());
                                bom_list_recycleview.setAdapter(adapter);
                                search.setOnQueryTextListener(listener);
                                no_data_bomlist_textview.setVisibility(View.GONE);
                                bom_list_recycleview.setVisibility(View.VISIBLE);
                                dialog.dismiss();
                            }
                        }
                    });
                } else {
                    error_dialog.show_error_dialog(getActivity(), getString(R.string.nobom_equip));
                }
            }
        });

        cloud_search_imagview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cd = new ConnectionDetector(getActivity());
                isInternetPresent = cd.isConnectingToInternet();
                if (isInternetPresent)
                {
                    cloud_search_status = "cloud_search";
                    new Get_BOM_CloudSearch_Data().execute();
                }
                else
                {
                    network_connection_dialog.show_network_connection_dialog(getActivity());
                }
            }
        });

        return rootView;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ZXING_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(getActivity(), Barcode_Scanner_Activity.class);
                    startActivityForResult(intent, 0);
                } else {
                    Toast.makeText(getActivity(), getString(R.string.user_permscanr), Toast.LENGTH_LONG).show();
                }
                return;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && !data.equals("")) {
            if (requestCode == 0) {
                String message = data.getStringExtra("MESSAGE");
                searchview_textview.setText(message);
            }
        }
    }


    public String getData() {
        String aa = bom_list.size() + "";
        return aa;
    }


    private class Get_BOM_List_Data extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(getActivity(), getResources().getString(R.string.loading_bom));
            bom_list.clear();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Cursor cursor = FieldTekPro_db.rawQuery("select * from EtBomHeader", null);
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            BOM_List_Object blo = new BOM_List_Object(cursor.getString(1), cursor.getString(2), cursor.getString(3));
                            bom_list.add(blo);
                        }
                        while (cursor.moveToNext());
                    }
                } else {
                    cursor.close();
                }
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (bom_list.size() > 0) {
                Collections.sort(bom_list, new Comparator<BOM_List_Object>() {
                    @Override
                    public int compare(BOM_List_Object rhs, BOM_List_Object lhs) {
                        return rhs.getBom().compareTo(lhs.getBom());
                    }
                });
                adapter = new Adapter(getActivity(), bom_list);
                bom_list_recycleview.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                bom_list_recycleview.setLayoutManager(layoutManager);
                bom_list_recycleview.setItemAnimator(new DefaultItemAnimator());
                bom_list_recycleview.setAdapter(adapter);
                search.setOnQueryTextListener(listener);
                no_data_bomlist_textview.setVisibility(View.GONE);
                bom_list_recycleview.setVisibility(View.VISIBLE);
            } else {
                no_data_bomlist_textview.setVisibility(View.VISIBLE);
                bom_list_recycleview.setVisibility(View.GONE);
            }
            ((Utilities_Activity) getActivity()).refreshMyData();
            custom_progress_dialog.dismiss_progress_dialog();
            swiperefreshlayout.setRefreshing(false);
        }
    }


    SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextChange(String query) {
            query = query.toLowerCase();
            final List<BOM_List_Object> filteredList = new ArrayList<>();
            for (int i = 0; i < bom_list.size(); i++) {
                String bom = bom_list.get(i).getBom().toLowerCase();
                String bom_desc = bom_list.get(i).getBomDesc().toLowerCase();
                String plant = bom_list.get(i).getPlant().toLowerCase();
                if (bom.contains(query) || bom_desc.contains(query) || plant.contains(query)) {
                    BOM_List_Object blo = new BOM_List_Object(bom_list.get(i).getBom().toString(), bom_list.get(i).getBomDesc().toString(), bom_list.get(i).getPlant().toString());
                    filteredList.add(blo);
                }
            }
            if (filteredList.size() > 0) {
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                bom_list_recycleview.setLayoutManager(layoutManager);
                adapter = new Adapter(getActivity(), filteredList);
                bom_list_recycleview.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                no_data_bomlist_textview.setVisibility(View.GONE);
                bom_list_recycleview.setVisibility(View.VISIBLE);
                cloud_search_imagview.setVisibility(View.GONE);
            } else {
                cloud_search_imagview.setVisibility(View.VISIBLE);
                no_data_bomlist_textview.setVisibility(View.VISIBLE);
                bom_list_recycleview.setVisibility(View.GONE);
            }
            return true;
        }

        public boolean onQueryTextSubmit(String query) {
            return false;
        }
    };


    public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
        private Context mContext;
        private List<BOM_List_Object> bom_list_data;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView bom_number_textview, bom_desc_textview, plant_textview;
            LinearLayout bom_list_data_layout;

            public MyViewHolder(View view) {
                super(view);
                bom_number_textview = (TextView) view.findViewById(R.id.bom_number_textview);
                bom_desc_textview = (TextView) view.findViewById(R.id.bom_desc_textview);
                plant_textview = (TextView) view.findViewById(R.id.plant_textview);
                bom_list_data_layout = (LinearLayout) view.findViewById(R.id.bom_list_data_layout);
            }
        }

        public Adapter(Context mContext, List<BOM_List_Object> bomlist) {
            this.mContext = mContext;
            this.bom_list_data = bomlist;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.utilities_bom_list_cardview, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final BOM_List_Object blo = bom_list_data.get(position);
            holder.bom_number_textview.setText(blo.getBom());
            holder.bom_desc_textview.setText(blo.getBomDesc());
            holder.plant_textview.setText(blo.getPlant());

            holder.bom_list_data_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bom_header_id = holder.bom_number_textview.getText().toString();
                    bom_header_desc = holder.bom_desc_textview.getText().toString();
                    bom_header_plant = holder.plant_textview.getText().toString();
                    try {
                        Cursor cursor = FieldTekPro_db.rawQuery("select * from EtBomItem where Bom =?", new String[]{bom_header_id});
                        if (cursor != null && cursor.getCount() > 0) {
                            Intent bom_detailed_intent = new Intent(getActivity(), BOM_List_DetailedView_Activity.class);
                            bom_detailed_intent.putExtra("bom_id", holder.bom_number_textview.getText().toString());
                            bom_detailed_intent.putExtra("bom_desc", holder.bom_desc_textview.getText().toString());
                            bom_detailed_intent.putExtra("bom_plant", holder.plant_textview.getText().toString());
                            startActivity(bom_detailed_intent);
                        } else {
                            cursor.close();
                            cd = new ConnectionDetector(getActivity());
                            isInternetPresent = cd.isConnectingToInternet();
                            if (isInternetPresent) {
                                cloud_search_status = "bom_child";
                                new Get_BOM_CloudSearch_Data().execute();
                            } else {
                                //showing network error and navigating to wifi settings.
                                network_connection_dialog.show_network_connection_dialog(getActivity());
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            });

            /*holder.cbSelect.setTag(position);
            holder.cbSelect.setChecked((bom_list_data.get(position).getStatus() == true ? true : false));
            holder.cbSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Integer pos = (Integer) holder.cbSelect.getTag();

                    if (bom_list_data.get(pos).getStatus())
                    {
                        bom_list_data.get(pos).setStatus(false);
                    }
                    else
                    {
                        bom_list_data.get(pos).setStatus(true);
                    }
                }
            });*/

        }

        @Override
        public int getItemCount() {
            return bom_list_data.size();
        }
    }


    public class Get_LoadSettings_Data extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(getActivity(), getResources().getString(R.string.refresh_bom));
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                BOM_Refresh_status = LoadSettings.Get_LoadSettings_Data(getActivity(), "");
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (BOM_Refresh_status.equalsIgnoreCase("success")) {
                String BOM_Load_Status_load_status = FieldTekPro_SharedPref.getString("Ebom_Load_status", null);
                if (BOM_Load_Status_load_status.equalsIgnoreCase("X")) {
                    new Get_BOM_Data().execute();
                } else {
                    custom_progress_dialog.dismiss_progress_dialog();
                    swiperefreshlayout.setRefreshing(false);
                    error_dialog.show_error_dialog(getActivity(), getString(R.string.data_uptodate));
                }
            } else {
                swiperefreshlayout.setRefreshing(false);
                custom_progress_dialog.dismiss_progress_dialog();
                error_dialog.show_error_dialog(getActivity(), getString(R.string.data_uptodate));
            }
        }
    }


    public class Get_BOM_Data extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                BOM_status = BOM.Get_BOM_Data(getActivity(), "", "");
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            custom_progress_dialog.dismiss_progress_dialog();
            swiperefreshlayout.setRefreshing(false);
            search.setQuery("", false);
            search.clearFocus();
            new Get_BOM_List_Data().execute();
        }
    }




    public class Get_BOM_REST_Data extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(getActivity(), getResources().getString(R.string.refresh_bom));
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                BOM_status = REST_BOM.Get_BOM_Data(getActivity(), "", "");
            }
            catch (Exception e)
            {
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values)
        {
        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            custom_progress_dialog.dismiss_progress_dialog();
            swiperefreshlayout.setRefreshing(false);
            search.setQuery("", false);
            search.clearFocus();
            new Get_BOM_List_Data().execute();
        }
    }




    public class Get_BOM_CloudSearch_Data extends AsyncTask<Void, Integer, Void>
    {
        String bom_id = "";
        String webservice_type = "rest";

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            if (cloud_search_status.equalsIgnoreCase("cloud_search"))
            {
                custom_progress_dialog.show_progress_dialog(getActivity(),
                        getString(R.string.search_for, searchview_textview.getText().toString()));
                bom_id = searchview_textview.getText().toString();
            }
            else
            {
                custom_progress_dialog.show_progress_dialog(getActivity(),
                        getString(R.string.loading_bomno, bom_header_id));
                bom_id = bom_header_id;
            }
            webservice_type = getString(R.string.webservice_type);
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                if(webservice_type.equalsIgnoreCase("odata"))
                {
                    BOMITEM_status = BOM.Get_BOM_Data(getActivity(), "", bom_id);
                }
                else
                {
                    BOMITEM_status = REST_BOM.Get_BOM_Data(getActivity(), "", bom_id);
                }
            }
            catch (Exception e)
            {
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ((Utilities_Activity) getActivity()).refreshMyData();
            custom_progress_dialog.dismiss_progress_dialog();
            if (cloud_search_status.equalsIgnoreCase("cloud_search"))
            {
                new Get_BOM_List_Data().execute();
            }
            else
            {
                Intent bom_detailed_intent = new Intent(getActivity(), BOM_List_DetailedView_Activity.class);
                bom_detailed_intent.putExtra("bom_id", bom_header_id);
                bom_detailed_intent.putExtra("bom_desc", bom_header_desc);
                bom_detailed_intent.putExtra("bom_plant", bom_header_plant);
                startActivity(bom_detailed_intent);
            }
        }
    }



    public class BOM_List_Object {
        private String Bom;
        private String BomDesc;
        private String Plant;

        public BOM_List_Object() {
        }

        public BOM_List_Object(String Bom, String BomDesc, String Plant) {
            this.Bom = Bom;
            this.BomDesc = BomDesc;
            this.Plant = Plant;
        }

        public String getBom() {
            return Bom;
        }

        public void setBom(String Bom) {
            this.Bom = Bom;
        }

        public String getBomDesc() {
            return BomDesc;
        }

        public void setBomDesc(String BomDesc) {
            this.BomDesc = BomDesc;
        }

        public String getPlant() {
            return Plant;
        }

        public void setPlant(String Plant) {
            this.Plant = Plant;
        }
    }

}
