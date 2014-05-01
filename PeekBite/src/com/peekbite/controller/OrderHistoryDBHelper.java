package com.peekbite.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.peekbite.model.Dish;
import com.peekbite.model.OrderHistoryRecord;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class OrderHistoryDBHelper extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "peekbiteDB";
	
	private static final String TABLE_ORDER_HISTORY = "order_history";
	private static final String COLUMN_HISTORY_ID = "order_hisroty_id"; 
	private static final String COLUMN_RESTUARANT_ID = "rest_id";
	private static final String COLUMN_RESTUARANT_NAME = "rest_name";
	private static final String COLUMN_ORDER_PRICE = "order_price";
	private static final String COLUMN_ORDER_DATE= "order_date";
	//private static final String COLUMN_TRIP_GIVEN_ID = "given_id"; ////
	
	private static final String TABLE_DISH ="dish";
	private static final String COLUMN_ORDER_ID = "order_id";// reference history(order_history_id)
	private static final String COLUMN_DISH_ID ="dish_id";
	private static final String COLUMN_DISH_NAME = "dish_name";
	private static final String COLUMN_DISH_PRICE ="dish_price";
	private static final String COLUMN_DISH_DISCRIPTION ="dish_discription";
	
	private final static String TAG = "OrderHistoryDBHelper";
	
	
	public OrderHistoryDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i(TAG, "in onCreate(SQLiteDatabase db");//test
		
		//create OrderHistory table
		db.execSQL("create table" + TABLE_ORDER_HISTORY + "("
				+ COLUMN_HISTORY_ID + " integer primary key antoincrement, "
				+ COLUMN_RESTUARANT_ID + " integer, "
				+ COLUMN_RESTUARANT_NAME + " text, "
				+ COLUMN_ORDER_PRICE + "integer, "
				+ COLUMN_ORDER_DATE + " text)");
		
		db.execSQL("create table" + TABLE_DISH + "("
				+ COLUMN_ORDER_ID + " integer references order_history(order_history_id), "
				+ COLUMN_DISH_ID + " integer, "
				+ COLUMN_DISH_NAME + " text, "
				+ COLUMN_DISH_PRICE + "integer, "
				+ COLUMN_DISH_DISCRIPTION + " text)");
		
	}
	
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		Log.i(TAG, "in onUpgrade");//test
		
		//Drop older table if exists
		db.execSQL("DROP TABLE IF EXISTS" + TABLE_DISH);
		db.execSQL("DROP TABLE IF EXISTS" + TABLE_ORDER_HISTORY);
		
		//Create tables again
		onCreate(db);
	}
	
	
	public void rebuildTable() {
		SQLiteDatabase db = this.getReadableDatabase();
		this.onUpgrade(db, DATABASE_VERSION, DATABASE_VERSION);
	}
	
	
	public long insertOrderHistory(OrderHistoryRecord orderHistory) {
		Log.i(TAG, "in insertOrderHistory");//test
		
		ContentValues cv = new ContentValues();
		
		cv.put(COLUMN_RESTUARANT_ID, orderHistory.getRestID());
		cv.put(COLUMN_RESTUARANT_NAME, orderHistory.getRestName());
		cv.put(COLUMN_ORDER_PRICE, orderHistory.getTotalPrice());
		String orderDateStr = String.valueOf(orderHistory.getOrderDate());//add for HW4
		Log.d(TAG,"order date string is:" + orderDateStr);//test
		cv.put(COLUMN_ORDER_DATE, orderDateStr);
		
		//return a new orderHistory
		return getWritableDatabase().insert(TABLE_ORDER_HISTORY, null, cv);
		
	}
	
	
	public long insertDish(Dish dish) {
		
		Log.i(TAG, "in insertDish(Dish dish)");
		
		//before insert dish, we need to get the order_history_id
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from " + TABLE_ORDER_HISTORY
				+ " where orderId = (select max(orderId) from order_history", null);
		cursor.moveToFirst();
		int orderIdInDB = cursor.getInt(0);
		
		ContentValues cv = new ContentValues();
		
		cv.put(COLUMN_ORDER_ID, orderIdInDB);
		cv.put(COLUMN_DISH_ID, dish.getDishId());
		cv.put(COLUMN_DISH_NAME, dish.getDishName());
		cv.put(COLUMN_DISH_PRICE, dish.getDishPrice());
		cv.put(COLUMN_DISH_DISCRIPTION, dish.getDishDiscription());
		
		return getWritableDatabase().insert(TABLE_DISH, null, cv);
	}
	
	
	public List<OrderHistoryRecord> getAllOrders() {
		Log.i(TAG, "in getAllDishes");//test
		
		List<OrderHistoryRecord> orderList = new ArrayList<OrderHistoryRecord>();
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from " + TABLE_ORDER_HISTORY, null);
		
		//Loop through all query results
		for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			
			OrderHistoryRecord orderHistoryRecord = new OrderHistoryRecord();
			
			orderHistoryRecord.setOrderId(cursor.getInt(0));
			orderHistoryRecord.setRestID(cursor.getInt(1));
			orderHistoryRecord.setRestName(cursor.getString(2));
			orderHistoryRecord.setTotalPrice(cursor.getInt(3));
			String orderDateStr = cursor.getString(4);
			Date dateTime = new Date(orderDateStr); //add for HW4
			orderHistoryRecord.setOrderDate(dateTime);
			
			orderList.add(orderHistoryRecord);
		}
		
		return orderList;
		
	}
	
	
	public List<Dish> getDish(int ordId) {
		Log.i(TAG, "in getDish(int ordId)");//test
		
		List<Dish> dishList = new ArrayList<Dish>();
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from " + TABLE_DISH 
				+ "where orderId = " + ordId, null);
		
		//Loop through all query results
		for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			
			Dish dish = new Dish();
			
			dish.setOrderId(cursor.getInt(0));
			dish.setDishId(cursor.getInt(1));
			dish.setDishPrice(cursor.getInt(2));
			dish.setDishName(cursor.getString(3));
			dish.setDishDiscription(cursor.getString(4));
			
			dishList.add(dish);
		}
		
		return dishList;
	}
	
	
	
}// class ENDS


