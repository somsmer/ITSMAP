package com.pandabearsinc.sommer.depolarizedpanda_11383;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Thomas on 10-10-2014.
 */
public class HistoryAdapter extends ArrayAdapter<Registration> {
    private ArrayList<Registration> registrations;
    Context context;

    public HistoryAdapter(Context context, int textViewResourceId, ArrayList<Registration> registrationArrayList) {
        super(context, textViewResourceId, registrationArrayList);
        this.registrations = registrationArrayList;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(v == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.listitem_history, null);
        }

        Registration registration = registrations.get(position);
        if (registration != null) {
            TextView date = (TextView) v.findViewById(R.id.history_date);
            TextView moodTitle = (TextView) v.findViewById(R.id.history_mood_title);
            TextView moodBody = (TextView) v.findViewById(R.id.history_mood_body);
            TextView medicineTitle = (TextView) v.findViewById(R.id.history_medicine_header);
            TextView medicineBody = (TextView) v.findViewById(R.id.history_medicine_body);
            TextView noteTitle = (TextView) v.findViewById(R.id.history_note_title);
            TextView noteBody = (TextView) v.findViewById(R.id.history_note_body);

            date.setText(registration.getDate());
            moodTitle.setText(R.string.moodTitle);
            moodBody.setText(registration.getMood());
            medicineTitle.setText(R.string.medicineTitle);
            medicineBody.setText(registration.getMedicine().getName() + " - " + registration.getTotalAmount());
            noteTitle.setText(R.string.noteTitle);
            noteBody.setText(registration.getNote());
        }

        return v;
    }
}