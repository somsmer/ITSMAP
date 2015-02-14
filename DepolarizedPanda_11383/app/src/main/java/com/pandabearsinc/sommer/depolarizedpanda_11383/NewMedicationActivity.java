package com.pandabearsinc.sommer.depolarizedpanda_11383;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewMedicationActivity extends Activity{
    DatabaseHelper dbHelper = new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newmedication);
    }

    public void addMedicineToDB(View view) {
        final EditText medicineName = (EditText)findViewById(R.id.txtNewMedicationName);
        final EditText medicinePotency = (EditText)findViewById(R.id.txtNewMedicationMg);
        String _medicine = medicineName.getText().toString();
        String _potency = medicinePotency.getText().toString();

        if(_medicine.isEmpty() || _potency.isEmpty() )
        {
            Toast.makeText(this, "Please enter medicin name AND potency",
                    Toast.LENGTH_SHORT).show();
        }

        else
           {
        Medicine medicine = new Medicine(medicineName.getText().toString(), Double.parseDouble(medicinePotency.getText().toString()));
        dbHelper.addMedicine(medicine);
               Toast.makeText(this, "Your medication has been added",
                       Toast.LENGTH_SHORT).show();

               Intent returnIntent = new Intent(this, MedicationActivity.class);
               startActivity(returnIntent);

           }
    }

    public void cancelNewMedication(View view) {
        Intent returnIntent = new Intent(this, MedicationActivity.class);
        startActivity(returnIntent);
    }


}
