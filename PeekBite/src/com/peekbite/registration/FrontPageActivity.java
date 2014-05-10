package com.peekbite.registration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.application.peekbite.R;
import com.peekbite.controller.MyOrderHistoryActivity;
import com.peekbite.controller.MyProfileActivity;
import com.peekbite.prototype.HomeScreenActivity;
import com.peekbite.qrcode.CaptureActivity;

public class FrontPageActivity extends Activity {
	
	private ImageButton scanQRcodeButton;
	//add by Nan at 7:42 pm, April 27th
	private TextView mMyProfile;
	private TextView mMyOrderHistory;
	
	private final static String TAG = "FrontPageActivity";
	//add ENDs

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

		//add by Nan at 7:42 pm, April 27th
		mMyProfile = (TextView)findViewById(R.id.myProfileLink);
		mMyProfile.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startMyProfile();
			}
		});

		mMyOrderHistory = (TextView)findViewById(R.id.myOrderHistoryLink);
		mMyOrderHistory.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startMyOrders();
			}
		});
		//add ENDs
	}

	public void scanQRCode() {

		Log.i(TAG, "in scanQRCode()");//test
		
		Intent intent = new Intent();
		intent.setClass(this, CaptureActivity.class);
//		intent.setClass(this, HomeScreenActivity.class);
		startActivity(intent);
	}
	
	//add by Nan at 7:42 pm, April 27th
	public void startMyProfile() {
		Log.i(TAG, "in startMyProfile()");//test
		
		Intent intent = new Intent(this, MyProfileActivity.class);
		startActivity(intent);
	}
	
	public void startMyOrders() {
		Log.i(TAG, "in startMyOrders()");//test
		
		Intent intent = new Intent(this, MyOrderHistoryActivity.class);
		startActivity(intent);
	}
	//add ENDs
}
