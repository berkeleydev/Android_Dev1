package com.timurb.mobsy;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class myMenu extends Activity {

	//MediaPlayer mpButtonClick;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		//set up the button sound
		final MediaPlayer mpButtonClick = MediaPlayer.create(this, R.raw.click);
		
		Button bFreeBrowse = (Button) findViewById(R.id.button1);
		bFreeBrowse.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent ("com.timurb.mobsy.FREEBROWSE"));
				mpButtonClick.start();
			}
		});
		/*
		Button bPredefTour = (Button) findViewById(R.id.button2);
		bPredefTour.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent ("com.timurb.mobsy.TUTORIALONE"));
				mpButtonClick.start();
			}
		});*/
		Button mapBrowsingMode = (Button) findViewById(R.id.button3);
		mapBrowsingMode.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent ("com.timurb.mobsy.MAPTRACKER"));
				mpButtonClick.start();
			}
		});
		
	}
	

}
