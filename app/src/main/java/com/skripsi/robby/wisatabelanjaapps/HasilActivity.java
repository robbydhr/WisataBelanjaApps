package com.skripsi.robby.wisatabelanjaapps;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;
import android.widget.TextView;

import com.skripsi.robby.wisatabelanjaapps.Adapter.RecomAdapter;
import com.skripsi.robby.wisatabelanjaapps.Adapter.WisbelAdapter;
import com.skripsi.robby.wisatabelanjaapps.Model.Wisbel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HasilActivity extends AppCompatActivity {
    JSONObject jsonObject;
    JSONArray jsonArray;

    String json_code;

    RecyclerView recyclerView;
    ViewPager viewPager;

    double myLatitude;
    double myLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil);
        Intent intent = getIntent();
        json_code = intent.getStringExtra("JSON_CODE");
        myLatitude = intent.getExtras().getDouble("MY_LAT");
        myLongitude = intent.getExtras().getDouble("MY_LNG");

        recyclerView = findViewById(R.id.rv);
        viewPager = findViewById(R.id.viewPager);

        try {
            jsonObject = new JSONObject(json_code);
            jsonArray = jsonObject.getJSONArray("server_response");
            int count = 0;
            String id, nama_tempat, status, alamat, jam_buka, jam_tutup;
            double rating, lat, lng;
            int ulasan;
            ArrayList<Wisbel> other = new ArrayList<>();
            ArrayList<Wisbel> recom = new ArrayList<>();
            while(count<jsonArray.length()){
                JSONObject JO = jsonArray.getJSONObject(count);
                id = JO.getString("id");
                nama_tempat = JO.getString("nama_tempat");
                rating = Double.valueOf(JO.getString("rating"));
                ulasan = Integer.valueOf(JO.getString("ulasan"));
                jam_buka = JO.getString("jam_buka");
                jam_tutup = JO.getString("jam_tutup");
                lat = JO.getDouble("lat");
                lng = JO.getDouble("long");
                status = JO.getString("status");
                alamat = JO.getString("alamat");

                if(count>=0 && count<=2){
                    recom.add(new Wisbel(id, nama_tempat, rating, ulasan, jam_buka, jam_tutup, lat, lng, status, alamat));
                }else {
                    other.add(new Wisbel(id, nama_tempat, rating, ulasan, jam_buka, jam_tutup, lat, lng, status, alamat));
                }
                count++;
            }
            RecomAdapter adapterRecom = new RecomAdapter(HasilActivity.this, recom, myLatitude, myLongitude);
            viewPager.setAdapter(adapterRecom);
            viewPager.setPadding(130,0,130,0);

            WisbelAdapter adapterOther = new WisbelAdapter(HasilActivity.this, other);
            recyclerView.setLayoutManager(new LinearLayoutManager(HasilActivity.this));
            recyclerView.setAdapter(adapterOther);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
