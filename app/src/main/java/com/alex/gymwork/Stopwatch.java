package com.alex.gymwork;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Stopwatch extends MyActionBar implements OnClickListener {

	private TextView mTextViewStopwatch;

	private Button mButtonStartStopwatch;
	private Button mButtonStopStopwatch;
	private Button mButtonResetStopwatch;
	 
	private final Handler mTimeHandler = new Handler();

	private long mStartTime = 0L;
	private long mTimeInMilliseconds = 0L;
	private long mTimeSwapBuff = 0L;
	private long mUpdatedTime = 0L;
	
	private void initViews() {
		mTextViewStopwatch = (TextView) findViewById(R.id.textview_stopwatch);
		
		mButtonStartStopwatch = (Button) findViewById(R.id.button_start_stopwatch);
		mButtonStopStopwatch = (Button) findViewById(R.id.button_stop_stopwatch);
		mButtonResetStopwatch = (Button) findViewById(R.id.button_reset_stopwatch);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stopwatch);
		
		initViews();
		
		mButtonStartStopwatch.setOnClickListener(this);
		mButtonStopStopwatch.setOnClickListener(this);
		mButtonResetStopwatch.setOnClickListener(this);
	}
	
	@Override
	public void onClick(final View view) {
		switch (view.getId()) {
		case R.id.button_start_stopwatch:
			mButtonStartStopwatch.setEnabled(false);
			final String currentTime = mTextViewStopwatch.getText().toString();
			if (currentTime.equalsIgnoreCase("0:00:000")) {
				mTimeInMilliseconds = 0L;
				mTimeSwapBuff = 0L;
				mUpdatedTime = 0L;
	        		    
				mStartTime = SystemClock.uptimeMillis();
				mTimeHandler.postDelayed(updateTimerThread, 0);
			} else {
				mStartTime = SystemClock.uptimeMillis();
				mTimeHandler.postDelayed(updateTimerThread, 0);
			}
			break;
			
		case R.id.button_stop_stopwatch:
			mButtonStartStopwatch.setEnabled(true);
			mTimeSwapBuff += mTimeInMilliseconds;
			mTimeHandler.removeCallbacks(updateTimerThread);
			break;
			
		case R.id.button_reset_stopwatch:
			mButtonStartStopwatch.setEnabled(true);
			mTimeHandler.removeCallbacks(updateTimerThread);
			mTextViewStopwatch.setText(getString(R.string.stopwatch_zero));
			break;
		}
		
	}
	
	private Runnable updateTimerThread = new Runnable() {
		public void run() {
			mTimeInMilliseconds = SystemClock.uptimeMillis() - mStartTime;
		 
			mUpdatedTime = mTimeSwapBuff + mTimeInMilliseconds;
			
			int seconds = (int) (mUpdatedTime / 1000);
			int minutes = seconds / 60;
			seconds = seconds % 60;
			int milliseconds = (int) (mUpdatedTime % 1000);
			mTextViewStopwatch.setText(minutes + ":"
					+ String.format("%02d", seconds) + ":"
					+ String.format("%03d", milliseconds));
			mTimeHandler.postDelayed(this, 0);
		}
	};
	
}
