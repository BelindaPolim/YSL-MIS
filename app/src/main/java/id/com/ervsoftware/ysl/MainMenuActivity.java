package id.com.ervsoftware.ysl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;


import com.pixplicity.easyprefs.library.Prefs;

import static id.com.ervsoftware.ysl.Setting.SP_TOKEN;

public class MainMenuActivity extends AppCompatActivity {
    ImageView btnLogout;
    LinearLayout llCust, llSupp;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        loadFragment(new CustomerFragment());

        btnLogout = findViewById(R.id.imgLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getApplicationContext(), btnLogout);
                popup.getMenuInflater().inflate(R.menu.menu_main, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(getApplicationContext(), "Logged out", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        Prefs.remove(SP_TOKEN);
                        finish();
                        return true;
                    }
                });
                popup.show();
            }
        });

        llCust = findViewById(R.id.layoutMenuCust);
        llCust.setBackgroundColor(Color.LTGRAY);
        llCust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new CustomerFragment());
            }
        });
        llCust.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        llSupp.setBackgroundColor(Color.WHITE);
                        llCust.setBackgroundColor(Color.LTGRAY);
                        break;
                }
                return false;
            }
        });

        llSupp = findViewById(R.id.layoutMenuSupp);
        llSupp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new SupplierFragment());
            }
        });
        llSupp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        llCust.setBackgroundColor(Color.WHITE);
                        llSupp.setBackgroundColor(Color.LTGRAY);
                        break;
                }
                return false;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        // create a FragmentManager
        FragmentManager fm = getSupportFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.layoutSubMenu, fragment);
        fragmentTransaction.commit(); // save the changes
    }
}
