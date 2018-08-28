package com.enstrapp.fieldtekpro.orders;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.R;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderType_Activity extends AppCompatActivity {

    Toolbar toolBar;
    RecyclerView ordrType_rv;
    TextView title_tv, searchview_textview;
    LinearLayout noData_ll;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    public SearchView search;

    ArrayList<HashMap<String, String>> ordrTypList = new ArrayList<HashMap<String, String>>();
    OrdrTypAdapter ordrTypAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_activity);

        search = findViewById(R.id.orders_sv);
        ordrType_rv = findViewById(R.id.recyclerView);
        title_tv = findViewById(R.id.title_tv);
        toolBar = findViewById(R.id.toolBar);
        noData_ll = findViewById(R.id.noData_ll);

        toolBar.setNavigationIcon(R.drawable.ic_arrow_back_white_24px);

        setSupportActionBar(toolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        title_tv.setText(getResources().getString(R.string.ordr_typ));

        DATABASE_NAME = getString(R.string.database_name);
        App_db = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        GetOrdrTypData();

        if (ordrTypList.size() > 0) {
            ordrTypAdapter = new OrdrTypAdapter(OrderType_Activity.this, ordrTypList);
            ordrType_rv.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(OrderType_Activity.this);
            ordrType_rv.setLayoutManager(layoutManager);
            ordrType_rv.setItemAnimator(new DefaultItemAnimator());
            ordrType_rv.setAdapter(ordrTypAdapter);
        }

        int id = search.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        search.setQueryHint("Search...");
        Typeface myCustomFont = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/metropolis_medium.ttf");
        searchview_textview = search.findViewById(id);
        searchview_textview.setTextColor(getResources().getColor(R.color.black));
        search.setBaselineAligned(false);
        searchview_textview.setTypeface(myCustomFont);
        searchview_textview.setTextSize(16);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class OrdrTypAdapter extends RecyclerView.Adapter<OrdrTypAdapter.MyViewHolder> {
        private Context mContext;
        private ArrayList<HashMap<String, String>> ordrTyp_list;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView id_textview, text_textview;
            LinearLayout data_layout;

            public MyViewHolder(View view) {
                super(view);
                id_textview = view.findViewById(R.id.id_textview);
                text_textview = view.findViewById(R.id.text_textview);
                data_layout = view.findViewById(R.id.data_layout);
            }
        }

        public OrdrTypAdapter(Context mContext, ArrayList<HashMap<String, String>> list) {
            this.mContext = mContext;
            this.ordrTyp_list = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.f4_list_data, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final HashMap<String, String> ot = ordrTyp_list.get(position);
            holder.id_textview.setText(ot.get("ordrTypId"));
            holder.text_textview.setText(ot.get("ordrTypTxt"));

            holder.data_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("ordrTypId", ordrTyp_list.get(position).get("ordrTypId"));
                    intent.putExtra("ordrTypTxt", ordrTyp_list.get(position).get("ordrTypTxt"));
                    setResult(RESULT_OK, intent);
                    OrderType_Activity.this.finish();
                }
            });
        }

        @Override
        public int getItemCount() {
            return ordrTyp_list.size();
        }
    }

    private void GetOrdrTypData() {
        ordrTypList.clear();
        Cursor cursor = null;
        try {
            cursor = App_db.rawQuery("select * from  GET_ORDER_TYPES", null);
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("ordrTypId", cursor.getString(1));//id
                        map.put("ordrTypTxt", cursor.getString(2));//text
                        ordrTypList.add(map);
                    }
                    while (cursor.moveToNext());
                }
            }
        } catch (Exception e) {
            if (cursor != null)
                cursor.close();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }
}
