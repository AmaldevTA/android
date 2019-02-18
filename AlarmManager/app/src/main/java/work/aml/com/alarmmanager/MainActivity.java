package work.aml.com.alarmmanager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import work.aml.com.alarmmanager.service.MyIntentService;

public class MainActivity extends AppCompatActivity {

    public static int TIME_INTERVAL = 3 * 60 *1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, MyIntentService.class);
        PendingIntent alarmIntent = PendingIntent.getService(this, 0, intent, 0);


        AlarmManager alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        if (alarmMgr != null) {
            alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime() + TIME_INTERVAL,
                    TIME_INTERVAL, alarmIntent);
        }

    }
}
