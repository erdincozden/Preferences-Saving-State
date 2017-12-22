package com.example.erdinc.chptr11;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.List;
import java.util.Set;


public class MainPreferenceActivity extends PreferenceActivity {

    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.headers, target);
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        return true;
    }

    public static class Frag1 extends PreferenceFragment implements
            Preference.OnPreferenceChangeListener, SharedPreferences.OnSharedPreferenceChangeListener {

        private static EditTextPreference packNamePref;
        private static EditTextPreference emailPref;
        private static ListPreference listPreference;
        private static MultiSelectListPreference pizzaPreference;


        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.main);
            listPreference=(ListPreference)findPreference("flight_sort_option");

            packNamePref=(EditTextPreference)findPreference("package_name_preference");
            packNamePref.setSummary(packNamePref.getText());

            emailPref=(EditTextPreference)findPreference("alert_email_address");
            emailPref.setSummary(emailPref.getText());

            pizzaPreference=(MultiSelectListPreference)findPreference("pizza_toppings");

        }

        @Override
        public void onResume() {
            super.onResume();
            listPreference.setOnPreferenceChangeListener(this);
            setFlightOptionSummary(null);
            packNamePref.setOnPreferenceChangeListener(this);
            packNamePref.setSummary(packNamePref.getText());
            emailPref.setOnPreferenceChangeListener(this);
            emailPref.setSummary(emailPref.getText());
            pizzaPreference.setOnPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            listPreference.setOnPreferenceChangeListener(null);
            packNamePref.setOnPreferenceChangeListener(null);
            emailPref.setOnPreferenceChangeListener(null);
            pizzaPreference.setOnPreferenceChangeListener(null);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if("package_name_preference".equals(key)){
                packNamePref.setSummary(packNamePref.getText());
            }else if("alert_email_address".equals(key)){
                emailPref.setSummary(emailPref.getText());
            }else if("flight_sort_option".equals(key)){
                setFlightOptionSummary(listPreference.getValue());
            }
        }

        private void setFlightOptionSummary(String value) {
            String setTo=value;
            if(setTo==null){
                setTo=listPreference.getValue();
            }
            String [] optionEntries=this.getResources().getStringArray(R.array.flight_sort_options);
            listPreference.setSummary("Currently is set to "+optionEntries[listPreference.findIndexOfValue(setTo)]);

        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            final String key=preference.getKey();
            if("package_name_preference".equals(key)){
                packNamePref.setSummary(newValue.toString());
            }else if("alert_email_address".equals(key)){
                emailPref.setSummary(newValue.toString());
            }else if("flight_sort_option".equals(key)){
                setFlightOptionSummary(newValue.toString());
            }else if("pizza_toppings".equals(key)){
                if(((Set<String>) newValue).size()>4){
                    Toast.makeText(getActivity(),"Too many toppings.No more than 4",Toast.LENGTH_LONG).show();
                    return false;
                }
            }
            return true;

        }
    }

    public static class Frag2 extends PreferenceFragment{
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.frag2);
        }
    }
}
