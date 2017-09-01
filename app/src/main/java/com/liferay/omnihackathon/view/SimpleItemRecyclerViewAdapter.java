package com.liferay.omnihackathon.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.liferay.omnihackathon.R;
import com.liferay.omnihackathon.model.Processo;
import com.liferay.omnihackathon.util.Constants;
import java.util.List;

/**
 * Created by victoroliveira on 16/01/17.
 */

public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

	private List<Processo> processos;
	private final ItemListActivity activity;
	//private User user;

	public SimpleItemRecyclerViewAdapter(ItemListActivity context, List items) {
		this.activity = context;
		this.processos = items;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_content, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final ViewHolder holder, int position) {

		final Processo processo = processos.get(position);

		int width = (int) activity.getResources().getDimension(R.dimen.small_image_width);
		int height = (int) activity.getResources().getDimension(R.dimen.small_image_height);

		holder.mContentView.setText(processo.getName());
		holder.price.setText(processo.getStatus());

		holder.mView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

			Context context = v.getContext();

			Intent intent = new Intent(context, ItemDetailActivity.class);
			intent.putExtra(Constants.ITEM, processo);

			context.startActivity(intent);
			}
		});
	}

	@Override
	public int getItemCount() {

		return processos.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {

		public final View mView;
		public final ImageView imageView;
		public final TextView mContentView;
		public final TextView price;

		public ViewHolder(View view) {
			super(view);
			mView = view;
			imageView = (ImageView) view.findViewById(R.id.small_image);
			mContentView = (TextView) view.findViewById(R.id.content);
			price = (TextView) view.findViewById(R.id.price);
		}
	}
}