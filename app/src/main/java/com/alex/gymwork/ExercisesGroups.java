package com.alex.gymwork;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.alex.gymwork.enums.GroupsNames;
import com.alex.gymwork.enums.IntentKeys;

public class ExercisesGroups extends MyActionBar implements OnClickListener{
	
	private Button mButtonGroupArms;
	private Button mButtonGroupShoulders;
	private Button mButtonGroupStomach;
	private Button mButtonGroupBack;
	private Button mButtonGroupChest;
	private Button mButtonGroupLegs;
	
	private void initViews() {
		mButtonGroupArms = (Button) findViewById(R.id.button_group_arms);
		mButtonGroupShoulders = (Button) findViewById(R.id.button_group_shoulders);
		mButtonGroupStomach = (Button) findViewById(R.id.button_group_stomach);
		mButtonGroupBack = (Button) findViewById(R.id.button_group_back);
		mButtonGroupChest = (Button) findViewById(R.id.button_group_chest);
		mButtonGroupLegs = (Button) findViewById(R.id.button_group_legs);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exercises_groups);getSupportActionBar();
		
		initViews();
		
		mButtonGroupArms.setOnClickListener(this);
		mButtonGroupShoulders.setOnClickListener(this);
		mButtonGroupStomach.setOnClickListener(this);
		mButtonGroupBack.setOnClickListener(this);
		mButtonGroupChest.setOnClickListener(this);
		mButtonGroupLegs.setOnClickListener(this);
	}
	
	@Override
	public void onClick(final View view) {
		final Intent intent = new Intent(ExercisesGroups.this, ExercisesListActivity.class);
		switch (view.getId()) {
		
		case R.id.button_group_arms:
			intent.putExtra(IntentKeys.GROUP.getIntentKey(), GroupsNames.ARMS.getGroupName());
			startActivity(intent);
			break;
			
		case R.id.button_group_shoulders:
			intent.putExtra(IntentKeys.GROUP.getIntentKey(), GroupsNames.SHOULDERS.getGroupName());
			startActivity(intent);
			break;
			
		case R.id.button_group_stomach:
			intent.putExtra(IntentKeys.GROUP.getIntentKey(), GroupsNames.STOMACH.getGroupName());
			startActivity(intent);
			break;
			
		case R.id.button_group_back:
			intent.putExtra(IntentKeys.GROUP.getIntentKey(), GroupsNames.BACK.getGroupName());
			startActivity(intent);
			break;
			
		case R.id.button_group_chest:
			intent.putExtra(IntentKeys.GROUP.getIntentKey(), GroupsNames.CHEST.getGroupName());
			startActivity(intent);
			break;
			
		case R.id.button_group_legs:
			intent.putExtra(IntentKeys.GROUP.getIntentKey(), GroupsNames.LEGS.getGroupName());
			startActivity(intent);
			break;			
		}
	}
	
}
