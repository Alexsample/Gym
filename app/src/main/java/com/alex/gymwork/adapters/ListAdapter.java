package com.alex.gymwork.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alex.gymwork.R;

import java.util.List;

public class ListAdapter extends BaseAdapter {
	
	private LayoutInflater mLayoutInflater;
	private List<String> mList;
    
	public ListAdapter(final Context context, final List<String> list) {
		mLayoutInflater =
				(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mList = list;
	}
	
	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(final int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(final int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {
		ViewHolder viewHolder;
		
    	if (convertView == null) {
    		convertView = mLayoutInflater.inflate(R.layout.list_item, parent, false);
    		
    		viewHolder = new ViewHolder();
            viewHolder.item = (TextView) convertView.findViewById(R.id.text_view_list_item);
            viewHolder.itemNumber = (TextView) convertView.findViewById(R.id.text_view_item_number);
            
            convertView.setTag(viewHolder);
    	} else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

		viewHolder.item.setText(mList.get(position));
    	viewHolder.itemNumber.setText("" + (1 + position));

    	return convertView;
	}
	
	private class ViewHolder {
    	TextView itemNumber;
		TextView item;
	}
	
	public void addItem(final String item) {
		mList.add(item);
		this.notifyDataSetChanged();
	}
	
	public void removeItem(final String item) {
		final int position = mList.indexOf(item);
		mList.remove(position);
		this.notifyDataSetChanged();
	}

}
