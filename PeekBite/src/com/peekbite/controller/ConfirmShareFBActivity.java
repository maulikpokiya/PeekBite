package com.peekbite.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.application.peekbite.R;

public class ConfirmShareFBActivity extends Activity{
	
	private TextView mConfirmMessage;
	private Button mConfirmButton;
	private String rest;
	private String dish;
	
	private final static String TAG = "ConfirmShareFBActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "in ConfirmShareFBActivity");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirm_share);
		
		mConfirmMessage = (TextView)findViewById(R.id.confirm_message);	
		mConfirmButton = (Button)findViewById(R.id.confirm_button);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		
		if(bundle!=null) {
			Log.i(TAG, "rest and dish are not NULL. Get Extras here");//test
			rest = bundle.getString("restName");
			dish = bundle.getString("dishName");
			mConfirmMessage.setText("Do you really want to share the post: \"I like "+ dish +" at "+ rest +".\"?");
		}
		
		mConfirmButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i(TAG, "click confirm share on FB button");//test
				
				Toast.makeText(ConfirmShareFBActivity.this, "Coming Soon!", Toast.LENGTH_SHORT).show();
			}
		});
		
	}
}
