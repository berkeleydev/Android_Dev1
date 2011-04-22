package com.timurb.mobsy;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

public class myMain extends Activity {

	MediaPlayer mp3splash;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		mp3splash = MediaPlayer.create(this, R.raw.begin);
		mp3splash.start();
		
		Thread logoTimer = new Thread(){
			public void run(){
				try{
					int logoTimer = 0;
					while(logoTimer <1000){
						sleep(100);
						logoTimer = logoTimer +100;
					}
					startActivity(new Intent("com.timurb.mobsy.CLEARSCREEN"));
				} 
				catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
				finally{
					finish();
				}
			}
		};
		
		
		logoTimer.start();
		
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mp3splash.release();
	}

	
	
}
