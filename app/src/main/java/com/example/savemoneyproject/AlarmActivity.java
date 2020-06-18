package com.example.savemoneyproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AlarmActivity extends AppCompatActivity {

    private Button startService;
    private Button stopService;
    private EditText time;
    public static int TIME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        Button start = (Button) findViewById(R.id.start_serice) ;
        Button stop = (Button) findViewById(R.id.stop_serice);
        final TextView alarmMessage = findViewById(R.id.alarm_text);
        time = (EditText) findViewById(R.id.time);


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(AlarmActivity.this,AlarmService.class);
                TIME = Integer.parseInt(time.getText().toString().trim());

                startIntent.putExtra("Time",TIME);
                alarmMessage.setText("You will receive the message " + time.getText().toString().trim() + "min later");
                Toast.makeText(AlarmActivity.this,"Start Notify",Toast.LENGTH_SHORT).show();
                startService(startIntent);

            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stopIntent = new Intent(AlarmActivity.this,AlarmService.class);
                alarmMessage.setText("Alarm Canceled");
                Toast.makeText(AlarmActivity.this,"Stop Notify",Toast.LENGTH_SHORT).show();
                stopService(stopIntent);

            }
        });
    }

}
