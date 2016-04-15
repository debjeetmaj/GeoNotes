package maj.geon.geonotes.util;

/**
 * Created by amit on 15/04/16.
 */

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyJobService extends JobService {

    RequestQueue queue;
    SessionManager sm;

    public MyJobService() {}

    @Override
    public boolean onStartJob(JobParameters params) {
        if (sm == null)
            sm = new SessionManager(getApplicationContext());
        if (queue == null)
            queue = Volley.newRequestQueue(getApplicationContext());

        Log.i("MyTestService", "Service running from job scheduler ");
        pushLocationUpdates();

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Toast.makeText(this,
                "MyJobService.onStopJob()",
                Toast.LENGTH_SHORT).show();
        return false;
    }

    private void pushLocationUpdates() {

        String url = GNConstants.server_URL + "/location";
        Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put("userID", sm.getKeyFbid());
        String[] location = Utils.getLocation(getApplicationContext());

        if(location != null) {
            jsonParams.put("locationLat", location[0]);
            jsonParams.put("locationLon", location[1]);
        }

        Log.d("reqObj", jsonParams.toString());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(  Request.Method.POST, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("response", response.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("response", "error handling request");
                    }
                });
        //add request to queue
        queue.add(jsonObjectRequest);
    }
}
