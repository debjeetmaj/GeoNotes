package maj.geon.geonotes.util;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * Created by amit on 15/04/16.
 */


public class MyTestService extends IntentService {

    public static final String ACTION = "com.codepath.example.servicesdemo.MyTestService";

    public MyTestService() {
        super("MyTestService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Do the task here
        Log.i("MyTestService", "Service running the test service");

    }
}