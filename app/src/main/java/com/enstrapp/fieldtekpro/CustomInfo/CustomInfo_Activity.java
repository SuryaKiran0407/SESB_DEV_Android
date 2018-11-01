package com.enstrapp.fieldtekpro.CustomInfo;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CustomInfo_Activity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView custominfo_recyclerview;
    Button cancel_button, add_button;
    TextView nodata_textview;
    String Zdoctype = "", ZdoctypeItem = "";
    Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog();
    private List<Custom_Info_Object> custominfo_data_list = new ArrayList<>();
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";
    CUSTOM_INFO_ADAPTER adapter;
    ImageView back_imageview;
    LinearLayout footer_layout;
    int request_id = 0;
    ArrayList<HashMap<String, String>> selected_custom_info_arraylist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custominfo_activity);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Zdoctype = bundle.getString("Zdoctype");
            ZdoctypeItem = bundle.getString("ZdoctypeItem");
            String request_ids = bundle.getString("request_id");
            if (request_ids != null && !request_ids.equals("")) {
                request_id = Integer.parseInt(request_ids);
            }
            selected_custom_info_arraylist = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("custom_info_arraylist");
        }


        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);


        custominfo_recyclerview = (RecyclerView) findViewById(R.id.custominfo_recyclerview);
        cancel_button = (Button) findViewById(R.id.cancel_button);
        add_button = (Button) findViewById(R.id.add_button);
        nodata_textview = (TextView) findViewById(R.id.nodata_textview);
        back_imageview = (ImageView) findViewById(R.id.back_imageview);
        footer_layout = (LinearLayout) findViewById(R.id.footer_layout);


        cancel_button.setOnClickListener(this);
        add_button.setOnClickListener(this);
        back_imageview.setOnClickListener(this);


        custominfo_data_list.clear();

        if (selected_custom_info_arraylist.size() > 0) {
            for (int i = 0; i < selected_custom_info_arraylist.size(); i++) {
                Custom_Info_Object nto = new Custom_Info_Object(selected_custom_info_arraylist.get(i).get("Fieldname"), selected_custom_info_arraylist.get(i).get("ZdoctypeItem"), selected_custom_info_arraylist.get(i).get("Datatype"), selected_custom_info_arraylist.get(i).get("Tabname"), selected_custom_info_arraylist.get(i).get("Zdoctype"), selected_custom_info_arraylist.get(i).get("Sequence"), selected_custom_info_arraylist.get(i).get("Flabel"), selected_custom_info_arraylist.get(i).get("Spras"), selected_custom_info_arraylist.get(i).get("Length"), selected_custom_info_arraylist.get(i).get("Value"));
                custominfo_data_list.add(nto);
            }
            adapter = new CUSTOM_INFO_ADAPTER(CustomInfo_Activity.this, custominfo_data_list);
            custominfo_recyclerview.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CustomInfo_Activity.this);
            custominfo_recyclerview.setLayoutManager(layoutManager);
            custominfo_recyclerview.setItemAnimator(new DefaultItemAnimator());
            custominfo_recyclerview.setAdapter(adapter);
            nodata_textview.setVisibility(View.GONE);
            custominfo_recyclerview.setVisibility(View.VISIBLE);
            footer_layout.setVisibility(View.VISIBLE);
        } else {
            new Get_Custom_Info_Data().execute();
        }

    }


    @Override
    public void onClick(View v) {
        if (v == cancel_button) {
            CustomInfo_Activity.this.finish();
        } else if (v == back_imageview) {
            CustomInfo_Activity.this.finish();
        } else if (v == add_button) {
            ArrayList<HashMap<String, String>> selected_custom_info_arraylist = new ArrayList<HashMap<String, String>>();
            for (int i = 0; i < custominfo_data_list.size(); i++) {
                HashMap<String, String> custom_info_hashMap = new HashMap<String, String>();
                custom_info_hashMap.put("Fieldname", custominfo_data_list.get(i).getFieldname());
                custom_info_hashMap.put("ZdoctypeItem", custominfo_data_list.get(i).getZdoctypeItem());
                custom_info_hashMap.put("Datatype", custominfo_data_list.get(i).getDatatype());
                custom_info_hashMap.put("Tabname", custominfo_data_list.get(i).getTabname());
                custom_info_hashMap.put("Zdoctype", custominfo_data_list.get(i).getZdoctype());
                custom_info_hashMap.put("Sequence", custominfo_data_list.get(i).getSequence());
                custom_info_hashMap.put("Flabel", custominfo_data_list.get(i).getFlabel());
                custom_info_hashMap.put("Spras", custominfo_data_list.get(i).getSpras());
                custom_info_hashMap.put("Length", custominfo_data_list.get(i).getLength());
                custom_info_hashMap.put("Value", custominfo_data_list.get(i).getValue());
                selected_custom_info_arraylist.add(custom_info_hashMap);
            }
            Intent intent = new Intent();
            intent.putExtra("selected_custom_info_arraylist", selected_custom_info_arraylist);
            setResult(RESULT_OK, intent);
            CustomInfo_Activity.this.finish();
        }
    }


    private class Get_Custom_Info_Data extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(CustomInfo_Activity.this, getResources().getString(R.string.loading));
            custominfo_data_list.clear();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Cursor cursor = FieldTekPro_db.rawQuery("select * from GET_CUSTOM_FIELDS where Zdoctype = ? and ZdoctypeItem = ? order by Sequence", new String[]{Zdoctype, ZdoctypeItem});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            Custom_Info_Object nto = new Custom_Info_Object(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), "");
                            custominfo_data_list.add(nto);
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
            if (custominfo_data_list.size() > 0) {
                adapter = new CUSTOM_INFO_ADAPTER(CustomInfo_Activity.this, custominfo_data_list);
                custominfo_recyclerview.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CustomInfo_Activity.this);
                custominfo_recyclerview.setLayoutManager(layoutManager);
                custominfo_recyclerview.setItemAnimator(new DefaultItemAnimator());
                custominfo_recyclerview.setAdapter(adapter);
                nodata_textview.setVisibility(View.GONE);
                custominfo_recyclerview.setVisibility(View.VISIBLE);
                footer_layout.setVisibility(View.VISIBLE);
            } else {
                nodata_textview.setVisibility(View.VISIBLE);
                custominfo_recyclerview.setVisibility(View.GONE);
                footer_layout.setVisibility(View.GONE);
            }
            custom_progress_dialog.dismiss_progress_dialog();
        }
    }


    public class CUSTOM_INFO_ADAPTER extends RecyclerView.Adapter<CUSTOM_INFO_ADAPTER.MyViewHolder> {
        private Context mContext;
        private List<Custom_Info_Object> custom_info_details_list;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public LinearLayout date_layout, time_layout;
            public TextInputLayout char_numc_dec_TextInputLayout, date_TextInputLayout, time_TextInputLayout;
            public TextInputEditText char_numc_dec_edittext, date_edittext, time_edittext;
            public TextView Spras_textview, Value_textview, Length_textview, Flabel_textview, Sequence_textview, Zdoctype_textview, Tabname_textview, Datatype_textview, ZdoctypeItem_textview, Fieldname_textview;

            public MyViewHolder(View view) {
                super(view);
                char_numc_dec_TextInputLayout = (TextInputLayout) view.findViewById(R.id.char_numc_dec_TextInputLayout);
                char_numc_dec_edittext = (TextInputEditText) view.findViewById(R.id.char_numc_dec_edittext);
                Fieldname_textview = (TextView) view.findViewById(R.id.Fieldname_textview);
                ZdoctypeItem_textview = (TextView) view.findViewById(R.id.ZdoctypeItem_textview);
                Datatype_textview = (TextView) view.findViewById(R.id.Datatype_textview);
                Tabname_textview = (TextView) view.findViewById(R.id.Tabname_textview);
                Zdoctype_textview = (TextView) view.findViewById(R.id.Zdoctype_textview);
                Sequence_textview = (TextView) view.findViewById(R.id.Sequence_textview);
                Flabel_textview = (TextView) view.findViewById(R.id.Flabel_textview);
                Length_textview = (TextView) view.findViewById(R.id.Length_textview);
                Value_textview = (TextView) view.findViewById(R.id.Value_textview);
                Spras_textview = (TextView) view.findViewById(R.id.Spras_textview);
                date_layout = (LinearLayout) view.findViewById(R.id.date_layout);
                date_edittext = (TextInputEditText) view.findViewById(R.id.date_edittext);
                date_TextInputLayout = (TextInputLayout) view.findViewById(R.id.date_TextInputLayout);
                time_layout = (LinearLayout) view.findViewById(R.id.time_layout);
                time_TextInputLayout = (TextInputLayout) view.findViewById(R.id.time_TextInputLayout);
                time_edittext = (TextInputEditText) view.findViewById(R.id.time_edittext);
            }
        }

        public CUSTOM_INFO_ADAPTER(Context mContext, List<Custom_Info_Object> list) {
            this.mContext = mContext;
            this.custom_info_details_list = list;
        }

        @Override
        public CUSTOM_INFO_ADAPTER.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custominfo_list_data, parent, false);
            return new CUSTOM_INFO_ADAPTER.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final CUSTOM_INFO_ADAPTER.MyViewHolder holder, final int position) {
            final Custom_Info_Object nto = custom_info_details_list.get(position);

            holder.Fieldname_textview.setText(nto.getFieldname());
            holder.ZdoctypeItem_textview.setText(nto.getZdoctypeItem());
            holder.Datatype_textview.setText(nto.getDatatype());
            holder.Tabname_textview.setText(nto.getTabname());
            holder.Zdoctype_textview.setText(nto.getZdoctype());
            holder.Sequence_textview.setText(nto.getSequence());
            holder.Flabel_textview.setText(nto.getFlabel());
            holder.Length_textview.setText(nto.getLength());
            holder.Value_textview.setText(nto.getValue());
            holder.Spras_textview.setText(nto.getSpras());

            if (holder.Datatype_textview.getText().toString().equalsIgnoreCase("CHAR")) {
                holder.char_numc_dec_TextInputLayout.setVisibility(View.VISIBLE);
                holder.char_numc_dec_TextInputLayout.setHint(nto.getFlabel());
                holder.char_numc_dec_edittext.setText(nto.getValue());
                holder.char_numc_dec_edittext.setInputType(InputType.TYPE_CLASS_TEXT);
                String length = holder.Length_textview.getText().toString();
                int maxLength = Integer.parseInt(length);
                InputFilter[] fArray = new InputFilter[1];
                fArray[0] = new InputFilter.LengthFilter(maxLength);
                holder.char_numc_dec_edittext.setFilters(fArray);
                holder.date_layout.setVisibility(View.GONE);
                holder.date_edittext.setText("");
                holder.date_TextInputLayout.setHint("");
                holder.time_layout.setVisibility(View.GONE);
                holder.time_edittext.setText("");
                holder.time_TextInputLayout.setHint("");
            } else if (holder.Datatype_textview.getText().toString().equalsIgnoreCase("NUMC")) {
                holder.char_numc_dec_TextInputLayout.setVisibility(View.VISIBLE);
                holder.char_numc_dec_TextInputLayout.setHint(nto.getFlabel());
                holder.char_numc_dec_edittext.setText(nto.getValue());
                holder.char_numc_dec_edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
                String length = holder.Length_textview.getText().toString();
                int maxLength = Integer.parseInt(length);
                InputFilter[] fArray = new InputFilter[1];
                fArray[0] = new InputFilter.LengthFilter(maxLength);
                holder.char_numc_dec_edittext.setFilters(fArray);
                holder.date_layout.setVisibility(View.GONE);
                holder.date_edittext.setText("");
                holder.date_TextInputLayout.setHint("");
                holder.time_layout.setVisibility(View.GONE);
                holder.time_edittext.setText("");
                holder.time_TextInputLayout.setHint("");
            } else if (holder.Datatype_textview.getText().toString().equalsIgnoreCase("DEC")) {
                holder.char_numc_dec_TextInputLayout.setVisibility(View.VISIBLE);
                holder.char_numc_dec_TextInputLayout.setHint(nto.getFlabel());
                holder.char_numc_dec_edittext.setText(nto.getValue());
                holder.char_numc_dec_edittext.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                String length = holder.Length_textview.getText().toString();
                int maxLength = Integer.parseInt(length);
                InputFilter[] fArray = new InputFilter[1];
                fArray[0] = new InputFilter.LengthFilter(maxLength);
                holder.char_numc_dec_edittext.setFilters(fArray);
                holder.date_layout.setVisibility(View.GONE);
                holder.date_edittext.setText("");
                holder.date_TextInputLayout.setHint("");
                holder.time_layout.setVisibility(View.GONE);
                holder.time_edittext.setText("");
                holder.time_TextInputLayout.setHint("");
            } else if (holder.Datatype_textview.getText().toString().equalsIgnoreCase("DATS")) {
                holder.date_layout.setVisibility(View.VISIBLE);
                holder.date_edittext.setText(nto.getValue());
                holder.date_TextInputLayout.setHint(nto.getFlabel());
                holder.char_numc_dec_TextInputLayout.setVisibility(View.GONE);
                holder.char_numc_dec_edittext.setText("");
                holder.time_layout.setVisibility(View.GONE);
                holder.time_edittext.setText("");
                holder.time_TextInputLayout.setHint("");
            } else if (holder.Datatype_textview.getText().toString().equalsIgnoreCase("TIMS")) {
                holder.date_layout.setVisibility(View.GONE);
                holder.date_edittext.setText("");
                holder.date_TextInputLayout.setHint("");
                holder.char_numc_dec_TextInputLayout.setVisibility(View.GONE);
                holder.char_numc_dec_edittext.setText("");
                holder.time_layout.setVisibility(View.VISIBLE);
                holder.time_edittext.setText(nto.getValue());
                holder.time_TextInputLayout.setHint(nto.getFlabel());
            } else {
                holder.char_numc_dec_TextInputLayout.setVisibility(View.VISIBLE);
                holder.char_numc_dec_TextInputLayout.setHint(nto.getFlabel());
                holder.char_numc_dec_edittext.setText(nto.getValue());
                holder.char_numc_dec_edittext.setInputType(InputType.TYPE_CLASS_TEXT);
                String length = holder.Length_textview.getText().toString();
                int maxLength = Integer.parseInt(length);
                InputFilter[] fArray = new InputFilter[1];
                fArray[0] = new InputFilter.LengthFilter(maxLength);
                holder.char_numc_dec_edittext.setFilters(fArray);
                holder.date_layout.setVisibility(View.GONE);
                holder.date_edittext.setText("");
                holder.date_TextInputLayout.setHint("");
                holder.time_layout.setVisibility(View.GONE);
                holder.time_edittext.setText("");
                holder.time_TextInputLayout.setHint("");
            }


            holder.date_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog aa = new Dialog(CustomInfo_Activity.this);
                    aa.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    aa.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    aa.setCancelable(false);
                    aa.setCanceledOnTouchOutside(false);
                    aa.setContentView(R.layout.datedialog);
                    aa.getWindow().getAttributes().windowAnimations = R.style.ErrorDialog;
                    final DatePicker datePicker = (DatePicker) aa.findViewById(R.id.datePicker);
                    Button cancel_button = (Button) aa.findViewById(R.id.cancel_button);
                    Button ok_button = (Button) aa.findViewById(R.id.add_button);
                    aa.show();
                    cancel_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            aa.dismiss();
                        }
                    });
                    ok_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int day = datePicker.getDayOfMonth();
                            int month = datePicker.getMonth() + 1;
                            int year = datePicker.getYear();
                            String inputPattern = "yyyy-MM-dd";
                            String outputPattern = "MMM dd, yyyy";
                            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                            try {
                                Date date = inputFormat.parse(year + "-" + month + "-" + day);
                                String selected_date = outputFormat.format(date);
                                holder.date_edittext.setText(selected_date);
                                custominfo_data_list.get(position).setValue(selected_date);
                            } catch (Exception e) {
                            }
                            aa.dismiss();
                        }
                    });
                }
            });


            holder.time_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog aa = new Dialog(CustomInfo_Activity.this);
                    aa.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    aa.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    aa.setCancelable(false);
                    aa.setCanceledOnTouchOutside(false);
                    aa.setContentView(R.layout.time_dialog);
                    aa.getWindow().getAttributes().windowAnimations = R.style.ErrorDialog;
                    final TimePicker timePicker = (TimePicker) aa.findViewById(R.id.timePicker);
                    Button cancel_button = (Button) aa.findViewById(R.id.cancel_button);
                    Button ok_button = (Button) aa.findViewById(R.id.add_button);
                    timePicker.setIs24HourView(true);
                    aa.show();
                    cancel_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            aa.dismiss();
                        }
                    });
                    ok_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String hr = "";
                            String mn = "";
                            int hour = timePicker.getCurrentHour();
                            if (hour < 10) {
                                hr = "0" + hour;
                            } else {
                                hr = "" + hour;
                            }
                            int min = timePicker.getCurrentMinute();
                            if (min < 10) {
                                mn = "0" + min;
                            } else {
                                mn = "" + min;
                            }
                            holder.time_edittext.setText(hr + ":" + mn + ":" + "00");
                            custominfo_data_list.get(position).setValue(hr + ":" + mn + ":" + "00");
                            aa.dismiss();
                        }
                    });
                }
            });


            holder.char_numc_dec_edittext.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    custominfo_data_list.get(position).setValue(s.toString());
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });


        }

        @Override
        public int getItemCount() {
            return custom_info_details_list.size();
        }
    }


}
