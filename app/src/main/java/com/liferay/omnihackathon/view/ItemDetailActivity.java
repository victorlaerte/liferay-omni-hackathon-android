package com.liferay.omnihackathon.view;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.liferay.omnihackathon.R;
import com.liferay.omnihackathon.model.Processo;
import com.liferay.omnihackathon.util.Constants;

public class ItemDetailActivity extends AppCompatActivity {

	private Processo processo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_detail);

		processo = getIntent().getParcelableExtra(Constants.ITEM);

		Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
		setSupportActionBar(toolbar);

		// Show the Up button in the action bar.
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}

		Bundle bundle = new Bundle();
		bundle.putParcelable(Constants.ITEM, processo);
		ItemDetailFragment fragment = new ItemDetailFragment();
		fragment.setArguments(bundle);
		getSupportFragmentManager().beginTransaction().add(R.id.item_detail_container, fragment).commit();
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
