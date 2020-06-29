package com.example.savemoneyproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.savemoneyproject.databinding.ActivityAlarmBinding;


// todo : please convert this with view binding
// todo : optimize && clean unused code
public class AlarmActivity extends AppCompatActivity {

    public static int TIME;
    private ActivityAlarmBinding bindingAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingAlarm = ActivityAlarmBinding.inflate(getLayoutInflater());
        View view = bindingAlarm.getRoot();
        setContentView(view);

        bindingAlarm.startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(AlarmActivity.this,AlarmService.class);
                TIME = Integer.parseInt(bindingAlarm.time.getText().toString().trim());

                startIntent.putExtra("Time",TIME);
                bindingAlarm.alarmText.setText("You will receive the message " + bindingAlarm.time.getText().toString().trim() + "min later");
                Toast.makeText(AlarmActivity.this,"Start Notify",Toast.LENGTH_SHORT).show();
                startService(startIntent);
            }
        });

        bindingAlarm.stopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stopIntent = new Intent(AlarmActivity.this,AlarmService.class);
                bindingAlarm.alarmText.setText("Alarm Canceled");
                Toast.makeText(AlarmActivity.this,"Stop Notify",Toast.LENGTH_SHORT).show();
                stopService(stopIntent);
            }
        });
    }

}
