package com.liferay.omnihackathon.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import com.liferay.mobile.android.auth.Authentication;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.push.Push;
import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.login.LoginScreenlet;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.context.storage.CredentialsStorageBuilder;
import com.liferay.omnihackathon.R;
import com.liferay.omnihackathon.util.Constants;
import com.liferay.omnihackathon.util.StringPool;
import org.json.JSONException;
import org.json.JSONObject;

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

                registerPushNotification(user);
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

    private void registerPushNotification(final User user) {

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String registrationId =
            sharedPref.getString("registrationId", StringPool.BLANK);
        //
        //if (!registrationId.isEmpty()) {
        //    return;
        //}

        try {
            final SharedPreferences.Editor editor = sharedPref.edit();

            final Session session = SessionContext.createSessionFromCurrentSession();

            Push push = Push.with(session);
            push.withPortalVersion(70);
            push.onSuccess(new Push.OnSuccess() {

                @Override
                public void onSuccess(JSONObject device) {
                    try {
                        String registrationId = device.getString("token");
                        editor.putString("registrationId", registrationId);
                        editor.commit();

                        JSONObject notification = new JSONObject();
                        notification.put("message", "Hello!");

                        Push.with(session).send(user.getId(), notification);

                    } catch (Exception je) {
                        Log.e("LOGIN", "Could not parse device's JSON.", je);
                    }
                }

            })
                .onFailure(new Push.OnFailure() {

                    @Override
                    public void onFailure(Exception e) {
                        Log.e("LOGIN", "Could not register device to portal.", e);
                    }

                })
                .register(getApplicationContext(), "82045552649");
        }
        catch (Exception e) {
            Log.e("LOGIN", "Could not register device.", e);
        }
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
        intent.putExtra(Constants.USER, new JSONObject(user.getValues()).toString());

		startActivity(intent);

		finish();
	}
}