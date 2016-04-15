package maj.geon.geonotes;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
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
import maj.geon.geonotes.util.GNConstants;
import maj.geon.geonotes.util.MyJobService;
import maj.geon.geonotes.util.SessionManager;
import maj.geon.geonotes.util.Utils;

public class RegisterActivity extends AppCompatActivity {

    private TextView info;
    private SessionManager sm;
    RequestQueue queue;
    JobScheduler jobScheduler;
    private static final int MYJOBID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        jobScheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        final Button registerGroup = (Button) findViewById(R.id.register_as_group);
        Button registerIndividual = (Button) findViewById(R.id.register_as_indivdual);
        info = (TextView)findViewById(R.id.info);
        sm = new SessionManager(getApplicationContext());

        try {
            queue = Volley.newRequestQueue(getApplicationContext());
        } catch(Exception e) {
            Log.d("Volley " , "failed");
        }

        registerGroup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser("group");
            }
        });

        registerIndividual.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser("individual");
            }
        });

        info.setText(sm.getKeyName());

    }

    private void registerUser(String userType) {
        String url = GNConstants.server_URL + "/adduser";

        Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put("userID", sm.getKeyFbid());
        jsonParams.put("userName", sm.getKeyName());
        jsonParams.put("userType", userType);
        jsonParams.put("gcmID", sm.getKeyGcmid());

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
                            Toast.makeText(RegisterActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                            Log.i("response", response.toString());

                            if(response.get("status").equals("success")){
                                sm.setIsRegistered(true);
                                scheduleJOB();
                                finish();
                                Intent intent = new Intent(getApplicationContext(), GNMainActivity.class);
                                startActivity(intent);
                            }
                            else
                                Toast.makeText(RegisterActivity.this,
                                        "Registration failed ",
                                        Toast.LENGTH_SHORT).show();

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

    private void scheduleJOB() {

        ComponentName jobService =
                new ComponentName(getPackageName(), MyJobService.class.getName());

        JobInfo jobInfo =
                new JobInfo.Builder(MYJOBID, jobService).setPeriodic(1000*60).build();

        int jobId = jobScheduler.schedule(jobInfo);
        if(jobScheduler.schedule(jobInfo)>0){
            Toast.makeText(RegisterActivity.this,
                    "Successfully scheduled job: " + jobId,
                    Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(RegisterActivity.this,
                    "RESULT_FAILURE: " + jobId,
                    Toast.LENGTH_SHORT).show();
        }
    }
}

