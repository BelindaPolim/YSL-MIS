package id.com.ervsoftware.ysl;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PiutangActivity extends AppCompatActivity {

    private String TAG = PiutangActivity.class.getSimpleName();

    private ProgressDialog prog;
    private ListView lv;
    EditText etSearch;
    ImageView imgBack, imgRefresh, imgSearch;
    TextView tvTotal, tvJml;
    long total = 0;
    int jml = 0;

    ArrayList<PiutangModel> piutang = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piutang);

        lv = findViewById(R.id.listView);
        lv.setTextFilterEnabled(true);
        etSearch = findViewById(R.id.inputSearch);
        String cari = etSearch.getText().toString().trim();
        new GetContacts().execute(cari);

        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParent();
                finish();
            }
        });

        imgRefresh = findViewById(R.id.imgRefresh);
        imgRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshData();
            }
        });

        etSearch = findViewById(R.id.inputSearch);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    piutang.clear();
                    String cari = etSearch.getText().toString().trim();
                    new GetContacts().execute(cari);
                    total = 0;
                    jml = 0;
                    return true;
                }
                return false;
            }
        });


        imgSearch = findViewById(R.id.imgSearch);
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                searchFunc(etSearch.getText().toString().trim());
                piutang.clear();
//                etSearch = findViewById(R.id.inputSearch);
                String cari = etSearch.getText().toString().trim();
                new GetContacts().execute(cari);
                total = 0;
                jml = 0;
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final String[] choices = {"Jatuh tempo piutang"};

                AlertDialog.Builder builder = new AlertDialog.Builder(PiutangActivity.this);
                builder.setItems(choices, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0){
                            Intent jatuhTempo = new Intent(PiutangActivity.this, JatuhTempoActivity.class);
                            String code = piutang.get(position).getID();
                            jatuhTempo.putExtra("url", Setting.API_Jatuh_Tempo_Piutang);
                            jatuhTempo.putExtra("param", "CustCode=");
                            jatuhTempo.putExtra("code", code);
                            startActivity(jatuhTempo);
                        }
                    }
                });
                builder.show();
                return true;
            }
        });

        tvTotal = findViewById(R.id.totalPiutang);
        tvJml = findViewById(R.id.jumlahCust);
    }

    private void refreshData() {
        startActivity(new Intent(PiutangActivity.this, PiutangActivity.class));
        finish();
    }

    private void  showProgressDialog(){
        if(prog == null){
            prog = new ProgressDialog(PiutangActivity.this);
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

            // Making a request to url and getting response
//            String url = "http://119.235.208.235:8092/piutang_dagang_per_customer";
            String url = Setting.API_Piutang_Dagang + "?LoginUserId=" + Setting.SP_USER;
            String jsonStr = sh.makeServiceCall(url);

//            DecimalFormat pemisahRibuan = (DecimalFormat) DecimalFormat.getCurrencyInstance();
//            DecimalFormatSymbols formatPemisah = new DecimalFormatSymbols();
//
//            formatPemisah.setCurrencySymbol("");
//            formatPemisah.setMonetaryDecimalSeparator(',');
//            formatPemisah.setGroupingSeparator('.');
//
//            pemisahRibuan.setDecimalFormatSymbols(formatPemisah);

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

                        String id = c.getString("CustCode");
                        String name = c.getString("FullName");
                        int sisaPiutang = c.getInt("SisaPiutang");

                        String sisa = Setting.pemisahRibuan(sisaPiutang);

                        String search = strings[0];
                        if(search.isEmpty()){
                            piutang.add(new PiutangModel(id, name, Setting.pemisahRibuan(sisaPiutang).substring(0, sisa.length()-3)));
                            total += sisaPiutang;
                            jml += 1;
                        }
                        else if(name.contains(search.toUpperCase())) {
                            piutang.add(new PiutangModel(id, name, Setting.pemisahRibuan(sisaPiutang).substring(0, sisa.length()-3)));
                            total += sisaPiutang;
                            jml += 1;
                        }
                        else {
//                            piutang.add(new PiutangModel(id, name, sisaPiutang));
                        }
                    }
//                    if(piutang == null){
//                        piutang.add(new PiutangModel("", "Tidak ditemukan", 0));
//                    }
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
            if (PiutangActivity.this.isDestroyed()){
                return;
            }
            dismissProgressDialog();

            lv.setAdapter(new PiutangAdapter(PiutangActivity.this, R.layout.list_piutang, piutang));
            tvTotal.setText(Setting.pemisahRibuan(total).substring(0, Setting.pemisahRibuan(total).length()-3));
            tvJml.setText(String.valueOf(jml));
        }
    }
}
