package work.aml.com.alarmmanager.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;


public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyIntentService");
    }



    @Override
    protected void onHandleIntent(Intent intent) {

        System.out.println("<<<>>>  MyIntentService");


        Intent serviceIntent = new Intent(this, MyIntentService.class);
        PendingIntent alarmIntent = PendingIntent.getService(this, 0, serviceIntent, 0);
        AlarmManager alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        if (alarmMgr!= null) {
            //TODO cancel alarm if all works are done
            alarmMgr.cancel(alarmIntent);
        }
    }

}
