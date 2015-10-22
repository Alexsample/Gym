package com.alex.gymwork;

import java.util.ArrayList;
import java.util.List;

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
import com.alex.gymwork.enums.IntentActions;
import com.alex.gymwork.enums.IntentKeys;

public class ProgramsListActivity extends MyActionBar {
	
	private ListView mListViewPrograms;
	private Button mButtonAddProgram;
	
	private List<String> mProgramsList;
	private ListAdapter mProgramsListAdapter;
	
	private DataBaseHelper mDataBaseHelper;
	
	private String mProgramName;
	
	private void initViews() {
		mListViewPrograms = (ListView) findViewById(R.id.listview_programs);
		mButtonAddProgram = (Button) findViewById(R.id.button_add_program);
		mDataBaseHelper = DataBaseHelper.getDataBase(this);
		mProgramsList = new ArrayList<String>();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.programs_list);
		
		initViews();
		
		completeProgramsList();
		
		mButtonAddProgram.setOnClickListener(mAddProgram);
		mListViewPrograms.setOnItemClickListener(mItemClickListener);
		
		mProgramsListAdapter = new ListAdapter(ProgramsListActivity.this, mProgramsList);
		mListViewPrograms.setAdapter(mProgramsListAdapter);
		registerForContextMenu(mListViewPrograms);
	}

	public void completeProgramsList() {
		/*final List<String> workoutsList = mDataBaseHelper.getAllWorkouts();
		if (workoutsList.size() > 0) {
			mProgramsList = workoutsList;
		}*/
		mProgramsList = mDataBaseHelper.getAllWorkouts();
	}

	/**
	 * onItemClickListener
	 */
	private OnItemClickListener mItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(final AdapterView<?> parent,
								final View view,
								final int position,
								final long id) {
			final TextView textView =
					(TextView) view.findViewById(R.id.text_view_list_item);
			mProgramName = textView.getText().toString();

			final Intent intent =
					new Intent(ProgramsListActivity.this, ProgramDescriptionActivity.class);
			intent.putExtra(IntentKeys.PROGRAM_NAME.getIntentKey(), mProgramName);
			startActivity(intent);
		}
	};

	/**
	 * onClick for buttons
	 */
	private OnClickListener mAddProgram = new OnClickListener() {

		@Override
		public void onClick(final View view) {
			final Intent intent = new Intent(IntentActions.ACTION_ADD_PROGRAM.getAction());
			startActivityForResult(intent, 200);
		}
	};

	@Override
	protected void onActivityResult(final int requestCode,
									final int resultCode,
									final Intent data) {
		if (resultCode == RESULT_OK &&
				data.getExtras().containsKey(IntentKeys.PROGRAM_NAME.getIntentKey())) {
			final String programName =
					data.getExtras().getString(IntentKeys.PROGRAM_NAME.getIntentKey());
			mDataBaseHelper.addWorkout(programName);
			mProgramsListAdapter.addItem(programName);
		}
	}

	/**
	 * Context menu, Create and ItemSelected
	 */
	@Override
	public void onCreateContextMenu(final ContextMenu menu,
									final View view,
									final ContextMenuInfo menuInfo) {
		if (view.getId() == R.id.listview_programs) {
			final AdapterView.AdapterContextMenuInfo info =
					(AdapterView.AdapterContextMenuInfo) menuInfo;
			menu.setHeaderTitle(mProgramsList.get(info.position));
			menu.add(0, 1, 0, getResources().getString(R.string.delete_program));
		}
	}
	
	@Override
	public boolean onContextItemSelected(final MenuItem item) {
		final AdapterView.AdapterContextMenuInfo info =
				(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		mProgramName = mProgramsList.get(info.position);

		final int itemID = item.getItemId();
		switch (itemID) {
		case 1:
			mDataBaseHelper.deleteWorkout(mProgramName);
			mProgramsListAdapter.removeItem(mProgramName);
			break;
		}

		return true;
	}

}
