package id.com.ervsoftware.ysl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class SetTimeActivity extends AppCompatActivity {

    private static final String TAG = "Set Time";

    private EditText setHari;
    private CheckBox chkJatuhTempo;
    private Button btnSetTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time);

        setHari = findViewById(R.id.setHari);
        setHari.setText(String.valueOf(Setting.SET_HARI));

        chkJatuhTempo = findViewById(R.id.chkJatuhTempo);
        chkJatuhTempo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkJatuhTempo.isChecked()){
                    Setting.STATE_JATUH_TEMPO = 1;
                    chkJatuhTempo.setActivated(true);
                }
                else {
                    Setting.STATE_JATUH_TEMPO = 0;
                    chkJatuhTempo.setActivated(!chkJatuhTempo.isActivated());
                }
            }
        });

        btnSetTime = findViewById(R.id.btnSetTime);
        btnSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Setting.SET_HARI = Integer.parseInt(setHari.getText().toString());
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
                Log.d(TAG, "onClick: value set hari :" + Setting.SET_HARI);
                Log.d(TAG, "onClick: value state :" + Setting.STATE_JATUH_TEMPO);
            }
        });
    }
}