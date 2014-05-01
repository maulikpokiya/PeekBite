package com.peekbite.prototype;

import com.application.peekbite.MainActivity;
import com.application.peekbite.R;
import com.peekbite.model.TotalQuantity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ItemDetails extends Activity implements OnClickListener {
	TextView logoutTV, numberofItemsTextView,costTextView,nameTextView;
	ImageView dishImageView;
	int numberofItems = 0;
	private TotalQuantity tq;
	LinearLayout type1Layout, type2Layout, type3Layout, type4Layout, type5Layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_details);
		
		logoutTV = (TextView) findViewById(R.id.logoutTV);
		numberofItemsTextView = (TextView) findViewById(R.id.numberof_itemsTxt);
		costTextView = (TextView)findViewById(R.id.dishcostTextView);
		nameTextView = (TextView)findViewById(R.id.dishnameTextView);
		dishImageView = (ImageView)findViewById(R.id.dishImageView);
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
		type5Layout.setOnClickListener(this); */
		
		numberofItems = getIntent().getIntExtra("ITEMS", 0);
		tq=(TotalQuantity)getApplication();
		numberofItemsTextView.setText(tq.getNumberofItems() + "  Items");
		
		/**
		 * LOG OUT FUNCTION
		 */
		logoutTV.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 
				Intent intent = new Intent(); 
				intent.setClass(ItemDetails.this, MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  //注意本行的FLAG设置
				startActivity(intent);
				finish();//关掉自己
				Toast.makeText(ItemDetails.this, "You logged out!", Toast.LENGTH_SHORT).show();
				 
			}
		});
		
		Intent intent = getIntent();
		String dishname = intent.getStringExtra("dishname");
		String dishcost = intent.getStringExtra("dishcost");
		int dishimageName = intent.getIntExtra("dishimage", 0);
		System.out.println("-------dish: "+dishname+"---"+dishcost+"---"+dishimageName);
		costTextView.setText("$"+dishcost);
		nameTextView.setText(dishname);
		dishImageView.setImageResource(dishimageName);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.type1layout:
			Intent intent1 = new Intent(ItemDetails.this, ItemDetails.class);
			intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent1.putExtra("ITEMS", numberofItems);
			startActivity(intent1);
			break;
		case R.id.type2layout:
			Intent intent2 = new Intent(ItemDetails.this, OrderScreen.class);
			intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent2.putExtra("KEY", "ItemDetails");
			startActivity(intent2);
			break;
		case R.id.type3layout:
			Intent intent3 = new Intent(ItemDetails.this, PaymentModeScreen.class);
			intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent3.putExtra("KEY", "ItemDetails");
			startActivity(intent3);
			break;
		case R.id.type4layout:
			Intent intent4 = new Intent(ItemDetails.this, PaymentType.class);
			intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent4.putExtra("KEY", "ItemDetails");
			startActivity(intent4);
			break;
		case R.id.type5layout:
			Intent intent5 = new Intent(ItemDetails.this, HomeScreenActivity.class);
			intent5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent5);
			break;

		default:
			break;
		}
	}
}
