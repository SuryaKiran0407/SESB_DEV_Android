package com.enstrapp.fieldtekpro.Initialload;

import android.app.Activity;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;

import com.enstrapp.fieldtekpro.Interface.Interface;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.checkempty.Check_Empty;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class JSA {

    private static String password = "", url_link = "", username = "", device_serial_number = "",
            device_id = "", device_uuid = "", Get_Response = "";
    private static SharedPreferences FieldTekPro_SharedPref;
    private static SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private static Check_Empty c_e = new Check_Empty();

    /* EtEHSOpstat Table and Fields Names */
    private static final String TABLE_EtEHSOpstat = "EtEHSOpstat";
    private static final String KEY_EtEHSOpstat_ID = "id";
    private static final String KEY_EtEHSOpstat_Code = "Code";
    private static final String KEY_EtEHSOpstat_Description = "Description";
    /* EtEHSOpstat Table and Fields Names */


    /* EtEHSHazcat Table and Fields Names */
    private static final String TABLE_EtEHSHazcat = "EtEHSHazcat";
    private static final String KEY_EtEHSHazcat_ID = "id";
    private static final String KEY_EtEHSHazcat_Code = "Code";
    private static final String KEY_EtEHSHazcat_Description = "Description";
    /* EtEHSHazcat Table and Fields Names */


    /* EtEHSHazard Table and Fields Names */
    private static final String TABLE_EtEHSHazard = "EtEHSHazard";
    private static final String KEY_EtEHSHazard_ID = "id";
    private static final String KEY_EtEHSHazard_Code = "Code";
    private static final String KEY_EtEHSHazard_Description = "Description";
    /* EtEHSHazard Table and Fields Names */


    /* EtEHSHazimp Table and Fields Names */
    private static final String TABLE_EtEHSHazimp = "EtEHSHazimp";
    private static final String KEY_EtEHSHazimp_ID = "id";
    private static final String KEY_EtEHSHazimp_HazardCode = "HazardCode";
    private static final String KEY_EtEHSHazimp_ImpactCode = "ImpactCode";
    private static final String KEY_EtEHSHazimp_Description = "Description";
    private static final String KEY_EtEHSHazimp_Type = "Type";
    /* EtEHSHazimp Table and Fields Names */


    /* EtEHSHazctrl Table and Fields Names */
    private static final String TABLE_EtEHSHazctrl = "EtEHSHazctrl";
    private static final String KEY_EtEHSHazctrl_ID = "id";
    private static final String KEY_EtEHSHazctrl_HazardCode = "HazardCode";
    private static final String KEY_EtEHSHazctrl_ControlCode = "ControlCode";
    private static final String KEY_EtEHSHazctrl_Type = "Type";
    private static final String KEY_EtEHSHazctrl_Subject = "Subject";
    private static final String KEY_EtEHSHazctrl_Description = "Description";
    private static final String KEY_EtEHSHazctrl_CtrlID = "CtrlID";
    /* EtEHSHazctrl Table and Fields Names */


    /* EtEHSLocTyp Table and Fields Names */
    private static final String TABLE_EtEHSLocTyp = "EtEHSLocTyp";
    private static final String KEY_EtEHSLocTyp_ID = "id";
    private static final String KEY_EtEHSLocTyp_Code = "Code";
    private static final String KEY_EtEHSLocTyp_Description = "Description";
    /* EtEHSLocTyp Table and Fields Names */


    /* EtEHSLocRev Table and Fields Names */
    private static final String TABLE_EtEHSLocRev = "EtEHSLocRev";
    private static final String KEY_EtEHSLocRev_ID = "id";
    private static final String KEY_EtEHSLocRev_Type = "Type";
    private static final String KEY_EtEHSLocRev_Status = "Status";
    private static final String KEY_EtEHSLocRev_FunctLocID = "FunctLocID";
    private static final String KEY_EtEHSLocRev_EquipmentID = "EquipmentID";
    private static final String KEY_EtEHSLocRev_PlantID = "PlantID";
    private static final String KEY_EtEHSLocRev_DbKey = "DbKey";
    private static final String KEY_EtEHSLocRev_ParentKey = "ParentKey";
    private static final String KEY_EtEHSLocRev_Text = "Text";
    private static final String KEY_EtEHSLocRev_LocRootRefKey = "LocRootRefKey";
    private static final String KEY_EtEHSLocRev_ParRootRefKey = "ParRootRefKey";
    private static final String KEY_EtEHSLocRev_RefID = "RefID";
    /* EtEHSLocRev Table and Fields Names */


    /* EtEHSJobTyp Table and Fields Names */
    private static final String TABLE_EtEHSJobTyp = "EtEHSJobTyp";
    private static final String KEY_EtEHSJobTyp_ID = "id";
    private static final String KEY_EtEHSJobTyp_Code = "Code";
    private static final String KEY_EtEHSJobTyp_Description = "Description";
    /* EtEHSJobTyp Table and Fields Names */


    /* EtEHSReason Table and Fields Names */
    private static final String TABLE_EtEHSReason = "EtEHSReason";
    private static final String KEY_EtEHSReason_ID = "id";
    private static final String KEY_EtEHSReason_Code = "Code";
    private static final String KEY_EtEHSReason_Description = "Description";
    /* EtEHSReason Table and Fields Names */


    /* EtEHSRasrole Table and Fields Names */
    private static final String TABLE_EtEHSRasrole = "EtEHSRasrole";
    private static final String KEY_EtEHSRasrole_ID = "id";
    private static final String KEY_EtEHSRasrole_Code = "Code";
    private static final String KEY_EtEHSRasrole_Description = "Description";
    /* EtEHSReason Table and Fields Names */


    /* EtEHSRasstep Table and Fields Names */
    private static final String TABLE_EtEHSRasstep = "EtEHSRasstep";
    private static final String KEY_EtEHSRasstep_ID = "id";
    private static final String KEY_EtEHSRasstep_Code = "Code";
    private static final String KEY_EtEHSRasstep_Description = "Description";
    /* EtEHSRasstep Table and Fields Names */


    /* EtJSAHdr Table and Fields Names */
    private static final String TABLE_EtJSAHdr = "EtJSAHdr";
    private static final String KEY_EtJSAHdr_ID = "id";
    private static final String KEY_EtJSAHdr_DbKey = "DbKey";
    private static final String KEY_EtJSAHdr_Aufnr = "Aufnr";
    private static final String KEY_EtJSAHdr_Rasid = "Rasid";
    private static final String KEY_EtJSAHdr_Rasstatus = "Rasstatus";
    private static final String KEY_EtJSAHdr_Rastype = "Rastype";
    private static final String KEY_EtJSAHdr_Title = "Title";
    private static final String KEY_EtJSAHdr_Job = "Job";
    private static final String KEY_EtJSAHdr_Opstatus = "Opstatus";
    private static final String KEY_EtJSAHdr_Location = "Location";
    private static final String KEY_EtJSAHdr_Comment = "Comment";
    private static final String KEY_EtJSAHdr_Statustxt = "Statustxt";
    private static final String KEY_EtJSAHdr_Rastypetxt = "Rastypetxt";
    private static final String KEY_EtJSAHdr_Opstatustxt = "Opstatustxt";
    private static final String KEY_EtJSAHdr_Locationtxt = "Locationtxt";
    private static final String KEY_EtJSAHdr_Jobtxt = "Jobtxt";
    private static final String KEY_EtJSAHdr_Action = "Action";
    /* EtJSAHdr Table and Fields Names */


    /* EtJSARisks Table and Fields Names */
    private static final String TABLE_EtJSARisks = "EtJSARisks";
    private static final String KEY_EtJSARisks_ID = "id";
    private static final String KEY_EtJSARisks_Aufnr = "Aufnr";
    private static final String KEY_EtJSARisks_Rasid = "Rasid";
    private static final String KEY_EtJSARisks_StepID = "StepID";
    private static final String KEY_EtJSARisks_StepSeq = "StepSeq";
    private static final String KEY_EtJSARisks_RiskID = "RiskID";
    private static final String KEY_EtJSARisks_StepPers = "StepPers";
    private static final String KEY_EtJSARisks_Hazard = "Hazard";
    private static final String KEY_EtJSARisks_RiskLevel = "RiskLevel";
    private static final String KEY_EtJSARisks_RiskType = "RiskType";
    private static final String KEY_EtJSARisks_Evaluation = "Evaluation";
    private static final String KEY_EtJSARisks_Likelihood = "Likelihood";
    private static final String KEY_EtJSARisks_Severity = "Severity";
    private static final String KEY_EtJSARisks_HazCat = "HazCat";
    private static final String KEY_EtJSARisks_Hazardtxt = "Hazardtxt";
    private static final String KEY_EtJSARisks_HazCattxt = "HazCattxt";
    private static final String KEY_EtJSARisks_StepTxt = "StepTxt";
    private static final String KEY_EtJSARisks_RiskLeveltxt = "RiskLeveltxt";
    private static final String KEY_EtJSARisks_RiskTypetxt = "RiskTypetxt";
    private static final String KEY_EtJSARisks_Action = "Action";
    /* EtJSARisks Table and Fields Names */


    /* EtJSAPer Table and Fields Names */
    private static final String TABLE_EtJSAPer = "EtJSAPer";
    private static final String KEY_EtJSAPer_ID = "id";
    private static final String KEY_EtJSAPer_Aufnr = "Aufnr";
    private static final String KEY_EtJSAPer_Rasid = "Rasid";
    private static final String KEY_EtJSAPer_PersonID = "PersonID";
    private static final String KEY_EtJSAPer_Role = "Role";
    private static final String KEY_EtJSAPer_Roletxt = "Roletxt";
    private static final String KEY_EtJSAPer_Persontxt = "Persontxt";
    private static final String KEY_EtJSAPer_Action = "Action";
    /* EtJSAPer Table and Fields Names */


    /* EtJSAImp Table and Fields Names */
    private static final String TABLE_EtJSAImp = "EtJSAImp";
    private static final String KEY_EtJSAImp_ID = "id";
    private static final String KEY_EtJSAImp_Aufnr = "Aufnr";
    private static final String KEY_EtJSAImp_Rasid = "Rasid";
    private static final String KEY_EtJSAImp_StepID = "StepID";
    private static final String KEY_EtJSAImp_RiskID = "RiskID";
    private static final String KEY_EtJSAImp_Impact = "Impact";
    private static final String KEY_EtJSAImp_Impacttxt = "Impacttxt";
    private static final String KEY_EtJSAImp_Action = "Action";
    /* EtJSAImp Table and Fields Names */


    /* EtJSARskCtrl Table and Fields Names */
    private static final String TABLE_EtJSARskCtrl = "EtJSARskCtrl";
    private static final String KEY_EtJSARskCtrl_ID = "id";
    private static final String KEY_EtJSARskCtrl_Aufnr = "Aufnr";
    private static final String KEY_EtJSARskCtrl_Rasid = "Rasid";
    private static final String KEY_EtJSARskCtrl_StepID = "StepID";
    private static final String KEY_EtJSARskCtrl_RiskID = "RiskID";
    private static final String KEY_EtJSARskCtrl_StepCode = "StepCode";
    private static final String KEY_EtJSARskCtrl_StepCompletion = "StepCompletion";
    private static final String KEY_EtJSARskCtrl_Ctrlid = "Ctrlid";
    private static final String KEY_EtJSARskCtrl_ControlCode = "ControlCode";
    private static final String KEY_EtJSARskCtrl_CatalogCode = "CatalogCode";
    private static final String KEY_EtJSARskCtrl_ImplStatus = "ImplStatus";
    private static final String KEY_EtJSARskCtrl_RespID = "RespID";
    private static final String KEY_EtJSARskCtrl_Type = "Type";
    private static final String KEY_EtJSARskCtrl_Subject = "Subject";
    private static final String KEY_EtJSARskCtrl_GoalTargetCode = "GoalTargetCode";
    private static final String KEY_EtJSARskCtrl_GoalObjectCode = "GoalObjectCode";
    private static final String KEY_EtJSARskCtrl_GoalCtrlEffect = "GoalCtrlEffect";
    private static final String KEY_EtJSARskCtrl_EffectDetDate = "EffectDetDate";
    private static final String KEY_EtJSARskCtrl_EffectDetCode = "EffectDetCode";
    private static final String KEY_EtJSARskCtrl_RefCategory = "RefCategory";
    private static final String KEY_EtJSARskCtrl_RefID = "RefID";
    private static final String KEY_EtJSARskCtrl_Ctrlidtxt = "Ctrlidtxt";
    private static final String KEY_EtJSARskCtrl_CatalogCodetxt = "CatalogCodetxt";
    private static final String KEY_EtJSARskCtrl_ImplStatustxt = "ImplStatustxt";
    private static final String KEY_EtJSARskCtrl_StepCodetxt = "StepCodetxt";
    private static final String KEY_EtJSARskCtrl_StepCompletiontxt = "StepCompletiontxt";
    private static final String KEY_EtJSARskCtrl_Respidtxt = "Respidtxt";
    private static final String KEY_EtJSARskCtrl_Action = "Action";
    /* EtJSARskCtrl Table and Fields Names */


    public static String Get_JRA_Data(Activity activity) {
        try {
            DATABASE_NAME = activity.getString(R.string.database_name);
            App_db = activity.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);


            /* Creating EtEHSOpstat Table with Fields */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtEHSOpstat);
            String CREATE_EtEHSOpstat_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_EtEHSOpstat + ""
                    + "( "
                    + KEY_EtEHSOpstat_ID + " INTEGER PRIMARY KEY,"
                    + KEY_EtEHSOpstat_Code + " TEXT,"
                    + KEY_EtEHSOpstat_Description + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_EtEHSOpstat_TABLE);
            /* Creating EtEHSOpstat Table with Fields */


            /* Creating EtEHSOpstat Table with Fields */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtEHSHazcat);
            String CREATE_EtEHSHazcat_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_EtEHSHazcat + ""
                    + "( "
                    + KEY_EtEHSHazcat_ID + " INTEGER PRIMARY KEY,"
                    + KEY_EtEHSHazcat_Code + " TEXT,"
                    + KEY_EtEHSHazcat_Description + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_EtEHSHazcat_TABLE);
            /* Creating EtEHSOpstat Table with Fields */


            /* Creating EtEHSHazard Table with Fields */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtEHSHazard);
            String CREATE_EtEHSHazard_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_EtEHSHazard + ""
                    + "( "
                    + KEY_EtEHSHazard_ID + " INTEGER PRIMARY KEY,"
                    + KEY_EtEHSHazard_Code + " TEXT,"
                    + KEY_EtEHSHazard_Description + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_EtEHSHazard_TABLE);
            /* Creating EtEHSHazard Table with Fields */


            /* Creating EtEHSHazimp Table with Fields */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtEHSHazimp);
            String CREATE_EtEHSHazimp_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_EtEHSHazimp + ""
                    + "( "
                    + KEY_EtEHSHazimp_ID + " INTEGER PRIMARY KEY,"
                    + KEY_EtEHSHazimp_HazardCode + " TEXT,"
                    + KEY_EtEHSHazimp_ImpactCode + " TEXT,"
                    + KEY_EtEHSHazimp_Description + " TEXT,"
                    + KEY_EtEHSHazimp_Type + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_EtEHSHazimp_TABLE);
            /* Creating EtEHSHazard Table with Fields */


            /* Creating EtEHSHazctrl Table with Fields */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtEHSHazctrl);
            String CREATE_EtEHSHazctrl_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_EtEHSHazctrl + ""
                    + "( "
                    + KEY_EtEHSHazctrl_ID + " INTEGER PRIMARY KEY,"
                    + KEY_EtEHSHazctrl_HazardCode + " TEXT,"
                    + KEY_EtEHSHazctrl_ControlCode + " TEXT,"
                    + KEY_EtEHSHazctrl_Type + " TEXT,"
                    + KEY_EtEHSHazctrl_Subject + " TEXT,"
                    + KEY_EtEHSHazctrl_Description + " TEXT,"
                    + KEY_EtEHSHazctrl_CtrlID + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_EtEHSHazctrl_TABLE);
            /* Creating EtEHSHazctrl Table with Fields */


            /* Creating EtEHSLocTyp Table with Fields */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtEHSLocTyp);
            String CREATE_EtEHSLocTyp_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_EtEHSLocTyp + ""
                    + "( "
                    + KEY_EtEHSLocTyp_ID + " INTEGER PRIMARY KEY,"
                    + KEY_EtEHSLocTyp_Code + " TEXT,"
                    + KEY_EtEHSLocTyp_Description + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_EtEHSLocTyp_TABLE);
            /* Creating EtEHSLocTyp Table with Fields */


            /* Creating EtEHSLocRev Table with Fields */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtEHSLocRev);
            String CREATE_EtEHSLocRev_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_EtEHSLocRev + ""
                    + "( "
                    + KEY_EtEHSLocRev_ID + " INTEGER PRIMARY KEY,"
                    + KEY_EtEHSLocRev_Type + " TEXT,"
                    + KEY_EtEHSLocRev_Status + " TEXT,"
                    + KEY_EtEHSLocRev_FunctLocID + " TEXT,"
                    + KEY_EtEHSLocRev_EquipmentID + " TEXT,"
                    + KEY_EtEHSLocRev_PlantID + " TEXT,"
                    + KEY_EtEHSLocRev_DbKey + " TEXT,"
                    + KEY_EtEHSLocRev_ParentKey + " TEXT,"
                    + KEY_EtEHSLocRev_Text + " TEXT,"
                    + KEY_EtEHSLocRev_LocRootRefKey + " TEXT,"
                    + KEY_EtEHSLocRev_ParRootRefKey + " TEXT,"
                    + KEY_EtEHSLocRev_RefID + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_EtEHSLocRev_TABLE);
            /* Creating EtEHSLocRev Table with Fields */


            /* Creating EtEHSJobTyp Table with Fields */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtEHSJobTyp);
            String CREATE_EtEHSJobTyp_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_EtEHSJobTyp + ""
                    + "( "
                    + KEY_EtEHSJobTyp_ID + " INTEGER PRIMARY KEY,"
                    + KEY_EtEHSJobTyp_Code + " TEXT,"
                    + KEY_EtEHSJobTyp_Description + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_EtEHSJobTyp_TABLE);
            /* Creating EtEHSJobTyp Table with Fields */


            /* Creating EtEHSReason Table with Fields */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtEHSReason);
            String CREATE_EtEHSReason_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_EtEHSReason + ""
                    + "( "
                    + KEY_EtEHSReason_ID + " INTEGER PRIMARY KEY,"
                    + KEY_EtEHSReason_Code + " TEXT,"
                    + KEY_EtEHSReason_Description + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_EtEHSReason_TABLE);
            /* Creating EtEHSReason Table with Fields */


            /* Creating EtEHSRasrole Table with Fields */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtEHSRasrole);
            String CREATE_EtEHSRasrole_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_EtEHSRasrole + ""
                    + "( "
                    + KEY_EtEHSRasrole_ID + " INTEGER PRIMARY KEY,"
                    + KEY_EtEHSRasrole_Code + " TEXT,"
                    + KEY_EtEHSRasrole_Description + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_EtEHSRasrole_TABLE);
            /* Creating EtEHSRasrole Table with Fields */


            /* Creating EtEHSRasstep Table with Fields */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtEHSRasstep);
            String CREATE_EtEHSRasstep_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_EtEHSRasstep + ""
                    + "( "
                    + KEY_EtEHSRasstep_ID + " INTEGER PRIMARY KEY,"
                    + KEY_EtEHSRasstep_Code + " TEXT,"
                    + KEY_EtEHSRasstep_Description + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_EtEHSRasstep_TABLE);
            /* Creating EtEHSRasstep Table with Fields */


            /* Creating EtJSAHdr Table with Fields */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtJSAHdr);
            String CREATE_TABLE_EtJSAHdr = "CREATE TABLE IF NOT EXISTS " + TABLE_EtJSAHdr + ""
                    + "( "
                    + KEY_EtJSAHdr_ID + " INTEGER PRIMARY KEY,"
                    + KEY_EtJSAHdr_DbKey + " TEXT,"
                    + KEY_EtJSAHdr_Aufnr + " TEXT,"
                    + KEY_EtJSAHdr_Rasid + " TEXT,"
                    + KEY_EtJSAHdr_Rasstatus + " TEXT,"
                    + KEY_EtJSAHdr_Rastype + " TEXT,"
                    + KEY_EtJSAHdr_Title + " TEXT,"
                    + KEY_EtJSAHdr_Job + " TEXT,"
                    + KEY_EtJSAHdr_Opstatus + " TEXT,"
                    + KEY_EtJSAHdr_Location + " TEXT,"
                    + KEY_EtJSAHdr_Comment + " TEXT,"
                    + KEY_EtJSAHdr_Statustxt + " TEXT,"
                    + KEY_EtJSAHdr_Rastypetxt + " TEXT,"
                    + KEY_EtJSAHdr_Opstatustxt + " TEXT,"
                    + KEY_EtJSAHdr_Locationtxt + " TEXT,"
                    + KEY_EtJSAHdr_Jobtxt + " TEXT,"
                    + KEY_EtJSAHdr_Action + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_TABLE_EtJSAHdr);
            /* Creating EtJSAHdr Table with Fields */


            /* Creating EtJSARisks Table with Fields */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtJSARisks);
            String CREATE_TABLE_EtJSARisks = "CREATE TABLE IF NOT EXISTS " + TABLE_EtJSARisks + ""
                    + "( "
                    + KEY_EtJSARisks_ID + " INTEGER PRIMARY KEY,"
                    + KEY_EtJSARisks_Aufnr + " TEXT,"
                    + KEY_EtJSARisks_Rasid + " TEXT,"
                    + KEY_EtJSARisks_StepID + " TEXT,"
                    + KEY_EtJSARisks_StepSeq + " TEXT,"
                    + KEY_EtJSARisks_RiskID + " TEXT,"
                    + KEY_EtJSARisks_StepPers + " TEXT,"
                    + KEY_EtJSARisks_Hazard + " TEXT,"
                    + KEY_EtJSARisks_RiskLevel + " TEXT,"
                    + KEY_EtJSARisks_RiskType + " TEXT,"
                    + KEY_EtJSARisks_Evaluation + " TEXT,"
                    + KEY_EtJSARisks_Likelihood + " TEXT,"
                    + KEY_EtJSARisks_Severity + " TEXT,"
                    + KEY_EtJSARisks_HazCat + " TEXT,"
                    + KEY_EtJSARisks_Hazardtxt + " TEXT,"
                    + KEY_EtJSARisks_HazCattxt + " TEXT,"
                    + KEY_EtJSARisks_StepTxt + " TEXT,"
                    + KEY_EtJSARisks_RiskLeveltxt + " TEXT,"
                    + KEY_EtJSARisks_RiskTypetxt + " TEXT,"
                    + KEY_EtJSARisks_Action + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_TABLE_EtJSARisks);
            /* Creating EtJSARisks Table with Fields */


            /* Creating EtJSAPer Table with Fields */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtJSAPer);
            String CREATE_TABLE_EtJSAPer = "CREATE TABLE IF NOT EXISTS " + TABLE_EtJSAPer + ""
                    + "( "
                    + KEY_EtJSAPer_ID + " INTEGER PRIMARY KEY,"
                    + KEY_EtJSAPer_Aufnr + " TEXT,"
                    + KEY_EtJSAPer_Rasid + " TEXT,"
                    + KEY_EtJSAPer_PersonID + " TEXT,"
                    + KEY_EtJSAPer_Role + " TEXT,"
                    + KEY_EtJSAPer_Roletxt + " TEXT,"
                    + KEY_EtJSAPer_Persontxt + " TEXT,"
                    + KEY_EtJSAPer_Action + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_TABLE_EtJSAPer);
            /* Creating EtJSAPer Table with Fields */


            /* Creating EtJSAImp Table with Fields */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtJSAImp);
            String CREATE_TABLE_EtJSAImp = "CREATE TABLE IF NOT EXISTS " + TABLE_EtJSAImp + ""
                    + "( "
                    + KEY_EtJSAImp_ID + " INTEGER PRIMARY KEY,"
                    + KEY_EtJSAImp_Aufnr + " TEXT,"
                    + KEY_EtJSAImp_Rasid + " TEXT,"
                    + KEY_EtJSAImp_StepID + " TEXT,"
                    + KEY_EtJSAImp_RiskID + " TEXT,"
                    + KEY_EtJSAImp_Impact + " TEXT,"
                    + KEY_EtJSAImp_Impacttxt + " TEXT,"
                    + KEY_EtJSAImp_Action + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_TABLE_EtJSAImp);
            /* Creating EtJSAImp Table with Fields */


            /* Creating EtJSARskCtrl Table with Fields */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtJSARskCtrl);
            String CREATE_TABLE_EtJSARskCtrl = "CREATE TABLE IF NOT EXISTS " + TABLE_EtJSARskCtrl + ""
                    + "( "
                    + KEY_EtJSARskCtrl_ID + " INTEGER PRIMARY KEY,"
                    + KEY_EtJSARskCtrl_Aufnr + " TEXT,"
                    + KEY_EtJSARskCtrl_Rasid + " TEXT,"
                    + KEY_EtJSARskCtrl_StepID + " TEXT,"
                    + KEY_EtJSARskCtrl_RiskID + " TEXT,"
                    + KEY_EtJSARskCtrl_StepCode + " TEXT,"
                    + KEY_EtJSARskCtrl_StepCompletion + " TEXT,"
                    + KEY_EtJSARskCtrl_Ctrlid + " TEXT,"
                    + KEY_EtJSARskCtrl_ControlCode + " TEXT,"
                    + KEY_EtJSARskCtrl_CatalogCode + " TEXT,"
                    + KEY_EtJSARskCtrl_ImplStatus + " TEXT,"
                    + KEY_EtJSARskCtrl_RespID + " TEXT,"
                    + KEY_EtJSARskCtrl_Type + " TEXT,"
                    + KEY_EtJSARskCtrl_Subject + " TEXT,"
                    + KEY_EtJSARskCtrl_GoalTargetCode + " TEXT,"
                    + KEY_EtJSARskCtrl_GoalObjectCode + " TEXT,"
                    + KEY_EtJSARskCtrl_GoalCtrlEffect + " TEXT,"
                    + KEY_EtJSARskCtrl_EffectDetDate + " TEXT,"
                    + KEY_EtJSARskCtrl_EffectDetCode + " TEXT,"
                    + KEY_EtJSARskCtrl_RefCategory + " TEXT,"
                    + KEY_EtJSARskCtrl_RefID + " TEXT,"
                    + KEY_EtJSARskCtrl_Ctrlidtxt + " TEXT,"
                    + KEY_EtJSARskCtrl_CatalogCodetxt + " TEXT,"
                    + KEY_EtJSARskCtrl_ImplStatustxt + " TEXT,"
                    + KEY_EtJSARskCtrl_StepCodetxt + " TEXT,"
                    + KEY_EtJSARskCtrl_StepCompletiontxt + " TEXT,"
                    + KEY_EtJSARskCtrl_Respidtxt + " TEXT,"
                    + KEY_EtJSARskCtrl_Action + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_TABLE_EtJSARskCtrl);
            /* Creating EtJSARskCtrl Table with Fields */


            /* Initializing Shared Preferences */
            FieldTekPro_SharedPref = activity
                    .getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();
            username = FieldTekPro_SharedPref.getString("Username", null);
            password = FieldTekPro_SharedPref.getString("Password", null);
            String webservice_type = FieldTekPro_SharedPref
                    .getString("webservice_type", null);
            /* Initializing Shared Preferences */
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype =" +
                            " ? and Zactivity = ? and Endpoint = ?",
                    new String[]{"Z2", "F4", webservice_type});
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToNext();
                url_link = cursor.getString(5);
            }
            /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
            device_id = Settings.Secure
                    .getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
            device_serial_number = Build.SERIAL;
            String androidId = "" + Settings.Secure
                    .getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
            UUID deviceUuid = new UUID(androidId.hashCode(),
                    ((long) device_id.hashCode() << 32) | device_serial_number.hashCode());
            device_uuid = deviceUuid.toString();
            /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
            String URL = activity.getString(R.string.ip_address);
            Map<String, String> map = new HashMap<>();
            map.put("IvUser", username);
            map.put("Muser", username.toUpperCase().toString());
            map.put("Deviceid", device_id);
            map.put("Devicesno", device_serial_number);
            map.put("Udid", device_uuid);
            map.put("IvTransmitType", "LOAD");
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(180000, TimeUnit.MILLISECONDS)
                    .writeTimeout(180000, TimeUnit.MILLISECONDS)
                    .readTimeout(180000, TimeUnit.MILLISECONDS).build();
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(URL).client(client).build();
            Interface service = retrofit.create(Interface.class);
            String credentials = username + ":" + password;
            final String basic = "Basic " +
                    Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            Call<JSA_SER> call = service.getJRADetails(url_link, basic, map);
            Response<JSA_SER> response = call.execute();
            int response_status_code = response.code();
            Log.v("kiran_JRA_code", response_status_code + "...");
            if (response_status_code == 200) {
                if (response.body().getD().getResults() != null && response.body().getD().getResults().size() > 0) {
                    App_db.beginTransaction();
                    try {

                        /*EtEHSOpstat*/
                        if (response.body().getD().getResults().get(0).getEtEHSOpstat() != null) {
                            if (response.body().getD().getResults().get(0).getEtEHSOpstat().getResults() != null
                                    && response.body().getD().getResults().get(0).getEtEHSOpstat().getResults().size() > 0) {
                                ContentValues values = new ContentValues();
                                for (JSA_SER.Result1 eR : response.body().getD().getResults().get(0).getEtEHSOpstat().getResults()) {
                                    values.put("Code, ", eR.getCode());
                                    values.put("Description", eR.getDescription());
                                    App_db.insert("EtEHSOpstat", null, values);
                                }
                            }
                        }
                        /*EtEHSHazcat*/
                        if (response.body().getD().getResults().get(0).getEtEHSHazcat() != null) {
                            if (response.body().getD().getResults().get(0).getEtEHSHazcat().getResults() != null
                                    && response.body().getD().getResults().get(0).getEtEHSHazcat().getResults().size() > 0) {
                                ContentValues values = new ContentValues();
                                for (JSA_SER.Result1 eR : response.body().getD().getResults().get(0).getEtEHSHazcat().getResults()) {
                                    values.put("Code, ", eR.getCode());
                                    values.put("Description", eR.getDescription());
                                    App_db.insert("EtEHSHazcat", null, values);
                                }
                            }
                        }
                        /*EtEHSHazard*/
                        if (response.body().getD().getResults().get(0).getEtEHSHazard() != null) {
                            if (response.body().getD().getResults().get(0).getEtEHSHazard().getResults() != null
                                    && response.body().getD().getResults().get(0).getEtEHSHazard().getResults().size() > 0) {
                                ContentValues values = new ContentValues();
                                for (JSA_SER.Result1 eR : response.body().getD().getResults().get(0).getEtEHSHazard().getResults()) {
                                    values.put("Code, ", eR.getCode());
                                    values.put("Description", eR.getDescription());
                                    App_db.insert("EtEHSHazard", null, values);
                                }
                            }
                        }
                        /*EtEHSHazimp*/
                        if (response.body().getD().getResults().get(0).getEtEHSHazimp() != null) {
                            if (response.body().getD().getResults().get(0).getEtEHSHazimp().getResults() != null
                                    && response.body().getD().getResults().get(0).getEtEHSHazimp().getResults().size() > 0) {
                                ContentValues values = new ContentValues();
                                for (JSA_SER.EtEHSHazimp_Result eRIMP : response.body().getD().getResults().get(0).getEtEHSHazimp().getResults()) {
                                    values.put("HazardCode, ", eRIMP.getHazardCode());
                                    values.put("ImpactCode", eRIMP.getImpactCode());
                                    values.put("Description", eRIMP.getDescription());
                                    values.put("Type", eRIMP.getType());
                                    App_db.insert("EtEHSHazimp", null, values);
                                }
                            }
                        }
                        /*EtEHSHazctrl*/
                        if (response.body().getD().getResults().get(0).getEtEHSHazctrl() != null) {
                            if (response.body().getD().getResults().get(0).getEtEHSHazctrl().getResults() != null
                                    && response.body().getD().getResults().get(0).getEtEHSHazctrl().getResults().size() > 0) {
                                ContentValues values = new ContentValues();
                                for (JSA_SER.EtEHSHazctrl_Result eTCTRL : response.body().getD().getResults().get(0).getEtEHSHazctrl().getResults()) {
                                    values.put("HazardCode, ", eTCTRL.getHazardCode());
                                    values.put("ControlCode", eTCTRL.getControlCode());
                                    values.put("Type", eTCTRL.getType());
                                    values.put("Subject", eTCTRL.getSubject());
                                    values.put("Description", eTCTRL.getDescription());
                                    values.put("CtrlID", eTCTRL.getCtrlid());
                                    App_db.insert("EtEHSHazctrl", null, values);
                                }
                            }
                        }
                        /*EtEHSLocTyp*/
                        if (response.body().getD().getResults().get(0).getEtEHSLocTyp() != null) {
                            if (response.body().getD().getResults().get(0).getEtEHSLocTyp().getResults() != null
                                    && response.body().getD().getResults().get(0).getEtEHSLocTyp().getResults().size() > 0) {
                                ContentValues values = new ContentValues();
                                for (JSA_SER.Result1 eTLT : response.body().getD().getResults().get(0).getEtEHSLocTyp().getResults()) {
                                    values.put("Code, ", eTLT.getCode());
                                    values.put("Description", eTLT.getDescription());
                                    App_db.insert("EtEHSLocTyp", null, values);
                                }
                            }
                        }
                        /*EtEHSLocRev*/
                        if (response.body().getD().getResults().get(0).getEtEHSLocRev() != null) {
                            if (response.body().getD().getResults().get(0).getEtEHSLocRev().getResults() != null
                                    && response.body().getD().getResults().get(0).getEtEHSLocRev().getResults().size() > 0) {
                                ContentValues values = new ContentValues();
                                for (JSA_SER.EtEHSLocRev_Result eTLR : response.body().getD().getResults().get(0).getEtEHSLocRev().getResults()) {
                                    values.put("Type, ", eTLR.getType());
                                    values.put("Status", eTLR.getStatus());
                                    values.put("FunctLocID", eTLR.getFunctLocID());
                                    values.put("EquipmentID", eTLR.getEquipmentID());
                                    values.put("PlantID", eTLR.getPlantID());
                                    values.put("DbKey", eTLR.getDbKey());
                                    values.put("ParentKey", eTLR.getParentKey());
                                    values.put("Text", eTLR.getText());
                                    values.put("LocRootRefKey", eTLR.getLocRootRefKey());
                                    values.put("ParRootRefKey", eTLR.getParRootRefKey());
                                    values.put("RefID", eTLR.getRefID());
                                    App_db.insert("EtEHSLocRev", null, values);
                                }
                            }
                        }
                        /*EtEHSJobTyp*/
                        if (response.body().getD().getResults().get(0).getEtEHSJobTyp() != null) {
                            if (response.body().getD().getResults().get(0).getEtEHSJobTyp().getResults() != null
                                    && response.body().getD().getResults().get(0).getEtEHSJobTyp().getResults().size() > 0) {
                                ContentValues values = new ContentValues();
                                for (JSA_SER.Result1 eTJT : response.body().getD().getResults().get(0).getEtEHSJobTyp().getResults()) {
                                    values.put("Code, ", eTJT.getCode());
                                    values.put("Description", eTJT.getDescription());
                                    App_db.insert("EtEHSJobTyp", null, values);
                                }
                            }
                        }
                        /*EtEHSReason*/
                        if (response.body().getD().getResults().get(0).getEtEHSReason() != null) {
                            if (response.body().getD().getResults().get(0).getEtEHSReason().getResults() != null
                                    && response.body().getD().getResults().get(0).getEtEHSReason().getResults().size() > 0) {
                                ContentValues values = new ContentValues();
                                for (JSA_SER.Result1 eTER : response.body().getD().getResults().get(0).getEtEHSReason().getResults()) {
                                    values.put("Code, ", eTER.getCode());
                                    values.put("Description", eTER.getDescription());
                                    App_db.insert("EtEHSReason", null, values);
                                }
                            }
                        }
                        /*EtEHSRasrole*/
                        if (response.body().getD().getResults().get(0).getEtEHSRasrole() != null) {
                            if (response.body().getD().getResults().get(0).getEtEHSRasrole().getResults() != null
                                    && response.body().getD().getResults().get(0).getEtEHSRasrole().getResults().size() > 0) {
                                ContentValues values = new ContentValues();
                                for (JSA_SER.Result1 eTER : response.body().getD().getResults().get(0).getEtEHSRasrole().getResults()) {
                                    values.put("Code, ", eTER.getCode());
                                    values.put("Description", eTER.getDescription());
                                    App_db.insert("EtEHSRasrole", null, values);
                                }
                            }
                        }
                        /*EtEHSRasstep*/
                        if (response.body().getD().getResults().get(0).getEtEHSRasstep() != null) {
                            if (response.body().getD().getResults().get(0).getEtEHSRasstep().getResults() != null
                                    && response.body().getD().getResults().get(0).getEtEHSRasstep().getResults().size() > 0) {
                                ContentValues values = new ContentValues();
                                for (JSA_SER.Result1 eTER : response.body().getD().getResults().get(0).getEtEHSRasstep().getResults()) {
                                    values.put("Code, ", eTER.getCode());
                                    values.put("Description", eTER.getDescription());
                                    App_db.insert("EtEHSRasstep", null, values);
                                }
                            }
                        }
                        App_db.setTransactionSuccessful();
                        Get_Response = "success";
                    } finally {
                        App_db.endTransaction();
                    }
                }
            }
        } catch (Exception ex) {
            Log.v("kiran_ee", ex.getMessage() + "....");
            Get_Response = "exception";
        }
        return Get_Response;
    }
}
