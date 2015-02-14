package com.pandabearsinc.sommer.depolarizedpanda_11383;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.support.v4.content.LocalBroadcastManager;
import java.util.ArrayList;
import java.util.List;

public class GetHistoryFromDB extends IntentService {
   final static String TAG = "GetHistoryFromDBService";

    public GetHistoryFromDB() {
        super("GetHistoryFromDB");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null)
            {
            String StartDate = intent.getStringExtra("StartDate");
            String EndDate = intent.getStringExtra("EndDate");

            Log.d(TAG, "startDate " + StartDate + " EndDate: " + EndDate);

            ArrayList<Registration> registrationList = StartDbSearch(StartDate, EndDate);

            Intent RegistrationIntent = new Intent("Registrations");
            RegistrationIntent.putParcelableArrayListExtra("RegistrationList", registrationList);

            LocalBroadcastManager.getInstance(this).sendBroadcast(RegistrationIntent);

            }
        }

      private ArrayList<Registration> StartDbSearch (String startDate, String endDate) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        Log.d(TAG, "StartDbSearch: " + startDate + " enddate: " +endDate);

        return databaseHelper.getHistoryFromDateToDate(startDate, endDate);
    }

}
