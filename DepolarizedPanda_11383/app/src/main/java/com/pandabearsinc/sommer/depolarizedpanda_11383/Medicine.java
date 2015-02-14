package com.pandabearsinc.sommer.depolarizedpanda_11383;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Thomas on 28-09-2014.
 */
public class Medicine implements Parcelable {

    private String _name;
    private double _doseInMg;
    private int _id;

    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(_name);
        dest.writeDouble(_doseInMg);
        dest.writeInt(_id);
    }

    public int describeContents()
    {
        return hashCode();
    }

    public Medicine(Parcel source)
    {
        _name = source.readString();
        _doseInMg = source.readDouble();
        _id = source.readInt();
    }

    public Medicine()
    {
        this._name = "";
        this._doseInMg = 0.0;

    }

    public Medicine (String name, double doseInMg)
    {
        this._name = name;
        this._doseInMg = doseInMg;
        this._id=1;
    }

    public String getName()
    {
        return this._name;
    }

    public void setName(String name)
    {
        this._name = name;
    }

    public double getDoseInMg()
    {
        return this._doseInMg;
    }

    public void setDoseInMg(double doseInMg)
    {
        this._doseInMg = doseInMg;
    }

    public int getId()
    {
        return this._id;
    }

    public void setId(int id)
    {
        this._id = id;
    }

    @Override
    public String toString() {
        return _name + " - " + _doseInMg + "mg";
    }

}

class MedicineCreator implements Parcelable.Creator<Medicine>{
    public Medicine createFromParcel(Parcel source) {
        return new Medicine(source);
    }
    public Medicine[] newArray(int size) {
        return new Medicine[size];
    }
};
