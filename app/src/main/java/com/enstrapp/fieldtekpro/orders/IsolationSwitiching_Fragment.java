package com.enstrapp.fieldtekpro.orders;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.R;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class IsolationSwitiching_Fragment extends Fragment {


    RecyclerView recyclerView;
    TextView noData_tv;
    Isolation_Add_Update_Activity ma;
    static final int WDIP = 3672;
    static final int WDIP_UP = 4672;
    ArrayList<OrdrWDItemPrcbl> wdip_al = new ArrayList<>();
    WdipAdapter wdipAdapter;
    boolean set = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recyclerview_fragment, container,
                false);
        ma = (Isolation_Add_Update_Activity) this.getActivity();

        recyclerView = rootView.findViewById(R.id.recyclerView);
        noData_tv = rootView.findViewById(R.id.noData_tv);

        if (ma.iso != null)
            if (ma.iso.getWdItemPrcbl_Al() != null) {
                if (ma.iso.getWdItemPrcbl_Al().size() > 0) {
                    wdip_al = new ArrayList<>();
                    wdip_al.addAll(ma.iso.getWdItemPrcbl_Al());
                    if (wdip_al.size() > 0) {
                        wdipAdapter = new WdipAdapter(getActivity(), wdip_al);
                        recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(wdipAdapter);
                    }
                }
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

        if(ma.iso.getComp()!= null && !ma.iso.getComp().equalsIgnoreCase("")) {
            if (ma.iso.getComp().equalsIgnoreCase("X")) {
                ma.fab.hide();
            } else{
                if (ma.setPrep)
                    ma.fab.hide();
                else
                    ma.fab.show();
            }
        } else {
            if (ma.setPrep)
                ma.fab.hide();
            else
                ma.fab.show();
        }

        if (wdip_al.size() > 0) {
            if (wdipAdapter != null) {
                wdipAdapter.notifyDataSetChanged();
            } else {
                wdipAdapter = new WdipAdapter(getActivity(), wdip_al);
                recyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(wdipAdapter);
            }
        }

        ma.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), IsoTagUntagCon_Activity.class);
                intent.putExtra("equip", ma.equipId);
                intent.putExtra("func_loc", ma.funcId);
                intent.putExtra("equip_txt", ma.equipName);
                intent.putExtra("iwerk", ma.iwerk);
                intent.putExtra("wcnr", ma.wcnr);
                startActivityForResult(intent, WDIP);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (WDIP):
                if (resultCode == RESULT_OK) {
                    OrdrWDItemPrcbl wdip = new OrdrWDItemPrcbl();
                    wdip = data.getParcelableExtra("wdip");
//                    wdip.setWcnr("WD0001");
                    if (wdip_al != null)
                        wdip.setWctim(String.valueOf(wdip_al.size() + 1));
                    else
                        wdip.setWctim("1");
                    wdip.setTagStatus(false);
                    wdip_al.add(wdip);
                    ma.wdip_al.add(wdip);
                    if (wdip_al.size() > 0) {
                        wdipAdapter = new WdipAdapter(getActivity(), wdip_al);
                        recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(wdipAdapter);
                        recyclerView.setVisibility(VISIBLE);
                    }
                }
                break;

            case (WDIP_UP):
                if (resultCode == RESULT_OK) {
                    OrdrWDItemPrcbl wdip = new OrdrWDItemPrcbl();
                    wdip = data.getParcelableExtra("wdip");
//                    wdip.setWcnr("WD0001");
                    ArrayList<OrdrWDItemPrcbl> rem = new ArrayList<>();
                    rem.addAll(wdip_al);
                    for (OrdrWDItemPrcbl rm : rem) {
                        if (rm.getWctim().equals(wdip.getWctim()))
                            wdip_al.remove(rm);
                    }
                    wdip_al.add(wdip);
                    /*if (wdip_al != null)
                        wdip.setWctim(String.valueOf(wdip_al.size() + 1));
                    else
                        wdip.setWctim("1");
                    wdip.setTagStatus(false);
                    wdip_al.add(wdip);*/
                    ma.wdip_al.clear();
                    ma.wdip_al.addAll(wdip_al);
                    if (wdip_al.size() > 0) {
                        wdipAdapter = new WdipAdapter(getActivity(), wdip_al);
                        recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(wdipAdapter);
                        recyclerView.setVisibility(VISIBLE);
                    }
                }
                break;
        }
    }

    public class WdipAdapter extends RecyclerView.Adapter<WdipAdapter.MyViewHolder> {
        private Context mContext;
        private ArrayList<OrdrWDItemPrcbl> wdipal;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView object_tv, tagUntagCond_tv, desc_tv, tagTxt_tv, status_tv, tagStatus_tv;
            LinearLayout data_ll, tagStatus_ll;
            ImageView delTag_iv;

            public MyViewHolder(View view) {
                super(view);
                object_tv = view.findViewById(R.id.object_tv);
                tagUntagCond_tv = view.findViewById(R.id.tagUntagCond_tv);
                desc_tv = view.findViewById(R.id.desc_tv);
                tagTxt_tv = view.findViewById(R.id.tagTxt_tv);
                status_tv = view.findViewById(R.id.status_tv);
                data_ll = view.findViewById(R.id.data_ll);
                tagStatus_tv = view.findViewById(R.id.tagStatus_tv);
                delTag_iv = view.findViewById(R.id.delTag_iv);
                tagStatus_ll = view.findViewById(R.id.tagStatus_ll);

                delTag_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = (Integer) v.getTag();
                        wdipal.remove(position);
                        ma.wdip_al.remove(position);
                        wdipAdapter.notifyDataSetChanged();
                    }
                });

                tagStatus_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = (Integer) v.getTag();
                        set = false;
                        if (wdipal.get(position).getAction().equals("I")) {
                            if (wdipal.get(position).getBtg().equalsIgnoreCase("X")) {
                                confirmationDialog(getString(R.string.perform_tag, "Tagged"), wdipal, position);
                            } else if (wdipal.get(position).getEtg().equalsIgnoreCase("X")) {
                            } else if (wdipal.get(position).getBug().equalsIgnoreCase("X")) {
                            } else if (wdipal.get(position).getEug().equalsIgnoreCase("X")) {
                            } else {
                                confirmationDialog(getString(R.string.perform_tag, "Tag"), wdipal, position);
                            }
                        } else {
                            if (wdipal.get(position).getBtg().equalsIgnoreCase("X")) {
                                confirmationDialog(getString(R.string.perform_tag, "Tagged"), wdipal, position);
                            } else if (wdipal.get(position).getEtg().equalsIgnoreCase("X")) {
                                if (wdip_al.get(position).isUntagStatus()) {
                                    confirmationDialog(getString(R.string.perform_tag, "UnTag"), wdipal, position);
                                }
                            } else if (wdipal.get(position).getBug().equalsIgnoreCase("X")) {
                                confirmationDialog(getString(R.string.perform_tag, "UnTagged"), wdipal, position);
                            } else if (wdipal.get(position).getEug().equalsIgnoreCase("X")) {
                            } else {
                                confirmationDialog(getString(R.string.perform_tag, "Tag"), wdipal, position);
                            }
                        }
                        wdipAdapter.notifyDataSetChanged();
                    }
                });
            }
        }

        public WdipAdapter(Context mContext, ArrayList<OrdrWDItemPrcbl> list) {
            this.mContext = mContext;
            this.wdipal = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_cond_list, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final OrdrWDItemPrcbl nto = wdipal.get(position);
            holder.tagUntagCond_tv.setText(nto.getTgproc() + "/" + nto.getUnproc());
            if (wdipal.get(position).getCctyp().equalsIgnoreCase("E"))
                holder.object_tv.setText(nto.getEquipdDesc());
            else
                holder.object_tv.setText(nto.getCcobj());
            holder.desc_tv.setText(nto.getTgprox());
            holder.tagTxt_tv.setText(nto.getTgtxt());
            holder.data_ll.setTag(position);
            holder.delTag_iv.setTag(position);
            holder.tagStatus_ll.setTag(position);
            holder.tagStatus_tv.setTag(position);
            if (wdipal.get(position).isTagStatus())
                holder.delTag_iv.setVisibility(GONE);
            else
                holder.delTag_iv.setVisibility(VISIBLE);

            if (wdipal.get(position).getBtg().equalsIgnoreCase("X")) {
                holder.status_tv.setText(getString(R.string.tag_x));
            } else if (wdipal.get(position).getEtg().equalsIgnoreCase("X")) {
                holder.status_tv.setText(getString(R.string.tagged_x));
            } else if (wdipal.get(position).getBug().equalsIgnoreCase("X")) {
                holder.status_tv.setText(getString(R.string.untag_x));
            } else if (wdipal.get(position).getEug().equalsIgnoreCase("X")) {
                holder.status_tv.setText(getString(R.string.untagged_x));
            }

            if (ma.setPrep) {
                holder.tagStatus_ll.setVisibility(VISIBLE);
                if (wdipal.get(position).getAction().equals("I")) {
                    if (wdipal.get(position).getBtg().equalsIgnoreCase("X")) {
                        holder.tagStatus_tv.setText("Tagged");
                    } else if (wdipal.get(position).getEtg().equalsIgnoreCase("X")) {
                        holder.tagStatus_tv.setText("Untag");
                        holder.tagStatus_tv.setTextColor(getResources().getColor(R.color.light_grey));
                    } else {
                        holder.tagStatus_tv.setText("Tag");
                    }
                } else {
                    holder.delTag_iv.setVisibility(GONE);
                    if (wdipal.get(position).getBtg().equalsIgnoreCase("X")) {
                        holder.tagStatus_tv.setText("Tagged");
                    } else if (wdipal.get(position).getEtg().equalsIgnoreCase("X")) {
                        holder.tagStatus_tv.setText("Untag");
                        if (!wdipal.get(position).isUntagStatus())
                            holder.tagStatus_tv.setTextColor(getResources().getColor(R.color.light_grey));
                    } else if (wdipal.get(position).getBug().equalsIgnoreCase("X")) {
                        holder.tagStatus_tv.setText("Untagged");
                    } else if (wdipal.get(position).getEug().equalsIgnoreCase("X")) {
                        holder.tagStatus_tv.setText("");
                    } else {
                        holder.tagStatus_tv.setText("Tag");
                    }
                }
            } else {
                holder.tagStatus_ll.setVisibility(GONE);
            }

            holder.data_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), IsoTagUntagCon_Activity.class);
                    intent.putExtra("wdip", wdipal.get(position));
                    intent.putExtra("equip", ma.equipId);
                    intent.putExtra("func_loc", ma.funcId);
                    intent.putExtra("equip_txt", ma.equipName);
                    intent.putExtra("iwerk", ma.iwerk);
                    intent.putExtra("wcnr", ma.wcnr);
                    startActivityForResult(intent, WDIP_UP);
                }
            });
        }

        @Override
        public int getItemCount() {
            return wdipal.size();
        }
    }

    private void confirmationDialog(String message, final ArrayList<OrdrWDItemPrcbl> wd_al, final int position) {
        final Dialog cancel_dialog = new Dialog(getActivity(), R.style.PauseDialog);
        cancel_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        cancel_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        cancel_dialog.setCancelable(false);
        cancel_dialog.setCanceledOnTouchOutside(false);
        cancel_dialog.setContentView(R.layout.network_error_dialog);
        final TextView description_textview = (TextView) cancel_dialog.findViewById(R.id.description_textview);
        description_textview.setText(message);
        Button confirm = (Button) cancel_dialog.findViewById(R.id.ok_button);
        Button cancel = (Button) cancel_dialog.findViewById(R.id.cancel_button);
        cancel_dialog.show();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSet(false);
                cancel_dialog.dismiss();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSet(true);
                cancel_dialog.dismiss();

                if (wd_al.get(position).getAction().equals("I")) {
                    if (wd_al.get(position).getBtg().equalsIgnoreCase("X")) {
                        if (set) {
                            ma.wdip_al.get(position).setEtg("X");
                            ma.wdip_al.get(position).setBtg("");
                            wdip_al.get(position).setEtg("X");
                            wdip_al.get(position).setBtg("");
                        }
                    } else if (wd_al.get(position).getEtg().equalsIgnoreCase("X")) {
                                /*confirmationDialog(getString(R.string.perform_tag, "UnTag"));
                                if (set) {
                                    ma.wdip_al.get(position).setEtg("");
                                    ma.wdip_al.get(position).setBug("X");
                                    wdip_al.get(position).setEtg("");
                                    wdip_al.get(position).setBug("X");
                                }*/
                    } else if (wd_al.get(position).getBug().equalsIgnoreCase("X")) {
                                /*confirmationDialog(getString(R.string.perform_tag, "UnTagged"));
                                if (set) {
                                    ma.wdip_al.get(position).setBug("");
                                    ma.wdip_al.get(position).setEug("X");
                                    wdip_al.get(position).setBug("");
                                    wdip_al.get(position).setEug("X");
                                }*/
                    } else if (wd_al.get(position).getEug().equalsIgnoreCase("X")) {
                    } else {
                        if (set) {
                            ma.wdip_al.get(position).setBtg("X");
                            wdip_al.get(position).setBtg("X");
                            wdip_al.get(position).setTagStatus(true);
                            ma.wdip_al.get(position).setTagStatus(true);
                        }
                    }
                } else {
                    if (wd_al.get(position).getBtg().equalsIgnoreCase("X")) {
                        if (set) {
                            ma.wdip_al.get(position).setEtg("X");
                            ma.wdip_al.get(position).setBtg("");
                            wdip_al.get(position).setEtg("X");
                            wdip_al.get(position).setBtg("");
                            wdip_al.get(position).setAction("U");
                            ma.wdip_al.get(position).setAction("U");
                        }
                    } else if (wd_al.get(position).getEtg().equalsIgnoreCase("X")) {
                        if (!wdip_al.get(position).isTagStatus()) {
                            if (set) {
                                ma.wdip_al.get(position).setEtg("");
                                ma.wdip_al.get(position).setBug("X");
                                wdip_al.get(position).setEtg("");
                                wdip_al.get(position).setBug("X");
                                wdip_al.get(position).setAction("U");
                                ma.wdip_al.get(position).setAction("U");
                            }
                        }
                    } else if (wd_al.get(position).getBug().equalsIgnoreCase("X")) {
                        if (set) {
                            ma.wdip_al.get(position).setBug("");
                            ma.wdip_al.get(position).setEug("X");
                            wdip_al.get(position).setBug("");
                            wdip_al.get(position).setEug("X");
                            wdip_al.get(position).setAction("U");
                            ma.wdip_al.get(position).setAction("U");
                        }
                    } else if (wd_al.get(position).getEug().equalsIgnoreCase("X")) {
                                /*confirmationDialog(getString(R.string.perform_tag, "Tag"));
                                if (set) {
                                    ma.wdip_al.get(position).setEtg("X");
                                    ma.wdip_al.get(position).setBtg("");
                                    wdip_al.get(position).setEtg("X");
                                    wdip_al.get(position).setBtg("");
                                }*/
                    } else {
                        if (set) {
                            ma.wdip_al.get(position).setBtg("X");
                            wdip_al.get(position).setBtg("X");
                            wdip_al.get(position).setAction("U");
                            ma.wdip_al.get(position).setAction("U");
                        }
                    }
                }
                wdipAdapter.notifyDataSetChanged();
            }
        });
    }

    private boolean isSet(boolean set) {
        this.set = set;
        return this.set;
    }

    public void refreshData() {
        if (ma.iso != null)
            if (ma.iso.getWdItemPrcbl_Al() != null) {
                if (ma.iso.getWdItemPrcbl_Al().size() > 0) {
                    wdip_al = new ArrayList<>();
                    wdip_al.addAll(ma.iso.getWdItemPrcbl_Al());
                    if (wdip_al.size() > 0) {
                        wdipAdapter = new WdipAdapter(getActivity(), wdip_al);
                        recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(wdipAdapter);
                    }
                }
            }

    }
}
