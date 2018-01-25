package com.example.a25zsa.myapplication;

import android.content.Context;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Switch;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.IOException;


public class MainActivity extends AppCompatActivity{

    Thread counter;
    Long time;
    String currentTime = "", alarmTime = "", day = "AM";
    TextView textView;
    TimePicker timePicker3;
    Switch switch1;
    boolean switchState = false;
    Vibrator vibrator;


    String testing = "success";

    Calendar now;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        now = Calendar.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textView);
        timePicker3 = (TimePicker)findViewById(R.id.timePicker3);
        switch1 = (Switch)findViewById(R.id.switch1);
        vibrator = (Vibrator) getSystemService( Context.VIBRATOR_SERVICE );


        counter = new Thread(){
            public void run(){
                while(!isInterrupted()){
                    try{
                        Thread.sleep(100);
                        runOnUiThread( new Runnable(){
                            public void run(){
                                tick();
                                checkTime();
                            }
                        } );

                    } catch( InterruptedException e ) {
                        e.printStackTrace();
                    }
                }
            }
        };
        counter.start();
    }

    public void checkTime(){
        if( currentTime.equals(alarmTime) && switchState ){
            vibrator.vibrate(100);
        }
    }

    public void tick(){
        currentTime = getCurrentTime();
        alarmTime = getAlarmTime();
        switchState = getSwitchState();
        textView.setText("Current Time: " + currentTime + "\n\nAlarm Time  : " + alarmTime);
    }

    public boolean getSwitchState(){
        return switch1.isChecked();
    }

    public String getAlarmTime(){
        Integer hour = timePicker3.getCurrentHour();
        Integer min = timePicker3.getCurrentMinute();
        String alarmDay = "AM";
        if(hour >= 12){
            alarmDay = "PM";
        }
        if(hour > 12){
            hour -= 12;
        }
        return ((hour < 10) ? ("0" + hour):hour) + ":" + ((min < 10) ? ("0" + min):min) + " " + alarmDay;
    }

    public String getCurrentTime(){
        DateFormat temp = new SimpleDateFormat("hh:mm a");
        return temp.format(new Date()).toString();
    }
}
