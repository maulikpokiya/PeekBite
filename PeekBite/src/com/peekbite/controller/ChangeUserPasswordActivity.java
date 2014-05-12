package com.peekbite.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.application.peekbite.R;
import com.peekbite.registration.JSONParser;

public class ChangeUserPasswordActivity extends Activity {
	
	private int uid = -1;//default value. Invalid value for normal user id.
	private String uname = null;
	private EditText mOldPassword;
	private EditText mNewPassword;
	private EditText mConfirmNewPw;
	private Button mConfirmChange;
	
	private final static String TAG = "ChangeUserPasswordActivity";//test
	
	//Refer to Maulik confirm_login code
	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	// url to get all products list
	private static String url_change_password = "http://peekbite.pinaksaha.me/change_password.php";
	//refering ends
	
	private int successFlag;
	private String responseMessage;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		
		if(bundle!=null) {
			Log.i(TAG, "bundle is not null. Get Extras here");//test
			uid = bundle.getInt("userID");
			uname = bundle.getString("userName");
		}
		
		mOldPassword = (EditText)findViewById(R.id.old_pw_editview);
		mNewPassword = (EditText)findViewById(R.id.new_pw_first_editview);
		mConfirmNewPw = (EditText)findViewById(R.id.new_pw_confirm_editview);
		mConfirmChange = (Button)findViewById(R.id.confirm_change_password_button);
		
		mConfirmChange.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				changePassword(uid);				
			}
		});
			
	}

	
	public void changePassword(int userid) {
		Log.i(TAG, "in changePassword");//test
		//Get all data from edittext view
		String passwordValue = mOldPassword.getText().toString();
		String newPassword = mNewPassword.getText().toString();
		String confirmNewPw = mConfirmNewPw.getText().toString();
		
		String id_str = String.valueOf(userid);
		new CheckPassword().execute(id_str, passwordValue, newPassword, confirmNewPw);
	}
	
	

	class CheckPassword extends AsyncTask<String, String, String> {

		// Progress Dialog
		private ProgressDialog pDialog;

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ChangeUserPasswordActivity.this);
			pDialog.setMessage("verifying password...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * getting All products from url
		 * */
		@Override
		protected String doInBackground(String... args) {

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("uid", args[0]));
			params.add(new BasicNameValuePair("input_pwd", args[1]));
			params.add(new BasicNameValuePair("new_pwd", args[2]));
			params.add(new BasicNameValuePair("confirm_pwd", args[3]));
			Log.i(TAG, "in doInBackGroud, args[0]= "+args[0]);//test
			Log.i(TAG, "in doInBackGroud, args[1]= "+args[1]);//test
			Log.i(TAG, "in doInBackGroud, args[2]= "+args[2]);//test
			Log.i(TAG, "in doInBackGroud, args[3]= "+args[3]);//test
			
			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url_change_password, "POST", params);

			// Check your log cat for JSON response
			Log.d(TAG, "JSON Response: " + json.toString());

			try {
				// Checking for SUCCESS TAG
				successFlag = json.getInt("success");
				responseMessage = json.getString("message");
				Log.i(TAG,"get message from server: "+responseMessage);//test

				if (successFlag == 1) {	
					Log.i(TAG, "sccessFlag == 1: "+successFlag);//test
					ChangeUserPasswordActivity.this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							// your alert dialog builder here
							new AlertDialog.Builder(ChangeUserPasswordActivity.this)
									.setIcon(android.R.drawable.ic_dialog_alert)
									.setTitle("Password Changed")
									.setMessage(responseMessage)
									.setPositiveButton("OK", new DialogInterface.OnClickListener() {
										
										@Override
										public void onClick(DialogInterface dialog, int which) {
											Intent intent = new Intent(((Dialog) dialog).getContext(), MyProfileActivity.class);
											intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//clear activity stack
											intent.putExtra("userID", uid);
											intent.putExtra("userName", uname);
											startActivity(intent);
											return;											
										}
									}).show();
						}
					});
				} 
				else if(successFlag == 0) {
					Log.i(TAG, "sccessFlag == 0: "+successFlag);//test
					ChangeUserPasswordActivity.this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							// your alert dialog builder here
							new AlertDialog.Builder(ChangeUserPasswordActivity.this)
									.setIcon(android.R.drawable.ic_dialog_alert)
									.setTitle("Error")
									.setMessage(responseMessage)
									.setPositiveButton("OK", null).show();
						}
					});

				}
			} catch (JSONException je) {
				Log.e("ERROR",
						"Error in MainActivity.doInBackground()"
								+ je.toString());
			}

			return null;
		}
		
		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		@Override
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all products
			pDialog.dismiss();
		}
	}//AsyncTask Ends

}
