/** 
 * Class Name: DialogClass
 * Author :
 * Description:DialogClass
 */
package com.peekbite.prototype;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public abstract class DialogClass implements DialogInterface.OnClickListener {

	static Context context;
	public DialogClass(Context context) {
		super();
		DialogClass.context = context;
	}

	/*
	 * Create Alert Dialog is method with parameters message that message
	 * displays as dialog
	 */
	public static void createDAlertDialog(final Context context, final String message, boolean calncelable) {
		String setmessage = message;
		AlertDialog.Builder alertbox = new AlertDialog.Builder(context);
		alertbox.setTitle("Alert Message");
		alertbox.setCancelable(calncelable);
		alertbox.setMessage(setmessage);
		alertbox.setNeutralButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				Intent intent = new Intent(context, HomeScreenActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				context.startActivity(intent);
			}
		});
		alertbox.show();
	}
	
	public static void createSessionAlertDialog(final Context context, final String message, boolean calncelable) {
		String setmessage = message;
		AlertDialog.Builder alertbox = new AlertDialog.Builder(context);
		alertbox.setTitle("Alert Message");
		alertbox.setCancelable(calncelable);
		alertbox.setMessage(setmessage);
		alertbox.setNeutralButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
//				Intent intent = new Intent(context, MainActivity.class);
//				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				context.startActivity(intent);
			}
		});
		alertbox.show();
	}
}