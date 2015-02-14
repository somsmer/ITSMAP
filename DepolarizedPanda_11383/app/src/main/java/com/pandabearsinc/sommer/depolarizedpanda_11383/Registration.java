package com.pandabearsinc.sommer.depolarizedpanda_11383;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Thomas on 26-09-2014.
 */
public class Registration implements Parcelable {

    private String _date;
    private String _mood;
    private String _note;
    private int _amount;
    private Medicine _medicine;

    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(_date);
        dest.writeString(_mood);
        dest.writeString(_note);
        dest.writeInt(_amount);
        dest.writeParcelable(_medicine, 1);
    }

    public Registration(Parcel source)
    {
        _date = source.readString();
        _mood = source.readString();
        _note = source.readString();
        _amount = source.readInt();
        _medicine = source.readParcelable(MedicineCreator.class.getClassLoader());
    }

    public int describeContents()
    {
        return hashCode();
    }

    public Registration()
    {
        _date = "";
        _mood = "";
        _note = "";
        _amount = 0;
        _medicine = null;
    }

    public Registration(String date, String mood, String note, int amount, Medicine medicine)
    {
        this._date = date;
        this._mood = mood;
        this._note = note;
        this._amount = amount;
        this._medicine = medicine;
    }

    public String getDate()
    {
        return this._date;
    }

    public void setDate(String date)
    {
        this._date = date;
    }

    public String getMood()
    {
        return this._mood;
    }

    public void setMood(String mood)
    {
        this._mood = mood;
    }

    public String getNote()
    {
        return this._note;
    }

    public void setNote(String note)
    {
        this._note = note;
    }

    public int getAmount()
    {
        return this._amount;
    }

    public void setAmount(int amount)
    {
        this._amount = amount;
    }

    public double getTotalAmount()
    {
        return _medicine.getDoseInMg()*_amount;
    }

    public Medicine getMedicine()
    {
        return _medicine;
    }

    public void setMedicine(Medicine medicine)
    {
        this._medicine = medicine;
    }
}

class RegistrationCreator implements Parcelable.Creator<Registration>{
    public Registration createFromParcel(Parcel source) {
        return new Registration(source);
    }
    public Registration[] newArray(int size) {
        return new Registration[size];
    }
};

