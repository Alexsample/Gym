package com.alex.gymwork;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.alex.gymwork.service.ServiceMusic;

public class MainScreen extends Activity implements OnClickListener {
	
	private Button mButtonExercises;
	private Button mButtonPrograms;
	private Button mButtonStopwatch;
	private Button mButtonAbout;
	private Button mButtonExit;
	private Button mButtonMusic;
	
	private SharedPreferences mSharedPreferences;

	private static final String MUSIC_PREFERENCES = "MusicPreferences";
	private static final String MUSIC_STATE = "musicState";
	private boolean mIsMusicOn;

	public void initViews() {
		mButtonExercises = (Button) findViewById(R.id.button_exercises);
		mButtonPrograms = (Button) findViewById(R.id.button_programs);
		mButtonStopwatch = (Button) findViewById(R.id.button_stopwatch);
		mButtonAbout = (Button) findViewById(R.id.button_about);
		mButtonExit = (Button) findViewById(R.id.button_exit);
		mButtonMusic = (Button) findViewById(R.id.button_music);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_screen);
		
		initViews();
		
		mButtonExercises.setOnClickListener(this);
		mButtonPrograms.setOnClickListener(this);
		mButtonStopwatch.setOnClickListener(this);
		mButtonAbout.setOnClickListener(this);
		mButtonExit.setOnClickListener(this);
		mButtonMusic.setOnClickListener(this);
	}
	
	@Override
	protected void onResume() {
		loadMusicSettings();
		super.onResume();
	}
	
	@Override
	protected void onStop() {
		saveMusicSettings(MUSIC_STATE, mIsMusicOn);
		super.onStop();
	}

	@Override
	public void onClick(final View view) {
		final Intent intent;
		switch (view.getId()) {
		
		case R.id.button_exercises:
			intent = new Intent(MainScreen.this, ExercisesGroups.class);
			startActivity(intent);
			break;
			
		case R.id.button_programs:
			intent = new Intent(MainScreen.this, ProgramsListActivity.class);
			startActivity(intent);
			break;
			
		case R.id.button_stopwatch:
			intent = new Intent(MainScreen.this, Stopwatch.class);
			startActivity(intent);
			break;
			
		case R.id.button_about:
			final AlertDialog.Builder dialogAboutProgram = new AlertDialog.Builder(MainScreen.this);
			dialogAboutProgram.setIcon(R.drawable.ic_launcher);
			dialogAboutProgram.setTitle(R.string.button_about);
			dialogAboutProgram.setMessage(getString(R.string.author)
					+ "\n" + getString(R.string.year));
			dialogAboutProgram.setNeutralButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {}
			});
			dialogAboutProgram.show();
			break;
			
		case R.id.button_exit:
			android.os.Process.killProcess(android.os.Process.myPid());
			break;
			
		case R.id.button_music:
			if (mIsMusicOn) {
				mButtonMusic.setText(getResources().getString(R.string.music_off));
				mButtonMusic.setCompoundDrawablesWithIntrinsicBounds(
						null,
						getResources().getDrawable(R.drawable.sound_off),
						null,
						null
				);
				stopService(new Intent(this, ServiceMusic.class));
				mIsMusicOn = false;

			} else {
				mButtonMusic.setText(getResources().getString(R.string.music_on));
				mButtonMusic.setCompoundDrawablesWithIntrinsicBounds(
						null,
						getResources().getDrawable(R.drawable.sound_on),
						null,
						null
				);
				startService(new Intent(this, ServiceMusic.class));
				mIsMusicOn = true;
			}
			break;
		}
		
	}
	
	public void saveMusicSettings(final String preferenceKey, final boolean musicState) {
		mSharedPreferences = getSharedPreferences(MUSIC_PREFERENCES, MODE_PRIVATE);
		final Editor editor = mSharedPreferences.edit();
		editor.putBoolean(preferenceKey, musicState);
		editor.apply();
	}
	
	public void loadMusicSettings() {
		mSharedPreferences = getSharedPreferences(MUSIC_PREFERENCES, MODE_PRIVATE);
		mIsMusicOn = mSharedPreferences.getBoolean(MUSIC_STATE, true);
		if (mIsMusicOn) {
			mButtonMusic.setText(getResources().getString(R.string.music_on));
			mButtonMusic.setCompoundDrawablesWithIntrinsicBounds(
					null,
					getResources().getDrawable(R.drawable.sound_on),
					null,
					null
			);
			startService(new Intent(this, ServiceMusic.class));
		} else {
			mButtonMusic.setText(getResources().getString(R.string.music_off));
			mButtonMusic.setCompoundDrawablesWithIntrinsicBounds(
					null,
					getResources().getDrawable(R.drawable.sound_off),
					null,
					null
			);
			stopService(new Intent(this, ServiceMusic.class));
		}
	}

	@Override
	protected void onDestroy() {
		if (mIsMusicOn) {
			saveMusicSettings(MUSIC_STATE, true);
		} else {
			saveMusicSettings(MUSIC_STATE, false);
		}
		stopService(new Intent(MainScreen.this, ServiceMusic.class));
		super.onDestroy();
	}

}
