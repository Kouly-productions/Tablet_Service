package com.example.tabletservice;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class LoanHistory extends AppCompatActivity {
    private LinearLayout loansContainer;
    private LoanManager loanManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_history);

        loansContainer = findViewById(R.id.loansContainer);
        loanManager = new LoanManager(this);

        displayLoans();
    }

    private void displayLoans() {
        ArrayList<TabletData> loans = loanManager.getLoans();

        if (loans.isEmpty()) {
            TextView emptyText = new TextView(this);
            emptyText.setText("No loans found");
            emptyText.setTextSize(18);
            emptyText.setPadding(16, 16, 16, 16);
            emptyText.setGravity(android.view.Gravity.CENTER);
            loansContainer.addView(emptyText);
            return;
        }

        for (TabletData loan : loans) {
            addLoanView(loan);
        }
    }

    private void addLoanView(TabletData loan) {
        // Create CardView container
        CardView cardView = new CardView(this);
        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        cardParams.setMargins(0, 0, 0, 16); // Add margin bottom
        cardView.setLayoutParams(cardParams);
        cardView.setCardElevation(4);
        cardView.setRadius(8);

        // Create content container
        LinearLayout contentLayout = new LinearLayout(this);
        contentLayout.setOrientation(LinearLayout.VERTICAL);
        contentLayout.setPadding(32, 16, 32, 16);

        // Add loan details
        String[] details = {
                "Brand: " + loan.getBrand(),
                "Cable: " + loan.getCable(),
                "Name: " + loan.getUserName(),
                "Phone: " + loan.getUserPhone(),
                "Email: " + loan.getUserEmail(),
                "Time: " + loan.getTime()
        };

        for (String detail : details) {
            TextView textView = new TextView(this);
            textView.setText(detail);
            textView.setTextSize(16);
            textView.setPadding(0, 4, 0, 4);
            contentLayout.addView(textView);
        }

        // Add a divider
        View divider = new View(this);
        LinearLayout.LayoutParams dividerParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                2
        );
        dividerParams.setMargins(0, 8, 0, 8);
        divider.setLayoutParams(dividerParams);
        divider.setBackgroundColor(Color.LTGRAY);

        // Assemble the views
        cardView.addView(contentLayout);
        loansContainer.addView(cardView);
        loansContainer.addView(divider);
    }
}