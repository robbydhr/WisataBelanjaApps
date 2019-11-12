package com.skripsi.robby.wisatabelanjaapps;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.skripsi.robby.wisatabelanjaapps.Adapter.WisbelAdapter;
import com.skripsi.robby.wisatabelanjaapps.Model.Wisbel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String JSON_STRING;
    String json_data;
    JSONObject jsonObject;
    JSONArray jsonArray;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if (!manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
        }
        recyclerView = findViewById(R.id.rv);
        new BackgroundTask().execute();

        Button bt_atur_bobot = (Button) findViewById(R.id.bt_atur_bobot);
        bt_atur_bobot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,BobotActivity.class);
                startActivity(i);
            }
        });

    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("GPS Anda tampaknya tidak aktif, apakah Anda ingin mengaktifkannya?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    class BackgroundTask extends AsyncTask<Void,Void,String>{
        String json_url;
        @Override
        protected void onPreExecute() {
            json_url = "https://Wisbel.000webhostapp.com/json_get_data.php";

        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while ((JSON_STRING = bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(JSON_STRING+"\n");

                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            json_data = result;
            if(json_data==null)
            {
                Toast.makeText(getApplicationContext(), "please restart apps", Toast.LENGTH_LONG).show();
            }
            else
            {
                try {
                    jsonObject = new JSONObject(json_data);
                    jsonArray = jsonObject.getJSONArray("server_response");
                    int count = 0;
                    String id, nama_tempat, status, alamat, jam_buka, jam_tutup;
                    double rating, lat, lng;
                    int ulasan;
                    ArrayList<Wisbel> wsb = new ArrayList<>();

                    while(count<jsonArray.length()){
                        JSONObject JO = jsonArray.getJSONObject(count);
                        id = JO.getString("id");
                        nama_tempat = JO.getString("nama_tempat");
                        rating = Double.valueOf(JO.getString("rating"));
                        ulasan = Integer.valueOf(JO.getString("ulasan"));
                        jam_buka = JO.getString("jam_buka");
                        jam_tutup = JO.getString("jam_tutup");
                        lat = Double.valueOf(JO.getString("lat"));
                        lng = Double.valueOf(JO.getString("long"));
                        status = JO.getString("status");
                        alamat = JO.getString("alamat");
                        wsb.add(new Wisbel(id,nama_tempat,rating,ulasan,jam_buka,jam_tutup,lat,lng,status,alamat));
                        count++;
                    }
                    WisbelAdapter adapter = new WisbelAdapter(MainActivity.this, wsb);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
