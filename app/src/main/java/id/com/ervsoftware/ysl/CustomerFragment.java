package id.com.ervsoftware.ysl;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomerFragment extends Fragment {
    LinearLayout llPenjualan, llPiutang, llProfilCust;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CustomerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CustomerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CustomerFragment newInstance(String param1, String param2) {
        CustomerFragment fragment = new CustomerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        llPenjualan = view.findViewById(R.id.layoutPenjualan);
        llPenjualan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PenjualanActivity.class));
            }
        });
        llPenjualan.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    llPiutang.setBackgroundColor(Color.WHITE);
                    llProfilCust.setBackgroundColor(Color.WHITE);

                    llPenjualan.setBackgroundColor(Color.LTGRAY);
                }
                return false;
            }
        });

        llPiutang = view.findViewById(R.id.layoutPiutang);
        llPiutang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PiutangActivity.class));
            }
        });
        llPiutang.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    llPenjualan.setBackgroundColor(Color.WHITE);
                    llProfilCust.setBackgroundColor(Color.WHITE);

                    llPiutang.setBackgroundColor(Color.LTGRAY);
                }
                return false;
            }
        });
//        llJatuhTempoPiutang = view.findViewById(R.id.layoutJatuhTempoPiutang);
//        llJatuhTempoPiutang.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), PiutangActivity.class));
//            }
//        });
//        llJatuhTempoPiutang.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    llPenjualan.setBackgroundColor(Color.WHITE);
//                    llPiutang.setBackgroundColor(Color.WHITE);
//                    llProfilCust.setBackgroundColor(Color.WHITE);
//
//                    llJatuhTempoPiutang.setBackgroundColor(Color.LTGRAY);
//                }
//                return false;
//            }
//        });

        llProfilCust = view.findViewById(R.id.layoutProfileCustomer);
        llProfilCust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProfileCustActivity.class));
            }
        });
        llProfilCust.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    llPenjualan.setBackgroundColor(Color.WHITE);
                    llPiutang.setBackgroundColor(Color.WHITE);

                    llProfilCust.setBackgroundColor(Color.LTGRAY);
                }
                return false;
            }
        });

    }
}