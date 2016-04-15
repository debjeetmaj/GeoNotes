package maj.geon.geonotes.util;

import android.content.Context;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by amit on 15/04/16.
 */

public class Utils {

    public static String[] getLocation(Context context) {
        String[] location = null;
        try {
            GeoGPSTrackerService gps = new GeoGPSTrackerService(context);
            if(gps.canGetLocation()){
                location = new String[2];
                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();
                location = new String[2];
                location[0] = latitude + "";
                location[1] = longitude + "";
            }
        } catch (SecurityException e) {
            Log.d("LocationManager", "An error occured with location service");
        }
        return location;
    }
}
