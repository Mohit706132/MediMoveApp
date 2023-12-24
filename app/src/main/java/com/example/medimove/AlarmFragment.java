package com.example.medimove;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toolbar;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AlarmFragment extends Fragment {
    FloatingActionButton add_alarm;
    Toolbar mToolbar;
    ListView reminderListView;
    ProgressDialog prgDialog;

    private static final int VEHICLE_LOADER = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);
        add_alarm = view.findViewById(R.id.add_alarm);

        mToolbar = view.findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.app_name);


        reminderListView = view.findViewById(R.id.List);
        View emptyView = view.findViewById(R.id.empty_view);
        reminderListView.setEmptyView(emptyView);

//        reminderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//
//                Intent intent = new Intent(getActivity(), Alarm_Main.class);
//
//                Uri currentVehicleUri = ContentUris.withAppendedId(AlarmReminderContract.AlarmReminderEntry.CONTENT_URI, id);
//
//                // Set the URI on the data field of the intent
//                intent.setData(currentVehicleUri);
//
//                startActivity(intent);
//
//            }
//        });

//        add_alarm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i =new Intent(getActivity(),Alarm_Main.class);
//                startActivity(i);
//            }
//        });


        add_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Alarm_Main.class);
                startActivity(intent);
            }
        });

//           getLoaderManager().initLoader(VEHICLE_LOADER, null, this);
    return view;
    }

//    @Override
//    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
//        String[] projection = {
//                AlarmReminderContract.AlarmReminderEntry._ID,
//                AlarmReminderContract.AlarmReminderEntry.KEY_TITLE,
//                AlarmReminderContract.AlarmReminderEntry.KEY_DATE,
//                AlarmReminderContract.AlarmReminderEntry.KEY_TIME,
//                AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT,
//                AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT_NO,
//                AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT_TYPE,
//                AlarmReminderContract.AlarmReminderEntry.KEY_ACTIVE
//
//        };
//
//        return new CursorLoader(requireContext(),   // Parent activity context
//                AlarmReminderContract.AlarmReminderEntry.CONTENT_URI,   // Provider content URI to query
//                projection,             // Columns to include in the resulting Cursor
//                null,                   // No selection clause
//                null,                   // No selection arguments
//                null);                  // Default sort order
//
//    }

//    @Override
//    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
////        mCursorAdapter.swapCursor(cursor);
//
//    }
//
//    @Override
//    public void onLoaderReset(Loader<Cursor> loader) {
////        mCursorAdapter.swapCursor(null);
//
//    }


}
