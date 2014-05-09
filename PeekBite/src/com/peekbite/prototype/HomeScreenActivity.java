package com.peekbite.prototype;

import java.io.InputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.net.URL;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import com.application.peekbite.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.peekbite.registration.JSONParser;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import com.peekbite.model.TotalQuantity;

public class HomeScreenActivity extends ListActivity implements OnClickListener{
	final Map<Integer, Integer> counter = new HashMap<Integer, Integer>();
	TextView logoutTV, numberofItemsTextView,shoppingcartNumberTextView,logout_tv;
	ImageView rateButton_mainImageView;
	static int numberofItems = 0;
//	ListView listView;
	Intent intent = new Intent();
	private TotalQuantity tq;
	ImageLoader imageLoader;
	DisplayImageOptions options;
	ImageLoaderConfiguration config;

	private String Server = "http://peekbite.pinaksaha.me/";
	// url to save order items
	private String url_store_order = Server + "process_order.php";
	// url to get menu items
	private String url_get_menu = Server + "get_menu.php";

	JSONParser jsonParser = new JSONParser();

	// JSON Node names
	private static final String TAG_SUCCESS = "success";

	LinearLayout type1Layout, type2Layout, type3Layout, type4Layout,
			type5Layout;

	private List<Drawable> dishImages = new ArrayList<Drawable>();
	private List<String> dishUrls = new ArrayList<String>();
	private List<String> dishNames = new ArrayList<String>();
	private List<Integer> dishCosts = new ArrayList<Integer>();
	private List<String> dishIngredients = new ArrayList<String>();

	private ArrayList<String> food = new ArrayList<String>();
	public static Hashtable<String, Integer> foodOrdering = new Hashtable<String, Integer>();
//	private ItemsAdapter adapter;
	private ListView listView;
	// ProgressDialog progressDialog;
	public GetMenu menu;

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

		config = new ImageLoaderConfiguration.Builder(this).build();
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(config);
		options = new DisplayImageOptions.Builder().build();
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

					// storing order to database in background thread
					new StoreOrder().execute(foodArray);

					// add by Nan for testing
					Intent intent2 = new Intent(HomeScreenActivity.this,
							OrderScreen.class);
					intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent2.putExtra("KEY", "Main");
					// put order into bundle
					Bundle extras = new Bundle();
					extras.putSerializable("counter", (Serializable) counter);
					intent2.putExtras(extras);
					startActivity(intent2);
					// add ends

				} else {
					DialogClass.createSessionAlertDialog(
							HomeScreenActivity.this,
							"Please select items to Order.", false);
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
					Intent intent = new Intent(HomeScreenActivity.this,
							OrderScreen.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.putExtra("KEY", "Main");

					//put order into bundle
					Bundle extras = new Bundle();
					extras.putSerializable("foodOrdering",(Serializable) foodOrdering);
					intent.putExtras(extras);
					startActivity(intent);
					//add ends
					
					startActivity(intent);
				} else {
					DialogClass.createSessionAlertDialog(
							HomeScreenActivity.this,
							"Please select items to Order.", false);
				}
			}
		});
		new GetMenu().execute("data");
		// synchronized(this) {
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
		
		// storing order to database in background thread
		try {
			// while(menu.getStatus() != AsyncTask.Status.FINISHED) {
			Thread.sleep(2000);
			// }
		} catch (Exception e) {
			Log.e("ERROR", e.toString());
		}
		// }
		for (String url : dishUrls) {
			Drawable d = loadImageFromWeb(url);
			dishImages.add(d);
		}
	}

	private Drawable loadImageFromWeb(String url) {
		try {
			InputStream is = (InputStream) new URL(url).getContent();
			Drawable d = Drawable.createFromStream(is, "src name");
			return d;
		} catch (Exception e) {
			Log.e("ERROR", e.toString());
			return null;
		}
	}

	/**
	 * ArrayAdapter
	 * 
	 * @author Shiyin
	 * 
	 */
	public class ItemsAdapter extends BaseAdapter {

		Context context;
		ViewHolder cache;
		int itemCounter=0;

		public ItemsAdapter(Context context) {
			this.context = context;
		}

		private class ViewHolder {
			TextView dishNameTextView, dishAmountTextView;
			ImageView dishImageView;
		}

		public int getCount() {
			return dishNames.size();
		}

		public Object getItem(int position) {
			return dishNames.get(position);
		}

		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			ViewHolder holder = null;
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

			if (convertView == null) {

				convertView = inflater.inflate(R.layout.inflater_file, null);
				holder = new ViewHolder();
				
				holder.dishNameTextView = (TextView) convertView
						.findViewById(R.id.dishName);
				holder.dishAmountTextView = (TextView) convertView
						.findViewById(R.id.dishAmount);
				holder.dishImageView = (ImageView) convertView
						.findViewById(R.id.dishImage);
				
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			cache = (ViewHolder) convertView.getTag();

			cache.dishNameTextView.setText(dishNames.get(position));
			cache.dishAmountTextView.setText("$"
					+ Integer.toString(dishCosts.get(position)));
			cache.dishImageView.setImageDrawable(dishImages.get(position));
			
			// Load image, decode it to Bitmap and display Bitmap in ImageView
			// (or any other view which implements ImageAware interface)
			imageLoader.displayImage(dishUrls.get(position),
					cache.dishImageView);

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

					// String[] items =
					// numberofItemsTextView.getText().toString().trim().split(" ");
					Intent intent = new Intent(HomeScreenActivity.this,
							ItemDetails.class);

					/*Bitmap bitmap = BitmapFactory.decodeResource(
							getResources(), R.drawable.ic_launcher);
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
					byte[] b = baos.toByteArray();
*/
//					cache.dishImageView.setDrawingCacheEnabled(true);
//				      Bitmap b=cache.dishImageView.getDrawingCache();
				      
					intent.putExtra("ITEMS", tq.getNumberofItems());
					intent.putExtra("dishname", dishNames.get(position));
//					intent.putExtra("dishContent", dishIngredients.get(position));
					intent.putExtra("dishcost",
							Integer.toString(dishCosts.get(position)));
					intent.putExtra("dishimage", dishUrls.get(position));
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
					if (food != null) {
						food.add(dishNames.get(position));
						//put ordered item into food array
					}
					// [0] = this.toString();
					View tag = (View) addcartlayout.getTag();
					
				if(foodOrdering.containsKey(dishNames.get(position))){
					quantityPerItem = foodOrdering.get(dishNames.get(position));
					foodOrdering.remove(position);//remove original item with the quantity
					foodOrdering.put(dishNames.get(position), ++quantityPerItem);//update the new quantity
					
					//UPDATE TOTAL QUANTITY
					numberofItems = numberofItems + 1;//total ordering displayed on the right top of the tab
					tq.setNumberofItems(numberofItems);
					numberofItemsTextView.setText(numberofItems + "  Items");
					// shoppingcartNumberTextView.setText(Integer.toString(numberofItems));

				} else {
					 
					foodOrdering.put(dishNames.get(position), ++quantityPerItem);
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
					if(foodOrdering.containsKey(dishNames.get(position)) && foodOrdering.get(dishNames.get(position))>=1){
						System.out.println("---------------decrement counter on each item click event-----------------------------------------");
						//PER ITEM QUANTITY
						int quantityPerItem = foodOrdering.get(dishNames.get(position));
						quantityPerItem--;//update current item's quantity
						foodOrdering.remove(dishNames.get(position));//remove original item with quantity in the hashtable
						foodOrdering.put(dishNames.get(position), quantityPerItem);
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

	/**
	 * Bottom types panels: directing to different activities
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.type1layout:
			Intent intent1 = new Intent(HomeScreenActivity.this,
					ItemDetails.class);
			intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent1.putExtra("ITEMS", numberofItems);
			startActivity(intent1);
			break;
		case R.id.type2layout:
			Intent intent2 = new Intent(HomeScreenActivity.this,
					OrderScreen.class);
			intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent2.putExtra("KEY", "Main");
			// put order into bundle
			Bundle extras = new Bundle();
			extras.putSerializable("counter", (Serializable) counter);
			intent2.putExtras(extras);
			startActivity(intent2);
			break;
		case R.id.type3layout:
			Intent intent3 = new Intent(HomeScreenActivity.this,
					PaymentModeScreen.class);
			intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent3.putExtra("KEY", "Main");
			startActivity(intent3);
			break;
		case R.id.type4layout:
			Intent intent4 = new Intent(HomeScreenActivity.this,
					PaymentType.class);
			intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent4.putExtra("KEY", "Main");
			startActivity(intent4);
			break;
		case R.id.type5layout:
			Intent intent5 = new Intent(HomeScreenActivity.this,
					HomeScreenActivity.class);
			startActivity(intent5);
			break;

		default:
			break;
		}
	}

	/**
	 * Background Async Task to Store Order in database
	 * 
	 * @author Maulik
	 * */
	class StoreOrder extends AsyncTask<String, String, String> {

		/**
		 * Storing user
		 * */
		protected String doInBackground(String... args) {
			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("food", args.toString()));

				// getting JSON Object
				JSONObject json = jsonParser.makeHttpRequest(url_store_order,
						"POST", params);

				// check log cat for response
				Log.d("JSON",
						"JSON Response in MainActivity :" + json.toString());

				// check for success tag
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
						"Error in MainActivity.storeorder()" + je.toString());
			}
			return "Success";
		}

	}

	/**
	 * Background Async Task to menu from database
	 * 
	 * @author Maulik
	 * */
	class GetMenu extends AsyncTask<String, String, String> {

		/**
		 * Retrieving Menu
		 * */
		protected String doInBackground(String... args) {
			try {
				JSONArray dish_names = null;
				JSONArray dish_price = null;
				JSONArray dish_url = null;
				JSONArray dish_ingredients = null;

				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("data", args.toString()));

				// getting JSON Object
				JSONObject json = jsonParser.makeHttpRequest(url_get_menu,
						"POST", params);

				// check log cat for response
				Log.d("JSON",
						"JSON Response in HomeScreenActivity :"
								+ json.toString());

				// check for success tag
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {

					dish_names = json.getJSONArray("food_name");
					dish_price = json.getJSONArray("food_price");
					dish_url = json.getJSONArray("pic_url");
					dish_ingredients = json.getJSONArray("ingredients");

					if (dish_names != null && dish_price != null
							&& dish_url != null) {
						for (int i = 0; i < dish_url.length(); i++) {
							dishNames.add(dish_names.get(i).toString());
							dishUrls.add(Server + dish_url.get(i).toString());
							dishCosts.add(dish_price.getInt(i));
							dishIngredients.add(dish_ingredients.get(i).toString());
						}
					}

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
						"Error in MainActivity.storeorder()" + je.toString());
			}
			return "Success";
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			/*
			 * adapter.loadData(dishUrls); listView.setAdapter(adapter);
			 */
		}
	}
	/*
	 * public class loadImageTask extends AsyncTask<String, String, String> {
	 * Drawable imgLoad;
	 * 
	 * @Override protected void onPreExecute() { super.onPreExecute();
	 * 
	 * // progressbar.setVisibility(View.VISIBLE); }
	 * 
	 * @Override protected String doInBackground(String... params) {
	 * 
	 * String[] url = params[0].split(","); for (int i = 0; i < url.length; i++)
	 * { imgLoad = LoadImageFromWeb(url[i]); dishImages.add(imgLoad); } return
	 * "Success"; }
	 * 
	 * @Override protected void onPostExecute(String result) {
	 * super.onPostExecute(result);
	 * 
	 * 
	 * // if(progressbar.isShown()) { // progressbar.setVisibility(View.GONE);
	 * 
	 * // imgLogo.setVisibility(View.VISIBLE);
	 * 
	 * // dishImageView.setBackgroundDrawable(imgLoad); // }
	 * 
	 * } }
	 * 
	 * public static Drawable LoadImageFromWeb(String url) { try { InputStream
	 * is = (InputStream) new URL(url).getContent(); Drawable d =
	 * Drawable.createFromStream(is, "src name"); return d; } catch (Exception
	 * e) { return null; } }
	 */
}
