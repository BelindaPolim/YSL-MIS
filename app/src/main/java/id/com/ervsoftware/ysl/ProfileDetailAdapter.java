package id.com.ervsoftware.ysl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class ProfileDetailAdapter extends ArrayAdapter<ProfileDetailModel> {
    private String TAG = ProfileDetailAdapter.class.getSimpleName();


    public ProfileDetailAdapter(@NonNull Context context, List<ProfileDetailModel> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        ProfileDetailModel profileDetail = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_profile_detail, parent, false);
        }

//        TextView infoName = convertView.findViewById(R.id.infoName);
        TextView infoVal = convertView.findViewById(R.id.infoValue);

//        infoName.setText(profileDetail.getInfo());

        infoVal.setText(profileDetail.getInfo());

        return convertView;
    }
}
