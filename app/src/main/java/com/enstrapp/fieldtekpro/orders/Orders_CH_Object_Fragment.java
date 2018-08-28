package com.enstrapp.fieldtekpro.orders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.Authorizations.Authorizations;
import com.enstrapp.fieldtekpro.R;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class Orders_CH_Object_Fragment extends Fragment {

    TextView add_tv, remove_tv;
    RecyclerView objects_rv;
    OrdrHeaderPrcbl ohp_h = null;
    ArrayList<OrdrObjectPrcbl> oobp_al = new ArrayList<OrdrObjectPrcbl>();
    ObjectsAdapter objectsAdapter;
    Orders_Change_Activity ma;
    static final int OBJCT_ADD = 50;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recyclerview_fragment, container,
                false);

        add_tv = rootView.findViewById(R.id.add_tv);
        remove_tv = rootView.findViewById(R.id.remove_tv);
        objects_rv = rootView.findViewById(R.id.recyclerView);
        ma = (Orders_Change_Activity) this.getActivity();

        if (ma.ohp.getOrdrObjectPrcbls() != null) {
            oobp_al.addAll(ma.ohp.getOrdrObjectPrcbls());
            objectsAdapter = new ObjectsAdapter(getActivity(), oobp_al);
            objects_rv.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            objects_rv.setLayoutManager(layoutManager);
            objects_rv.setItemAnimator(new DefaultItemAnimator());
            objects_rv.setAdapter(objectsAdapter);
        }

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

        String auth_status = Authorizations.Get_Authorizations_Data(getActivity(),"W","U");
        if (auth_status.equalsIgnoreCase("true"))
        {
            ma.fab.show();
        }
        else
        {
            ma.fab.hide();
        }

        ma.animateFab(false);

        ma.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Objects_Add_Activity.class);
                startActivityForResult(intent, OBJCT_ADD);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case (OBJCT_ADD):
                if (resultCode == RESULT_OK) {
                    OrdrObjectPrcbl oobp = new OrdrObjectPrcbl();
                    oobp = data.getParcelableExtra("oobp");
                    oobp.setAufnr(ma.ohp.getOrdrId());
                    oobp_al.add(oobp);
                    ma.ohp.setOrdrObjectPrcbls(oobp_al);
                    objectsAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    private class ObjectsAdapter extends RecyclerView.Adapter<ObjectsAdapter.MyViewHolder> {
        private Context mContext;
        private ArrayList<OrdrObjectPrcbl> objectList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView notifNo_tv, funcLoc_tv, equip_tv, equipTxt_tv;

            public MyViewHolder(View view) {
                super(view);
                notifNo_tv = view.findViewById(R.id.notifNo_tv);
                funcLoc_tv = view.findViewById(R.id.funcLoc_tv);
                equip_tv = view.findViewById(R.id.equip_tv);
                equipTxt_tv = view.findViewById(R.id.equipTxt_tv);
            }
        }

        public ObjectsAdapter(Context mContext, ArrayList<OrdrObjectPrcbl> list) {
            this.mContext = mContext;
            this.objectList = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_object_list, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final OrdrObjectPrcbl oobp = objectList.get(position);
            holder.notifNo_tv.setText(oobp.getNotifNo());
            holder.funcLoc_tv.setText(oobp.getTplnr());
            holder.equip_tv.setText(oobp.getEquipId());
            holder.equipTxt_tv.setText(oobp.getEquipTxt());
        }

        @Override
        public int getItemCount() {
            return objectList.size();
        }
    }

}
