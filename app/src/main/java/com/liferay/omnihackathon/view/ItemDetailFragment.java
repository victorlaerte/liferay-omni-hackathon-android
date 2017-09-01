package com.liferay.omnihackathon.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.liferay.omnihackathon.R;
import com.liferay.omnihackathon.model.Processo;
import com.liferay.omnihackathon.util.Constants;

public class ItemDetailFragment extends Fragment {

	private Processo mItem;

	public ItemDetailFragment() {}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(Constants.ITEM)) {

			mItem = (Processo) getArguments().getParcelable(Constants.ITEM);

			Activity activity = this.getActivity();
			CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
			if (appBarLayout != null) {
				appBarLayout.setTitle(mItem.getName());
			}

		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.item_detail, container, false);

		if (mItem != null) {

			//ImageButton imageButtonAdd = (ImageButton) rootView.findViewById(R.id.item_add);
            //
			//imageButtonAdd.setOnClickListener(new View.OnClickListener() {
            //
			//	@Override
			//	public void onClick(View view) {
            //
			//	}
			//});

			//imageButtonAdd.setVisibility(View.VISIBLE);

			//((TextView) rootView.findViewById(R.id.item_name)).setText(mItem.getName());
			//((TextView) rootView.findViewById(R.id.item_price)).setText(getString(R.string.currency_symbol) + mItem.getPrice());
			((TextView) rootView.findViewById(R.id.item_description)).setText(mItem.getStatus());
		}

		return rootView;
	}
}
