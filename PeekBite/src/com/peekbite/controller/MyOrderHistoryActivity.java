package com.peekbite.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.application.peekbite.R;
import com.peekbite.model.OrderHistoryItem;
import com.peekbite.prototype.HomeScreenActivity;
import com.peekbite.prototype.OrderScreen;
import com.peekbite.registration.JSONParser;

public class MyOrderHistoryActivity extends Activity{
	
	private List<OrderHistoryItem> orderItemArray;
	
	private final static String TAG = "MyOrderHistoryActivity";
	private String Server = "http://peekbite.pinaksaha.me/";
	// url to save order items
	private String url_get_orders = Server + "get_history.php";
	private List<Integer> cartIds = new ArrayList<Integer>();
	private List<Integer> restIds = new ArrayList<Integer>();
	private List<String> restNames = new ArrayList<String>();
	private List<String> cartDates = new ArrayList<String>();
	private List<String> fNames = new ArrayList<String>();
	private List<String> fPrices = new ArrayList<String>();

	JSONParser jsonParser = new JSONParser();
	// JSON Node names
	private static final String TAG_SUCCESS = "success";

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
		
		//for(int i=0; i<orderIds.length; i++) {
		if(dishNameArr.size() == 0) {
			dishImageArr.add(dishImages[0]);
			dishNameArr.add(dishNames[0]);
			dishPriceArr.add(dishPrices[0]);
			//add for test
			dishImageArr.add(dishImages[5]);
			dishNameArr.add(dishNames[5]);
			dishPriceArr.add(dishPrices[5]);
			//add Ends
		}
		//}
		//fake data ends
		
		
		ListView listView = (ListView)findViewById(R.id.order_listview);
		//List<OrderHistoryItem> orderItemArray = new ArrayList<OrderHistoryItem>();
		orderItemArray = new ArrayList<OrderHistoryItem>();
		
		for(int i=0; i< orderIds.length; i++) {
			Log.i(TAG, "TEST order ID is: "+orderIds[i]);//test
			
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
		Toast.makeText(this, "test onItemclick in MyOrderHistoryActivity at position: "+item.getOrderId(), Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * Background Async Task to get Order history from database
	 * 
	 * @author Maulik
	 * */
	class getOrders extends AsyncTask<String, String, String> {

		/**
		 * Storing user
		 * */
		protected String doInBackground(String... args) {
			try {
				JSONArray cart_Ids = null;
				JSONArray rest_Ids = null;
				JSONArray rest_Names = null;
				JSONArray cart_Dates = null;
				JSONArray f_Names = null;
				JSONArray f_Prices = null;
				
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("uname", args[0].toString()));

				// getting JSON Object
				JSONObject json = jsonParser.makeHttpRequest(url_get_orders,
						"POST", params);

				// check log cat for response
				Log.d("JSON",
						"JSON Response in MainActivity :" + json.toString());

				// check for success tag
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {

					cart_Ids = json.getJSONArray("cart_id");
					rest_Ids = json.getJSONArray("rest_id");
					rest_Names = json.getJSONArray("rest_name");
					cart_Dates = json.getJSONArray("cart_date");
					f_Names = json.getJSONArray("fname");
					f_Prices = json.getJSONArray("fprice");

					if (cart_Ids != null && rest_Ids != null
							&& cart_Dates != null && cart_Ids != null && f_Names != null
							&& f_Prices != null) {
						for (int i = 0; i < cart_Ids.length(); i++) {
							cartIds.add(cart_Ids.getInt(i));
							restIds.add(rest_Ids.getInt(i));
							restNames.add(rest_Names.get(i).toString());
							cartDates.add(cart_Dates.get(i).toString());
							fNames.add(f_Names.get(i).toString());
							fPrices.add(f_Prices.get(i).toString());
						}
					}

				} else {
					// failed to create User
					MyOrderHistoryActivity.this.runOnUiThread(new Runnable() {
						public void run() {
							// your alert dialog builder here
							new AlertDialog.Builder(MyOrderHistoryActivity.this)
									.setIcon(android.R.drawable.ic_dialog_alert)
									.setTitle("Error")
									.setMessage(
											"Oops, Error occurred while getting order history.")
									.setPositiveButton("OK", null).show();
						}
					});
				}
			} catch (JSONException je) {
				Log.e("ERROR",
						"Error in MyOrderHistoryActivity.getorders()" + je.toString());
			}
			return "Success";
		}

	}
	
	
	
	
}
