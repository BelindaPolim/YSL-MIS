package id.com.ervsoftware.ysl;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JatuhTempoActivity extends AppCompatActivity {

    private static final String TAG = "Jatuh Tempo";

    ImageView imgBack, imgTime, imgRefresh, imgSearch;
    TextView tvJudul, tvNama;
    EditText etSetHari, etSearch;
    CheckBox chkJatuhTempo;
    Button btnUpdate;
    ListView lv;
    private ProgressDialog prog;
    String apiUrl;

    ArrayList<JatuhTempoModel> jatuhTempo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jatuh_tempo);

        etSetHari = findViewById(R.id.setHari);
        etSetHari.setText(String.valueOf(Setting.SET_HARI));


        chkJatuhTempo = findViewById(R.id.chkJatuhTempo);
        chkJatuhTempo.setChecked(Setting.jtChecked);

        chkJatuhTempo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Setting.jtChecked){
                    Setting.STATE_JATUH_TEMPO = 1;
                    Setting.jtChecked = true;
                }
                else {
                    Setting.STATE_JATUH_TEMPO = 0;
                    Setting.jtChecked = false;
                }
            }
        });

        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Setting.SET_HARI = Integer.parseInt(etSetHari.getText().toString());
                refreshData();
            }
        });

        String url = Setting.jtUrl;
        String param = Setting.jtParam;
        String code = Setting.jtCode;
        String name = Setting.jtName;

        tvJudul = findViewById(R.id.judul);
        String ifCust = "Nama Customer : ";
        String ifSupp = "Nama Supplier : ";

        if (param.contains("Cust")){
            tvJudul.setText(ifCust);
        }
        else {
            tvJudul.setText(ifSupp);
        }
        tvNama = findViewById(R.id.nama);
        tvNama.setText(name);

        apiUrl = url + "JumlahHari=" + Setting.SET_HARI + "&BelumJatuhTempo=" + Setting.STATE_JATUH_TEMPO + "&LoginUserId=" + Setting.SP_USER + param + code;
        Log.d(TAG, "onCreate: url = " + apiUrl);

        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParent();
                finish();
            }
        });

//        imgTime = findViewById(R.id.imgTime);
//        imgTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent display = new Intent(getApplicationContext(), SetTimeActivity.class);
//                startActivityForResult(display, 1);
//            }
//        });

//        imgRefresh = findViewById(R.id.imgRefresh);
//        imgRefresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                refreshData();
//            }
//        });

        lv = findViewById(R.id.listView);
        lv.setTextFilterEnabled(true);

        etSearch = findViewById(R.id.inputSearch);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    jatuhTempo.clear();
                    String cari = etSearch.getText().toString().trim();
                    new JatuhTempoActivity.GetContacts().execute(cari);
                    return true;
                }
                return false;
            }
        });

        String cari = etSearch.getText().toString().trim();
        new JatuhTempoActivity.GetContacts().execute(cari);

        imgSearch = findViewById(R.id.imgSearch);
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jatuhTempo.clear();
                String cari = etSearch.getText().toString().trim();
                new JatuhTempoActivity.GetContacts().execute(cari);

            }
        });
    }

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1) {
//            if(resultCode == RESULT_OK){
//                refreshData();
//            }
//        }
//    }

    private void refreshData() {
        startActivity(new Intent(JatuhTempoActivity.this, JatuhTempoActivity.class));
        finish();
    }

    private void  showProgressDialog(){
        if(prog == null){
            prog = new ProgressDialog(JatuhTempoActivity.this);
            prog.setMessage("Fetching data...");
            prog.setIndeterminate(false);
            prog.setCancelable(false);
        }
        prog.show();
    }

    private void dismissProgressDialog() {
        if (prog != null && prog.isShowing()) {
            prog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        dismissProgressDialog();
        super.onDestroy();
    }

    private class GetContacts extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected Void doInBackground(String... strings) {
            HttpHandler sh = new HttpHandler();
//            Log.d(TAG, "doInBackground: INI VALUE SP FROM DATE" + Setting.FROM_DATE + "SP_TODATE" + Setting.TO_DATE);
            String url = apiUrl;
            String jsonStr = sh.makeServiceCall(url);

            if(jsonStr == null) Log.e(TAG, "JSON null");

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("data");
                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String nomor = c.getString("NomorFkt");
                        String tanggal = c.getString("TglFkt");
                        String jatuhtempo = c.getString("TglJatuhTempo");
                        int jmlHari = c.getInt("JumlahHari");
                        int nominal = c.getInt("NilaiPIutang");

                        String nilai = Setting.pemisahRibuan(nominal);

                        String search = strings[0];
                        if(search.isEmpty()){
                            jatuhTempo.add(new JatuhTempoModel(nomor, tanggal, jatuhtempo, jmlHari, Setting.pemisahRibuan(nominal).substring(0, nilai.length()-3)));
                        }
                        else if(nomor.contains(search)) {
                            jatuhTempo.add(new JatuhTempoModel(nomor, tanggal, jatuhtempo, jmlHari, Setting.pemisahRibuan(nominal).substring(0, nilai.length()-3)));
                        }
                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (JatuhTempoActivity.this.isDestroyed()){
                return;
            }
            dismissProgressDialog();

            lv.setAdapter(new JatuhTempoAdapter(JatuhTempoActivity.this, R.layout.list_jatuh_tempo, jatuhTempo));
        }
    }

}

