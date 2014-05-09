package com.peekbite.controller;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.application.peekbite.R;
import com.peekbite.model.OrderHistoryItem;

public class MyOrderHistoryAdapter extends ArrayAdapter<OrderHistoryItem>{
	private final static String TAG = "MyOrderHistoryAdapter";
	
	private List<OrderHistoryItem> mItems;
	private Context mContext;
	
	
	//Constructor
	public MyOrderHistoryAdapter(Context context, int resourceId, List<OrderHistoryItem> items) {
		
		super(context, resourceId, items);
		
		Log.i(TAG, "in Constructor");//test
		
		this.mContext = context;
		this.mItems = items;
	}
	
    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public OrderHistoryItem getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
	
    
	//static class ViewHolder: to reduce the payload of loading layout elements everytime
	static class ViewHolder {
		
		TextView orderIdText;
		TextView orderDateText;
		TextView restNameText;
		LinearLayout dishesLayout;
	}
	
    @Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Log.i(TAG, "in getView");//test
		
		ViewHolder holder=null;

		//Reuse inflated convertView to reduce system consumption
		// ( Reference: http://www.codeproject.com/Articles/316690/Custom-list-item-layout )
		if(convertView == null) {
			Log.i(TAG, "in create new convertView");//test
			
			LayoutInflater inflater = (LayoutInflater) mContext
	                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        convertView = inflater.inflate(R.layout.order_history_item, null);
			
			holder = new ViewHolder();
			
			holder.orderIdText = (TextView)convertView.findViewById(R.id.history_order_id);//solve resource later
			holder.orderDateText = (TextView)convertView.findViewById(R.id.history_order_date);
			holder.restNameText = (TextView)convertView.findViewById(R.id.history_rest_name);
			holder.dishesLayout = (LinearLayout)convertView.findViewById(R.id.history_item_dishes_layout);
				
			convertView.setTag(holder);
			
//			OrderHistoryItem item = mItems.get(position);
//			Log.i(TAG, "The Item position is: "+position);//test
//			
//			String orderid = String.valueOf(item.getOrderId());
//			holder.orderIdText.setText("Order ID: #" + orderid);
//			Log.i(TAG, "FINAL order ID in item is: "+orderid);//test
//			String orderDateStr = String.valueOf(item.getOrderDate());
//			holder.orderDateText.setText(orderDateStr);
//			holder.restNameText.setText("Restaurant: " + String.valueOf(item.getRestName()));
//			
//			
//			for(int i=0; i<item.getDishNameArr().size(); i++) {
//	    		LinearLayout LL = new LinearLayout(mContext);
//	        	//LL.setBackgroundColor(Color.CYAN);//test
//	            LL.setOrientation(LinearLayout.HORIZONTAL);
//	            
//	            LayoutParams LLParams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
//
//	            //LL.setWeightSum(6f);
//	            LL.setLayoutParams(LLParams);
//	            
//	            TextView dishName = new TextView(mContext);
//	            TextView dishPrice = new TextView(mContext);
//	            
//	            dishName.setText(item.getDishNameArr().get(i));
//	            Log.i(TAG, "dish name is for No."+i+"dish is: "+ dishName.getText());//test
//	            String price = String.valueOf(item.getDishPriceArr().get(i));//int to string
//				dishPrice.setText(price);
//	            Log.i(TAG, "dish price is for No."+i+"dish is: "+ dishPrice.getText());//test
//	            
//	            dishName.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f) );
//	            dishPrice.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f) );
//	                        
//	            LL.addView(dishName);
//	            LL.addView(dishPrice);
//	            
//	            holder.dishesLayout.addView(LL);
//	    	} 
			
		}
		else {
			Log.i(TAG, "in get old convertView");//test
			holder = (ViewHolder) convertView.getTag();
			
//			holder.orderIdText = (TextView)convertView.findViewById(R.id.history_order_id);//solve resource later
//			holder.orderDateText = (TextView)convertView.findViewById(R.id.history_order_date);
//			holder.restNameText = (TextView)convertView.findViewById(R.id.history_rest_name);
//			holder.dishesLayout = (LinearLayout)convertView.findViewById(R.id.history_item_dishes_layout);
		}
		
		
		OrderHistoryItem item = mItems.get(position);
		Log.i(TAG, "The Item position is: "+position);//test
		
		String orderid = String.valueOf(item.getOrderId());
		holder.orderIdText.setText("Order ID: #" + orderid);
		Log.i(TAG, "FINAL order ID in item is: "+orderid);//test
		String orderDateStr = String.valueOf(item.getOrderDate());
		holder.orderDateText.setText(orderDateStr);
		holder.restNameText.setText("Restaurant: " + String.valueOf(item.getRestName()));
		
		holder.dishesLayout.removeAllViews();
		
		for(int i=0; i<item.getDishNameArr().size(); i++) {
    		LinearLayout LL = new LinearLayout(mContext);
        	//LL.setBackgroundColor(Color.CYAN);//test
            LL.setOrientation(LinearLayout.HORIZONTAL);
            
            LayoutParams LLParams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);

            //LL.setWeightSum(6f);
            LL.setLayoutParams(LLParams);
            
            TextView dishName = new TextView(mContext);
            TextView dishPrice = new TextView(mContext);
            
            dishName.setText(item.getDishNameArr().get(i));
            Log.i(TAG, "dish name is for No."+i+"dish is: "+ dishName.getText());//test
            String price = String.valueOf(item.getDishPriceArr().get(i));//int to string
			dishPrice.setText(price);
            Log.i(TAG, "dish price is for No."+i+"dish is: "+ dishPrice.getText());//test
            
            dishName.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f) );
            dishPrice.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f) );
                        
            LL.addView(dishName);
            LL.addView(dishPrice);
            
            holder.dishesLayout.addView(LL);
		}
            
        return convertView;	
        
	}
    
    
}
