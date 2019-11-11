package com.skripsi.robby.wisatabelanjaapps.Adapter;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.skripsi.robby.wisatabelanjaapps.Model.Wisbel;
import com.skripsi.robby.wisatabelanjaapps.R;


import java.util.ArrayList;

public class RecomAdapter extends PagerAdapter {

    private LayoutInflater layoutInflater;
    Context context;
    ArrayList<Wisbel> data;
    int a = 1;

    Uri g;

    double sourceLatitude, lokasilat;
    double sourceLongitude, lokasilng;

    //declare adapter constructor
    public RecomAdapter(Context context, ArrayList<Wisbel> data, double lat, double lng){
        this.context = context;
        this.data = data;
        sourceLatitude = lat;
        sourceLongitude = lng;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container,final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.row_recommendation, container, false);

        TextView nama_tempat, alamat, ulasan, rating, bookmark, waktu;
        LinearLayout linearLayout;

        nama_tempat = view.findViewById(R.id.tv_nama_tempat);
        alamat = view.findViewById(R.id.tv_alamat);
        ulasan = view.findViewById(R.id.tv_ulasan);
        rating = view.findViewById(R.id.tv_rating);
        waktu = view.findViewById(R.id.tv_waktu_operasional);
        bookmark = view.findViewById(R.id.tv_bookmark);
        linearLayout = view.findViewById(R.id.bg_layout);

        nama_tempat.setText(data.get(position).getNama());
        alamat.setText(data.get(position).getAlamat());
        ulasan.setText(data.get(position).getUlasan()+" Ulasan");
        rating.setText(String.valueOf(data.get(position).getRating()));

//      Mengambil isi sebagian String (Waktu)
        String j_buka = data.get(position).getJam_buka().substring(0, 5);
        String j_tutup = data.get(position).getJam_tutup().substring(0, 5);
        waktu.setText(j_buka+" - "+j_tutup);

        bookmark.setText(String.valueOf(a++));

        if(data.get(position).getStatus().equals("swalayan")){
            linearLayout.setBackgroundResource(R.color.badgeBlue);
        }
        if(data.get(position).getStatus().equals("mall")){
            linearLayout.setBackgroundResource(R.color.badgeGreen);
        }
        if(data.get(position).getStatus().equals("pasar")){
            linearLayout.setBackgroundResource(R.color.badgeYellow);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to open Maps?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                g = Uri.parse("google.navigation:q="+data.get(position).getLat()+","+data.get(position).getLng());
                                Intent intent = new Intent(Intent.ACTION_VIEW, g);

                                intent.setPackage("com.google.android.apps.maps");
                                context.startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        container.addView(view,0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
