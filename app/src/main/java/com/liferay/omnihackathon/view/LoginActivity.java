package com.liferay.omnihackathon.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import com.liferay.mobile.android.auth.Authentication;
import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.login.LoginScreenlet;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.context.storage.CredentialsStorageBuilder;
import com.liferay.omnihackathon.util.Constants;
import com.victorlaerte.supermarket.R;

public class LoginActivity extends AppCompatActivity {

	private static final String TAG = LoginActivity.class.getName();
	private EditText emailView;
	private EditText passwordView;
	private LoginScreenlet loginScreenlet;
	private TextView signupButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		signupButton = (TextView) findViewById(R.id.sign_up_button);

		loginScreenlet = (LoginScreenlet) findViewById(R.id.login_screenlet);
        emailView = (EditText) loginScreenlet.findViewById(R.id.liferay_login);
        passwordView = (EditText) loginScreenlet.findViewById(R.id.liferay_password);

        loginScreenlet.setListener(new LoginListener() {
			@Override
			public void onLoginSuccess(User user) {
                initMainActivity(user);
			}

			@Override
			public void onLoginFailure(Exception e) {
                Snackbar.make(loginScreenlet, "Could not login", 1000).show();
			}
		});

		signupButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
			}
		});

		tryPersistentLogin();
	}

	private void tryPersistentLogin() {

        CredentialsStorageBuilder.StorageType credentialsStorage =
            loginScreenlet.getCredentialsStorage();

        SessionContext.loadStoredCredentials(credentialsStorage);

        User currentUser = SessionContext.getCurrentUser();
        Authentication authentication = SessionContext.getAuthentication();

        if (currentUser != null) {
            initMainActivity(currentUser);
        }
	}

	private void initMainActivity(User user) {

		Intent intent = new Intent(getApplicationContext(), ItemListActivity.class);
		intent.putExtra(Constants.USER, user);

		startActivity(intent);

		finish();
	}
}