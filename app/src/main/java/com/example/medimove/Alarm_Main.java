package com.example.medimove;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Random;

public class Alarm_Main extends AppCompatActivity {
    private NumberPicker numberPicker1, numberPicker2, Am_Pm_picker;
    TextView save, cancel;
    TextView su, m, t, w, th, f, s, ring, snooze;
    EditText alarm_name;
    String hour, min, am_pm;
    int count = 0;
    ImageView calender;
    String display;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_main);

        numberPicker1 = findViewById(R.id.hourPicker);
        numberPicker1.setMinValue(1);
        numberPicker1.setMaxValue(12);


        numberPicker2 = findViewById(R.id.minutePicker);
        numberPicker2.setMinValue(0);
        numberPicker2.setMaxValue(59);

        String ampm[] = {"AM", "PM"};
        Am_Pm_picker = findViewById(R.id.AM_PM);
        Am_Pm_picker.setDisplayedValues(ampm);
        Am_Pm_picker.setMinValue(0);
        Am_Pm_picker.setMaxValue(1);

        su = findViewById(R.id.Sunday);
        m = findViewById(R.id.Monday);
        t = findViewById(R.id.Tuesday);
        w = findViewById(R.id.Wednesday);
        th = findViewById(R.id.Thursday);
        f = findViewById(R.id.Friday);
        s = findViewById(R.id.Saturday);
        ring = findViewById(R.id.ringtone_switch);
        snooze = findViewById(R.id.snooze_switch);
        alarm_name = findViewById(R.id.alarm_name);
        save = findViewById(R.id.save_button);
        cancel = findViewById(R.id.cancel_button);
        calender =findViewById(R.id.calender);


        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomDialog();
            }
        });

        Drawable highlight = getResources().getDrawable(R.drawable.hollow_circle_border);

        su.setOnClickListener(view -> {
            if ((count & 1) == 0) {
                su.setBackground(highlight);
            } else {
                su.setBackground(null);
            }
            count ^= 1;
        });
        m.setOnClickListener(view -> {
            if ((count & 2) == 0) {
                m.setBackground(highlight);
            } else {
                m.setBackground(null);
            }
            count ^= 2;
        });
        t.setOnClickListener(view -> {
            if ((count & 4) == 0) {
                t.setBackground(highlight);
            } else {
                t.setBackground(null);
            }
            count ^= 4;
        });
        w.setOnClickListener(view -> {
            if ((count & 8) == 0) {
                w.setBackground(highlight);
            } else {
                w.setBackground(null);
            }
            count ^= 8;
        });
        th.setOnClickListener(view -> {
            if ((count & 16) == 0) {
                th.setBackground(highlight);
            } else {
                th.setBackground(null);
            }
            count ^= 16;
        });
        f.setOnClickListener(view -> {
            if ((count & 32) == 0) {
                f.setBackground(highlight);
            } else {
                f.setBackground(null);
            }
            count ^= 32;
        });
        s.setOnClickListener(view -> {
            if ((count & 64) == 0) {
                s.setBackground(highlight);
            } else {
                s.setBackground(null);
            }
            count ^= 64;
        });
        save.setOnClickListener(v -> {
            hour = Integer.toString(numberPicker1.getValue()) ;
            min = Integer.toString(numberPicker2.getValue()) ;
            am_pm = ampm[Am_Pm_picker.getValue()];
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, ampm[Am_Pm_picker.getValue()].equalsIgnoreCase("PM") ? numberPicker1.getValue() + 12 : numberPicker1.getValue());
        calendar.set(Calendar.MINUTE, numberPicker2.getValue());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        for (int dayOfWeek = Calendar.SUNDAY; dayOfWeek <= Calendar.SATURDAY; dayOfWeek++) {
            int mask = 1 << (7 - dayOfWeek);

            if ((count & mask) != 0) {
                // Bitwise AND to check if the corresponding day is selected
                calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);

                // Create an Intent for the BroadcastReceiver
                Intent intent = new Intent(this, AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, generateUniqueId(), intent, PendingIntent.FLAG_IMMUTABLE);

                // Get the AlarmManager service
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                // Schedule the repeating alarm
                alarmManager.setRepeating(
                        AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY * 7, // Repeat every week
                        pendingIntent
                );
                 display = hour+" "+min+" "+dayOfWeek ;
                alarm_name.setText(display);
            }
        }
            Toast.makeText(Alarm_Main.this,"Alarm set ",Toast.LENGTH_SHORT).show();
//        Intent i = new Intent(Alarm_Main.this,AlarmFragment.class);
//        startActivity(i);
//                String week[] = {"1", "2", "3", "4", "5", "6", "7"};
//        int week =(count&1)==0?1:count&2?2:count&4?3:count&8?4:count&2?2:count&2?2:count&2?2:count&2?2:count&2?2:;
//        if((count&1)==0 ||(count&2)==0 ||(count&4)==0 ||(count&8)==0 ||(count&16)==0 ||(count&32)==0 ||(count&64)==0)
//    {
//        if((count&1)==0 )
//        {
//
//        }
//    }


        });

    }

    private int generateUniqueId()
    {
        return new Random().nextInt(Integer.MAX_VALUE);
    }
    private void showBottomDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.calender);

        dialog.findViewById(R.id.Done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        dialog.findViewById(R.id.Cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        dialog.show();

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }
}
