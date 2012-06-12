package com.androideity.miservicio;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MiServicio extends Service {
	private Timer timer = new Timer();
	private static final long UPDATE_INTERVAL = 5000;
	private final IBinder mBinder = new MyBinder();
	private ArrayList<String> list = new ArrayList<String>();
	private String[] fixedList = { "Cupcake", "Donut", "Eclair", "Froyo",
			"Gingerbread", "Honeycomb", "Ice Cream Sandwich" };
	private int index = 0;

	public void onCreate() {
		super.onCreate();
		pollForUpdates();
	}

	private void pollForUpdates() {
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				// Esta parte podría sustuirse por un web service que consultemos
				if (list.size() >= 6) {
					list.remove(0);
				}
				list.add(fixedList[index++]);
				if (index >= fixedList.length) {
					index = 0;
				}
			}
		}, 0, UPDATE_INTERVAL);
		Log.i(getClass().getSimpleName(), "Timer started.");

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (timer != null) {
			timer.cancel();
		}
		Log.i(getClass().getSimpleName(), "Timer stopped.");

	}

	
	@Override
	public IBinder onBind(Intent arg0) {
		return mBinder;
	}

	public class MyBinder extends Binder {
		MiServicio getService() {
			return MiServicio.this;
		}
	}

	public List<String> getWordList() {
		return list;
	}
}
