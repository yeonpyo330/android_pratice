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
    private ActivityAlarmBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAlarmBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(AlarmActivity.this,AlarmService.class);
                TIME = Integer.parseInt(binding.time.getText().toString().trim());

                startIntent.putExtra("Time",TIME);
                binding.alarmText.setText("You will receive the message " + binding.time.getText().toString().trim() + "min later");
                Toast.makeText(AlarmActivity.this,"Start Notify",Toast.LENGTH_SHORT).show();
                startService(startIntent);
            }
        });

        binding.stopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stopIntent = new Intent(AlarmActivity.this,AlarmService.class);
                binding.alarmText.setText("Alarm Canceled");
                Toast.makeText(AlarmActivity.this,"Stop Notify",Toast.LENGTH_SHORT).show();
                stopService(stopIntent);
            }
        });
    }

}
