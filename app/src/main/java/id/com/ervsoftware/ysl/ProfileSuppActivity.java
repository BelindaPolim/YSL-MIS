package id.com.ervsoftware.ysl;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

public class ProfileSuppActivity extends AppCompatActivity {

    private String TAG = ProfileSuppActivity.class.getSimpleName();

    private ProgressDialog prog;
    private ListView lv;
//    ExpandableListAdapter listAdapter;
//    ExpandableListView expListView;
//    List<String> listProfileNames;
//    HashMap<String, List<String>> listProfileDetails;
    EditText etSearch;
    ImageView imgBack, imgRefresh, imgSearch;

    ArrayList<ProfileModel> profileSupp = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_supp);

//        expListView = (ExpandableListView) findViewById(R.id.expandableListView);
//        expListView.setTextFilterEnabled(true);

        lv = findViewById(R.id.listView);
        lv.setTextFilterEnabled(true);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailProfil = new Intent(ProfileSuppActivity.this, ProfileDetailActivity.class);
                String code = profileSupp.get(position).getID();
                String name = profileSupp.get(position).getName();
                detailProfil.putExtra("url", Setting.API_Profile_Supplier_Details);
                detailProfil.putExtra("param", "?SuppCode=");
                detailProfil.putExtra("code", code);
                detailProfil.putExtra("name", name);
                startActivity(detailProfil);
                return false;
            }
        });

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
                    profileSupp.clear();
                    String cari = etSearch.getText().toString().trim();
                    new ProfileSuppActivity.GetContacts().execute(cari);
                    return true;
                }
                return false;
            }
        });


        imgSearch = findViewById(R.id.imgSearch);
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileSupp.clear();
                String cari = etSearch.getText().toString().trim();
                new ProfileSuppActivity.GetContacts().execute(cari);
            }
        });
    }


    private void refreshData() {
        startActivity(new Intent(getApplicationContext(), ProfileSuppActivity.class));
        finish();
    }

    private void  showProgressDialog(){
        if(prog == null){
            prog = new ProgressDialog(ProfileSuppActivity.this);
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

            String url = Setting.API_Profile_Supplier + "?LoginUserId=" + Setting.SP_USER;
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

                        String id = c.getString("SuppCode");
                        String name = c.getString("FullName");

                        String search = strings[0];
                        if(search.isEmpty()){
                            profileSupp.add(new ProfileModel(id, name));
                        }
                        else if(name.contains(search.toUpperCase())) {
                            profileSupp.add(new ProfileModel(id, name));
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
            if (ProfileSuppActivity.this.isDestroyed()){
                return;
            }
            dismissProgressDialog();

//            Log.d(TAG, "onPostExecute: value listProfileNames " + listProfileNames);

//            listAdapter = new ExpandableListAdapter(getApplicationContext(), listProfileNames, listProfileDetails);
//            expListView.setAdapter(new ExpandableListAdapter(ProfileSuppActivity.this, listProfileNames, listProfileDetails));
            lv.setAdapter(new ProfileAdapter(ProfileSuppActivity.this, R.layout.list_profile, profileSupp));
        }
    }
}