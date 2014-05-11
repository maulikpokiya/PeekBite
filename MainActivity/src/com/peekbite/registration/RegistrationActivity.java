package com.peekbite.registration;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.application.peekbite.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationActivity extends Activity {

	private EditText userFirstName = null;
	private EditText userLastName = null;
	private EditText emailEditText = null;
	private EditText pwdEditText = null;
	private EditText pwsEditTextConfirm = null;
	private CheckBox acceptCheckBox = null;
	private Button signUpSaveButton;

	// Added for Database task by Maulik

	JSONParser jsonParser = new JSONParser();

	// url to create new user
	private static String signup_user_url = "http://peekbite.pinaksaha.me/signup_user.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";

	// End by Maulik
	Intent intent = new Intent();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		setTitle("Registration");

		userFirstName = (EditText)findViewById(R.id.signupUserFirstName);
		userLastName = (EditText)findViewById(R.id.signupUserLastName);
		pwdEditText = (EditText)findViewById(R.id.singupPwd);
		pwsEditTextConfirm = (EditText)findViewById(R.id.singupPwd2);
		acceptCheckBox = (CheckBox)findViewById(R.id.check);
		emailEditText = (EditText)findViewById(R.id.signupEmail);
		signUpSaveButton = (Button)findViewById(R.id.registrationButton);
		
		signUpSaveButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				signup();
				
			}
		});
				
	}
	
	
	

	public void signup() {

		String email = emailEditText.getText().toString().trim();
		String firstName = userFirstName.getText().toString().trim();
		String lastName = userLastName.getText().toString().trim();
		String password = pwdEditText.getText().toString().trim();
		String passwordConfirm = pwsEditTextConfirm.getText().toString().trim();

		if (TextUtils.isEmpty(email) || TextUtils.isEmpty(firstName)
				|| TextUtils.isEmpty(lastName)
				|| TextUtils.isEmpty(password)
				|| TextUtils.isEmpty(passwordConfirm)) {
			Toast.makeText(this,
					"Username, password or email address can't be null.",
					Toast.LENGTH_LONG).show();
		} else if (!password.equals(passwordConfirm)) {
			new AlertDialog.Builder(RegistrationActivity.this)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle("Alert")
					.setMessage("You entered different passwords")
					.setPositiveButton("Confirm", null).show();
			return;
		} else {
			/**
			 * !!!!!NEED TO WRITE INFORMATION TO DATABASE
			 */

			if (acceptCheckBox.isChecked()) {

				intent.putExtra("firstname", firstName);
				intent.putExtra("lastname", lastName);
				intent.putExtra("password", password);
				intent.putExtra("email", email);

				// Begin changes for Database task by Maulik

				// creating new user in background thread
				new CreateNewUser().execute(firstName, lastName, password, email);

				// End of changes by Maulik

			} else {
				Toast.makeText(this, "Please accept.....blahblahblah",
						Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registration, menu);
		return true;
	}

	// Begin changes by Maulik
	/**
	 * Background Async Task to Create new user
	 * */
	class CreateNewUser extends AsyncTask<String, String, String> {

		// Progress Dialog
		private ProgressDialog pDialog;

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(RegistrationActivity.this);
			pDialog.setMessage("Creating User..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Creating user
		 * */
		@Override
		protected String doInBackground(String... args) {

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("firstname", args[0]));
			params.add(new BasicNameValuePair("lastname", args[1]));
			params.add(new BasicNameValuePair("password", args[2]));
			params.add(new BasicNameValuePair("uname", args[3]));

			// getting JSON Object
			JSONObject json = jsonParser.makeHttpRequest(signup_user_url,
					"POST", params);

			// check log cat for response
			Log.d("JSON",
					"JSON Response in RegistrationActivity :" + json.toString());

			// check for success tag
			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {

					intent.setClass(RegistrationActivity.this,
							RegistrationFinished.class);
					RegistrationActivity.this.startActivity(intent);
					RegistrationActivity.this.finish();
				} else {
					// failed to create User
					RegistrationActivity.this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							// your alert dialog builder here
							new AlertDialog.Builder(RegistrationActivity.this)
									.setIcon(android.R.drawable.ic_dialog_alert)
									.setTitle("Error")
									.setMessage(
											"Oops, Error occurred while signing up.")
									.setPositiveButton("OK", null).show();
						}
					});

				}
			} catch (JSONException je) {
				Log.e("ERROR",
						"Error in RegistrationActivity.signup()"
								+ je.toString());
			}
			return "Success";
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
	// End of changes by Maulik
}
