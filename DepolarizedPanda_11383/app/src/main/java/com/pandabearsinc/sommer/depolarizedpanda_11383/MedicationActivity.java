package com.pandabearsinc.sommer.depolarizedpanda_11383;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MedicationActivity extends ListActivity {
DatabaseHelper dbHelper = new DatabaseHelper(this);
    List<Medicine> listOfMedicine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_medication);
        ListView listView = (ListView)findViewById(android.R.id.list);
        listOfMedicine = dbHelper.getAllMedicines();
        ArrayAdapter<Medicine> medicineAdapter = new ArrayAdapter<Medicine>(this, android.R.layout.simple_expandable_list_item_1, listOfMedicine);
        listView.setAdapter(medicineAdapter);
        listView.setItemsCanFocus(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onListItemClick(final ListView l,final View v, final int position, long id){
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_layout, null);
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
                ad.setView(view);
                ad.setTitle("Choose amount of pills");
                ad.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Insert code HERE!
                        final EditText amount = (EditText)view.findViewById(R.id.dialogEditText);
                        String stringAmount = amount.getText().toString();
                        if(stringAmount.matches("")){
                            Toast.makeText(getApplicationContext(), "Please enter amount", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            int intAmount = Integer.parseInt(amount.getText().toString());
                            Medicine medicine = (Medicine) l.getAdapter().getItem(position);
                            int medicine_id = medicine.getId();
                            Log.d("MedicationDialog", "mg: " + intAmount + " id: " + medicine_id);

                            Intent intent = new Intent(getBaseContext(), MainActivity.class);
                            intent.putExtra("Amount", intAmount);
                            intent.putExtra("Id", medicine_id);
                            //set resultcode
                            startActivity(intent);
                        }
                    }
                });
                ad.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        }
                );
    ad.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.medication, menu);
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
 /*       public void saveMedication(View view) {
        //dummy string
        String result = "RITALIIIN";
        Intent returnIntent = new Intent(this, MainActivity.class);
        returnIntent.putExtra("result",result);
        setResult(RESULT_OK,returnIntent);
        startActivity(returnIntent);
    }*/

    //button Cancel to go back to MainActivity
    public void cancelMedication(View view) {
        Intent returnIntent = new Intent(this, MainActivity.class);
        setResult(RESULT_CANCELED, returnIntent);
        startActivity(returnIntent);

    }

    //button click to start Add New Medication Activity
    public void addNewMedication(View view) {
        Intent createNewMedicationIntent = new Intent(this, NewMedicationActivity.class);
        startActivity(createNewMedicationIntent);
    }

}
