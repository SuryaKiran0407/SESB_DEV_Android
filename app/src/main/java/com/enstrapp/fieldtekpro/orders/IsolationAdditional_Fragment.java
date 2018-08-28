package com.enstrapp.fieldtekpro.orders;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.enstrapp.fieldtekpro.R;

import java.util.ArrayList;

public class IsolationAdditional_Fragment extends Fragment {

    EditText tag_et, unTag_et;
    Isolation_Add_Update_Activity ma;
    StringBuilder tagTxt = new StringBuilder();
    StringBuilder unTagTxt = new StringBuilder();
    ArrayList<OrdrTagUnTagTextPrcbl> tag_al = new ArrayList<>();
    ArrayList<OrdrTagUnTagTextPrcbl> unTag_al = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.iso_additional_fragment, container,
                false);
        ma = (Isolation_Add_Update_Activity) this.getActivity();

        unTag_et = rootView.findViewById(R.id.unTag_et);
        tag_et = rootView.findViewById(R.id.tag_et);

        if(ma.iso != null)
            if(ma.iso.getTagText_al() != null){
                if(ma.iso.getTagText_al().size() > 0){
                    tagTxt = new StringBuilder();
                    for (OrdrTagUnTagTextPrcbl tut:ma.iso.getTagText_al()){
                        tagTxt.append(tut.getTextLine());
                        tagTxt.append("\n");
                    }
                    if(tagTxt != null)
                        tag_et.setText(tagTxt);
                }
            }
        if(ma.iso != null)
            if(ma.iso.getUnTagText_al() != null){
                if(ma.iso.getUnTagText_al().size() > 0){
                    unTagTxt = new StringBuilder();
                    for (OrdrTagUnTagTextPrcbl tut:ma.iso.getUnTagText_al()){
                        unTagTxt.append(tut.getTextLine());
                        unTagTxt.append("\n");
                    }
                    if(unTagTxt != null)
                        unTag_et.setText(unTagTxt);
                }
            }

        tag_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                tagTxt = new StringBuilder();
                if (tag_et.getText().toString() != null && !tag_et.getText().toString().equals("")) {
                    if (tag_et.getText().toString().contains("\n")) {
                        String[] streets;
                        streets = tag_et.getText().toString().split("\n");
                        tag_al = new ArrayList<>();
                        for(int i = 0;i < streets.length;i++){
                            OrdrTagUnTagTextPrcbl tago = new OrdrTagUnTagTextPrcbl();
                            tago.setWcnr(ma.wcnr);
                            tago.setTextLine(streets[i]);
                            tago.setAction("I");
                            tag_al.add(tago);
                        }
                    } else {
                        tag_al = new ArrayList<>();
                        OrdrTagUnTagTextPrcbl tago = new OrdrTagUnTagTextPrcbl();
                        tago.setWcnr(ma.wcnr);
                        tago.setTextLine(tag_et.getText().toString());
                        tago.setAction("I");
                        tag_al.add(tago);
                    }
                    ma.iso.setTagText_al(tag_al);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });

        unTag_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                unTagTxt = new StringBuilder();
                if (unTag_et.getText().toString() != null && !unTag_et.getText().toString().equals("")) {
                    if (unTag_et.getText().toString().contains("\n")) {
                        String[] streets;
                        streets = unTag_et.getText().toString().split("\n");
                        unTag_al = new ArrayList<>();
                        for(int i = 0;i < streets.length;i++){
                            OrdrTagUnTagTextPrcbl tago = new OrdrTagUnTagTextPrcbl();
                            tago.setWcnr(ma.wcnr);
                            tago.setTextLine(streets[i]);
                            tago.setAction("I");
                            unTag_al.add(tago);
                        }
                    } else {
                        unTag_al = new ArrayList<>();
                        OrdrTagUnTagTextPrcbl tago = new OrdrTagUnTagTextPrcbl();
                        tago.setWcnr(ma.wcnr);
                        tago.setTextLine(unTag_et.getText().toString());
                        tago.setAction("I");
                        unTag_al.add(tago);
                    }
                    ma.iso.setUnTagText_al(unTag_al);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });

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

        ma.fab.hide();
    }

    public void refreshData(){
        if(ma.iso != null)
            if(ma.iso.getTagText_al() != null){
                if(ma.iso.getTagText_al().size() > 0){
                    tagTxt = new StringBuilder();
                    for (OrdrTagUnTagTextPrcbl tut:ma.iso.getUnTagText_al()){
                        tagTxt.append(tut.getTextLine());
                        tagTxt.append("\n");
                    }
                    if(tagTxt != null)
                        tag_et.setText(tagTxt);
                }
            }
        if(ma.iso != null)
            if(ma.iso.getUnTagText_al() != null){
                if(ma.iso.getUnTagText_al().size() > 0){
                    unTagTxt = new StringBuilder();
                    for (OrdrTagUnTagTextPrcbl tut:ma.iso.getUnTagText_al()){
                        unTagTxt.append(tut.getTextLine());
                        unTagTxt.append("\n");
                    }
                    if(unTagTxt != null)
                        unTag_et.setText(unTagTxt);
                }
            }
    }
}
