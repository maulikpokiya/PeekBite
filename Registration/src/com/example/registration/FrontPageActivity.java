package com.example.registration;

import com.example.Demo.CaptureActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.app.Activity;
import android.content.Intent;

public class FrontPageActivity extends Activity {
	
	private ImageButton scanQRcodeButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_frontpage);
		setTitle("Front Page");
		
		scanQRcodeButton = (ImageButton)findViewById(R.id.QRIconImageButton);
		scanQRcodeButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				scanQRCode();
				
			}
		});
		
		
	}
	
	
	
	public void scanQRCode() {

		Intent intent = new Intent();
		intent.setClass(this, CaptureActivity.class);
		startActivity(intent);
	}
}
