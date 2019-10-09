package com.enstrapp.fieldtekpro.orders;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.enstrapp.fieldtekpro.equipment.Equipment_Activity;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.enstrapp.fieldtekpro.functionlocation.FunctionLocation_Activity;

public class Objects_Add_Activity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolBar;
    TextInputEditText notifId_tiet, funcLocId_tiet, equipId_tiet, equipName_tiet;
    Button submit_bt, cancel_bt;
    ImageView equipId_iv, funcLoc_iv;
    static final int OBJCT_EQUIP = 500;
    static final int OBJCT_FUNC = 501;
    Error_Dialog errorDialog = new Error_Dialog();
    OrdrObjectPrcbl oobp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_object_create_activity);

        toolBar = findViewById(R.id.toolBar);
        notifId_tiet = findViewById(R.id.notifId_tiet);
        funcLocId_tiet = findViewById(R.id.funcLocId_tiet);
        equipId_tiet = findViewById(R.id.equipId_tiet);
        equipName_tiet = findViewById(R.id.equipName_tiet);
        submit_bt = findViewById(R.id.submit_bt);
        cancel_bt = findViewById(R.id.cancel_bt);
        equipId_iv = findViewById(R.id.equipId_iv);
        funcLoc_iv = findViewById(R.id.funcLoc_iv);

        toolBar.setNavigationIcon(R.drawable.ic_arrow_back_white_24px);

        setSupportActionBar(toolBar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        equipId_iv.setOnClickListener(this);
        funcLoc_iv.setOnClickListener(this);
        submit_bt.setOnClickListener(this);
        cancel_bt.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.equipId_iv):
                Intent equipIdIntent = new Intent(Objects_Add_Activity.this,
                        Equipment_Activity.class);
                equipIdIntent.putExtra("functionlocation_id",
                        funcLocId_tiet.getText().toString());
                startActivityForResult(equipIdIntent, OBJCT_EQUIP);
                break;

            case (R.id.funcLoc_iv):
                Intent funcLocIntent = new Intent(Objects_Add_Activity.this,
                        FunctionLocation_Activity.class);
                startActivityForResult(funcLocIntent, OBJCT_FUNC);
                break;

            case (R.id.submit_bt):
                if (!notifId_tiet.getText().toString().equals("") ||
                        !funcLocId_tiet.getText().toString().equals("") ||
                        !equipId_tiet.getText().toString().equals("")) {
                    oobp = new OrdrObjectPrcbl();
                    oobp.setTplnr(funcLocId_tiet.getText().toString());
                    oobp.setEquipId(equipId_tiet.getText().toString());
                    oobp.setNotifNo(notifId_tiet.getText().toString());
                    oobp.setEquipTxt(equipName_tiet.getText().toString());
                    oobp.setAction("I");
                    Intent intent = new Intent();
                    intent.putExtra("oobp", oobp);
                    setResult(RESULT_OK, intent);
                    Objects_Add_Activity.this.finish();
                } else {
                    errorDialog.show_error_dialog(Objects_Add_Activity.this,
                            getResources().getString(R.string.any_one));
                }
                break;

            case (R.id.cancel_bt):
                Intent intent = new Intent();
                setResult(RESULT_CANCELED);
                Objects_Add_Activity.this.finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case (OBJCT_EQUIP):
                if (resultCode == RESULT_OK) {
                    equipId_tiet.setText(data.getStringExtra("equipment_id"));
                    equipName_tiet.setText(data.getStringExtra("equipment_text"));
                    funcLocId_tiet.setText(data.getStringExtra("functionlocation_id"));
                }
                break;

            case (OBJCT_FUNC):
                if (resultCode == RESULT_OK) {
                    funcLocId_tiet.setText(data.getStringExtra("functionlocation_id"));
                }
                break;
        }
    }
}
