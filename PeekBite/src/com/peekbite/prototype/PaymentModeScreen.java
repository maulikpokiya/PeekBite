package com.peekbite.prototype;

import com.application.peekbite.MainActivity;
import com.application.peekbite.R;
import com.facebook.Session;
import com.peekbite.model.TotalQuantity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class PaymentModeScreen extends Activity implements OnClickListener{
	TableRow withPeekRow, withCallWaiter, withCreditcard;
	ImageView backButton;
	String key = "";
	TextView logout_tv;
	LinearLayout type1Layout, type2Layout, type3Layout, type4Layout, type5Layout;
	private TotalQuantity tq;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.payment_screen);
		logout_tv = (TextView)findViewById(R.id.logout_tv);
		withPeekRow = (TableRow) findViewById(R.id.paywithpeek_row);
		withCallWaiter = (TableRow) findViewById(R.id.callwaiter_row);
		withCreditcard = (TableRow) findViewById(R.id.paywithcredit_row);
		
		tq=(TotalQuantity)getApplication();
		/**
		 * 
		 
		type1Layout = (LinearLayout) findViewById(R.id.type1layout);
		type2Layout = (LinearLayout) findViewById(R.id.type2layout);
		type3Layout = (LinearLayout) findViewById(R.id.type3layout);
		type4Layout = (LinearLayout) findViewById(R.id.type4layout);
		type5Layout = (LinearLayout) findViewById(R.id.type5layout);
		
		type1Layout.setOnClickListener(this);
		type2Layout.setOnClickListener(this);
		type3Layout.setOnClickListener(this);
		type4Layout.setOnClickListener(this);
		type5Layout.setOnClickListener(this);*/
		
		key = getIntent().getStringExtra("KEY");

		backButton = (ImageView) findViewById(R.id.backBtn);
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PaymentModeScreen.this, OrderScreen.class);
				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				//intent.putExtra("KEY", key);
				//startActivity(intent);
				
				PaymentModeScreen.this.setResult(RESULT_CANCELED,intent);
				PaymentModeScreen.this.finish();
			}
		});
		
		withPeekRow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PaymentModeScreen.this, PaymentType.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("TYPE", "PEEK");
				startActivity(intent);
			}
		});
		withCallWaiter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PaymentModeScreen.this, PaymentType.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("TYPE", "CALL");
				startActivity(intent);
			}
		});
		withCreditcard.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PaymentModeScreen.this, PaymentType.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("TYPE", "CREDIT");
				startActivity(intent);
			}
		});
		/**
		 * log out function
		 */
		logout_tv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Log out from Fb session
				Session session = Session.getActiveSession();
				
				 if (session != null) {

				        if (!session.isClosed()) {
				            session.closeAndClearTokenInformation();
				            //clear your preferences if saved
				        }
				    } 
				
				//clear the global variable totalQuantity to 0
				tq.setNumberofItems(0);
				Intent intent = new Intent(); 
				intent.setClass(PaymentModeScreen.this, MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				PaymentModeScreen.this.setResult(RESULT_CANCELED,intent);
				PaymentModeScreen.this.finish();
				Toast.makeText(PaymentModeScreen.this, "You logged out!", Toast.LENGTH_SHORT).show();
				 
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.type1layout:
			Intent intent1 = new Intent(PaymentModeScreen.this, ItemDetails.class);
			intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent1.putExtra("ITEMS", "0");
			startActivity(intent1);
			break;
		case R.id.type2layout:
			Intent intent2 = new Intent(PaymentModeScreen.this, OrderScreen.class);
			intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent2.putExtra("KEY", key);
			startActivity(intent2);
			break;
		case R.id.type3layout:
			Intent intent3 = new Intent(PaymentModeScreen.this, PaymentModeScreen.class);
			intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent3.putExtra("KEY", key);
			startActivity(intent3);
			break;
		case R.id.type4layout:
			Intent intent4 = new Intent(PaymentModeScreen.this, PaymentType.class);
			intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent4.putExtra("KEY", key);
			startActivity(intent4);
			break;
		case R.id.type5layout:
			Intent intent5 = new Intent(PaymentModeScreen.this, HomeScreenActivity.class);
			intent5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent5);
			break;

		default:
			break;
		}
	}
}
