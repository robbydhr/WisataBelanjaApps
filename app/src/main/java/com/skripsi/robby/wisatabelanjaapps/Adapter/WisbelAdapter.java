package com.skripsi.robby.wisatabelanjaapps.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skripsi.robby.wisatabelanjaapps.Model.Wisbel;
import com.skripsi.robby.wisatabelanjaapps.R;

import java.util.ArrayList;
import java.util.Locale;

public class WisbelAdapter extends RecyclerView.Adapter<WisbelAdapter.CustomViewHolder> {

    Context context;
    ArrayList<Wisbel> data;

    Uri g;

    //declare adapter constructor
    public WisbelAdapter(Context context, ArrayList<Wisbel> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.row_data, parent, false);

        return new CustomViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {
        holder.tv_nama_tempat.setText(((Wisbel)data.get(position)).getNama());
        holder.tv_alamat.setText(((Wisbel)data.get(position)).getAlamat());
        holder.tv_rating_ulasan.setText(String.valueOf(((Wisbel)data.get(position)).getRating())+" / "+String.valueOf(((Wisbel)data.get(position)).getUlasan())+" Ulasan");

        //Mengambil isi sebagian String (Waktu)
        String j_buka = ((Wisbel)data.get(position)).getJam_buka().substring(0, 5);
        String j_tutup = ((Wisbel)data.get(position)).getJam_tutup().substring(0, 5);
        holder.tv_waktu.setText(j_buka+" - "+j_tutup);

        //Warna status
        holder.tv_status.setText(((Wisbel)data.get(position)).getStatus());
        if(holder.tv_status.getText().equals("swalayan")){
            holder.badge.setBackgroundResource(R.drawable.badge_blue);
        }
        if(holder.tv_status.getText().equals("mall")){
            holder.badge.setBackgroundResource(R.drawable.badge_green);
        }
        if (holder.tv_status.getText().equals("pasar")){
            holder.badge.setBackgroundResource(R.drawable.badge_yellow);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
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
}
    @Override
    public int getItemCount() {
        return data.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder{
        LinearLayout badge;
        TextView tv_nama_tempat, tv_alamat, tv_rating_ulasan, tv_waktu, tv_status;

        public CustomViewHolder(View itemView) {
            super(itemView);
            tv_nama_tempat = itemView.findViewById(R.id.tv_nama_tempat);
            tv_alamat = itemView.findViewById(R.id.tv_alamat);
            tv_rating_ulasan = itemView.findViewById(R.id.tv_rating_ulasan);
            tv_waktu = itemView.findViewById(R.id.tv_waktu_operasional);
            tv_status = itemView.findViewById(R.id.tv_status);
            badge = itemView.findViewById(R.id.badge_status);
        }
    }
}
