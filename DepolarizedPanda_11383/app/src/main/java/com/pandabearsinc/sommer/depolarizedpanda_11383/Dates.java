package com.pandabearsinc.sommer.depolarizedpanda_11383;

import android.app.Application;

/**
 * Created by Eva on 03-10-2014.
 */
public class Dates extends Application {

    private String _date;
    private String _year;
    private String _day;
    private String _month;

    public String getDate() {
        return _date;
    }

    public void setDate(String date) {
        this._date = date;
    }

    public String db_getDate() {


        return _year+"-"+_month+"-"+_day;
    }

    public void db_setDate(String year,String day,String month)
    {
        this._year = year;
        this._day = day;
        this._month = month;
    }

}
