package com.enstrapp.fieldtekpro.Authorizations;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.enstrapp.fieldtekpro_sesb_dev.R;

import static android.content.Context.MODE_PRIVATE;

public class Authorizations {

    private static String display_status = "false";
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";

    public static String Get_Authorizations_Data(Activity activity, String func, String type) {
        try {
            DATABASE_NAME = activity.getString(R.string.database_name);
            App_db = activity.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            String Create_notif_EtUsrf_inactive = "", Create_notif_EtMUsrf_inactive = "";
            try {
                Cursor cursor = App_db.rawQuery("select * from GET_USER_FUNCTIONS_EtUsrf where Zdoctype = ? AND Zactivity = ?", new String[]{func, type});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            Create_notif_EtUsrf_inactive = cursor.getString(5);
                        }
                        while (cursor.moveToNext());
                    }
                } else {
                    cursor.close();
                }
            } catch (Exception e) {
            }
            try {
                Cursor cursor = App_db.rawQuery("select * from Authorization_EtMusrf where Zdoctype = ? AND Zactivity = ?", new String[]{func, type});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            Create_notif_EtMUsrf_inactive = cursor.getString(5);
                        }
                        while (cursor.moveToNext());
                    }
                } else {
                    cursor.close();
                }
            } catch (Exception e) {
            }
            if (Create_notif_EtUsrf_inactive.equalsIgnoreCase("X")) {
                if (Create_notif_EtMUsrf_inactive.equalsIgnoreCase("X")) {
                    display_status = "false";
                } else {
                    display_status = "false";
                }
            } else {
                if (Create_notif_EtMUsrf_inactive.equalsIgnoreCase("X")) {
                    display_status = "false";
                } else {
                    display_status = "true";
                }
            }
        } catch (Exception ex) {
            display_status = "false";
        } finally {
        }
        return display_status;
    }

}
