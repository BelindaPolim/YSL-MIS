package id.com.ervsoftware.ysl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class JatuhTempoActivity extends AppCompatActivity {

    private static final String TAG = "Jatuh Tempo";

    ImageView imgBack, imgTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jatuh_tempo);

        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParent();
                finish();
            }
        });

        imgTime = findViewById(R.id.imgTime);
        imgTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent display = new Intent(getApplicationContext(), SetTimeActivity.class);
                startActivityForResult(display, 1);
            }
        });
    }
}