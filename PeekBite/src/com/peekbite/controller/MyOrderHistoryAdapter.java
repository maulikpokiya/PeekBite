package com.peekbite.controller;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.application.peekbite.R;
import com.peekbite.model.OrderHistoryItem;

public class MyOrderHistoryAdapter extends ArrayAdapter<OrderHistoryItem>{
	private final static String TAG = "MyOrderHistoryAdapter";
	
	private List<OrderHistoryItem> mItems;
	//private LayoutInflater mInflater;
	private Context mContext;
	
	
	//Constructor
	public MyOrderHistoryAdapter(Context context, int resourceId, List<OrderHistoryItem> items) {
		
		super(context, resourceId, items);
		
		Log.i(TAG, "in Constructor");//test
		
		this.mContext = context;
		this.mItems = items;
		//this.mInflater = LayoutInflater.from(context);
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
		TableLayout dishTable;
	}
	
    @Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Log.i(TAG, "in getView");//test
		
		ViewHolder holder=null;
		OrderHistoryItem orderHisItem = getItem(position);/////need?
		
		
		//LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);		
		
		//Reuse inflated convertView to reduce system consumption
		// ( Reference: http://www.codeproject.com/Articles/316690/Custom-list-item-layout )
		if(convertView == null) {
			Log.i(TAG, "in create new convertView");//test
			
			//convertView = mInflater.inflate(R.layout.order_history_item, null);
			
			//test
			LayoutInflater inflater = (LayoutInflater) mContext
	                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        convertView = inflater.inflate(R.layout.order_history_item, null);
			//test ends
			
			holder = new ViewHolder();
			
			holder.orderIdText = (TextView)convertView.findViewById(R.id.history_order_id);//solve resource later
			holder.orderDateText = (TextView)convertView.findViewById(R.id.history_order_date);
			holder.restNameText = (TextView)convertView.findViewById(R.id.history_rest_name);
			holder.dishTable = (TableLayout)convertView.findViewById(R.id.history_dishes_table);
				
			convertView.setTag(holder);
		}
		else {
			Log.i(TAG, "in get old convertView");//test
			holder = (ViewHolder) convertView.getTag();
		}

		//dishTablesss = (TableLayout)convertView.findViewById(R.id.history_dishes_table);/////
		
		//add
		OrderHistoryItem item = mItems.get(position);
		
		holder.orderIdText.setText(String.valueOf(item.getOrderId()));
		String orderDateStr = String.valueOf(item.getOrderDate());
		holder.orderDateText.setText(orderDateStr);
		holder.restNameText.setText(String.valueOf(item.getRestName()));
		
		for(int current = 0; current < item.getDishNameArr().size(); current++) {
			Log.i(TAG, "in insert TableRow for loop: round "+current);//test
			
			//create a TableRow and give it an ID
			TableRow tr = new TableRow(mContext);
			tr.setId(100 + current);
			tr.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, 
					LayoutParams.WRAP_CONTENT));
			
			//create a TextView to hold the dish name
			TextView dishname = new TextView(mContext);
			String dname = item.getDishNameArr().get(current);
			dishname.setText(dname);
			Log.i(TAG,"table row dish name: "+dname);//test
			dishname.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));
			tr.addView(dishname);
			
			//create a TextView to hold the dish price
			TextView dishprice = new TextView(mContext);
			String price = String.valueOf(item.getDishPriceArr().get(current));//int to string
			dishprice.setText(price);
			Log.i(TAG,"table row price: "+price);//test
			dishprice.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));
			tr.addView(dishprice);
			
			//add the TableRow to the TableLayout
	
			holder.dishTable.addView(tr, new TableLayout.LayoutParams(
					LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT));
			
					
		}
		//add ends
		
        return convertView;		
		
	}
    
    
}
