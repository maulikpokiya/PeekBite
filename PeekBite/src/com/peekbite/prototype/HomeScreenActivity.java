package com.peekbite.prototype;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

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
import android.widget.Toast;

import com.application.peekbite.MainActivity;
import com.application.peekbite.R;
import com.peekbite.model.TotalQuantity;
import com.peekbite.registration.JSONParser;

public class HomeScreenActivity extends ListActivity implements OnClickListener{
	final Map<Integer, Integer> counter = new HashMap<Integer, Integer>();
	TextView logoutTV, numberofItemsTextView,shoppingcartNumberTextView,logout_tv;
	ImageView rateButton_mainImageView;
	static int numberofItems = 0;
	ListView listView;
	Intent intent = new Intent();
	private TotalQuantity tq;

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
	public static Hashtable<String, Integer> foodOrdering = new Hashtable<String, Integer>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splashmain);
		//ready_to_order button
		logout_tv = (TextView) findViewById(R.id.logout_tv);
		//total items added
		numberofItemsTextView = (TextView) findViewById(R.id.numberof_itemsTxt);
//		shoppingcartNumberTextView = (TextView) findViewById(R.id.shoppingcart_number);
		rateButton_mainImageView = (ImageView) findViewById(R.id.rateButton_main);
		tq=(TotalQuantity)getApplication();
		/**
		 * Type buttons in the bottom
		
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
		 */
		listView = (ListView) findViewById(android.R.id.list);
		listView.setAdapter(new ItemsAdapter(this));

		//numberofItems = getIntent().getIntExtra("ITEMS", 0);
		numberofItemsTextView.setText(tq.getNumberofItems() + "  Items");
		/**
		 * Ready_to_order button clickListener
		 
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
		});*/
		
		
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

					//put order into buncle
					Bundle extras = new Bundle();
					extras.putSerializable("foodOrdering",(Serializable) foodOrdering);
					intent.putExtras(extras);
					startActivity(intent);
					//add ends
					
					startActivity(intent);
				} else {
					DialogClass.createSessionAlertDialog(HomeScreenActivity.this, "Please select items to Order.", false);
				}

			}
		});
		
		/**
		 * log out function
		 */
		logout_tv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				tq.setNumberofItems(0);
				Intent intent = new Intent(); 
				intent.setClass(HomeScreenActivity.this, MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  //注意本行的FLAG设置
				//tq.setNumberofItems(0);//clear the global variable totalQuantity to 0
				startActivity(intent);
				HomeScreenActivity.this.setResult(RESULT_CANCELED,intent);
				HomeScreenActivity.this.finish();
				//finish();//关掉自己
				Toast.makeText(HomeScreenActivity.this, "You logged out!", Toast.LENGTH_SHORT).show();
				 
			}
		});
		
 	}//end of onCreate method
	
	/**
	 * ArrayAdapter
	 * 
	 * @author Shiyin
	 * 
	 */
	public class ItemsAdapter extends BaseAdapter {
		Context context;
		int itemCounter=0;
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
					intent.putExtra("ITEMS", tq.getNumberofItems());
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
				Integer quantityPerItem=0;
				@Override
				public void onClick(View v) {
					if(food != null) {
						food.add(dishNames[position]);
						//put ordered item into food array
						
					}
					//[0] = this.toString();
					View tag = (View) addcartlayout.getTag();
					
				if(foodOrdering.containsKey(dishNames[position])){
					quantityPerItem = foodOrdering.get(dishNames[position]);
					foodOrdering.remove(position);//remove original item with the quantity
					foodOrdering.put(dishNames[position], ++quantityPerItem);//update the new quantity
					
					//UPDATE TOTAL QUANTITY
					numberofItems = numberofItems + 1;//total ordering displayed on the right top of the tab
					tq.setNumberofItems(numberofItems);
					numberofItemsTextView.setText(numberofItems + "  Items");
					
				}else {
					 
					foodOrdering.put(dishNames[position], ++quantityPerItem);
					++numberofItems;
					tq.setNumberofItems(numberofItems);
					numberofItemsTextView.setText(numberofItems + "  Items");				
				}
				
				}
			});

			/**
			 * delete item from cart
			 */
			deletecartButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if(foodOrdering.containsKey(dishNames[position])&&foodOrdering.get(dishNames[position])>=1){
						System.out.println("---------------decrement counter on each item click event-----------------------------------------");
						//PER ITEM QUANTITY
						int quantityPerItem = foodOrdering.get(dishNames[position]);
						quantityPerItem--;//update current item's quantity
						foodOrdering.remove(dishNames[position]);//remove original item with quantity in the hashtable
						foodOrdering.put(dishNames[position], quantityPerItem);
						//give a new quantity to the item;
						numberofItems--;//update TOTAL QUANTITY
						tq.setNumberofItems(numberofItems);
						numberofItemsTextView.setText(numberofItems + "  Items");		
					}else{
						DialogClass.createSessionAlertDialog(HomeScreenActivity.this, "You didn't add this dish to the cart.", false);
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
		@Override
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
						@Override
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
