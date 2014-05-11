package com.peekbite.model;

import java.util.ArrayList;
import java.util.Date;

public class OrderHistoryItem {
	
	private int mOrderId;
	private String mRestName;
	private int mRestId;
	private Date orderDate;
	private ArrayList<Integer> mDishImageIdArr;
	//private ArrayList<Integer> mDishIdArr;
	private ArrayList<String> mDishNameArr;
	private ArrayList<Integer> mDishPriceArr;
	
	public OrderHistoryItem(int orderId, String restName, 
			int restId, Date date, ArrayList<Integer> dishImageArr, 
			ArrayList<String> dishNameArr, ArrayList<Integer> dishPriceArr) {
		
		mOrderId = orderId;
		mRestName = restName;
		mRestId = restId;
		orderDate = date;
		mDishImageIdArr = dishImageArr;
		mDishNameArr = dishNameArr;
		mDishPriceArr = dishPriceArr;
	}

	
	//Getters
	public int getOrderId() {
		return mOrderId;
	}

	public String getRestName() {
		return mRestName;
	}
	
	public int getRestId() {
		return mRestId;
	}
	
	public Date getOrderDate() {
		return orderDate;
	}
	
	public ArrayList<Integer> getDishImageIdArr() {
		return mDishImageIdArr;
	}
	
	public ArrayList<String> getDishNameArr() {
		return mDishNameArr;
	}
	
	public ArrayList<Integer> getDishPriceArr() {
		return mDishPriceArr;
	}

	
	//Setters
	public void setOrderId(int orderId) {
		mOrderId = orderId;
	}

	public void setRestName(String restName) {
		mRestName = restName;
	}

	public void setRestId(int restId) {
		mRestId = restId;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public void addDishImageIdArr(int dishImageId) {
		mDishImageIdArr.add(dishImageId);
	}

	public void addDishNameArr(String dishName) {
		mDishNameArr.add(dishName);
	}

	public void setDishPriceArr(int dishPrice) {
		mDishPriceArr.add(dishPrice);
	}
	
	
}
