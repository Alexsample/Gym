package com.alex.gymwork;

import com.alex.gymwork.enums.IntentActions;
import com.alex.gymwork.enums.IntentKeys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class AddDataActivity extends Activity implements OnClickListener {
	
	private LinearLayout mAddDataHorizontalLinearLayout;

	private EditText mEditTextSet;
	private EditText mEditTextWeight;
	private EditText mEditTextRepeats;
	private EditText mEditTextName;
	private EditText mEditTextDescription;

	private Button mButtonAddData;
	private Button mButtonCancelData;
	
	private Intent intent;

	private String mIntentAction;

	private void initViews() {
		mAddDataHorizontalLinearLayout = (LinearLayout) findViewById(R.id.linear_layout_add_data);
		mEditTextSet = (EditText) findViewById(R.id.edit_text_set);
		mEditTextWeight = (EditText) findViewById(R.id.edit_text_weight);
		mEditTextRepeats = (EditText) findViewById(R.id.edit_text_repeats);
		
		mEditTextName = (EditText) findViewById(R.id.edit_text_name);
		mEditTextDescription = (EditText) findViewById(R.id.edit_text_description);
		
		mButtonAddData = (Button) findViewById(R.id.button_add_data);
		mButtonCancelData = (Button) findViewById(R.id.button_cancel_data);
	}

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_data_activity);
		
		initViews();
		
		intent = getIntent();
		mIntentAction = intent.getAction();
		if (mIntentAction.equalsIgnoreCase(
				IntentActions.ACTION_ADD_EXERCISE.getAction())) { //Add exercise name, description
			this.setTitle(getResources().getString(R.string.add_exercise));
			mAddDataHorizontalLinearLayout.setVisibility(View.GONE);
			
		} else if (mIntentAction.equalsIgnoreCase(
				IntentActions.ACTION_ADD_PROGRAM.getAction())) { //Add program name
			this.setTitle(getResources().getString(R.string.add_program));
			mAddDataHorizontalLinearLayout.setVisibility(View.GONE);
			mEditTextDescription.setVisibility(View.GONE);
			
		} else if (mIntentAction.equalsIgnoreCase(
				IntentActions.ACTION_ADD_WEEKDAY.getAction())) { //Add weekday name
			this.setTitle(getResources().getString(R.string.program_description_add_week_day));
			mAddDataHorizontalLinearLayout.setVisibility(View.GONE);
			mEditTextDescription.setVisibility(View.GONE);
				
		} else if (mIntentAction.equalsIgnoreCase(
				IntentActions.ACTION_ADD_EXERCISE_IN_PROGRAM.getAction())) { //Add exercise in program
			this.setTitle(getResources().getString(R.string.program_description_add_exercise));
			mAddDataHorizontalLinearLayout.setVisibility(View.GONE);
			mEditTextDescription.setVisibility(View.GONE);
			
		} else if (mIntentAction.equalsIgnoreCase(
				IntentActions.ACTION_ADD_SET_WEIGHT_REPEATS.getAction())) { //Add mSet, mWeight, mRepeats
			this.setTitle(getResources().getString(R.string.program_description_add_data));
			mEditTextName.setVisibility(View.GONE);
			mEditTextDescription.setVisibility(View.GONE);
		}
		
		mButtonAddData.setOnClickListener(this);
		mButtonCancelData.setOnClickListener(this);
	}
	
	/**
	 * Method onClick for buttons
	 */
	@Override
	public void onClick(final View view) {
		switch (view.getId()) {
		
		case R.id.button_add_data:
			if (mIntentAction.equalsIgnoreCase(
					IntentActions.ACTION_ADD_EXERCISE.getAction())) {
				intent.putExtra(IntentKeys.EXERCISE_NAME.getIntentKey(),
								mEditTextName.getText().toString());
				intent.putExtra(IntentKeys.EXERCISE_DESCRIPTION.getIntentKey(),
								mEditTextDescription.getText().toString());
				
			} else if (mIntentAction.equalsIgnoreCase(
					IntentActions.ACTION_ADD_PROGRAM.getAction())) {
				intent.putExtra(IntentKeys.PROGRAM_NAME.getIntentKey(),
								mEditTextName.getText().toString());
			
			} else if (mIntentAction.equalsIgnoreCase(
					IntentActions.ACTION_ADD_WEEKDAY.getAction())) {
				intent.putExtra(IntentKeys.WEEKDAY_NAME.getIntentKey(),
								mEditTextName.getText().toString());
				
			} else if (mIntentAction.equalsIgnoreCase(
					IntentActions.ACTION_ADD_EXERCISE_IN_PROGRAM.getAction())) {
				intent.putExtra(IntentKeys.EXERCISE_IN_PROGRAM.getIntentKey(),
								mEditTextName.getText().toString());
				
			} else if (mIntentAction.equalsIgnoreCase(
					IntentActions.ACTION_ADD_SET_WEIGHT_REPEATS.getAction())) {
				intent.putExtra(IntentKeys.SET.getIntentKey(),
								mEditTextSet.getText().toString());
				intent.putExtra(IntentKeys.WEIGHT.getIntentKey(),
								mEditTextWeight.getText().toString());
				intent.putExtra(IntentKeys.REPEATS.getIntentKey(),
								mEditTextRepeats.getText().toString());
				
			}
			setResult(RESULT_OK, intent);
			break;
			
		case R.id.button_cancel_data:
			setResult(RESULT_CANCELED, intent);
			break;
		}		
		finish();
	}
	
}
