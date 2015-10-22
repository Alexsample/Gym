package com.alex.gymwork;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.alex.gymwork.adapters.ListAdapter;
import com.alex.gymwork.databasehelper.DataBaseHelper;
import com.alex.gymwork.enums.GroupsNames;
import com.alex.gymwork.enums.IntentActions;
import com.alex.gymwork.enums.IntentKeys;

import java.util.ArrayList;
import java.util.List;

public class ExercisesListActivity extends MyActionBar {

	private ListView mListViewExercises;
	private Button mButtonAddExercise;

	private List<String> mExercisesList;
	private ListAdapter mExercisesListAdapter;

	private String groupName;
	
	private DataBaseHelper mDataBaseHelper;
	
	private void initViews() {
		mListViewExercises = (ListView) findViewById(R.id.listview_exercises);
		mButtonAddExercise = (Button) findViewById(R.id.button_add_exercise);

		mExercisesList = new ArrayList<String>();
		mDataBaseHelper = DataBaseHelper.getDataBase(ExercisesListActivity.this);
	}
	
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exercises_list);
		
		initViews();

		final Intent intent = getIntent();
		groupName = intent.getStringExtra(IntentKeys.GROUP.getIntentKey());

		completeArrayList();

		mExercisesListAdapter = new ListAdapter(ExercisesListActivity.this, mExercisesList);
		mListViewExercises.setAdapter(mExercisesListAdapter);
		registerForContextMenu(mListViewExercises);
		
		mButtonAddExercise.setOnClickListener(mAddExercise);
		mListViewExercises.setOnItemClickListener(mItemClickListener);
	}
	
	public void completeArrayList() {
		if (groupName.equals(GroupsNames.ARMS.getGroupName())) {
			mExercisesList = mDataBaseHelper.getOneGroupExercises(groupName);

		} else if (groupName.equals(GroupsNames.SHOULDERS.getGroupName())) {
			mExercisesList = mDataBaseHelper.getOneGroupExercises(groupName);

		} else if (groupName.equals(GroupsNames.STOMACH.getGroupName())) {
			mExercisesList = mDataBaseHelper.getOneGroupExercises(groupName);

		} else if (groupName.equals(GroupsNames.BACK.getGroupName())) {
			mExercisesList = mDataBaseHelper.getOneGroupExercises(groupName);

		} else if (groupName.equals(GroupsNames.CHEST.getGroupName())) {
			mExercisesList = mDataBaseHelper.getOneGroupExercises(groupName);

		} else if (groupName.equals(GroupsNames.LEGS.getGroupName())) {
			mExercisesList = mDataBaseHelper.getOneGroupExercises(groupName);

		}
	}

	/**
	 * onItemClickListener
	 */
	private final OnItemClickListener mItemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(final AdapterView<?> parent,
								final View view,
								final int position,
								final long id) {
			final TextView textView =
					(TextView) view.findViewById(R.id.text_view_list_item);
			final String exerciseName = textView.getText().toString();

			final Intent intent =
					new Intent(ExercisesListActivity.this, ExerciseDescriptionActivity.class);
			intent.putExtra(IntentKeys.GROUP.getIntentKey(), groupName);
			intent.putExtra(IntentKeys.EXERCISE_NAME.getIntentKey(), exerciseName);
			startActivity(intent);
		}
	};
	
	/**
	 * onClick for buttons
	 */
	private final OnClickListener mAddExercise = new OnClickListener() {
		@Override
		public void onClick(final View view) {
			final Intent intent = new Intent(IntentActions.ACTION_ADD_EXERCISE.getAction());
			startActivityForResult(intent, 100);
		}
	};

	@Override
	protected void onActivityResult(final int requestCode,
									final int resultCode,
									final Intent data) {
		if (resultCode == RESULT_OK &&
				data.getExtras().containsKey(IntentKeys.EXERCISE_NAME.getIntentKey())) {
			final String exerciseName =
					data.getExtras().getString(IntentKeys.EXERCISE_NAME.getIntentKey());
			final String exerciseDescription=
					data.getExtras().getString(IntentKeys.EXERCISE_DESCRIPTION.getIntentKey());
        	
			mDataBaseHelper.addExerciseData(groupName, exerciseName, exerciseDescription);
			mExercisesListAdapter.addItem(exerciseName);
		}
	}

	/**
	 * Context menu, Create and ItemSelected
	 */
	@Override
	public void onCreateContextMenu(final ContextMenu menu,
									final View view,
									final ContextMenuInfo menuInfo) {
		if (view.getId() == R.id.listview_exercises) {
			final AdapterView.AdapterContextMenuInfo info =
					(AdapterView.AdapterContextMenuInfo) menuInfo;
			menu.setHeaderTitle(mExercisesList.get(info.position));
			menu.add(0, 1, 0, getResources().getString(R.string.delete_exercise));
		}
		
	}
	
	@Override
	public boolean onContextItemSelected(final MenuItem item) {
		final AdapterView.AdapterContextMenuInfo info =
				(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		final String exerciseName = mExercisesList.get(info.position);
		int itemId = item.getItemId();

		switch (itemId) {
		case 1:
			mDataBaseHelper.deleteExercise(exerciseName);
			mExercisesListAdapter.removeItem(exerciseName);
			break;
		}
		return true;
	}

}
