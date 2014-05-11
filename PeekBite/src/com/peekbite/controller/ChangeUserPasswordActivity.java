package com.peekbite.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.application.peekbite.R;

public class ChangeUserPasswordActivity extends Activity {
	
	private int uid = -1;//default value. Invalid value for normal user id.
	private EditText mOldPassword;
	private EditText mNewPassword;
	private EditText mConfirmNewPw;
	private Button mConfirmChange;
	
	private final static String TAG = "ChangeUserPasswordActivity";//test
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		
		if(bundle!=null) {
			Log.i(TAG, "bundle is not null. Get Extras here");//test
			uid = bundle.getInt("userID");
		}
		
		mOldPassword = (EditText)findViewById(R.id.old_pw_editview);
		mNewPassword = (EditText)findViewById(R.id.new_pw_first_editview);
		mConfirmNewPw = (EditText)findViewById(R.id.new_pw_confirm_editview);
		mConfirmChange = (Button)findViewById(R.id.confirm_change_password_button);
		
		mConfirmChange.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean pass = checkOldPw();
				if(pass) {
					boolean finalPass = checkNewPw();
					if(finalPass) {
						changePassword();
					}
					else {
						Toast.makeText(getApplicationContext(), "Two inputs for new password are not same. Please check it", Toast.LENGTH_SHORT).show();
					}
				}
				else {
					Toast.makeText(getApplicationContext(), "The old password input is wrong. Please input it again", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
			
	}
	
	public boolean checkOldPw() {
		return true;
	}
	
	public boolean checkNewPw() {
		return true;
	}
	
	public void changePassword() {
		
	}

}
