package id.com.ervsoftware.ysl;

import com.google.gson.annotations.SerializedName;

public class JatuhTempoModel {
    @SerializedName("nomor")
    private String mNomor;
    @SerializedName("tanggalFkt")
    private String mTanggal;
    @SerializedName("jatuhTempo")
    private String mJatuhTempo;
    @SerializedName("jmlHari")
    private int mJmlHari;
    @SerializedName("nilai")
    private String mNilai;

    public JatuhTempoModel(String nomor, String tanggal, String jatuhtempo, int jumlahhari, String nilai){
        mNomor = nomor;
        mTanggal = tanggal;
        mJatuhTempo = jatuhtempo;
        mJmlHari = jumlahhari;
        mNilai = nilai;
    }

    public String getNomor(){
        return mNomor;
    }
    public String getTanggal(){
        return mTanggal;
    }
    public String getJatuhTempo(){
        return mJatuhTempo;
    }
    public int getJmlHari(){
        return mJmlHari;
    }
    public String getNilai(){
        return mNilai;
    }
}
