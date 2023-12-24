package com.example.medimove;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class Alarm_trig  extends Activity {
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

			// Make sure the activity is displayed even when the device is locked
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
					WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
					WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
					WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

			setContentView(R.layout.alarm_trig);

			// Set up UI components
			Button stopButton = findViewById(R.id.stopButton);
			Button snoozeButton = findViewById(R.id.snoozeButton);

			stopButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					// Handle stopping the alarm
					// ...

					// Finish the activity
					finish();
				}
			});

			snoozeButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					// Handle snoozing the alarm
					// ...

					// Finish the activity
					finish();
				}
			});
		}
	}
