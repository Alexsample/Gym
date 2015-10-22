package com.alex.gymwork;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.alex.gymwork.databasehelper.DataBaseHelper;

import java.io.IOException;

public class SplashScreen extends Activity {
	
	private static final int SPLASH_SHOW_TIME = 2000;
	private final DataBaseHelper dataBaseHelper = DataBaseHelper.getDataBase(SplashScreen.this);
	
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);

		new BackgroundSplashTask().execute();
	}
	
	private class BackgroundSplashTask extends AsyncTask<Object, Object, Void> {
		
		@Override
		protected Void doInBackground(final Object... params) {

			try {
				Thread.sleep(SPLASH_SHOW_TIME);

				dataBaseHelper.createDataBase();
			} catch (final InterruptedException interruptedException) {
				// To do nothing
			} catch (final IOException ioException) {
				// To do nothing
			}

			return null;
		}
		
		@Override
		protected void onPostExecute(final Void result) {
			super.onPostExecute(result);

			final Intent intent = new Intent(SplashScreen.this, MainScreen.class);
			startActivity(intent);
			finish();
		}
	}
	
}
