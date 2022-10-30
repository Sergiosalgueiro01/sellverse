package com.main.es.sellverse.util.pickers;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TimePicker;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.main.es.sellverse.util.datasavers.TemporalTimeSaver;

import java.util.Calendar;


public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private EditText et;

    public TimePickerFragment(EditText et) {
        this.et = et;
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minutes) {
        TemporalTimeSaver.getInstance().hours = hour;
        TemporalTimeSaver.getInstance().minutes=minutes;
        et.setText(hour+":"+minutes);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int minutes=calendar.get(Calendar.MINUTE);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return new TimePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, this,hour,minutes,true);
    }
}
