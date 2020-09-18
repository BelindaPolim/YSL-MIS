package id.com.ervsoftware.ysl;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProfileDetailActivity extends AppCompatActivity {

    private String TAG = ProfileDetailActivity.class.getSimpleName();

    private ProgressDialog prog;
    private ListView lv;
    TextView tvNama;

    ArrayList<ProfileDetailModel> profileDetail = new ArrayList<>();
    String apiUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);

        lv = findViewById(R.id.listView);
        lv.setDivider(null);

        Intent getPosition = getIntent();
        String url = getPosition.getStringExtra("url");
        String param = getPosition.getStringExtra("param");
        String code = getPosition.getStringExtra("code");
        String name = getPosition.getStringExtra("name");

        apiUrl = url + param + code + "&LoginUserId=" + Setting.SP_USER;
        Log.d(TAG, "onCreate - apiUrl : " + apiUrl);

        tvNama = findViewById(R.id.valNama);
        tvNama.setText(name);

        new GetInfo().execute();
    }

    private void  showProgressDialog(){
        if(prog == null){
            prog = new ProgressDialog(ProfileDetailActivity.this);
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

    private class GetInfo extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected Void doInBackground(String... strings) {
            HttpHandler sh = new HttpHandler();

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

                        int seq = c.getInt("NoSeq");
                        String info = c.getString("Info");
                        profileDetail.add(new ProfileDetailModel(seq, info));
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
            if (ProfileDetailActivity.this.isDestroyed()){
                return;
            }
            dismissProgressDialog();

//            lv.setAdapter(new InfoAdapter(InformasiTambahan.this, R.layout.list_info_tambahan, informasi));

            ProfileDetailAdapter adapter = new ProfileDetailAdapter(getApplicationContext(), profileDetail);
            lv.setAdapter(adapter);
        }
    }

}