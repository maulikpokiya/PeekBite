package com.peekbite.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.application.peekbite.R;
import com.peekbite.model.OrderHistoryItem;

public class MyOrderHistoryActivity extends Activity{
	
	private List<OrderHistoryItem> orderItemArray;
	
	private final static String TAG = "MyOrderHistoryActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "in onCreate");//test
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_order_history);
		setTitle("Order History Page");//test
		
		
		//Fake data for testing. Need to get data from Database later
		int[] orderIds = new int[]{1,2,3,4,5,6,7,8};
		String[] restNames = new String[]{"rest1", "rest2", "rest3", 
		                                "rest1", "rest1", "rest2",
		                                "rest3", "rest1"};
		int[] restIds = new int[]{1,2,3,1,1,2,3,1}; 
		Date fakeDate = new Date();
		Date[] dates = new Date[] {fakeDate,fakeDate,fakeDate,fakeDate,
				fakeDate,fakeDate,fakeDate,fakeDate};
		int[] dishImages=new int[]{R.drawable.download_eight,R.drawable.download_eleven,
				R.drawable.download_five, R.drawable.download_four,
				R.drawable.download_nine, R.drawable.download_one,
				R.drawable.download_seven, R.drawable.download_six}; 
		String[] dishNames=new String[]{"dish 1","dish 2","dish 3",
				"dish 4","dish 5","dish 6","dish 7","dish 8"};
		int[] dishPrices = new int[] {20,30,10,24,14,12,23,82};
		
		ArrayList<Integer> dishImageArr = new ArrayList<Integer>();
		ArrayList<String> dishNameArr = new ArrayList<String>();
		ArrayList<Integer> dishPriceArr = new ArrayList<Integer>();
		
		for(int i=0; i<orderIds.length; i++) {
			dishImageArr.add(dishImages[i]);
			dishNameArr.add(dishNames[i]);
			dishPriceArr.add(dishPrices[i]);
		}
		//fake data ends
		
		
		ListView listView = (ListView)findViewById(R.id.order_listview);
		//List<OrderHistoryItem> orderItemArray = new ArrayList<OrderHistoryItem>();
		orderItemArray = new ArrayList<OrderHistoryItem>();
		
		for(int i=0; i< dishNames.length; i++) {
			OrderHistoryItem item = new OrderHistoryItem(orderIds[i], 
					restNames[i], restIds[i], dates[i], dishImageArr,
					dishNameArr, dishPriceArr);
			orderItemArray.add(item);
		
		}
		
		//setup adapter
		//Context context = this;
		MyOrderHistoryAdapter adapter = new MyOrderHistoryAdapter(
							this, R.layout.order_history_item, orderItemArray);
		listView.setAdapter(adapter);
		
		
	}
	
	
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Log.i(TAG, "in onItemClick");//test
		OrderHistoryItem item = orderItemArray.get(position);
		Toast.makeText(this, "test onItemclick in MyOrderHistoryActivity", Toast.LENGTH_SHORT).show();
	}
	
	
	
	
}
