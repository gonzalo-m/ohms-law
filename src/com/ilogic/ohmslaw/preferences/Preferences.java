package com.ilogic.ohmslaw.preferences;

import com.ilogic.ohmslaw.R;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceClickListener;
import android.view.Menu;
import android.widget.Toast;

public class Preferences extends PreferenceActivity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		
		// Get the custom preference
        Preference feedbackPref = findPreference("feedbackPref");
        feedbackPref.setOnPreferenceClickListener(new OnPreferenceClickListener() {
        	 
            @Override
			public boolean onPreferenceClick(Preference preference) {
                //Toast.makeText(getBaseContext(),"The feedback preference has been clicked",Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=PGvq3jp5dec")));
                      
            	/* List more apps to complete action
//            	final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
//            	emailIntent.setType("text/plain");
//            	emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"gmartz21@gmail.com"});
//            	emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "ss");
//            	emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "some text");
            	
                /* Create the Intent */
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SENDTO, 
                		Uri.fromParts("mailto","gmartz21@gmail.com", null));
               
                /* Fill it with Data */
                //emailIntent.setType("text/plain");
                //emailIntent.setData(Uri.parse("mailto:" + "gmartz21@gmail.com"));
                //emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"gmartz21@gmail.com"});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Ohm's Law " + getResources().getString(R.string.soft_version));
                //emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Text");

                /* Send it off to the Activity-Chooser */
                try {
                    startActivity(Intent.createChooser(emailIntent, "Complete action using"));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(Preferences.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
                
                //SharedPreferences customSharedPreference = getSharedPreferences("myCustomSharedPrefs", Activity.MODE_PRIVATE);
                //SharedPreferences.Editor editor = customSharedPreference.edit();
                //editor.putString("myCustomPref","The preference has been clicked");
                //editor.commit();
                
                return true;
            }
        });
        
        Preference rateAppPref = findPreference("rateAppPref");
        rateAppPref.setOnPreferenceClickListener(new OnPreferenceClickListener() {
        	 
            @Override
			public boolean onPreferenceClick(Preference preference) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("market://details?id=" + getPackageName()));
                    startActivity(intent);
                    //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=5HPXvE1g_go&feature=g-logo")));
                    
                    return true;
            }
        });
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		finish();
		return super.onPrepareOptionsMenu(menu);
	}
}