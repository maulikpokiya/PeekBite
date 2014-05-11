package com.peekbite.prototype;

import com.application.peekbite.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class PaymentType extends Activity implements OnClickListener{
	Button  payButton;
	EditText fnameEditText, cardnumberEditText, cwcEditText, zipEditText;
	LinearLayout peekbiteLayout, callwaiterLayout, creditcardLayout;
	String key, type = "";
	ImageView backButton;
	String fname, cardnum, cwc, zip = "";
	LinearLayout type1Layout, type2Layout, type3Layout, type4Layout, type5Layout;
	private CountDownTimer time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.payment_type);
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
		type = getIntent().getStringExtra("TYPE");
		
		peekbiteLayout = (LinearLayout) findViewById(R.id.peekbite);
		callwaiterLayout = (LinearLayout) findViewById(R.id.callwaiter);
		creditcardLayout = (LinearLayout) findViewById(R.id.paywithcredit);
		
		fnameEditText = (EditText) findViewById(R.id.fnameedttxt);
		cardnumberEditText = (EditText) findViewById(R.id.cardnumedttxt);
		cwcEditText = (EditText) findViewById(R.id.cwcedttxt);
		zipEditText = (EditText) findViewById(R.id.zipedttxt);
		
		/*if (type.equals("PEEK")) {
			peekbiteLayout.setVisibility(View.VISIBLE);
			callwaiterLayout.setVisibility(View.GONE);
			creditcardLayout.setVisibility(View.GONE); 
		 else if (type.equals("CALL")) {
			peekbiteLayout.setVisibility(View.GONE);
			callwaiterLayout.setVisibility(View.VISIBLE);
			creditcardLayout.setVisibility(View.GONE);
		} else if (type.equals("CREDIT")) {
			peekbiteLayout.setVisibility(View.GONE);
			callwaiterLayout.setVisibility(View.GONE);
			creditcardLayout.setVisibility(View.VISIBLE);
		}**/

		backButton = (ImageView) findViewById(R.id.backBtn);
		payButton = (Button) findViewById(R.id.paybtn);
		
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PaymentType.this, PaymentModeScreen.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("KEY", key);
				startActivity(intent);
			}
		});
		
		payButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				fname = fnameEditText.getEditableText().toString().trim();
				cardnum = cardnumberEditText.getEditableText().toString().trim();
				cwc = cwcEditText.getEditableText().toString().trim();
				zip = zipEditText.getEditableText().toString().trim();
				
				if (fname.equals("")) {
					DialogClass.createSessionAlertDialog(PaymentType.this, "Please enter Firstname.", false);
				} else if (fname.equals("")){
					DialogClass.createSessionAlertDialog(PaymentType.this, "Please enter Cardnumber.", false);
				}  else if (fname.equals("")){
					DialogClass.createSessionAlertDialog(PaymentType.this, "Please enter Cwcnumber.", false);
				}  else if (fname.equals("")){
					DialogClass.createSessionAlertDialog(PaymentType.this, "Please enter Zipcode.", false);
				} else {
					DialogClass.createDAlertDialog(PaymentType.this, "Payment Successful.", false);
				}
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.type1layout:
			Intent intent1 = new Intent(PaymentType.this, ItemDetails.class);
			intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent1.putExtra("ITEMS", "0");
			startActivity(intent1);
			break;
		case R.id.type2layout:
			Intent intent2 = new Intent(PaymentType.this, OrderScreen.class);
			intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent2.putExtra("KEY", key);
			startActivity(intent2);
			break;
		case R.id.type3layout:
			Intent intent3 = new Intent(PaymentType.this, PaymentModeScreen.class);
			intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent3.putExtra("KEY", key);
			startActivity(intent3);
			break;
		case R.id.type4layout:
			Intent intent4 = new Intent(PaymentType.this, PaymentType.class);
			intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent4.putExtra("KEY", key);
			startActivity(intent4);
			break;
		case R.id.type5layout:
			Intent intent5 = new Intent(PaymentType.this, HomeScreenActivity.class);
			intent5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent5);
			break;

		default:
			break;
		}
	}
}
