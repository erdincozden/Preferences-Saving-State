package com.example.erdinc.chptr11;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Resources resources;
    private TextView textView = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        PreferenceManager.setDefaultValues(this, R.xml.main, false);
        resources = getResources();
        textView = (TextView) findViewById(R.id.text1);
        setOptionText();
    }

    private void setOptionText() {
        String valuesText;
        SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);

        String flight_option=prefs.getString(resources.getString(R.string.flight_sort_option),
                                    resources.getString(R.string.flight_sort_option_default_value));

        String [] optionEntries=resources.getStringArray(R.array.flight_sort_options);
        valuesText="option value is "+flight_option+" ("+optionEntries[Integer.parseInt(flight_option)]+")";

        String[] optionValues=resources.getStringArray(R.array.flight_sort_options_values);

        int index=0;
        for(;index<optionValues.length;index++){
            if(optionValues[index].equals(flight_option)){
                break;
            }
        }
        if(index<optionValues.length){
            valuesText="\n ...or the other way to get it ( "+optionEntries[index]+")";
        }
        valuesText += "\nShow Airline: " +
                prefs.getBoolean("show_airline_column_pref", false);

        valuesText += "\nAlert email address: " +
                prefs.getString("alert_email_address", "");

        valuesText += "\nFavorite pizza toppings: " +
                prefs.getStringSet("pizza_toppings", null);
        textView.setText(valuesText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent().setClass(this, MainPreferenceActivity.class);
            this.startActivityForResult(intent, 0);
        }
        return true;
    }
}
