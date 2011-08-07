package com.halodev.EncryptStringTest;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class EncryptStringTest extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final SharedPreferences en_prefs = new ObscuredSharedPreferences( 
        	    this, this.getSharedPreferences("data", Context.MODE_PRIVATE) );
        final TextView tv = (TextView)this.findViewById(R.id.key);
        String key=en_prefs.getString("key","0");
        if(!key.equals("true")||key.equals("0")){
        	//DRM
        	//if ok
        	en_prefs.edit().putString("key","true").commit();
        	//Run main
        	tv.setText("DRM ok, Run main");
        }else{
        	//Run main
        	tv.setText("Run main only");
        }
    }
}