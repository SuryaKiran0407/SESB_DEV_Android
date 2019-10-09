package com.enstrapp.fieldtekpro.Utilities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
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

import com.bumptech.glide.Glide;
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


public class Stock_List_Activity extends Fragment {

    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";
    SwipeRefreshLayout swiperefreshlayout;
    Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog();
    public SearchView search;
    TextView no_data_textview, searchview_textview;
    FloatingActionButton refresh_fab_button, sort_fab_button;
    ConnectionDetector cd;
    Boolean isInternetPresent = false;
    FloatingActionMenu floatingActionMenu;
    SharedPreferences FieldTekPro_SharedPref;
    SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    String sort_selected = "", BOM_status = "", BOM_Refresh_status = "", username = "", password = "", device_serial_number = "", device_id = "", device_uuid = "";
    Error_Dialog error_dialog = new Error_Dialog();
    private List<STOCK_List_Object> stock_list = new ArrayList<>();
    private Adapter adapter;
    RecyclerView stock_list_recycleview;
    Dialog decision_dialog;
    Network_Connection_Dialog network_connection_dialog = new Network_Connection_Dialog();

    public Stock_List_Activity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.utilities_stock_list_activity, container, false);

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

        stock_list_recycleview = (RecyclerView) rootView.findViewById(R.id.bom_list_recycleview);
        swiperefreshlayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefreshlayout);
        search = (SearchView) rootView.findViewById(R.id.search);
        no_data_textview = (TextView) rootView.findViewById(R.id.no_data_textview);
        refresh_fab_button = (FloatingActionButton) rootView.findViewById(R.id.refresh_fab_button);
        sort_fab_button = (FloatingActionButton) rootView.findViewById(R.id.sort_fab_button);
        floatingActionMenu = (FloatingActionMenu) rootView.findViewById(R.id.floatingactionmenu);

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

        new Get_STOCK_List_Data().execute();

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
                            new Get_LoadSettings_Data().execute();
                        }
                    });
                } else {
                    //showing network error and navigating to wifi settings.
                    swiperefreshlayout.setRefreshing(false);
                    network_connection_dialog.show_network_connection_dialog(getActivity());
                }
            }
        });

        swiperefreshlayout.setColorSchemeResources(R.color.red, R.color.lime, R.color.colorAccent, R.color.red, R.color.blue, R.color.black, R.color.orange);

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
                            new Get_LoadSettings_Data().execute();
                        }
                    });
                } else {
                    //showing network error and navigating to wifi settings.
                    network_connection_dialog.show_network_connection_dialog(getActivity());
                }
            }
        });


        sort_fab_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionMenu.close(true);
                if (stock_list.size() > 0) {
                    final Dialog dialog = new Dialog(getActivity(), R.style.AppThemeDialog_Dark);
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.utilities_bom_sort_dialog);
                    dialog.setCancelable(true);
                    dialog.setCanceledOnTouchOutside(false);
                    TextView sort_clear_textview = (TextView) dialog.findViewById(R.id.sort_clear_textview);
                    TextView text_textview = (TextView) dialog.findViewById(R.id.text_textview);
                    text_textview.setText(getResources().getString(R.string.material_num));
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
                            Collections.sort(stock_list, new Comparator<STOCK_List_Object>() {
                                @Override
                                public int compare(STOCK_List_Object rhs, STOCK_List_Object lhs) {
                                    return rhs.getMatnr().compareTo(lhs.getMatnr());
                                }
                            });
                            adapter = new Adapter(getActivity(), stock_list);
                            stock_list_recycleview.setHasFixedSize(true);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                            stock_list_recycleview.setLayoutManager(layoutManager);
                            stock_list_recycleview.setItemAnimator(new DefaultItemAnimator());
                            stock_list_recycleview.setAdapter(adapter);
                            search.setOnQueryTextListener(listener);
                            no_data_textview.setVisibility(View.GONE);
                            stock_list_recycleview.setVisibility(View.VISIBLE);
                            dialog.dismiss();
                            sort_selected = "";
                        }
                    });
                    sort_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            if (checkedId == R.id.description_radiobutton1) {
                                sort_selected = "description_sort1";
                                Collections.sort(stock_list, new Comparator<STOCK_List_Object>() {
                                    @Override
                                    public int compare(STOCK_List_Object rhs, STOCK_List_Object lhs) {
                                        return rhs.getMaktx().compareTo(lhs.getMaktx());
                                    }
                                });
                                adapter = new Adapter(getActivity(), stock_list);
                                stock_list_recycleview.setHasFixedSize(true);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                stock_list_recycleview.setLayoutManager(layoutManager);
                                stock_list_recycleview.setItemAnimator(new DefaultItemAnimator());
                                stock_list_recycleview.setAdapter(adapter);
                                search.setOnQueryTextListener(listener);
                                no_data_textview.setVisibility(View.GONE);
                                stock_list_recycleview.setVisibility(View.VISIBLE);
                                dialog.dismiss();
                            } else if (checkedId == R.id.description_radiobutton2) {
                                sort_selected = "description_sort2";
                                Collections.sort(stock_list, new Comparator<STOCK_List_Object>() {
                                    @Override
                                    public int compare(STOCK_List_Object rhs, STOCK_List_Object lhs) {
                                        return lhs.getMaktx().compareTo(rhs.getMaktx());
                                    }
                                });
                                adapter = new Adapter(getActivity(), stock_list);
                                stock_list_recycleview.setHasFixedSize(true);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                stock_list_recycleview.setLayoutManager(layoutManager);
                                stock_list_recycleview.setItemAnimator(new DefaultItemAnimator());
                                stock_list_recycleview.setAdapter(adapter);
                                search.setOnQueryTextListener(listener);
                                no_data_textview.setVisibility(View.GONE);
                                stock_list_recycleview.setVisibility(View.VISIBLE);
                                dialog.dismiss();
                            } else if (checkedId == R.id.plant_radiobutton1) {
                                sort_selected = "plant_sort1";
                                Collections.sort(stock_list, new Comparator<STOCK_List_Object>() {
                                    @Override
                                    public int compare(STOCK_List_Object rhs, STOCK_List_Object lhs) {
                                        return rhs.getWerks().compareTo(lhs.getWerks());
                                    }
                                });
                                adapter = new Adapter(getActivity(), stock_list);
                                stock_list_recycleview.setHasFixedSize(true);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                stock_list_recycleview.setLayoutManager(layoutManager);
                                stock_list_recycleview.setItemAnimator(new DefaultItemAnimator());
                                stock_list_recycleview.setAdapter(adapter);
                                search.setOnQueryTextListener(listener);
                                no_data_textview.setVisibility(View.GONE);
                                stock_list_recycleview.setVisibility(View.VISIBLE);
                                dialog.dismiss();
                            } else if (checkedId == R.id.plant_radiobutton2) {
                                sort_selected = "plant_sort2";
                                Collections.sort(stock_list, new Comparator<STOCK_List_Object>() {
                                    @Override
                                    public int compare(STOCK_List_Object rhs, STOCK_List_Object lhs) {
                                        return lhs.getWerks().compareTo(rhs.getWerks());
                                    }
                                });
                                adapter = new Adapter(getActivity(), stock_list);
                                stock_list_recycleview.setHasFixedSize(true);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                stock_list_recycleview.setLayoutManager(layoutManager);
                                stock_list_recycleview.setItemAnimator(new DefaultItemAnimator());
                                stock_list_recycleview.setAdapter(adapter);
                                search.setOnQueryTextListener(listener);
                                no_data_textview.setVisibility(View.GONE);
                                stock_list_recycleview.setVisibility(View.VISIBLE);
                                dialog.dismiss();
                            } else if (checkedId == R.id.bom_number_radiobutton1) {
                                sort_selected = "bom_number_sort1";
                                Collections.sort(stock_list, new Comparator<STOCK_List_Object>() {
                                    @Override
                                    public int compare(STOCK_List_Object rhs, STOCK_List_Object lhs) {
                                        return rhs.getMatnr().compareTo(lhs.getMatnr());
                                    }
                                });
                                adapter = new Adapter(getActivity(), stock_list);
                                stock_list_recycleview.setHasFixedSize(true);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                stock_list_recycleview.setLayoutManager(layoutManager);
                                stock_list_recycleview.setItemAnimator(new DefaultItemAnimator());
                                stock_list_recycleview.setAdapter(adapter);
                                search.setOnQueryTextListener(listener);
                                no_data_textview.setVisibility(View.GONE);
                                stock_list_recycleview.setVisibility(View.VISIBLE);
                                dialog.dismiss();
                            } else if (checkedId == R.id.bom_number_radiobutton2) {
                                sort_selected = "bom_number_sort2";
                                Collections.sort(stock_list, new Comparator<STOCK_List_Object>() {
                                    @Override
                                    public int compare(STOCK_List_Object rhs, STOCK_List_Object lhs) {
                                        return lhs.getMatnr().compareTo(rhs.getMatnr());
                                    }
                                });
                                adapter = new Adapter(getActivity(), stock_list);
                                stock_list_recycleview.setHasFixedSize(true);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                stock_list_recycleview.setLayoutManager(layoutManager);
                                stock_list_recycleview.setItemAnimator(new DefaultItemAnimator());
                                stock_list_recycleview.setAdapter(adapter);
                                search.setOnQueryTextListener(listener);
                                no_data_textview.setVisibility(View.GONE);
                                stock_list_recycleview.setVisibility(View.VISIBLE);
                                dialog.dismiss();
                            }
                        }
                    });
                } else {
                    error_dialog.show_error_dialog(getActivity(), getString(R.string.no_stckfrsort));
                }
            }
        });


        return rootView;
    }


    private class Get_STOCK_List_Data extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(getActivity(), getResources().getString(R.string.loading_bom));
            stock_list.clear();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Cursor cursor = FieldTekPro_db.rawQuery("select * from GET_STOCK_DATA", null);
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            STOCK_List_Object blo = new STOCK_List_Object(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(8), cursor.getString(7), cursor.getString(10));
                            stock_list.add(blo);
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
            if (stock_list.size() > 0) {
                Collections.sort(stock_list, new Comparator<STOCK_List_Object>() {
                    @Override
                    public int compare(STOCK_List_Object rhs, STOCK_List_Object lhs) {
                        return rhs.getMatnr().compareTo(lhs.getMatnr());
                    }
                });
                adapter = new Adapter(getActivity(), stock_list);
                stock_list_recycleview.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                stock_list_recycleview.setLayoutManager(layoutManager);
                stock_list_recycleview.setItemAnimator(new DefaultItemAnimator());
                stock_list_recycleview.setAdapter(adapter);
                search.setOnQueryTextListener(listener);
                no_data_textview.setVisibility(View.GONE);
                stock_list_recycleview.setVisibility(View.VISIBLE);
            } else {
                no_data_textview.setVisibility(View.VISIBLE);
                stock_list_recycleview.setVisibility(View.GONE);
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
            final List<STOCK_List_Object> filteredList = new ArrayList<>();
            for (int i = 0; i < stock_list.size(); i++) {
                String matnr = stock_list.get(i).getMatnr().toLowerCase();
                String matkx = stock_list.get(i).getMaktx().toLowerCase();
                String plant = stock_list.get(i).getWerks().toLowerCase();
                if (matnr.contains(query) || matkx.contains(query) || plant.contains(query)) {
                    STOCK_List_Object blo = new STOCK_List_Object(stock_list.get(i).getMatnr().toString(), stock_list.get(i).getWerks().toString(), stock_list.get(i).getMaktx().toString(), stock_list.get(i).getLgort().toString(), stock_list.get(i).getLabst().toString(), stock_list.get(i).getSpeme().toString(), stock_list.get(i).getLgpbe().toString(), stock_list.get(i).getBwtar(), stock_list.get(i).getCharg());
                    filteredList.add(blo);
                }
            }
            if (filteredList.size() > 0) {
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                stock_list_recycleview.setLayoutManager(layoutManager);
                adapter = new Adapter(getActivity(), filteredList);
                stock_list_recycleview.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                no_data_textview.setVisibility(View.GONE);
                stock_list_recycleview.setVisibility(View.VISIBLE);
            } else {
                no_data_textview.setVisibility(View.VISIBLE);
                stock_list_recycleview.setVisibility(View.GONE);
            }
            return true;
        }

        public boolean onQueryTextSubmit(String query) {
            return false;
        }
    };


    public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
        private Context mContext;
        private List<STOCK_List_Object> bom_list_data;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView batch_textview, valuation_type_textview,material_number_textview, desc_textview, unrestricted_textview,strgBin_tv,strgLoc_tv, plant_tv;
            LinearLayout bom_list_data_layout;

            public MyViewHolder(View view) {
                super(view);
                material_number_textview = (TextView) view.findViewById(R.id.material_number_textview);
                desc_textview = (TextView) view.findViewById(R.id.desc_textview);
                unrestricted_textview = (TextView) view.findViewById(R.id.unrestricted_textview);
                strgBin_tv = (TextView) view.findViewById(R.id.strgBin_tv);
                strgLoc_tv = (TextView) view.findViewById(R.id.strgLoc_tv);
                plant_tv = (TextView) view.findViewById(R.id.plant_tv);
                valuation_type_textview = (TextView) view.findViewById(R.id.valuation_type_textview);
                batch_textview = (TextView) view.findViewById(R.id.batch_textview);
                bom_list_data_layout = (LinearLayout) view.findViewById(R.id.bom_list_data_layout);
            }
        }

        public Adapter(Context mContext, List<STOCK_List_Object> bomlist) {
            this.mContext = mContext;
            this.bom_list_data = bomlist;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.utilities_stock_list_cardview, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final STOCK_List_Object blo = bom_list_data.get(position);
            holder.material_number_textview.setText(blo.getMatnr());
            holder.desc_textview.setText(blo.getMaktx());
            holder.unrestricted_textview.setText(blo.getLabst());
            holder.strgBin_tv.setText(blo.getLgpbe());
            holder.strgLoc_tv.setText(blo.getLgort());
            holder.plant_tv.setText(blo.getWerks());
            holder.valuation_type_textview.setText(blo.getBwtar());
            holder.batch_textview.setText(blo.getCharg());

            holder.bom_list_data_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), Stock_DetailedView_Activity.class);
                    intent.putExtra("Matnr", blo.getMatnr());
                    intent.putExtra("Werks", blo.getWerks());
                    intent.putExtra("Maktx", blo.getMaktx());
                    intent.putExtra("Lgort", blo.getLgort());
                    intent.putExtra("Labst", blo.getLabst());
                    intent.putExtra("Speme", blo.getSpeme());
                    intent.putExtra("Lgpbe", blo.getLgpbe());
                    intent.putExtra("Val_type", blo.getBwtar());
                    intent.putExtra("Batch", blo.getCharg());
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return bom_list_data.size();
        }
    }


    public class STOCK_List_Object {
        private String Matnr;
        private String Werks;
        private String Maktx;
        private String Lgort;
        private String Labst;
        private String Speme;
        private String Lgpbe;
        private String Bwtar;
        private String Charg;

        public String getCharg() {
            return Charg;
        }

        public void setCharg(String charg) {
            Charg = charg;
        }

        public String getBwtar() {
            return Bwtar;
        }

        public void setBwtar(String bwtar) {
            Bwtar = bwtar;
        }

        public String getMatnr() {
            return Matnr;
        }

        public void setMatnr(String matnr) {
            Matnr = matnr;
        }

        public String getWerks() {
            return Werks;
        }

        public void setWerks(String werks) {
            Werks = werks;
        }

        public String getMaktx() {
            return Maktx;
        }

        public void setMaktx(String maktx) {
            Maktx = maktx;
        }

        public String getLgort() {
            return Lgort;
        }

        public void setLgort(String lgort) {
            Lgort = lgort;
        }

        public String getLabst() {
            return Labst;
        }

        public void setLabst(String labst) {
            Labst = labst;
        }

        public String getSpeme() {
            return Speme;
        }

        public void setSpeme(String speme) {
            Speme = speme;
        }

        public String getLgpbe() {
            return Lgpbe;
        }

        public void setLgpbe(String lgpbe) {
            Lgpbe = lgpbe;
        }

        public STOCK_List_Object() {
        }

        public STOCK_List_Object(String Matnr, String Werks, String Maktx, String Lgort, String Labst, String Speme, String Lgpbe, String Bwtar, String Charg) {
            this.Matnr = Matnr;
            this.Werks = Werks;
            this.Maktx = Maktx;
            this.Lgort = Lgort;
            this.Labst = Labst;
            this.Speme = Speme;
            this.Lgpbe = Lgpbe;
            this.Bwtar = Bwtar;
            this.Charg = Charg;
        }

    }


    public class Get_LoadSettings_Data extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(getActivity(), getResources().getString(R.string.refresh_stock));
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
            new Get_STOCK_List_Data().execute();
        }
    }


}
