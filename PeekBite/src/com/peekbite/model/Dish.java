package com.peekbite.model;

public class Dish {
	
	private int orderId;
	private int dishId;
	private int dishPrice;
	//private int likes;////
	private String dishName;
	//private String dishBrief;
	private String dishDiscription;
	
	public Dish() {
		
		orderId = -1;
		dishId = -1;
		dishPrice = -1;
		//likes = -1;
		dishName = null;
		//dishBrief = null;
		dishDiscription = null;
	}
	
	public Dish(int orderid, int dsId, int dsPrice, int like, String dsName, 
			String dsBrief, String dsDiscript) {
		
		orderId = orderid;
		dishId = dsId;
		dishPrice = dsPrice;
		//likes = like;
		dishName = dsName;
		//dishBrief = dsBrief;
		dishDiscription = dsDiscript;
	}

	
	//Getters and Setters of private fields
	
	//Getters
	public int getOrderId() {
		return orderId;
	}
	
	public int getDishId() {
		return dishId;
	}
	
	public int getDishPrice() {
		return dishPrice;
	}
	
//	public int getLikes() {
//		return likes;
//	}
	
	public String getDishName() {
		return dishName;
	}
	
//	public String getDishBrief() {
//		return dishBrief;
//	}
	
	public String getDishDiscription() {
		return dishDiscription;
	}
	
	
	//Setters
	public void setOrderId(int ordId) {
		this.orderId = ordId;
	}

	public void setDishId(int dishId) {
		this.dishId = dishId;
	}

	public void setDishPrice(int dishPrice) {
		this.dishPrice = dishPrice;
	}

//	public void setLikes(int likes) {
//		this.likes = likes;
//	}

	public void setDishName(String dishName) {
		this.dishName = dishName;
	}

//	public void setDishBrief(String dishBrief) {
//		this.dishBrief = dishBrief;
//	}

	public void setDishDiscription(String dishDiscription) {
		this.dishDiscription = dishDiscription;
	}
	
	
	
}
