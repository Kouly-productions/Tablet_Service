package com.example.tabletservice;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class LoanTabletForm extends AppCompatActivity {
    private Button btnSubmit;
    private Spinner brandSpinner, cableSpinner;
    private EditText userName, userPhone, userEmail;
    private LoanManager loanManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_tablet_form);

        loanManager = new LoanManager(this);

        brandSpinner = findViewById(R.id.spinner_brand);
        cableSpinner = findViewById(R.id.spinner_cable);
        btnSubmit = findViewById(R.id.btn_submit);
        userName = findViewById(R.id.edit_username);
        userPhone = findViewById(R.id.edit_phone);
        userEmail = findViewById(R.id.edit_email);

        // Populate spinners
        ArrayAdapter<TabletData.Brand> brandAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                TabletData.Brand.values());
        brandAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<TabletData.Cable> cableAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                TabletData.Cable.values());
        cableAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        brandSpinner.setAdapter(brandAdapter);
        cableSpinner.setAdapter(cableAdapter);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitData();
            }
        });
    }

    private void submitData()
    {
        if (checkFields())
        {
            saveLoan();
            Toast.makeText(this, "Loan saved successfully", Toast.LENGTH_SHORT).show();
            clearFields();
        }
        else
        {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkFields()
    {
        return !userName.getText().toString().isEmpty() &&
                !userPhone.getText().toString().isEmpty() &&
                !userEmail.getText().toString().isEmpty();
    }

    private void saveLoan()
    {
        TabletData.Brand brand = (TabletData.Brand) brandSpinner.getSelectedItem();
        TabletData.Cable cable = (TabletData.Cable) cableSpinner.getSelectedItem();
        String name = userName.getText().toString();
        String phone = userPhone.getText().toString();
        String email = userEmail.getText().toString();
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        TabletData newLoan = new TabletData(brand, cable, name, phone, email, currentTime);

        // Load existing loans
        ArrayList<TabletData> existingLoans = loadExistingLoans();
        // Add new loan
        existingLoans.add(newLoan);
        // Save list again
        loanManager.saveLoan(existingLoans);

    }

    private ArrayList<TabletData> loadExistingLoans() {
        return loanManager.getLoans();
    }

    private void clearFields() {
        userName.setText("");
        userPhone.setText("");
        userEmail.setText("");
        brandSpinner.setSelection(0);
        cableSpinner.setSelection(0);
    }
}