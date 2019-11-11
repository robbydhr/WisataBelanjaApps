package com.skripsi.robby.wisatabelanjaapps;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.nex3z.togglebuttongroup.MultiSelectToggleGroup;
import com.nex3z.togglebuttongroup.button.LabelToggle;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BobotActivity extends AppCompatActivity {
    SeekBar sbRating, sbFasilitas, sbWaktu, sbJarak;
    int b_rating, b_ulasan, b_waktu, b_jarak;
    Button bt_submit;
    boolean [] status = new boolean[3];

    RequestQueue requestQueue;
    String insertUrl = "https://wisbel.000webhostapp.com/json_count.php";

    double myLatitude;
    double myLongitude;

    private FusedLocationProviderClient myLocation;
    private static final int LOCATION_REQUEST = 1000;

    TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bobot);

        sbRating = findViewById(R.id.sbRating);
        sbFasilitas = findViewById(R.id.sbFasilitas);
        sbWaktu = findViewById(R.id.sbWaktu);
        sbJarak = findViewById(R.id.sbJarak);

        bt_submit = findViewById(R.id.bt_submit);

        test = findViewById(R.id.test);

        if (ActivityCompat.checkSelfPermission(BobotActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(BobotActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_REQUEST);
        }
        myLocation = LocationServices.getFusedLocationProviderClient(BobotActivity.this);
        myLocation.getLastLocation().addOnSuccessListener(BobotActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                myLatitude = location.getLatitude();
                myLongitude = location.getLongitude();
            }
        });

        MultiSelectToggleGroup multiSelectToggleGroup = findViewById(R.id.group_status);
        String[] array_status = {"Mall", "Swalayan", "Pasar"};
        for (String status : array_status) {
            LabelToggle toggle = new LabelToggle(this);
            toggle.setText(status);
            toggle.setMarkerColor(ContextCompat.getColor(this, R.color.colorPrimary));
            multiSelectToggleGroup.addView(toggle);
        }
        multiSelectToggleGroup.setOnCheckedChangeListener(new MultiSelectToggleGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedStateChanged(MultiSelectToggleGroup group, int checkedId, boolean isChecked) {
                if(isChecked){
                    switch (checkedId){
                        case 1 : status[0] = true; break;
                        case 2 : status[1] = true; break;
                        case 3 : status[2] = true; break;
                    }
                }
                else {
                    switch (checkedId) {
                        case 1 : status[0] = false; break;
                        case 2 : status[1] = false; break;
                        case 3 : status[2] = false; break;
                    }
                }
            }
        });

        requestQueue = Volley.newRequestQueue(this);
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!status[0]&&!status[1]&&!status[2]){
                    Toast.makeText(getApplicationContext(), "Tolong Masukkan Alternatif", Toast.LENGTH_LONG).show();
                }else{
                    insertValue();
                }
            }
        });

        sbRating.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                b_rating = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sbFasilitas.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                b_ulasan = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sbWaktu.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                b_waktu = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sbJarak.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                b_jarak = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void insertValue(){
        final ProgressDialog dialog = ProgressDialog.show(this, null, "Mohon tunggu...");
        dialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response", response);
                dialog.hide();
                if(response!=null){
//                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(BobotActivity.this, HasilActivity.class);
                    intent.putExtra("MY_LAT", myLatitude);
                    intent.putExtra("MY_LNG", myLongitude);
                    intent.putExtra("JSON_CODE", response);
                    requestQueue.stop();
                    finish();
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error.Response", error.toString());
                dialog.hide();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parameters  = new HashMap<String, String>();
                parameters.put("b_rating", String.valueOf(b_rating));
                parameters.put("b_ulasan", String.valueOf(b_ulasan));
                parameters.put("b_waktu", String.valueOf(b_waktu));
                parameters.put("b_jarak", String.valueOf(b_jarak));
                parameters.put("s_mall", String.valueOf(status[0]));
                parameters.put("s_swalayan", String.valueOf(status[1]));
                parameters.put("s_pasar", String.valueOf(status[2]));
                parameters.put("my_lat", String.valueOf(myLatitude));
                parameters.put("my_lng", String.valueOf(myLongitude));
                return parameters;
            }
        };
        requestQueue.add(request);
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 6000);//60 second delay
            }
        };
        handler.postDelayed(runnable, 6000);
    }
}
