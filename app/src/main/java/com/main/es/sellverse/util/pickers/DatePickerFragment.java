package com.main.es.sellverse.util.pickers;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.main.es.sellverse.R;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment  implements DatePickerDialog.OnDateSetListener {


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Calendar calendar = Calendar.getInstance();
        int day= calendar.get(Calendar.DAY_OF_MONTH);
        int month=calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog picker=new DatePickerDialog(getActivity(), R.style.Theme_datePicker,this,year,month,day);
        picker.getDatePicker().setMinDate(calendar.getTimeInMillis());
        return picker;
    }
}
