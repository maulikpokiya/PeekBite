package com.peekbite.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.application.peekbite.R;

public class MyProfileActivity extends Activity {
	
	//add
	private int uid;
	private String uname;
	private TextView mUserName;
	private Button mChangePwButton;
	//add ends
	
	private final static String TAG = "MyProfileActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_profile);
		
		//add
		mUserName = (TextView)findViewById(R.id.profile_username_text);
		mChangePwButton = (Button)findViewById(R.id.profile_change_password_button);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		
		if(bundle!=null) {
			Log.i(TAG, "uid and uname bundle is not NULL. Get Extras here");//test
			uid = bundle.getInt("userID");
			uname = bundle.getString("userName");
		}
		else {
			uid = -1;
			uname = null;
		}
		
		mUserName.setText(uname);
		mChangePwButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				changePassword();
			}
		});
				
		//add ends
		
	}
	
	//add
	public void changePassword() {
		Intent intent = new Intent(this, ChangeUserPasswordActivity.class);
		intent.putExtra("userID", uid);
		startActivity(intent);
	}
	//add ends
	
	
}

