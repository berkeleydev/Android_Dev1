package com.timurb.mobsy;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class tutorialOne extends Activity {
	
	TextView textOut;
	EditText getInput;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tutorial1);
		
		textOut = (TextView) findViewById(R.id.tvGetInput);
		getInput = (EditText) findViewById(R.id.etInput);
		//Button ok = (Button) findViweById.(R.id.bOK);
		

	}

	
}
