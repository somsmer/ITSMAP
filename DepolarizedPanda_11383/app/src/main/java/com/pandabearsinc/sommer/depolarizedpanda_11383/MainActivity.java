package com.pandabearsinc.sommer.depolarizedpanda_11383;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class MainActivity extends FragmentActivity implements DatePickerFragment.OnCompleteListener {
    static final int NOTE_REQUEST = 1;
    static final int PICK_MEDICATIONACTIVITY_REQUEST = 2;  // The request code
    static final String TAG = "MainActivity";
    EditText editText;
    String year, day, month;
    static String date;
    boolean errorFlag = false;
    String note ="";
    String timeOfnote="";
    int medicinAmount;
    int medicinId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.txtDate);
        Spinner spinner = (Spinner) findViewById(R.id.moodspinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.moodsArray, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        editText.requestFocus();

    }

    public String getSpinnerValue() {
        Spinner spinner = (Spinner) findViewById(R.id.moodspinner);
        String Spinnertext = spinner.getSelectedItem().toString();

        return Spinnertext;
    }
    public void startMedicationActivity(View view) {
        Intent medicationIntent = new Intent(this, MedicationActivity.class);
        startActivityForResult(medicationIntent, PICK_MEDICATIONACTIVITY_REQUEST);
    }

    public void startNoteActivity(View view) {
        Intent noteIntent = new Intent(this, NoteActivity.class);
        startActivityForResult(noteIntent, NOTE_REQUEST);
    }

    public void startHistoryActivity(View view) {
        Intent HistoryIntent = new Intent(this, ChooseHistoryActivity.class);
        startActivity(HistoryIntent);
    }

    //---------------SaveToDatabase BUTTON click------------------------//
    public void SaveToDatabase(View view) {
        //Date
        if(((Dates) this.getApplication()).getDate() ==null)
            {
            Toast.makeText(this, "Please enter date",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            String DB_date = ((Dates) this.getApplication()).db_getDate();
            Log.d(TAG +"SaveToDatabase_dateToBeSaved:",DB_date);

            //SPinner
            String MoodValue = getSpinnerValue();
            Log.d(TAG + "SaveToDatabase_Mood",MoodValue);

           //Medicine
        if(medicinId == 0 || medicinAmount == 0)

        {           Toast.makeText(this, "Please choose a medicine",
                Toast.LENGTH_SHORT).show();
        }
        else
        {
         //Excecute SaveInfoToDatabase Asynctask
        new SaveInfoToDatabase().execute(DB_date,MoodValue,note,Integer.toString(medicinAmount),Integer.toString(medicinId));
        }
      }
   }
    //---------------ASYNCTASK to save to DB------------------------//
    //AsyncTask<params, progress, returnType>
    class SaveInfoToDatabase extends AsyncTask<String, Void, Void> {
        protected Void doInBackground(String ... params) {
            //Get params passsed when excecuted
            String date = params[0];
            String mood = (params[1] !=null) ? params[1] : "";
            String note = (params[2] !=null) ? params[2] : "No note today";
            String medicinamount = (params[3] !=null) ?  params[3] : "1";
            String medicinId = (params[4] !=null) ?  params[4] : "";
            //convert amount and Id back to ints
            int int_medicinamount = Integer.parseInt(medicinamount);
            int int_medicinId = Integer.parseInt(medicinId);
            Log.d(TAG, "doInBackground: date: "+date+"mood: "+mood+"note"+note);
            DatabaseHelper databaseHelper = new DatabaseHelper(getBaseContext());

            Medicine medicine = databaseHelper.getMedicineFromId(int_medicinId);
            Registration registration =  new Registration(date,mood,note,int_medicinamount,medicine);
            databaseHelper.addRegistration(registration);

            errorFlag = false;
          //errorFlag = true;
            return null;
        }
        protected void onPostExecute(Void result) {

            if (errorFlag)
            {
                Log.e(TAG, "failed to save to DB");
                Log.d(TAG, "onPostExecute");
                //TODO:brugervenlig tekst i toasten
                Toast.makeText(getBaseContext(), "failed to save to DB, Please try again later",
                    Toast.LENGTH_SHORT).show();
            }
            else
            {  Log.d(TAG, "onPostExecute");
                //TODO:brugervenlig tekst i toasten
                Toast.makeText(getBaseContext(), "SAVED",
                        Toast.LENGTH_SHORT).show();

            }
        }
    }

    //---------------onActivityResult - Checks where answer came from------------------------//
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       //Toast is only for test
/*        Toast.makeText(this, "Requestcode is: " + requestCode,
                Toast.LENGTH_SHORT).show();*/
        Log.d(TAG, "Requestcode: " + requestCode);

        // Checks which request we're responding to

        // Checks which request we're responding to
        if (requestCode == PICK_MEDICATIONACTIVITY_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                int medicinAmount = data.getIntExtra("Amount", 0);
                int medicinId = data.getIntExtra("Id", 0);
                Log.d(TAG +"My medication amount is: ",Integer.toString(medicinAmount) +"id: " +medicinId);
               }
            else{
                Log.d(TAG, "User cancelled");
            }
        }
        // Checks which request we're responding to
        if (requestCode == NOTE_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                note = data.getStringExtra("NoteContent");
                timeOfnote = data.getStringExtra("TimeOfNote");
                Toast.makeText(this, "Note saved",
                        Toast.LENGTH_LONG).show();

            }
            else{
                Toast.makeText(this, "User cancelled",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }

    //-----------------------DatePicker------------------------//
    public void showDatePickerDialog(View view) {

        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker" );

    }
    //-----------------------Reply from DatePicker------------------------//
    public void onComplete(String selectedDay, String selectedmonth, String selectedyear, String mytag) {
        day = selectedDay;
        month = selectedmonth;
        year = selectedyear;

        date = day+"-"+month+"-"+year;
        //set date so we kan get it later.. not saved in global variable
       ((Dates) this.getApplication()).setDate(date);
       ((Dates) this.getApplication()).db_setDate(year,day,month);
        editText.setText(date);
    }

    @Override
    public void onResume(){
        super.onResume();

        date = ((Dates) this.getApplication()).getDate();
        if(date != null)

        {  editText.setText(date);}

        Intent intent = getIntent();

        int ma = intent.getIntExtra("Amount", 0);
        int id = intent.getIntExtra("Id", 0);
        medicinAmount = (ma !=0) ? ma : 0;
        medicinId=(id !=0) ? id : 0;
    }

  }