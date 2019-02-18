package work.aml.com.alarmmanager.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //TODO check database,then set alarm
        System.out.println("<<<>>>  MyBootReceiver");
    }
}
