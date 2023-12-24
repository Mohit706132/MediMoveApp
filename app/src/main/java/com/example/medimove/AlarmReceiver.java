package com.example.medimove;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

	private static final Uri DEFAULT_RINGTONE_URI = Uri.parse("content://settings/system/alarm_alert");
	@Override
	public void onReceive(Context context, Intent intent) {
		// Acquire a wake lock to wake up the device
		PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		PowerManager.WakeLock wakeLock = powerManager.newWakeLock(
				PowerManager.PARTIAL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP,
				"YourApp:AlarmWakeLock"
		);
          wakeLock.acquire();
		// Show the activity to stop or snooze the alarm
		Intent alarmIntent = new Intent(context, Alarm_Main.class);
		alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(alarmIntent);

		// Handle the task you want to perform when the alarm fires
		// For example, play a loud ringtone
		playRingtone(context,DEFAULT_RINGTONE_URI);

		// Release the wake lock
		wakeLock.release();
	}

	private void playRingtone(Context context, Uri ringtoneUri) {
		try {
			Ringtone ringtone = RingtoneManager.getRingtone(context, ringtoneUri);
			if (ringtone != null) {
				// Play the ringtone
				ringtone.play();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(context, "Error playing ringtone", Toast.LENGTH_SHORT).show();
		}
	}

}
