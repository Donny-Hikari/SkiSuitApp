package com.donny.skisuit;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

public class TemperatureActivity extends AppCompatActivity {

    private final static int nMinTemperature = 25;
    private final static int nMaxTemperature = 81;

    private boolean mHeatOn = false;
    private int mCurrentTemperature = 25;
    private int mBeginHour = 10, mBeginMin = 0;
    private int mEndHour = 22, mEndMin = 0;
    private int mTimerHour = 3, mTimerMin = 0;

    private CircularSlider temperature;
    private TextView dispNum;
    private Switch mainSwitch;
    private Switch scheduleTime, timer;
    private RelativeLayout beginTime, endTime, timerTime;
    private TextView beginHourMin, endHourMin, timerHourMin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temperature = (CircularSlider) findViewById(R.id.id_temperature);
        dispNum = (TextView) findViewById(R.id.id_dispNum);
        mainSwitch = (Switch) findViewById(R.id.id_mainSwitch);

        scheduleTime = (Switch) findViewById(R.id.id_scheduleTime);
        beginTime = (RelativeLayout) findViewById(R.id.id_startTime);
        endTime = (RelativeLayout) findViewById(R.id.id_endTime);
        beginHourMin = (TextView) findViewById(R.id.id_beginHourMin);
        endHourMin = (TextView) findViewById(R.id.id_endHourMin);

        timer = (Switch) findViewById(R.id.id_timer);
        timerTime = (RelativeLayout) findViewById(R.id.id_timer_time);
        timerHourMin = (TextView) findViewById(R.id.id_timerHourMin);

        temperature.setOnSliderMovedListener(new CircularSlider.OnSliderMovedListener() {
            @Override
            public void onSliderMoved(double pos) {
                if (pos > 0.5) pos = 1 - pos;
                mCurrentTemperature = (int) ((nMaxTemperature - nMinTemperature) * pos * 2 + nMinTemperature);
                onChangeTemperature();
            }
        });

        onClickScheduleTime(scheduleTime);
        onClickTimer(timer);
    }

    public void onChangeTemperature() {
        if (dispNum != null) {
            dispNum.setText(mCurrentTemperature + "°");
            // TODO: Add Real Temperature Control Here.
        }
    }

    public void onClickScheduleTime(View v) {
        if ((v instanceof Switch) && (beginTime != null) && (endTime != null)) {
            if (((Switch) v).isChecked()) {
                beginTime.setVisibility(RelativeLayout.VISIBLE);
                endTime.setVisibility(RelativeLayout.VISIBLE);
            } else {
                beginTime.setVisibility(RelativeLayout.GONE);
                endTime.setVisibility(RelativeLayout.GONE);
            }
        }
    }

    public void onClickTimer(View v) {
        if ((v instanceof Switch) && (timerTime != null)) {
            if (((Switch) v).isChecked()) {
                timerTime.setVisibility(RelativeLayout.VISIBLE);
            } else {
                timerTime.setVisibility(RelativeLayout.GONE);
            }
        }
    }

    public void onClickSetSchedule(View view) {
        if (view == beginTime) {
            new TimePickerDialog(TemperatureActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    mBeginHour = hourOfDay;
                    mBeginMin = minute;
                    beginHourMin.setText(String.format("%02d:%02d", mBeginHour, mBeginMin));
                }
            }, mBeginHour, mBeginMin, true).show();
        } else if (view == endTime) {
            new TimePickerDialog(TemperatureActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    mEndHour = hourOfDay;
                    mEndMin = minute;
                    endHourMin.setText(String.format("%02d:%02d", mEndHour, mEndMin));
                }
            }, mEndHour, mEndMin, true).show();
        }
    }

    public void onClickMainSwitch(View view) {
        if (view == mainSwitch) {
            mHeatOn = mainSwitch.isChecked();
            // TODO: Turn On Or Off Clothes Heating Here.
            if (mHeatOn) {
                dispNum.setTextColor(ContextCompat.getColor(TemperatureActivity.this, R.color.tptOnColor));
            } else {
                dispNum.setTextColor(ContextCompat.getColor(TemperatureActivity.this, R.color.tptOffColor));
            }
        }
    }

    public void onClickSetTimer(View view) {
        if (view == timerTime) {
            new TimePickerDialog(TemperatureActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    mTimerHour = hourOfDay;
                    mTimerMin = minute;
                    timerHourMin.setText(String.format("%02d:%02d", mTimerHour, mTimerMin));
                }
            }, mTimerHour, mTimerMin, true).show();
        }
    }
}
