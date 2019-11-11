package com.skripsi.robby.wisatabelanjaapps.Model;

public class Wisbel {
    private String id;
    private String nama;
    private String status;
    private String alamat;
    private String jam_buka;
    private String jam_tutup;
    private double rating;
    private int ulasan;
    private double lat;
    private double lng;

    public Wisbel(String id, String nama, double rating, int ulasan, String jam_buka, String jam_tutup, double lat, double lng, String status, String alamat){
        this.setId(id);
        this.setNama(nama);
        this.setRating(rating);
        this.setUlasan(ulasan);
        this.setJam_buka(jam_buka);
        this.setJam_tutup(jam_tutup);
        this.setLat(lat);
        this.setLng(lng);
        this.setStatus(status);
        this.setAlamat(alamat);
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getUlasan() {
        return ulasan;
    }

    public void setUlasan(int ulasan) {
        this.ulasan = ulasan;
    }

    public String getJam_buka() {
        return jam_buka;
    }

    public void setJam_buka(String jam_buka) {
        this.jam_buka = jam_buka;
    }

    public String getJam_tutup() {
        return jam_tutup;
    }

    public void setJam_tutup(String jam_tutup) {
        this.jam_tutup = jam_tutup;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
