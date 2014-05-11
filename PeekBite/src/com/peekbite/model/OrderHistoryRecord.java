package com.peekbite.model;

import java.util.ArrayList;
import java.util.Date;

public class OrderHistoryRecord {
	
	private int orderId;
	private int restID;
	private String restName;
	private int totalPrice;
	private Date orderDate;
	private ArrayList<Dish> orderDishesArr;
	
	
	public OrderHistoryRecord() {
		
		orderId = -1;
		restID = -1;
		totalPrice = -1;
		restName = null;
		orderDate = null;
		orderDishesArr = new ArrayList<Dish>();
	}
	
	public OrderHistoryRecord(int oid, int restid, int price, 
			String restname, String dishname, Date date) {
		
		orderId = oid;
		restID = restid;
		totalPrice = price;
		restName = restname;
		orderDate = date;
		orderDishesArr = new ArrayList<Dish>();
	}
	
	
	public OrderHistoryRecord(int oid, int restid, int price, 
			String restname, String dishname, Date date, ArrayList<Dish> arrDish) {
		
		orderId = oid;
		restID = restid;
		totalPrice = price;
		restName = restname;
		orderDate = date;
		orderDishesArr = arrDish;
	}


	
	
	//Getters and Setters of private fields
	
	//Getters
	public int getOrderId() {
		return orderId;
	}
	
	public int getRestID() {
		return restID;
	}
	
	public int getTotalPrice() {
		return totalPrice;
	}
	
	public String getRestName() {
		return restName;
	}
	
	public Date getOrderDate() {
		return orderDate;
	}
	
	public ArrayList<Dish> getOrderDishesArr() {
		return orderDishesArr;
	}

	
	
	//Setters

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public void setRestID(int restID) {
		this.restID = restID;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void setRestName(String restName) {
		this.restName = restName;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public void addDishes(Dish dish) {
		orderDishesArr.add(dish);
	}
	
	
}
