package com.liferay.omnihackathon.view;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import com.liferay.mobile.screens.context.User;
import com.liferay.omnihackathon.util.AndroidUtil;
import com.liferay.omnihackathon.util.Constants;
import com.liferay.omnihackathon.util.DialogUtil;
import com.liferay.omnihackathon.util.Validator;
import com.victorlaerte.supermarket.R;

public class ItemDetailActivity extends AppCompatActivity {

	private Object obj;
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_detail);

		Parcelable item = getIntent().getParcelableExtra(Constants.ITEM);
		Parcelable parcelableUser = getIntent().getParcelableExtra(Constants.USER);

		if (Validator.isNotNull(item)) {

			obj = item;
		}

		if (Validator.isNotNull(parcelableUser)) {

			user = (User) parcelableUser;
		}

		Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {

				actionFloatingButton(view);
			}
		});

		// Show the Up button in the action bar.
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}

		if (savedInstanceState == null) {
			Bundle arguments = new Bundle();
			//arguments.putParcelable(Constants.ITEM, obj);

			ItemDetailFragment fragment = new ItemDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction().add(R.id.item_detail_container, fragment).commit();
		}
	}

	public void actionFloatingButton(View view) {

		if (AndroidUtil.isNetworkAvaliable(getApplicationContext())) {


		} else {

			DialogUtil.showAlertDialog(ItemDetailActivity.this, getString(R.string.error), "Internet não disponível");
		}

		//Snackbar.make(view, getString(R.string.item_added_to_cart), Snackbar.LENGTH_LONG)
		//		.setAction("Action", null)
		//		.show();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();

		if (id == android.R.id.home) {

			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
