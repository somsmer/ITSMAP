package com.pandabearsinc.sommer.depolarizedpanda_11383;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.Calendar;
import java.util.TimeZone;

public class NoteActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
    }

    public void saveNote(View view) {
        //get note content
        EditText noteContent = (EditText)findViewById(R.id.txtNoteContent);
        String note = noteContent.getText().toString();
        //get time
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int hrs = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int sec = calendar.get(Calendar.SECOND);
        //format with leading zero's since int cannot hold leading zero's
        String timeOfNote = String.format("%02d:%02d:%02d", hrs,min,sec);
        Log.d("ContentCheck", "Note: " + note + "TimeOfnote: " + timeOfNote);
        //Create intent
        Intent noteIntent = new Intent(this, MainActivity.class);
        //put extras
        noteIntent.putExtra("NoteContent", note);
        noteIntent.putExtra("TimeOfNote", timeOfNote);
        //set resultcode
        setResult(RESULT_OK,noteIntent);
        finish();

    }

    public void cancelNote(View view) {
        Intent returnIntent = new Intent();
        setResult(RESULT_CANCELED, returnIntent);
        finish();
    }
}
