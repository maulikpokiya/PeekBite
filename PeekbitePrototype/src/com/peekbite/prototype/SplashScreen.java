package com.peekbite.prototype;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class SplashScreen extends Activity {
	
	private Runnable runnable;
	int sleepTime = 1500;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);
		
		

		/*
		 * Runnable thread for holding the screen for 5000 ms open Display
		 * activity.
		 */
		runnable = new Runnable() {
			public void run() {
				try {
					Thread.sleep(sleepTime);
					Intent in = new Intent(SplashScreen.this, MainActivity.class);
					startActivity(in);
					finish();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		try {
			Thread t = new Thread(null, runnable);
			t.start();
		} catch (Exception e) {

		}
	}

}
