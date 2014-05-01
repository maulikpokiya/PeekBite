package com.peekbite.prototype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.application.peekbite.MainActivity;
import com.application.peekbite.R;
 

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class OrderScreen extends ListActivity implements OnClickListener {
	Button payButton;
	String key = "";
	ImageView backButton;
	int numberofItems = 0;
	LinearLayout type1Layout, type2Layout, type3Layout, type4Layout, type5Layout;
	TextView itemTotalPriceBeforeTax_orderScreen,taxcostTextView,itemTotalPriceAfterTax_orderScreenTextView,logoutTV;

	/**
	 * DUMMY DATA IN TWO ARRAYS
	 */
	private String itemName[] = {"dish 1", "dish 2", "dish 3", "dish 4", "dish 5"
			,"dish 6", "dish 7", "dish 8"};
	private int itemPrice[] = {20,12,34,33,13,12,5,8};
	private HashMap<String, Integer> getOrderFromHomeScreenActivity =null;
	private Bundle bundle;
	private ListView listView;
	//MyAdapter myAdapter = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_screen);
		
		logoutTV = (TextView)findViewById(R.id.logout_tv);
		itemTotalPriceBeforeTax_orderScreen = (TextView)findViewById(R.id.itemTotalPriceBeforeTax_orderScreen);
		itemTotalPriceAfterTax_orderScreenTextView = (TextView)findViewById(R.id.itemTotalPriceAfterTax_orderScreen);
		taxcostTextView = (TextView)findViewById(R.id.taxcost);
		backButton = (ImageView) findViewById(R.id.backBtn);
		payButton = (Button) findViewById(R.id.payBtn);
		key = getIntent().getStringExtra("KEY");
		numberofItems = getIntent().getIntExtra("ITEMS", 0);
		bundle = this.getIntent().getExtras();
		
		itemTotalPriceBeforeTax_orderScreen.setText(String.format("%.2f",getTotalPrice()));
		taxcostTextView.setText(String.format("%.2f",getTotalPrice()*0.08875));
		itemTotalPriceAfterTax_orderScreenTextView.setText(String.format("%.2f",getTotalPrice()+getTotalPrice()*0.08875));
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
		
		/**
		 * HERE I JUST SEND EACH ITEM ORDERED QUANTITIES TO ORDERSCREEN ACTIVITY FROM MAINACTIVITY
		 * SINCE I SAVED DATA INTO A HASHMAP WITH THE KEY IS THE INDEX AND VALUE IS THE QUANTITY
		 * WE CAN GET THE QUANTITY BY INDEXING THE KEY
		 * 
		 * I DIDN'T SEND EACH ITEM'S NAME, PRICE, DESCRIPTION AND ETC. HERE MAULIK NEEDS TO QUERY THOSE
		 * DATA FROM DATABASED WITH THE KEY
		 * 
		 * HERE, IN ORDER TO MOCK REAL SITUATION, I CREATED TO ARRAYS CONTAINS ORDER NAME AND COST
		 * AND SET THOSE INFORMATION ON THE ORDER SUMMARY SCREEN
		 */
		
		
		
		/*FOR TESTING 
		   if(bundle!=null){
		   HashMap<String, Integer> getOrder= (HashMap<String, Integer>) bundle.getSerializable("foodOrdering");
		   Iterator iter = getOrder.entrySet().iterator(); 
		   System.out.println("-----order screen---------");
			while (iter.hasNext()) { 
			    Map.Entry entry = (Map.Entry) iter.next(); 
			    Object key = entry.getKey(); 
			    Object val = entry.getValue();
			    
			    System.out.println("Key : " + key + " Value : "
						+ val);
			}
			System.out.println("order size: -------"+getOrder.size());
		 }  */ 
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(key != null) {
				if (key.equals("Main")) {
					Intent intent = new Intent(OrderScreen.this, HomeScreenActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//					intent.putExtra("ITEMS", numberofItems);
					startActivity(intent);
				} else if (key.equals("ItemDetails")) {
					Intent intent = new Intent(OrderScreen.this, ItemDetails.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.putExtra("ITEMS", numberofItems);
					startActivity(intent);
				}
				}
				/*Intent intent = new Intent(OrderScreen.this, HomeScreenActivity.class);
				OrderScreen.this.setResult(RESULT_CANCELED,intent);
				OrderScreen.this.finish();*/
			}
		});
		
		payButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(OrderScreen.this, PaymentModeScreen.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("KEY", key);
				startActivity(intent);
			}	
		});
		SimpleAdapter adapter = new SimpleAdapter(this,getData(),R.layout.item,
				new String[]{"itemName","itemUnitPrice","quantity", "total"},
				new int[]{R.id.itemName_orderScreen,R.id.itemprice_orderScreen,R.id.itemquantity_orderScreen
			, R.id.UnitItemTotalPrice_orderScreen});
		setListAdapter(adapter); 
		
		//LOGOUT FUNCTION
		logoutTV.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 
				Intent intent = new Intent(); 
				intent.setClass(OrderScreen.this, MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  //注意本行的FLAG设置
				startActivity(intent);
				finish();//关掉自己
				Toast.makeText(OrderScreen.this, "You logged out!", Toast.LENGTH_SHORT).show();
				 
			}
		});
		
	}//end of onCreate
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Bundle bundle = this.getIntent().getExtras();
		getOrderFromHomeScreenActivity= (HashMap<String, Integer>) bundle.getSerializable("foodOrdering");
		
		
		if(getOrderFromHomeScreenActivity!=null){
			int counter=0;
			Iterator iter = getOrderFromHomeScreenActivity.entrySet().iterator(); 
			   System.out.println("-----order screen---------");
				while (iter.hasNext()) { 
				    Map.Entry entry = (Map.Entry) iter.next(); 
				    String key = (String) entry.getKey(); 
				    Integer val = (Integer) entry.getValue();
				    
				    Map<String, Object> map = new HashMap<String, Object>();
				    map.put("quantity", val);
				    map.put("itemName", key);
				    map.put("itemUnitPrice", itemPrice[counter]);//item price is needed to either query database or send from HomeScreenActivity 
				    //which is also needed to query database in the HomeScreenActivity
				    
				    map.put("total", itemPrice[counter]*val);
				    list.add(map);
				    counter++;
			}
		}
		return list;
	}
	
	 
	public double getTotalPrice() {
		double totalPrice = 0;
		Bundle bundle = this.getIntent().getExtras();
		getOrderFromHomeScreenActivity= (HashMap<String, Integer>) bundle.getSerializable("foodOrdering");

		if(getOrderFromHomeScreenActivity!=null){
			int counter=0;
			Iterator iter = getOrderFromHomeScreenActivity.entrySet().iterator(); 
			   System.out.println("-----order screen---------");
				while (iter.hasNext()) { 
				    Map.Entry entry = (Map.Entry) iter.next(); 
				    Integer val = (Integer) entry.getValue();
				    double totalPerItem = itemPrice[counter]*val;
				    totalPrice = totalPrice+totalPerItem;
				    counter++;
			}
		}
		return totalPrice;
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.type1layout:
			Intent intent1 = new Intent(OrderScreen.this, ItemDetails.class);
			intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent1.putExtra("ITEMS", "0");
			startActivity(intent1);
			break;
		case R.id.type2layout:
			Intent intent2 = new Intent(OrderScreen.this, OrderScreen.class);
			intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent2.putExtra("KEY", key);
			startActivity(intent2);
			break;
		case R.id.type3layout:
			Intent intent3 = new Intent(OrderScreen.this, PaymentModeScreen.class);
			intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent3.putExtra("KEY", key);
			startActivity(intent3);
			break;
		case R.id.type4layout:
			Intent intent4 = new Intent(OrderScreen.this, PaymentType.class);
			intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent4.putExtra("KEY", key);
			startActivity(intent4);
			break;
		case R.id.type5layout:
			Intent intent5 = new Intent(OrderScreen.this, HomeScreenActivity.class);
			intent5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent5);
			break;

		default:
			break;
		}
	}
}
