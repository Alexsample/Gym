package com.alex.gymwork.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alex.gymwork.R;
import com.alex.gymwork.Weekday;

import java.util.List;

public class ProgramDescriptionAdapter extends BaseAdapter {
	
	private final int VIEW_TYPE_WEEKDAY = 0;
	private final int VIEW_TYPE_EXERCISE = 1;
	private final int VIEW_TYPE_DATA = 2;

	private List<Weekday> mProgramDescriptionList; // = new ArrayList<Weekday>();
	private LayoutInflater mInflater;

	public ProgramDescriptionAdapter(final Context context,
									 final List<Weekday> programDescriptionList) {
		mProgramDescriptionList = programDescriptionList;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return mProgramDescriptionList.size();
	}

	@Override
	public Object getItem(final int position) {
		return mProgramDescriptionList.get(position);
	}

	@Override
	public long getItemId(final int position) {
		return position;
	}

	@Override
	public View getView(final int position,
						View convertView,
						final ViewGroup parent) {
		ViewHolder holder;
		
		final int type = getItemViewType(position);
		if (type == VIEW_TYPE_WEEKDAY) {
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.row_type_weekday, parent, false);

				holder = new ViewHolder();
				holder.weekday =
						(TextView) convertView.findViewById(R.id.text_view_type_weekday);
				
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			holder.weekday.setText(mProgramDescriptionList.get(position).getWeekday());
			
		} else if (type == VIEW_TYPE_EXERCISE) {
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.row_type_exercise, parent, false);

				holder = new ViewHolder();
				holder.exercise =
						(TextView) convertView.findViewById(R.id.text_view_type_exercise);
				
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			holder.exercise.setText(mProgramDescriptionList.get(position).getWeekday());
			
		} else {
			if (convertView == null) {
				convertView =
						mInflater.inflate(R.layout.row_type_set_wieght_repeat, parent, false);

				holder = new ViewHolder();
				holder.sets = (TextView) convertView.findViewById(R.id.text_view_type_set);
				holder.weight = (TextView) convertView.findViewById(R.id.text_view_type_weight);
				holder.repeats = (TextView) convertView.findViewById(R.id.text_view_type_repeats);
				
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			final String[] exerciseData =
					mProgramDescriptionList.get(position).getWeekday().split("_");
			if (mProgramDescriptionList.get(position - 1).getType() == VIEW_TYPE_DATA) {
				holder.sets.setText(exerciseData[0]);
				holder.weight.setText(exerciseData[1]);
				holder.repeats.setText(exerciseData[2]);
			} else {
				holder.sets.setText("set:" + "\n" + exerciseData[0]);
				holder.weight.setText("weight:" + "\n" + exerciseData[1]);
				holder.repeats.setText("repeat:" + "\n" + exerciseData[2]);
			}
		}
		return convertView;
	}

	class ViewHolder {
		public TextView weekday;
		public TextView exercise;
		public TextView sets;
		public TextView weight;
		public TextView repeats;
	}
	
	@Override
	public int getViewTypeCount() {
		return 3;
	}

	@Override
	public int getItemViewType(final int position) {
		final int type = mProgramDescriptionList.get(position).getType();

		if(type == 0){
			return VIEW_TYPE_WEEKDAY;

		}else if(type == 1){
			return VIEW_TYPE_EXERCISE;

		}else{
			return VIEW_TYPE_DATA;
		}
	}

	public void addItem(final Weekday weekday) {
		mProgramDescriptionList.add(weekday);
		this.notifyDataSetChanged();
	}

	public void deleteItem(final int position) {
		mProgramDescriptionList.remove(position);
		this.notifyDataSetChanged();
	}
	
}
