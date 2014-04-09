package com.peekbite.prototype;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import com.application.peekbite.R;
import com.peekbite.registration.JSONParser;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.TextView;

public class HomeScreenActivity extends ListActivity implements OnClickListener{
	final Map<Integer, Integer> counter = new HashMap<Integer, Integer>();
	TextView orderTextView, numberofItemsTextView,shoppingcartNumberTextView;
	ImageView rateButton_mainImageView;
	static int numberofItems = 0;
	ListView listView;
	Intent intent = new Intent();

	// url to save order items
	private static String url_store_order = "http://peekbite.pinaksaha.me/process_order.php";

	JSONParser jsonParser = new JSONParser();

	// JSON Node names
	private static final String TAG_SUCCESS = "success";

	LinearLayout type1Layout, type2Layout, type3Layout, type4Layout,
			type5Layout;
	/**
	 * WARNING: TO MAULIK Those images should be retrieved from database instead
	 * of from resource folder. Now just take it as an example
	 */
	private int[] dishImages=new int[]{R.drawable.download_eight,R.drawable.download_eleven,
			R.drawable.download_five, R.drawable.download_four,
			R.drawable.download_nine, R.drawable.download_one,
			R.drawable.download_seven, R.drawable.download_six}; 
	private  String[] dishNames=new String[]{"dish 1","dish 2","dish 3",
			"dish 4","dish 5","dish 6","dish 7","dish 8"};  
	private int[] dishCosts = new int[] {20,30,10,24,14,12,23,82};

	private ArrayList<String> food = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splashmain);
		//ready_to_order button
		orderTextView = (TextView) findViewById(R.id.orderTxt);
		numberofItemsTextView = (TextView) findViewById(R.id.numberof_itemsTxt);
//		shoppingcartNumberTextView = (TextView) findViewById(R.id.shoppingcart_number);
		rateButton_mainImageView = (ImageView) findViewById(R.id.rateButton_main);

		/**
		 * Type buttons in the bottom
		 */
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

		listView = (ListView) findViewById(android.R.id.list);
		listView.setAdapter(new ItemsAdapter(this));

		/**
		 * Ready_to_order button clickListener
		 */
		orderTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (numberofItems > 0) {

					intent.putExtra("order", food);
					intent.putExtra("KEY", "Main");
					String[] foodArray = new String[food.size()];
					
					for(int i=0; i<foodArray.length; i++) {
						foodArray[i] = food.get(i);
					}
					
					// storing order to database in background thread
					//new StoreOrder().execute(foodArray);
					
					//add by Nan for testing
					Intent intent2 = new Intent(HomeScreenActivity.this, OrderScreen.class);
					intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent2.putExtra("KEY", "Main");
					//put order into buncle
					Bundle extras = new Bundle();
					extras.putSerializable("counter",(Serializable) counter);
					intent2.putExtras(extras);
					startActivity(intent2);
					//add ends

				} else {
					DialogClass.createSessionAlertDialog(HomeScreenActivity.this, "Please select items to Order.", false);
				}
			}
		});
		
		
		/**
		 * click even for shopping cart
		 */
		rateButton_mainImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (numberofItems > 0) {
					Intent intent = new Intent(HomeScreenActivity.this, OrderScreen.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.putExtra("KEY", "Main");

					startActivity(intent);
				} else {
					DialogClass.createSessionAlertDialog(HomeScreenActivity.this, "Please select items to Order.", false);
				}

			}
		});
		
		/*listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Map<Integer, Integer> counter = new HashMap<Integer, Integer>();
				if(counter.containsKey(position)){
					Integer keyMappingValueInteger = counter.get((Integer)position);
					keyMappingValueInteger++;
					counter.remove(position);
					counter.put(position, keyMappingValueInteger);
					
				}else {
					 
					counter.put((Integer)position,1);
				}
				
				for (Map.Entry<Integer, Integer> entry : counter.entrySet()) {
					System.out.println("Key : " + entry.getKey() + " Value : "
						+ entry.getValue());
				}
			}
			
		});*/
		
 	}
	/**
	 * This event responds to the Listview user action. */
	
	/*@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
			Toast.makeText(this, "You clicked"+position, Toast.LENGTH_SHORT).show();
			super.onListItemClick(l, v, position, id); 
			 
	} */
	/**
	 * ArrayAdapter
	 * 
	 * @author Shiyin
	 * 
	 */
	public class ItemsAdapter extends BaseAdapter {
		Context context;

		public ItemsAdapter(Context c) {
			context = c;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return dishNames.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			if (convertView == null) {

				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = LayoutInflater.from(context).inflate(
						R.layout.inflater_file, null);
				// System.out.println("convertView "+convertView);
				ItemViewCache viewCache = new ItemViewCache();
				viewCache.dishNameTextView = (TextView) convertView
						.findViewById(R.id.dishName);
				viewCache.dishAmountTextView = (TextView) convertView
						.findViewById(R.id.dishAmount);
				viewCache.dishImageView = (ImageView) convertView
						.findViewById(R.id.dishImage);

				// System.out.println("viewCache.dishNameTextView "+viewCache.dishNameTextView);

				// convertView = inflater.inflate(R.layout.inflater_file, null);
				convertView.setTag(viewCache);
			}
			ItemViewCache cache = (ItemViewCache) convertView.getTag();
			// System.out.println("dishNameTextView "+cache.dishNameTextView);
			cache.dishNameTextView.setText(dishNames[position]);
			cache.dishAmountTextView.setText("$"
					+ Integer.toString(dishCosts[position]));
			cache.dishImageView.setImageResource(dishImages[position]);

			// View view;
			// LayoutInflater inflater = (LayoutInflater)
			// getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// convertView = inflater.inflate(R.layout.inflater_file, null);
			// convertView.setTag(convertView);

			/**
			 * retrieve add/delete item button on each dish pic from the
			 * activity_main.xml
			 */

			final LinearLayout addcartlayout = (LinearLayout) convertView
					.findViewById(R.id.addcartLayout);
			final LinearLayout deletecartlayout = (LinearLayout) convertView
					.findViewById(R.id.deletecartLayout);
			addcartlayout.setVisibility(View.VISIBLE);
			deletecartlayout.setVisibility(View.VISIBLE);

			Button addcartButton = (Button) convertView
					.findViewById(R.id.addcartBtn);
			Button deletecartButton = (Button) convertView
					.findViewById(R.id.deletecartBtn);

			/**
			 * This onClickAction directs to the itemDescription page when user
			 * touches the item pic
			 */
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					 
//					String[] items = numberofItemsTextView.getText().toString().trim().split(" ");
					Intent intent = new Intent(HomeScreenActivity.this, ItemDetails.class);
					intent.putExtra("ITEMS", numberofItems);
					intent.putExtra("dishname", dishNames[position]);
					intent.putExtra("dishcost",
							Integer.toString(dishCosts[position]));
					intent.putExtra("dishimage", dishImages[position]);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				}
			});
			/**
			 * when user clicks "+" sign, items are added and numbers are
			 * increased in the right top
			 */
			addcartButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if(food != null) {
						food.add(dishNames[position]);
					}
					//[0] = this.toString();
					View tag = (View) addcartlayout.getTag();
					// addcartlayout.setVisibility(View.GONE);
					// ? can only order one
					// deletecartlayout.setVisibility(View.VISIBLE);
					numberofItems = numberofItems + 1;
					numberofItemsTextView.setText(numberofItems + "  Items");
//					shoppingcartNumberTextView.setText(Integer.toString(numberofItems));
					
				if(counter.containsKey(position)){
					Integer keyMappingValueInteger = counter.get((Integer)position);
					keyMappingValueInteger++;
					counter.remove(position);
					counter.put(position, keyMappingValueInteger);
					
				}else {
					 
					counter.put((Integer)position,1);
				}
			/*	Iterator iter = counter.entrySet().iterator(); 
				while (iter.hasNext()) { 
				    Map.Entry entry = (Map.Entry) iter.next(); 
				    Object key = entry.getKey(); 
				    Object val = entry.getValue();
				    System.out.println("Key : " + key + " Value : "
							+ val);
				} */
				 
				
				}
			});

			/**
			 * delete item from cart
			 */
			deletecartButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// addcartlayout.setVisibility(View.VISIBLE);
					// deletecartlayout.setVisibility(View.GONE);
					if (numberofItems >= 1) {
						numberofItems = numberofItems - 1;
						numberofItemsTextView.setText(numberofItems + "  Items");
//						shoppingcartNumberTextView.setText(Integer.toString(numberofItems));
	System.out.println("---------------decrement counter on each item click event-----------------------------------------");
					
					if(counter.containsKey(position)&&counter.get((Integer)position)>=1){
						Integer keyMappingValueInteger= counter.get((Integer)position);
						keyMappingValueInteger--;
						counter.remove(position);
						counter.put(position, keyMappingValueInteger);
						
					}
					 
					/*Iterator iter = counter.entrySet().iterator(); 
					while (iter.hasNext()) { 
					    Map.Entry entry = (Map.Entry) iter.next(); 
					    Object key = entry.getKey(); 
					    Object val = entry.getValue();
					    System.out.println("Key : " + key + " Value : "
								+ val);
					} */
					}
				}
			});

			return convertView;
		}

	}

	private static class ItemViewCache {
		public TextView dishNameTextView, dishAmountTextView;
		public ImageView dishImageView;
	}

	/**
	 * Bottom types panels: directing to different activities
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.type1layout:
			Intent intent1 = new Intent(HomeScreenActivity.this, ItemDetails.class);
			intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent1.putExtra("ITEMS", numberofItems);
			startActivity(intent1);
			break;
		case R.id.type2layout:
			Intent intent2 = new Intent(HomeScreenActivity.this, OrderScreen.class);
			intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent2.putExtra("KEY", "Main");
			//put order into buncle
			Bundle extras = new Bundle();
			extras.putSerializable("counter",(Serializable) counter);
			intent2.putExtras(extras);
			startActivity(intent2);
			break;
		case R.id.type3layout:
			Intent intent3 = new Intent(HomeScreenActivity.this, PaymentModeScreen.class);
			intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent3.putExtra("KEY", "Main");
			startActivity(intent3);
			break;
		case R.id.type4layout:
			Intent intent4 = new Intent(HomeScreenActivity.this, PaymentType.class);
			intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent4.putExtra("KEY", "Main");
			startActivity(intent4);
			break;
		case R.id.type5layout:
			Intent intent5 = new Intent(HomeScreenActivity.this, HomeScreenActivity.class);
			startActivity(intent5);
			break;

		default:
			break;
		}
	}

	// Begin changes by Maulik
	/**
	 * Background Async Task to Create new user
	 * */
	class StoreOrder extends AsyncTask<String, String, String> {

		/**
		 * Storing user
		 * */
		@TargetApi(19)
		protected String doInBackground(String... args) {
			try {
			JSONArray jarray = new JSONArray(args);
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("food", jarray.toString()));
			

			// getting JSON Object
			JSONObject json = jsonParser.makeHttpRequest(url_store_order,
					"POST", params);

			
			// check log cat for response
			Log.d("JSON", "JSON Response in MainActivity :" + json.toString());

			// check for success tag
//			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {

					intent.setClass(HomeScreenActivity.this, OrderScreen.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					// intent.putExtra("KEY", "Main");
					startActivity(intent);
					finish();

				} else {
					// failed to create User
					HomeScreenActivity.this.runOnUiThread(new Runnable() {
						public void run() {
							// your alert dialog builder here
							new AlertDialog.Builder(HomeScreenActivity.this)
									.setIcon(android.R.drawable.ic_dialog_alert)
									.setTitle("Error")
									.setMessage(
											"Oops, Error occurred while storing order.")
									.setPositiveButton("OK", null).show();
						}
					});

				}
			} catch (JSONException je) {
				Log.e("ERROR",
						"Error in MainActivity.storeorder()"
								+ je.toString());
			}
			return "Success";
		}

	}
	// End of changes by Maulik
}
