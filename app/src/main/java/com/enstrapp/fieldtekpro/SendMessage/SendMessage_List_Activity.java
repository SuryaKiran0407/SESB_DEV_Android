package com.enstrapp.fieldtekpro.SendMessage;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.enstrapp.fieldtekpro.networkconnection.ConnectionDetector;
import com.enstrapp.fieldtekpro.networkconnectiondialog.Network_Connection_Dialog;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;
import com.enstrapp.fieldtekpro.successdialog.Success_Dialog;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SendMessage_List_Activity extends AppCompatActivity implements View.OnClickListener {

    TextView title_textview, no_data_textview, searchview_textview;
    ImageView back_imageview, refresh_imageview;
    RecyclerView recyclerview;
    Button cancel_button, send_button;
    SearchView search;
    EditText searchEditText;
    private List<List_Object> data_list = new ArrayList<>();
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    DATAADAPTER dataadapter;
    CheckBox users_all_checkbox;
    JSONArray selected_user_array = new JSONArray();
    ConnectionDetector cd;
    Boolean isInternetPresent = false;
    Error_Dialog error_dialog = new Error_Dialog();
    Network_Connection_Dialog network_connection_dialog = new Network_Connection_Dialog();
    Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog();
    Dialog sendmessage_dialog, decision_dialog;
    Success_Dialog success_dialog = new Success_Dialog();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendmessage_list_activity);

        title_textview = (TextView) findViewById(R.id.title_textview);
        no_data_textview = (TextView) findViewById(R.id.no_data_textview);
        back_imageview = (ImageView) findViewById(R.id.back_imageview);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        cancel_button = (Button) findViewById(R.id.cancel_button);
        send_button = (Button) findViewById(R.id.send_button);
        search = (SearchView) findViewById(R.id.search);
        users_all_checkbox = (CheckBox) findViewById(R.id.users_all_checkbox);
        refresh_imageview = (ImageView) findViewById(R.id.refresh_imageview);


        DATABASE_NAME = SendMessage_List_Activity.this.getString(R.string.database_name);
        App_db = SendMessage_List_Activity.this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);


        int id = search.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        search.setQueryHint("Search...");
        Typeface myCustomFont = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/metropolis_medium.ttf");
        searchview_textview = (TextView) search.findViewById(id);
        searchview_textview.setTextColor(getResources().getColor(R.color.black));
        search.setBaselineAligned(false);
        searchview_textview.setTypeface(myCustomFont);
        searchview_textview.setTextSize(16);
        searchEditText = (EditText) search.findViewById(id);
        searchEditText.setTextColor(getResources().getColor(R.color.black));
        searchEditText.setHintTextColor(getResources().getColor(R.color.light_grey1));


        users_all_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (users_all_checkbox.isChecked()) {
                    for (List_Object list_object : data_list) {
                        list_object.setChecked_status(true);
                    }
                    if (dataadapter != null)
                        dataadapter.notifyDataSetChanged();
                } else {
                    for (List_Object list_object : data_list) {
                        list_object.setChecked_status(false);
                    }
                    if (dataadapter != null)
                        dataadapter.notifyDataSetChanged();
                }
            }
        });


        new Get_Data().execute();


        back_imageview.setOnClickListener(this);
        cancel_button.setOnClickListener(this);
        send_button.setOnClickListener(this);
        refresh_imageview.setOnClickListener(this);
    }


    private class Get_Data extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(SendMessage_List_Activity.this, getResources().getString(R.string.loading));
            data_list.clear();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Cursor cursor = App_db.rawQuery("select * from EtUsers", null);
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            List_Object olo = new List_Object(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), false);
                            data_list.add(olo);
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
            if (data_list.size() > 0) {
                dataadapter = new DATAADAPTER(SendMessage_List_Activity.this, data_list);
                recyclerview.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SendMessage_List_Activity.this);
                recyclerview.setLayoutManager(layoutManager);
                recyclerview.setItemAnimator(new DefaultItemAnimator());
                recyclerview.setAdapter(dataadapter);
                search.setOnQueryTextListener(listener);
                no_data_textview.setVisibility(View.GONE);
                search.setVisibility(View.VISIBLE);
                recyclerview.setVisibility(View.VISIBLE);
                title_textview.setText(getString(R.string.send_message) + " (" + data_list.size() + ")");
                users_all_checkbox.setVisibility(View.VISIBLE);
            } else {
                title_textview.setText(getString(R.string.send_message) + " (0)");
                no_data_textview.setVisibility(View.VISIBLE);
                search.setVisibility(View.GONE);
                recyclerview.setVisibility(View.GONE);
                users_all_checkbox.setVisibility(View.GONE);
            }
            custom_progress_dialog.dismiss_progress_dialog();
        }
    }


    @Override
    public void onClick(View v) {
        if (v == back_imageview) {
            SendMessage_List_Activity.this.finish();
        } else if (v == cancel_button) {
            SendMessage_List_Activity.this.finish();
        } else if (v == refresh_imageview) {
            cd = new ConnectionDetector(getApplicationContext());
            isInternetPresent = cd.isConnectingToInternet();
            if (isInternetPresent) {
                decision_dialog = new Dialog(SendMessage_List_Activity.this);
                decision_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                decision_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                decision_dialog.setCancelable(false);
                decision_dialog.setCanceledOnTouchOutside(false);
                decision_dialog.setContentView(R.layout.decision_dialog);
                ImageView imageview = (ImageView) decision_dialog.findViewById(R.id.imageView1);
                TextView description_textview = (TextView) decision_dialog.findViewById(R.id.description_textview);
                Glide.with(SendMessage_List_Activity.this).load(R.drawable.error_dialog_gif).into(imageview);
                Button confirm = (Button) decision_dialog.findViewById(R.id.yes_button);
                Button cancel = (Button) decision_dialog.findViewById(R.id.no_button);
                description_textview.setText(getString(R.string.refresh_text));
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
                        new Get_VHLP_Token().execute();
                    }
                });
            } else {
                network_connection_dialog.show_network_connection_dialog(SendMessage_List_Activity.this);
            }
        } else if (v == send_button) {
            cd = new ConnectionDetector(getApplicationContext());
            isInternetPresent = cd.isConnectingToInternet();
            if (isInternetPresent) {
                ArrayList selected_count = new ArrayList();
                selected_user_array = new JSONArray();
                for (List_Object list_object : data_list) {
                    if (list_object.isChecked_status()) {
                        selected_count.add(list_object.getMuser());
                        String token_id = list_object.getTokenid();
                        if (token_id != null && !token_id.equals("")) {
                            selected_user_array.put(token_id);
                        }
                    }
                }
                if (selected_count.size() > 0) {
                    if (selected_user_array.length() > 0) {
                        sendmessage_dialog = new Dialog(SendMessage_List_Activity.this);
                        sendmessage_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        sendmessage_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        sendmessage_dialog.setCancelable(false);
                        sendmessage_dialog.setCanceledOnTouchOutside(false);
                        sendmessage_dialog.setContentView(R.layout.sendmessage_dialog);
                        Button ok_button = (Button) sendmessage_dialog.findViewById(R.id.send_button);
                        Button cancel_button = (Button) sendmessage_dialog.findViewById(R.id.cancel_button);
                        final TextView count_textview = (TextView) sendmessage_dialog.findViewById(R.id.count_textview);
                        final EditText message_edittext = (EditText) sendmessage_dialog.findViewById(R.id.message_edittext);
                        sendmessage_dialog.show();
                        cancel_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sendmessage_dialog.dismiss();
                            }
                        });
                        message_edittext.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                int text_count = s.length();
                                int remain_text = 300 - text_count;
                                count_textview.setText(getString(R.string.characters_left,
                                        Integer.toString(remain_text)));
                            }

                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                            }
                        });
                        ok_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (message_edittext.getText().toString() != null && !message_edittext.getText().toString().equals("")) {
                                    new POST_MESSAGE().execute(message_edittext.getText().toString());
                                } else {
                                    error_dialog.show_error_dialog(SendMessage_List_Activity.this,
                                            getString(R.string.message_please));
                                }
                            }
                        });
                    } else {
                        error_dialog.show_error_dialog(SendMessage_List_Activity.this,
                                getString(R.string.usertkn_no));
                    }
                } else {
                    error_dialog.show_error_dialog(SendMessage_List_Activity.this,
                            getString(R.string.select_usr));
                }
            } else {
                network_connection_dialog.show_network_connection_dialog(SendMessage_List_Activity.this);
            }
        }
    }


    SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextChange(String query) {
            query = query.toLowerCase();
            if (query.length() == 0) {
                new Get_Data().execute();
                users_all_checkbox.setChecked(false);
            } else {
                final List<List_Object> filteredList = new ArrayList<>();
                for (int i = 0; i < data_list.size(); i++) {
                    String Muser = data_list.get(i).getMuser().toLowerCase();
                    String fname = data_list.get(i).getFname().toLowerCase();
                    String lname = data_list.get(i).getLname().toLowerCase();
                    if (Muser.contains(query) || fname.contains(query) || lname.contains(query)) {
                        List_Object blo = new List_Object(data_list.get(i).getMuser().toString(), data_list.get(i).getFname().toString(), data_list.get(i).getLname().toString(), data_list.get(i).getTokenid().toString(), data_list.get(i).isChecked_status());
                        filteredList.add(blo);
                    }
                }
                if (filteredList.size() > 0) {
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SendMessage_List_Activity.this);
                    recyclerview.setLayoutManager(layoutManager);
                    dataadapter = new DATAADAPTER(SendMessage_List_Activity.this, filteredList);
                    recyclerview.setAdapter(dataadapter);
                    dataadapter.notifyDataSetChanged();
                    no_data_textview.setVisibility(View.GONE);
                } else {
                    no_data_textview.setVisibility(View.VISIBLE);
                }
            }
            return true;
        }

        public boolean onQueryTextSubmit(String query) {
            return false;
        }
    };


    public class DATAADAPTER extends RecyclerView.Adapter<DATAADAPTER.MyViewHolder> {
        private Context mContext;
        private List<List_Object> list_data;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView muser_textview, name_textview;
            CheckBox checkbox;

            public MyViewHolder(View view) {
                super(view);
                muser_textview = (TextView) view.findViewById(R.id.muser_textview);
                name_textview = (TextView) view.findViewById(R.id.name_textview);
                checkbox = (CheckBox) view.findViewById(R.id.checkbox);
                checkbox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkbox.isChecked()) {
                            int getPosition = (Integer) v.getTag();
                            data_list.get(getPosition).setChecked_status(true);
                            checkbox.setChecked(true);
                            ArrayList<String> selected_user = new ArrayList<>();
                            for (List_Object list_object : data_list) {
                                if (list_object.isChecked_status()) {
                                    selected_user.add(list_object.getMuser());
                                }
                            }
                            if (selected_user.size() == data_list.size()) {
                                users_all_checkbox.setChecked(true);
                            } else {
                                users_all_checkbox.setChecked(false);
                            }
                        } else {
                            int getPosition = (Integer) v.getTag();
                            data_list.get(getPosition).setChecked_status(false);
                            checkbox.setChecked(false);
                            users_all_checkbox.setChecked(false);
                        }
                    }
                });
            }
        }

        public DATAADAPTER(Context mContext, List<List_Object> list) {
            this.mContext = mContext;
            this.list_data = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sendmessage_list_data, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final List_Object olo = list_data.get(position);
            holder.muser_textview.setText(olo.getMuser());
            holder.name_textview.setText(olo.getFname() + "  " + olo.getLname());
            holder.checkbox.setTag(position);
            holder.checkbox.setChecked((list_data.get(position).isChecked_status() == true ? true : false));
        }

        @Override
        public int getItemCount() {
            return list_data.size();
        }
    }


    public class List_Object {
        private String Muser;
        private String Fname;
        private String Lname;
        private String Tokenid;
        private boolean checked_status;

        public boolean isChecked_status() {
            return checked_status;
        }

        public void setChecked_status(boolean checked_status) {
            this.checked_status = checked_status;
        }

        public String getMuser() {
            return Muser;
        }

        public void setMuser(String muser) {
            Muser = muser;
        }

        public String getFname() {
            return Fname;
        }

        public void setFname(String fname) {
            Fname = fname;
        }

        public String getLname() {
            return Lname;
        }

        public void setLname(String lname) {
            Lname = lname;
        }

        public String getTokenid() {
            return Tokenid;
        }

        public void setTokenid(String tokenid) {
            Tokenid = tokenid;
        }

        public List_Object(String Muser, String Fname, String Lname, String Tokenid, boolean checked_status) {
            this.Muser = Muser;
            this.Fname = Fname;
            this.Lname = Lname;
            this.Tokenid = Tokenid;
            this.checked_status = checked_status;
        }
    }


    private class POST_MESSAGE extends AsyncTask<String, Integer, Void> {
        int response_status_code = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(SendMessage_List_Activity.this,
                    getString(R.string.msg_send));
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                String message = params[0];
                String url = "https://fcm.googleapis.com/fcm/send";
                JSONObject jobj = new JSONObject();
                JSONObject notif_jobj = new JSONObject();
                notif_jobj.put("title", "Message From FieldTekPro");
                notif_jobj.put("sound", "default");
                notif_jobj.put("text", message);
                jobj.put("notification", notif_jobj);
                jobj.put("registration_ids", selected_user_array);
                String json = jobj.toString();
                StringEntity input = new StringEntity(jobj.toString(), HTTP.UTF_8);
                HttpClient httpclient1 = new DefaultHttpClient();
                HttpPost httppost1 = new HttpPost(url);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
                nameValuePairs.add(new BasicNameValuePair("json", json));
                httppost1.setEntity(input);
                httppost1.setHeader(HTTP.CONTENT_TYPE, "application/json; charset=utf-8");
                httppost1.addHeader("Authorization", "key=AIzaSyDacTg9jDCoIlUjKpc_roM3y_ndyYyixJs");
                HttpResponse login_response = httpclient1.execute(httppost1);
                response_status_code = login_response.getStatusLine().getStatusCode();
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
            if (response_status_code == 200) {
                sendmessage_dialog.dismiss();
                custom_progress_dialog.dismiss_progress_dialog();
                success_dialog.show_success_dialog(SendMessage_List_Activity.this,
                        getString(R.string.msg_success));
            } else {
                custom_progress_dialog.dismiss_progress_dialog();
                error_dialog.show_error_dialog(SendMessage_List_Activity.this,
                        getString(R.string.msg_fail));
            }
        }
    }


    public class Get_VHLP_Token extends AsyncTask<Void, Integer, Void> {
        String VHLP_Token_Status = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(SendMessage_List_Activity.this,
                    getString(R.string.refreshing));
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                VHLP_Token_Status = VHLP_Token.Get_Token_Data(SendMessage_List_Activity.this);
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
            new Get_Data().execute();
        }
    }

}
