package com.peekbite.prototype;

import com.application.peekbite.MainActivity;
import com.application.peekbite.R;
import com.facebook.Session;
import com.peekbite.model.TotalQuantity;
import com.peekbite.registration.FrontPageActivity;

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
import android.widget.TextView;
import android.widget.Toast;

public class PaymentType extends Activity implements OnClickListener{
	Button  payButton;
	EditText fnameEditText, cardnumberEditText, cwcEditText, zipEditText;
	LinearLayout peekbiteLayout, callwaiterLayout, creditcardLayout;
	String key, type = "";
	ImageView backButton;
	String fname, cardnum, cwc, zip = "";
	LinearLayout type1Layout, type2Layout, type3Layout, type4Layout, type5Layout;
	private CountDownTimer time;
	private TextView logout_tv;
	private TotalQuantity tq;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.payment_type);
		
		tq=(TotalQuantity)getApplication();
		
		logout_tv = (TextView) findViewById(R.id.logout_tv);
		
		/**
		 * log out function - logout from peekbite as well as fb 
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
				intent.setClass(PaymentType.this, MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				PaymentType.this.setResult(RESULT_CANCELED,intent);
				PaymentType.this.finish();
				Toast.makeText(PaymentType.this, "You logged out!", Toast.LENGTH_SHORT).show();
			}
		});
		
		
		key = getIntent().getStringExtra("KEY");
		type = getIntent().getStringExtra("TYPE");
		
		peekbiteLayout = (LinearLayout) findViewById(R.id.peekbite);
		callwaiterLayout = (LinearLayout) findViewById(R.id.callwaiter);
		creditcardLayout = (LinearLayout) findViewById(R.id.paywithcredit);
		
		fnameEditText = (EditText) findViewById(R.id.fnameedttxt);
		cardnumberEditText = (EditText) findViewById(R.id.cardnumedttxt);
		cwcEditText = (EditText) findViewById(R.id.cwcedttxt);
		zipEditText = (EditText) findViewById(R.id.zipedttxt);

		backButton = (ImageView) findViewById(R.id.backBtn);
		payButton = (Button) findViewById(R.id.paybtn);
		
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PaymentType.this, FrontPageActivity.class);
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
