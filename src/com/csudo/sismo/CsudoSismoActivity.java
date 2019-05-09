package com.csudo.sismo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;

public class CsudoSismoActivity extends Activity implements OnChronometerTickListener{
	Chronometer chronometer;
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_csudo_sismo);
		
		chronometer = (Chronometer)findViewById(R.id.chronometer1);
	
		chronometer.setOnChronometerTickListener(this);
		
		chronometer.start();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.csudo_sismo, menu);
		return true;
	}
	
	@Override
	public void onChronometerTick(Chronometer chronometer) {
		// TODO Auto-generated method stub
		CharSequence text = chronometer.getText();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		Date start = null,end = null;
		
		try {
			start = sdf.parse(text.toString());
			end   = sdf.parse("00:01");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		long difference = end.getTime() - start.getTime();
		
		if (start.getMinutes() > end.getMinutes()){
					
			chronometer.stop();
			
            //Llamada a BuscarSismoActivity
			Intent intent = new Intent(CsudoSismoActivity.this, BuscarSismoActivity.class);
    		
    		startActivity(intent);
        }
		
	}

}
/*
 * 
 *
 * 
 * <ImageButton
        android:id="@+id/BtnPedir"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_centerHorizontal="true"
        android:src="@drawable/buttonsismo" />*/