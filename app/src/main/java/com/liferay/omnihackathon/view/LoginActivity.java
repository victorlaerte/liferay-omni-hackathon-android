package com.liferay.omnihackathon.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.login.LoginScreenlet;
import com.liferay.mobile.screens.context.User;
import com.liferay.omnihackathon.util.AndroidUtil;
import com.liferay.omnihackathon.util.Constants;
import com.liferay.omnihackathon.util.DialogUtil;
import com.liferay.omnihackathon.util.StringPool;
import com.liferay.omnihackathon.util.Validator;
import com.victorlaerte.supermarket.R;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

	private static final String TAG = LoginActivity.class.getName();
	//private EditText nameView;
	private EditText emailView;
	private EditText passwordView;
	//private Button primaryActionButton;
	private LoginScreenlet loginScreenlet;
	private TextView signupButton;
	private View progressView;
	private View loginFormView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		//emailView = (EditText) findViewById(R.id.email);
		//nameView = (EditText) findViewById(R.id.name);
		//passwordView = (EditText) findViewById(R.id.password);
		loginFormView = findViewById(R.id.login_form);
		progressView = findViewById(R.id.login_progress);
		//primaryActionButton = (Button) findViewById(R.id.primary_action_button);
		signupButton = (TextView) findViewById(R.id.sign_up_button);

		loginScreenlet = (LoginScreenlet) findViewById(R.id.login_screenlet);
        emailView = (EditText) loginScreenlet.findViewById(R.id.liferay_login);
        passwordView = (EditText) loginScreenlet.findViewById(R.id.liferay_password);

        loginScreenlet.setListener(new LoginListener() {
			@Override
			public void onLoginSuccess(User user) {
                //user.get
                initMainActivity(user);
				Snackbar.make(loginScreenlet, "SUCCESS", 1000).show();
			}

			@Override
			public void onLoginFailure(Exception e) {
                Intent it = new Intent(getApplicationContext(), ItemListActivity.class);
                startActivity(it);

                Snackbar.make(loginScreenlet, "ERROR", 1000).show();
			}
		});

		signupButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				//showSignUpFormToggle(signUp);
			}
		});

		//tryPersistentLogin();
	}

	private void tryPersistentLogin() {

		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		String userStr = preferences.getString(Constants.USER, null);

		if (Validator.isNotNull(userStr)) {

			//try {

				//JSONObject userJSON = new JSONObject(userStr);
				//User user = new UserImpl(userJSON);
                //
				//if (Validator.isNotNull(user)) {
				//	initMainActivity(user);
				//}

			//} catch (JSONException e) {
			//	Log.e(TAG, e.getMessage());
			//}
		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {

		int shortAnimTime = getResources().getInteger(android.R.integer.config_mediumAnimTime);

		if (AndroidUtil.isAnimationAvailable()) {

			loginFormView	.animate()
							.setDuration(shortAnimTime)
							.alpha(show ? 0 : 1)
							.setListener(new AnimatorListenerAdapter() {

								@Override
								public void onAnimationEnd(Animator animation) {

									loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
								}
							});

			progressView.animate()
						.setDuration(shortAnimTime)
						.alpha(show ? 1 : 0)
						.setListener(new AnimatorListenerAdapter() {

							@Override
							public void onAnimationEnd(Animator animation) {

								progressView.setVisibility(show ? View.VISIBLE : View.GONE);
							}
						});
		} else {

			progressView.setVisibility(show ? View.VISIBLE : View.GONE);
			loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}

	}

	private void initMainActivity(User user) {

		Intent intent = new Intent(getApplicationContext(), ItemListActivity.class);
		intent.putExtra(Constants.USER, user);

		startActivity(intent);

		finish();
	}
}