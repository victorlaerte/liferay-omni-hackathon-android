package com.liferay.omnihackathon.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import com.liferay.mobile.push.Push;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.context.storage.CredentialsStorageBuilder;
import com.liferay.omnihackathon.R;
import com.liferay.omnihackathon.util.AndroidUtil;
import com.liferay.omnihackathon.util.Constants;
import com.liferay.omnihackathon.util.DialogUtil;
import com.liferay.omnihackathon.util.StringPool;
import com.liferay.omnihackathon.util.Validator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class ItemListActivity extends AppCompatActivity {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private static final String TAG = ItemListActivity.class.getName();
	private User user;
	private SimpleItemRecyclerViewAdapter simpleItemRecyclerViewAdapter;
	private AlertDialog alertDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		Bundle bundle = getIntent().getExtras();

		try {
			user = new User(new JSONObject(bundle.getString(Constants.USER)));

			if (user == null) {
				logout();
			}

		} catch (Exception e) {
		}


		setContentView(R.layout.activity_item_list);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		toolbar.setTitle(getTitle());

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(final View view) {

			}
		});

		loadContent();
	}

	private void loadContent() {

		if (AndroidUtil.isNetworkAvaliable(this)) {

			showProgress(true);

			//GetCartItemsTask getCartItemsTask = new GetCartItemsTask(this, user);
			//getCartItemsTask.execute();
            //
			//GetMarketItemsTask getMarketItemsTask = new GetMarketItemsTask(this, user, null);
			//getMarketItemsTask.execute();

		} else {
			//Snackbar.make()
		}
	}

	public void setupRecyclerView(boolean success, String errorMsg, List<Object> list) {

		View view = findViewById(R.id.item_list);

		showProgress(false);

		if (Validator.isNotNull(view)) {

			RecyclerView recyclerView = (RecyclerView) view;

			if (success) {

				if (Validator.isNull(simpleItemRecyclerViewAdapter)) {

					//simpleItemRecyclerViewAdapter = new SimpleItemRecyclerViewAdapter(ItemListActivity.this,
					//		marketItemList, user, twoPane);
				}

				recyclerView.setAdapter(simpleItemRecyclerViewAdapter);

			} else {

				if (errorMsg.equals(StringPool.BLANK)) {

					errorMsg = getString(R.string.error_unknown_error) + "\n" + getString(
							R.string.error_contact_administrator);

				}

				DialogUtil.showAlertDialog(ItemListActivity.this, getString(R.string.error), errorMsg);
			}
		}
	}

	private void showProgress(final boolean show) {

		final View progressView = findViewById(R.id.item_list_progress);

		progressView.setVisibility(show ? View.VISIBLE : View.GONE);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);

		final MenuItem searchItem = menu.findItem(R.id.action_search);
		final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {

				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {

				return false;
			}
		});

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();

		if (id == R.id.logout) {

			try {
				logout();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;

		}

		return super.onOptionsItemSelected(item);
	}

	private void logout() throws Exception {

		SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
		String registrationId = sharedPref.getString("registrationId", "");
		SharedPreferences.Editor edit = sharedPref.edit();
		edit.clear();
		edit.commit();

		Push.with(SessionContext.createSessionFromCurrentSession()).unregister(registrationId);

		SessionContext.removeStoredCredentials(CredentialsStorageBuilder.StorageType.SHARED_PREFERENCES);
		SessionContext.logout();

		Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
		startActivity(intent);

		finish();
	}
}
