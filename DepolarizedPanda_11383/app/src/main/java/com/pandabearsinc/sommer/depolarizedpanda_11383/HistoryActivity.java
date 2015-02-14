package com.pandabearsinc.sommer.depolarizedpanda_11383;

import android.app.Activity;
import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class HistoryActivity extends ListActivity{

    final static String TAG = "HistoryActivity";
    private ListView listView;
    ArrayAdapter<String> adapter = null;
    private HistoryAdapter historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        listView = (ListView) findViewById(android.R.id.list);

        //Get intent from called activity
        Intent overviewIntent = getIntent();
        String StartDate = overviewIntent.getStringExtra("StartDate");
        String EndDate = overviewIntent.getStringExtra("EndDate");
       //Start service to get values from DB
        Intent ServiceIntent = new Intent(this, GetHistoryFromDB.class);
        ServiceIntent.putExtra("StartDate", StartDate);
        ServiceIntent.putExtra("EndDate", EndDate);

        LocalBroadcastManager.getInstance(this).registerReceiver(HistoryReceiver, new IntentFilter("Registrations"));

        startService(ServiceIntent);

        Log.d(TAG, "startDate " + StartDate + " EndDate: " + EndDate);

    }

    private BroadcastReceiver HistoryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            // Get extra data included in the Intent
            ArrayList<Registration> message = intent.getParcelableArrayListExtra("RegistrationList");
            Log.d("receiver", "Got message: " + message);

            historyAdapter = new HistoryAdapter(getApplicationContext(), android.R.id.list, message);
            setListAdapter(historyAdapter);

            listView.setAdapter(historyAdapter);
        }
    };

    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(HistoryReceiver);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
