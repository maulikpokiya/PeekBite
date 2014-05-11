package com.peekbite.model;

import android.app.Application;

public class TotalQuantity extends Application{

	private int numberofItems=0;

	public int getNumberofItems() {
		return numberofItems;
	}

	public void setNumberofItems(int numberofItems) {
		this.numberofItems = numberofItems;
	}
	 @Override    
	    public void onCreate() {    
	        // TODO Auto-generated method stub    
	        super.onCreate();    
	        setNumberofItems(0);   //初始化全局变量    
	    }    
}
