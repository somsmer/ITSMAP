package com.pandabearsinc.sommer.depolarizedpanda_11383;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChooseHistoryActivity extends FragmentActivity
        implements DatePickerFragment.OnCompleteListener {

    EditText startdate_EditText;
    EditText enddate_EditText;
    String string_startdate="";
    String string_enddate="";
    String DB_startdate="";
    String DB_enddate="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_history);
        startdate_EditText = (EditText)findViewById(R.id.HistoryStartdate);
        enddate_EditText = (EditText)findViewById(R.id.HistoryEnddate);
    }

    public void DatePickerStartDate(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePickerStartDate");
    }

    public void DatePickerEndDate(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePickerEndDate" );
    }

    @Override
    public void onComplete(String day, String month, String year, String mytag) {


          if (mytag == "datePickerStartDate")
        {
            DB_startdate=year+"-"+month+"-"+day;
            string_startdate = day+"-"+month+"-"+year;
            startdate_EditText.setText(string_startdate);
            enddate_EditText.requestFocus();

        } if (mytag == "datePickerEndDate")
        {
            if(string_startdate == null  || string_startdate.isEmpty()) {
                Toast.makeText(this, "Please chose start date first",
                        Toast.LENGTH_SHORT).show();
            }
            else{
           DB_enddate=year+"-"+month+"-"+day;
           string_enddate = day+"-"+month+"-"+year;
           SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            try {
                Date date_startdate = format.parse(string_startdate);
                Date date_enddate = format.parse(string_enddate);
                if (date_enddate.compareTo(date_startdate) < 0) {
                    Toast.makeText(this, "Please chose an end date later than start date",
                            Toast.LENGTH_SHORT).show();
                    enddate_EditText.setText("");
                }
                else
                {
                    enddate_EditText.setText(string_enddate);
                }
              } catch (ParseException e) {
                e.printStackTrace();
            }
       }
      }
    }
    //ButtonClick
    public void showHistory(View view) {

        if (string_enddate.matches("") || DB_startdate.matches("") || DB_enddate.matches(""))
         {
             Toast.makeText(this, "Please enter date", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent showHistorydIntent = new Intent(this, HistoryActivity.class);
            showHistorydIntent.putExtra("StartDate", DB_startdate);
            showHistorydIntent.putExtra("EndDate", DB_enddate);
            startActivity(showHistorydIntent);

        }
    }
}
