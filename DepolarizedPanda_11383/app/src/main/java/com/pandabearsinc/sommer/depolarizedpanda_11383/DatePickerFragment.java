package com.pandabearsinc.sommer.depolarizedpanda_11383;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    final static String TAG ="DatePickerFragment";
    private OnCompleteListener mListener;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (OnCompleteListener)activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
        Calendar c1 = GregorianCalendar.getInstance();

        String day;
        String month;
        String SelectedYear = Integer.toString(year);

        monthOfYear++;
        if (dayOfMonth < 10) {
            day = "0" + dayOfMonth;
        } else {
            day = dayOfMonth +"";
        }
        if (monthOfYear < 10) {
            month = "0" + monthOfYear;
        } else {
            month = monthOfYear + "";
        }

        Log.d(TAG +"monthYear", day + month);
        //Get tag, so we know which datepicker it is

        String mytag=getTag();
        Log.d(TAG," day:" + day +" month:"+ month +" year:" +year);

        this.mListener.onComplete(day,month,SelectedYear,mytag);
    }

    public static interface OnCompleteListener {

        public abstract void onComplete(String day, String month, String year, String mytag);
    }




}