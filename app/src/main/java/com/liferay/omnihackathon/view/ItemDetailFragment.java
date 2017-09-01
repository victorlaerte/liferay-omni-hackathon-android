package com.liferay.omnihackathon.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.liferay.omnihackathon.util.Constants;
import com.victorlaerte.supermarket.R;

public class ItemDetailFragment extends Fragment {

	private Object mItem;

	public ItemDetailFragment() {}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(Constants.ITEM)) {

			mItem = getArguments().getParcelable(Constants.ITEM);

			Activity activity = this.getActivity();
			CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
			if (appBarLayout != null) {
				//appBarLayout.setTitle(mItem.getTitle());
			}

		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.item_detail, container, false);

		if (mItem != null) {

			//String url = Constants.PUBLIC_BASE_URL + Constants.IMAGES_ENDPOINT + mItem.getImageFileName();

			//Picasso.with(getContext()).load(url).resize(mItem.getWidth(), mItem.getHeight()).into(
			//		(ImageView) rootView.findViewById(R.id.item_large_image));

			ImageButton imageButtonAdd = (ImageButton) rootView.findViewById(R.id.item_add);

			imageButtonAdd.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View view) {

				}
			});

			imageButtonAdd.setVisibility(View.VISIBLE);

			//((TextView) rootView.findViewById(R.id.item_price)).setText(getString(R.string.currency_symbol) + mItem.getPrice());
			//((TextView) rootView.findViewById(R.id.item_description)).setText(mItem.getDescription());
		}

		return rootView;
	}
}
