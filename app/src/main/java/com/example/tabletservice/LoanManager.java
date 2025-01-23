package com.example.tabletservice;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class LoanManager {
    private static final String PREF_NAME = "TabletLoans";
    private static final String LOAN_KEY = "loans";
    private final SharedPreferences preferences;
    private final Gson gson;

    public LoanManager(Context context)
    {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void saveLoan(ArrayList<TabletData> loans)
    {
        String jsonLoans = gson.toJson(loans);
        preferences.edit().putString(LOAN_KEY, jsonLoans).apply();
    }

    public ArrayList<TabletData> getLoans()
    {
        String jsonLoans = preferences.getString(LOAN_KEY, "");
        if (jsonLoans.isEmpty())
        {
            return new ArrayList<>();
        }
        Type type = new TypeToken<ArrayList<TabletData>>(){}.getType();
        return gson.fromJson(jsonLoans, type);
    }
}
