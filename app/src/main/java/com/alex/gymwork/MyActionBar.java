package com.alex.gymwork;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MyActionBar extends ActionBarActivity {

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final ActionBar mActionBar = getSupportActionBar();
		mActionBar.setHomeButtonEnabled(true);
		mActionBar.setTitle("");
		mActionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(30, 30, 30)));
	}
	
	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		getMenuInflater().inflate(R.menu.gym, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		final int itemId = item.getItemId();

		if (itemId == R.id.barbell) {
			final Intent intentExercises =
					new Intent(MyActionBar.this, ExercisesGroups.class);
			startActivity(intentExercises);
			return true;

		}  else if (itemId == R.id.stopwatch) {
			final Intent intentTimer =
					new Intent(MyActionBar.this, Stopwatch.class);
			startActivity(intentTimer);
			return true;

		} else if (itemId == android.R.id.home) {
			final Intent intentHome =
					new Intent(MyActionBar.this, MainScreen.class);
			intentHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intentHome);
			return true;

		} else {
			return super.onOptionsItemSelected(item);
		}
	}

}
