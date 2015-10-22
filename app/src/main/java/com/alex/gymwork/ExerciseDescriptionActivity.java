package com.alex.gymwork;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.alex.gymwork.databasehelper.DataBaseHelper;
import com.alex.gymwork.enums.IntentKeys;

import java.util.ArrayList;
import java.util.List;

public class ExerciseDescriptionActivity extends MyActionBar implements OnClickListener {
	
	private TextView mTextViewExerciseDescriptionTitle;
	private TextView mTextViewExerciseDescriptionText;
	private Button mButtonBack;
	private Button mButtonForward;

	private DataBaseHelper mDataBaseHelper;
	
	private String mGroupName, mExerciseName, mExerciseDescription;
	
	private List<String> mExercisesList, mDescriptionsList;
	private int counter;
	
	public void initViews() {
		mTextViewExerciseDescriptionTitle =
				(TextView) findViewById(R.id.textview_exercise_description_title);
		mTextViewExerciseDescriptionText =
				(TextView) findViewById(R.id.textview_exercise_description_text);
		mButtonBack = (Button) findViewById(R.id.button_back);
		mButtonForward = (Button) findViewById(R.id.button_forward);
		
		mDataBaseHelper = DataBaseHelper.getDataBase(this);
		
		final Intent intent = getIntent();
		mGroupName = intent.getStringExtra(IntentKeys.GROUP.getIntentKey());
		mExerciseName = intent.getStringExtra(IntentKeys.EXERCISE_NAME.getIntentKey());
		mExerciseDescription =
				mDataBaseHelper.getExerciseDescription(mGroupName, mExerciseName);
		
		mExercisesList = new ArrayList<String>();
		mDescriptionsList = new ArrayList<String>();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exercises_description);
		
		initViews();
		
		mButtonBack.setOnClickListener(this);
		mButtonForward.setOnClickListener(this);
		
		mTextViewExerciseDescriptionTitle.setText(mExerciseName);
		mTextViewExerciseDescriptionText.setText(mExerciseDescription);
		
		mExercisesList =
				mDataBaseHelper.getOneGroupExercises(mGroupName);
		mDescriptionsList =
				mDataBaseHelper.getOneGroupDescriptions(mGroupName);

		for (String exercise : mExercisesList) {
			if (exercise.equalsIgnoreCase(mExerciseName)) {
				counter = mExercisesList.indexOf(exercise);
				break;
			}
		}
	}

	@Override
	public void onClick(final View view) {
		switch (view.getId()) {
		case R.id.button_back:
			if (counter == 0) {
				counter = mDescriptionsList.size() - 1;
			} else {
				counter--;
			}
			mTextViewExerciseDescriptionTitle.setText(mExercisesList.get(counter));
			mTextViewExerciseDescriptionText.setText(mDescriptionsList.get(counter));
			break;

		case R.id.button_forward:
			if (counter == mDescriptionsList.size() - 1) {
				counter = 0;
			} else {
				counter++;
			}
			mTextViewExerciseDescriptionTitle.setText(mExercisesList.get(counter));
			mTextViewExerciseDescriptionText.setText(mDescriptionsList.get(counter));
			break;
		}
	}

}
