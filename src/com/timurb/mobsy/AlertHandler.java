package com.timurb.mobsy;

import android.R.string;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.widget.TextView;

public class AlertHandler { 

	
	
	static void ShowAlert (final Context localcontext, double dist, final String description) {
		
		final double dist_threshold = 5.0;
		
		if(dist < dist_threshold) {   
	        // prepare the alert box
		    //final Context localcontext = this;
	        AlertDialog.Builder alertbox = new AlertDialog.Builder(localcontext);
	        
	        // set the message to display
	        alertbox.setMessage("You're approaching a sightseeing item! Do you want more info?");

	        // add a neutral button to the alert box and assign a click listener
	        alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {

	            // click listener on the alert box
	            public void onClick(DialogInterface arg0, int arg1) {
	                // the button was clicked
	            	AlertDialog.Builder Dialog = new AlertDialog.Builder(localcontext);
	            	Dialog.setTitle("Nearby Site");
	            	Dialog.setMessage(description);
	            	AlertDialog dial = Dialog.show();
	            	TextView messageView = (TextView)dial.findViewById(android.R.id.message);
	            	messageView.setGravity(Gravity.CENTER);
	            }
	        });

	        // show it
	        alertbox.show();
	}
	
    }
}
