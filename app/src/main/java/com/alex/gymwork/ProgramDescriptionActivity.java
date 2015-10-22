package com.alex.gymwork;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alex.gymwork.adapters.ProgramDescriptionAdapter;
import com.alex.gymwork.enums.IntentActions;
import com.alex.gymwork.enums.IntentKeys;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ProgramDescriptionActivity extends MyActionBar implements OnClickListener {
	
	private TextView mTextViewProgramDescriptionTitle;
	private Button mButtonAddWeekDay;
	private Button mButtonAddExerciseInProgram;
	private Button mButtonAddData;
	private Button mButtonSaveData;

	private ListView mListViewProgramDescription;
	private List<Weekday> mProgramDescriptionList;
	private ProgramDescriptionAdapter mProgramDescriptionAdapter;
	private Intent intent;
	private String fileName;

	private void initViews() {
		mTextViewProgramDescriptionTitle =
				(TextView) findViewById(R.id.textview_program_description_title);
		mButtonAddWeekDay = (Button) findViewById(R.id.button_add_week_day);
		mButtonAddExerciseInProgram = (
				Button) findViewById(R.id.button_add_exercise_in_program);
		mButtonAddData = (Button) findViewById(R.id.button_add_data);
		mButtonSaveData = (Button) findViewById(R.id.button_save_data);
		mListViewProgramDescription = (
				ListView) findViewById(R.id.listview_program_description);
		mProgramDescriptionList = new ArrayList<Weekday>();
		mProgramDescriptionAdapter = new ProgramDescriptionAdapter(
				ProgramDescriptionActivity.this,
				mProgramDescriptionList
		);
		
		intent = getIntent();
		fileName = intent.getStringExtra(IntentKeys.PROGRAM_NAME.getIntentKey());
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.programs_description);
		
		initViews();
		
		mTextViewProgramDescriptionTitle.setText(
				intent.getStringExtra(IntentKeys.PROGRAM_NAME.getIntentKey())
		);
		
		mButtonAddWeekDay.setOnClickListener(this);
		mButtonAddExerciseInProgram.setOnClickListener(this);
		mButtonAddData.setOnClickListener(this);
		mButtonSaveData.setOnClickListener(this);
		
		mListViewProgramDescription.setAdapter(mProgramDescriptionAdapter);
		registerForContextMenu(mListViewProgramDescription);
		
		final List<Weekday> loadedList =
				loadWeekday(ProgramDescriptionActivity.this, fileName);
		if (loadedList != null) {
			for (Weekday weekday : loadedList) {
				mProgramDescriptionAdapter.addItem(weekday);
			}
		}
	}
	
	/**
	 * Method onClick for buttons
	 */
	@Override
	public void onClick(final View view) {
		switch (view.getId()) {
		case R.id.button_add_week_day:
			final Intent intentAddWeekday =
					new Intent(IntentActions.ACTION_ADD_WEEKDAY.getAction());
			startActivityForResult(intentAddWeekday, 400);
			break;
			
		case R.id.button_add_exercise_in_program:
			final Intent intentAddExerciseInProgram =
					new Intent(IntentActions.ACTION_ADD_EXERCISE_IN_PROGRAM.getAction());
			startActivityForResult(intentAddExerciseInProgram, 500);
			break;
			
		case R.id.button_add_data:
			final Intent intentAddData =
					new Intent(IntentActions.ACTION_ADD_SET_WEIGHT_REPEATS.getAction());
			startActivityForResult(intentAddData, 600);
			break;
			
		case R.id.button_save_data:
			saveWeekday(ProgramDescriptionActivity.this, fileName, mProgramDescriptionList);
			break;
		}
	}
	
	/**
	 * Receive result from dialog
	 */
	@Override
	protected void onActivityResult(final int requestCode,
									final int resultCode,
									final Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == 400 &&
					data.getExtras().containsKey(IntentKeys.WEEKDAY_NAME.getIntentKey())) {
				final String weekdayName =
						data.getExtras().getString(IntentKeys.WEEKDAY_NAME.getIntentKey());
				mProgramDescriptionAdapter.addItem(new Weekday(weekdayName, 0));
				
			} else if (requestCode == 500 &&
					data.getExtras().containsKey(IntentKeys.EXERCISE_IN_PROGRAM.getIntentKey())) {
				final String exerciseName =
						data.getExtras().getString(IntentKeys.EXERCISE_IN_PROGRAM.getIntentKey());
				mProgramDescriptionAdapter.addItem(new Weekday(exerciseName, 1));
				
			} else if (requestCode == 600 &&
					data.getExtras().containsKey(IntentKeys.SET.getIntentKey())) {
				final String set = data.getExtras().getString(IntentKeys.SET.getIntentKey());
				final String weight = data.getExtras().getString(IntentKeys.WEIGHT.getIntentKey());
				final String repeats = data.getExtras().getString(IntentKeys.REPEATS.getIntentKey());
				final String allData = set + "_" + weight + "_" + repeats;
	        	mProgramDescriptionAdapter.addItem(new Weekday(allData, 2));
			}
		}
	}

	/**
	 * Save and Load ArrayList<Weekday> from file
	 */
	public static void saveWeekday(final Context context,
								   final String fileName,
								   final List<Weekday> list) {
	    try {
			final File file = new File(fileName);
			final FileOutputStream fos =
					context.openFileOutput(file.getName() + ".txt", Context.MODE_PRIVATE);
			final ObjectOutputStream oos = new ObjectOutputStream(fos);
	        oos.writeObject(list);
	        oos.close();
	    } catch (final IOException ioException) {
	        // To do nothing
	    }

	    Toast.makeText(context, "Saved as " + fileName, Toast.LENGTH_SHORT).show();
	}
	
	public static List<Weekday> loadWeekday(final Context context,
											final String fileName) {
	    try {
			final FileInputStream fis = context.openFileInput(fileName + ".txt");
			final ObjectInputStream is = new ObjectInputStream(fis);
			final Object readObject = is.readObject();
	        is.close();
			final List<Weekday> result = new ArrayList<Weekday>();
	        if(readObject != null && readObject instanceof ArrayList) {
	        	for(int i = 0; i < ((ArrayList<?>)readObject).size(); i++){
	                Object item = ((ArrayList<?>) readObject).get(i);
	                if(item instanceof Weekday){
	                    result.add((Weekday) item);
	                }
	            }
	        	Toast.makeText(context, fileName + " is loaded", Toast.LENGTH_SHORT).show();
	            return result;
	        }
	    } catch (final IOException ioException) {
	        // To do nothing
	    } catch (final ClassNotFoundException classNotFoundException) {
			// To do nothing
	    }
	    return null;
	}

	/**
	 * Context menu, Create and ItemSelected
	 */
	@Override
	public void onCreateContextMenu(final ContextMenu menu,
									final View view,
									final ContextMenuInfo menuInfo) {
		if (view.getId() == R.id.listview_programs) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			menu.setHeaderTitle(mProgramDescriptionList.get(info.position).getWeekday());
			menu.add(0, 1, 0, getResources().getString(R.string.program_description_delete));
		}
	}
	
	@Override
	public boolean onContextItemSelected(final MenuItem item) {
		final AdapterView.AdapterContextMenuInfo info =
				(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

		final int itemID = item.getItemId();
		switch (itemID) {

		case 1:
			mProgramDescriptionAdapter.deleteItem(info.position);
			break;
		}
		
		return true;
	}
	
}
