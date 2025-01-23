package com.example.tabletservice;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.LinearLayout;
import android.app.DatePickerDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.view.LayoutInflater;

public class AdminLoggedIn extends AppCompatActivity {
    private LoanManager loanManager;
    private ArrayList<TabletData> allLoans; // Original full list
    private ArrayList<TabletData> filteredLoans; // Filtered list
    private LoanAdapter adapter;
    private ListView loanListView;
    private Spinner brandSpinner, cableSpinner;
    private Button startDateBtn, endDateBtn, filterBtn, resetBtn;
    private Date startDate = null;
    private Date endDate = null;

    private class LoanAdapter extends ArrayAdapter<String> {
        public LoanAdapter(Context context, ArrayList<String> loans) {
            super(context, R.layout.activity_admin_logged_in, loans);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.activity_admin_logged_in, parent, false);
            }

            TextView loanInfo = convertView.findViewById(R.id.loanInfo);
            Button deleteButton = convertView.findViewById(R.id.deleteButton);

            loanInfo.setText(getItem(position));

            deleteButton.setOnClickListener(v -> {
                TabletData loanToRemove = filteredLoans.get(position);

                new androidx.appcompat.app.AlertDialog.Builder(AdminLoggedIn.this)
                        .setTitle("Return Tablet")
                        .setMessage("Mark this tablet as returned?\n\nBrand: " + loanToRemove.getBrand() +
                                "\nUser: " + loanToRemove.getUserName())
                        .setPositiveButton("Yes", (dialog, which) -> {
                            allLoans.remove(loanToRemove);
                            filteredLoans.remove(position);
                            loanManager.saveLoan(allLoans);
                            updateListView();
                            Toast.makeText(AdminLoggedIn.this, "Tablet marked as returned",
                                    Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("No", null)
                        .show();
            });

            return convertView;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Layout creation
        //_________________________________________________________________________________________
        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setPadding(16, 16, 16, 16);

        // Add filter controls
        brandSpinner = new Spinner(this);
        cableSpinner = new Spinner(this);
        startDateBtn = new Button(this);
        endDateBtn = new Button(this);
        filterBtn = new Button(this);
        resetBtn = new Button(this);
        loanListView = new ListView(this);

        // Setup UI elements
        startDateBtn.setText("Start Date");
        endDateBtn.setText("End Date");
        filterBtn.setText("Apply Filters");
        resetBtn.setText("Reset Filters");

        // Add views to layout
        mainLayout.addView(brandSpinner);
        mainLayout.addView(cableSpinner);
        mainLayout.addView(startDateBtn);
        mainLayout.addView(endDateBtn);
        mainLayout.addView(filterBtn);
        mainLayout.addView(resetBtn);
        mainLayout.addView(loanListView);

        setContentView(mainLayout);

        // Initialize data
        loanManager = new LoanManager(this);
        allLoans = loanManager.getLoans();
        filteredLoans = new ArrayList<>(allLoans);

        // Setup spinners
        setupSpinners();

        // Setup list
        updateListView();

        // Setup click listeners
        setupClickListeners();
    }

    private void setupSpinners() {
        // Brand spinner
        ArrayAdapter<TabletData.Brand> brandAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, TabletData.Brand.values());
        brandSpinner.setAdapter(brandAdapter);

        // Cable spinner
        ArrayAdapter<TabletData.Cable> cableAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, TabletData.Cable.values());
        cableSpinner.setAdapter(cableAdapter);
    }

    private void updateListView() {
        ArrayList<String> displayList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        for (TabletData loan : filteredLoans) {
            displayList.add(String.format("Brand: %s\nCable: %s\nUser: %s\nTime: %s",
                    loan.getBrand(), loan.getCable(), loan.getUserName(), loan.getTime()));
        }

        adapter = new LoanAdapter(this, displayList);
        loanListView.setAdapter(adapter);
    }

    private void setupClickListeners() {
        // Date picker dialogs
        startDateBtn.setOnClickListener(v -> showDatePicker(true));
        endDateBtn.setOnClickListener(v -> showDatePicker(false));

        // Filter button
        filterBtn.setOnClickListener(v -> applyFilters());

        // Reset button
        resetBtn.setOnClickListener(v -> {
            filteredLoans = new ArrayList<>(allLoans);
            startDate = null;
            endDate = null;
            brandSpinner.setSelection(0);
            cableSpinner.setSelection(0);
            updateListView();
        });

        // Long press to mark as returned with confirmation dialog
        loanListView.setOnItemLongClickListener((parent, view, position, id) -> {
            TabletData loanToRemove = filteredLoans.get(position);

            // Create confirmation dialog
            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Return Tablet")
                    .setMessage("Mark this tablet as returned?\n\nBrand: " + loanToRemove.getBrand() +
                            "\nUser: " + loanToRemove.getUserName())
                    .setPositiveButton("Yes", (dialog, which) -> {
                        // Remove from both lists
                        allLoans.remove(loanToRemove);
                        filteredLoans.remove(position);

                        // Save changes
                        loanManager.saveLoan(allLoans);

                        // Update view
                        updateListView();

                        // Show confirmation
                        Toast.makeText(this, "Tablet marked as returned", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();

            return true;
        });
    }

    private void showDatePicker(boolean isStartDate) {
        Calendar cal = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    Calendar selected = Calendar.getInstance();
                    selected.set(year, month, dayOfMonth);
                    if (isStartDate) {
                        startDate = selected.getTime();
                        startDateBtn.setText("Start: " + dayOfMonth + "/" + (month + 1) + "/" + year);
                    } else {
                        endDate = selected.getTime();
                        endDateBtn.setText("End: " + dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void applyFilters() {
        filteredLoans = new ArrayList<>();
        TabletData.Brand selectedBrand = (TabletData.Brand) brandSpinner.getSelectedItem();
        TabletData.Cable selectedCable = (TabletData.Cable) cableSpinner.getSelectedItem();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        for (TabletData loan : allLoans) {
            boolean matches = true;

            // Check brand filter
            if (selectedBrand != null && loan.getBrand() != selectedBrand) {
                matches = false;
            }

            // Check cable filter
            if (matches && selectedCable != null && loan.getCable() != selectedCable) {
                matches = false;
            }

            // Check date range
            if (matches && (startDate != null || endDate != null)) {
                try {
                    Date loanDate = sdf.parse(loan.getTime());
                    if (startDate != null && loanDate.before(startDate)) {
                        matches = false;
                    }
                    if (endDate != null && loanDate.after(endDate)) {
                        matches = false;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            if (matches) {
                filteredLoans.add(loan);
            }
        }

        updateListView();
    }
}