package com.peekbite.prototype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
 

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

	/**
	 * DUMMY DATA IN TWO ARRAYS
	 */
	private String itemName[] = {"dish 1", "dish 2", "dish 3", "dish 4", "dish 5"
			,"dish 6", "dish 7", "dish 8"};
	private int itemPrice[] = {20,12,34,33,13,12,5,8};
	private HashMap<Integer, Integer> getOrder =null;
	private Bundle bundle;
	private ListView listView;
	//MyAdapter myAdapter = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_screen);
		
		backButton = (ImageView) findViewById(R.id.backBtn);
		payButton = (Button) findViewById(R.id.payBtn);
		key = getIntent().getStringExtra("KEY");
		numberofItems = getIntent().getIntExtra("ITEMS", 0);
		bundle = this.getIntent().getExtras();
		
		type1Layout = (LinearLayout) findViewById(R.id.type1layout);
		type2Layout = (LinearLayout) findViewById(R.id.type2layout);
		type3Layout = (LinearLayout) findViewById(R.id.type3layout);
		type4Layout = (LinearLayout) findViewById(R.id.type4layout);
		type5Layout = (LinearLayout) findViewById(R.id.type5layout);
		
		type1Layout.setOnClickListener(this);
		type2Layout.setOnClickListener(this);
		type3Layout.setOnClickListener(this);
		type4Layout.setOnClickListener(this);
		type5Layout.setOnClickListener(this);
		
		
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
		 *  if(bundle!=null){
		   HashMap<Integer, Integer> getOrder= (HashMap<Integer, Integer>) bundle.getSerializable("counter");
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
				if (key.equals("Main")) {
					Intent intent = new Intent(OrderScreen.this, MainActivity.class);
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
				new String[]{"itemName","itemUnitPrice","quantities", "total"},
				new int[]{R.id.itemName_orderScreen,R.id.itemprice_orderScreen,R.id.itemquantity_orderScreen
			, R.id.UnitItemTotalPrice_orderScreen});
		setListAdapter(adapter); 
		
	}//end of onCreate
	
	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Bundle bundle = this.getIntent().getExtras();
		getOrder= (HashMap<Integer, Integer>) bundle.getSerializable("counter");
		
		
		if(getOrder!=null){
			Iterator iter = getOrder.entrySet().iterator(); 
			   System.out.println("-----order screen---------");
				while (iter.hasNext()) { 
				    Map.Entry entry = (Map.Entry) iter.next(); 
				    Integer key = (Integer) entry.getKey(); 
				    Integer val = (Integer) entry.getValue();
				    
				    Map<String, Object> map = new HashMap<String, Object>();
				    map.put("quantities", val);
				    map.put("itemName", itemName[key]);
				    map.put("itemUnitPrice", itemPrice[key]);
				    map.put("total", itemPrice[key]*val);
				    list.add(map);
			}
		 
		}
		return list;
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
			Intent intent5 = new Intent(OrderScreen.this, MainActivity.class);
			intent5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent5);
			break;

		default:
			break;
		}
	}
}
