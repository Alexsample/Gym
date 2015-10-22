package com.alex.gymwork.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.alex.gymwork.R;

public class ServiceMusic extends Service {

	private MediaPlayer mPlayer;
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		mPlayer = MediaPlayer.create(this, R.raw.kraddy_android_porn);
		mPlayer.setLooping(true);
	}

	@Override
	public int onStartCommand(final Intent intent, final int flags, final  int startId) {
		mPlayer.start();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mPlayer.stop();
	}

}
