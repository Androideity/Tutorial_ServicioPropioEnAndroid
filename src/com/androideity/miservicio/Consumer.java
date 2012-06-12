package com.androideity.miservicio;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Consumer extends Activity {
	private MiServicio s;
	private ArrayList<String> values;

	
/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		doBindService();
		values = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, values);
		ListView list = (ListView) findViewById(R.id.list);
		list.setAdapter(adapter);

	}

	private ServiceConnection mConnection = new ServiceConnection() {

		public void onServiceConnected(ComponentName className, IBinder binder) {
			s = ((MiServicio.MyBinder) binder).getService();
			Toast.makeText(Consumer.this, "Connected",
					Toast.LENGTH_SHORT).show();
		}

		public void onServiceDisconnected(ComponentName className) {
			s = null;
		}
	};
	private ArrayAdapter<String> adapter;

	void doBindService() {
		bindService(new Intent(this, MiServicio.class), mConnection,
				Context.BIND_AUTO_CREATE);
	}

	public void showServiceData(View view) {
		if (s != null) {
			List<String> wordList = s.getWordList();
			values.clear();
			values.addAll(wordList);
			adapter.notifyDataSetChanged();
		}
	}
}