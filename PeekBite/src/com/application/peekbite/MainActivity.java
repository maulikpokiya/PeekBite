package com.application.peekbite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
//import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.facebook.*;
import com.facebook.model.*;

import com.facebook.Request;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.OnErrorListener;
import com.peekbite.registration.FrontPageActivity;
import com.peekbite.registration.JSONParser;
import com.peekbite.registration.RegistrationActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
//import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
//import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	// start of code by Maulik
	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	// JSON Node names
	private static final String TAG_SUCCESS = "success";

	protected static final String TAG = "MainActivity";

	// url to get all products list
	private static String url_confirm_login = "http://peekbite.pinaksaha.me/confirm_login.php";

	// end of code by Maulik
	
	
	private EditText emailEditText = null;
	private EditText pwdEditText = null;
	private TextView signUpLink;
	private Button logInButton, fbAuthButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitle("Log in");

		emailEditText = (EditText) findViewById(R.id.et_email);
		pwdEditText = (EditText) findViewById(R.id.et_password);
		
		signUpLink = (TextView)findViewById(R.id.SignUpLink);
		logInButton = (Button)findViewById(R.id.logInButton);
		fbAuthButton = (Button)findViewById(R.id.authButton);

		signUpLink.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
				startActivity(intent);
			}
		});

		logInButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				login();
			}
		});
/*
		fbAuthButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {				
				fbLogin();
			}
		});*/
	}

	@Override
	 public void onActivityResult(int requestCode, int resultCode, Intent data) {
	     super.onActivityResult(requestCode, resultCode, data);
	     Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	     
	     if(resultCode == -1) {
	     Intent frontPage = new Intent(getApplicationContext(),
					FrontPageActivity.class);
			// Closing all previous activities
			// i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(frontPage);
	     }
	 }

	
	/**
	 * log out and clear the entire session
	 */
	//Override
	protected void onNewIntent(Intent intent) {

	super.onNewIntent(intent);
	//ÍË³ö
	        if ((Intent.FLAG_ACTIVITY_CLEAR_TOP & intent.getFlags()) != 0) {
	               finish();
	        }
	}

	public void fbLogin() {
		  // start Facebook Login
	    Session.openActiveSession(this, true, new Session.StatusCallback() {

	      // callback when session changes state
	      @Override
	      public void call(Session session, SessionState state, Exception exception) {
	        if (session.isOpened()) {

	          // make request to the /me API
	          Request.newMeRequest(session, new Request.GraphUserCallback() {

	            // callback after Graph API response with user object
	            @Override
	            public void onCompleted(GraphUser user, Response response) {
	              if (user != null) {
//	                TextView welcome = (TextView) findViewById(R.id.welcome);
//	                welcome.setText("Hello " + user.getName() + "!");
	            	  Intent frontPage = new Intent(getApplicationContext(),
								FrontPageActivity.class);
						// Closing all previous activities
						// i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(frontPage);
	              }
	            }
	          }).executeAsync();
	        }
	      }
	    });
	}
	
	public void login() {
		String Email = emailEditText.getText().toString().trim();
		String password = pwdEditText.getText().toString().trim();

		if (TextUtils.isEmpty(Email) || TextUtils.isEmpty(password)) {
			Toast.makeText(this, "Email or password can't be null.",
					Toast.LENGTH_LONG).show();
		} else {
			/**
			 * RETRIVE DATA FROM DATABASE
			 */
			// Start of code by Maulik

			// Check login details in Background Thread
			new ConfirmLogin().execute(Email, password);

			// End of code by Maulik
		}
	}



	// Start Code change by Maulik
	/**
	 * Background Async Task to check login details by making HTTP Request
	 * */
	class ConfirmLogin extends AsyncTask<String, String, String> {

		// Progress Dialog
		private ProgressDialog pDialog;

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("verifying user...");
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
			params.add(new BasicNameValuePair("uname", args[0]));
			params.add(new BasicNameValuePair("password", args[1]));

			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url_confirm_login,
					"POST", params);

			// Check your log cat for JSON response
			Log.d("JSON", "JSON Response in MainActivity: " + json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);
				int id = json.getInt("id");
				String uname = json.getString("uname");

				if (success == 1) {

					Intent frontPage = new Intent(getApplicationContext(),
							FrontPageActivity.class);
					//add
					frontPage.putExtra("userID", id);
					frontPage.putExtra("userName", uname);
					//add ends
					// Closing all previous activities
					// i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(frontPage);

				} else {
					MainActivity.this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							// your alert dialog builder here
							new AlertDialog.Builder(MainActivity.this)
									.setIcon(android.R.drawable.ic_dialog_alert)
									.setTitle("Error")
									.setMessage(
											"Incorrect Email or Password.")
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
	}
	// End code change by Maulik
}
