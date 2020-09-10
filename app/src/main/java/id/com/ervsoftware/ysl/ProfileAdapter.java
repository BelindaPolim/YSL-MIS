package id.com.ervsoftware.ysl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ProfileAdapter extends ArrayAdapter {
    private ArrayList<ProfileModel> items;
    private ArrayList<ProfileModel> items2;
    private ProfileAdapter.ProfileSuppFilter filter;

    @NonNull
    @Override
    public Filter getFilter() {
        if(filter==null){
            filter = new ProfileAdapter.ProfileSuppFilter();
        }
        return filter;
    }

    ProfileAdapter(Context context, int layout, ArrayList<ProfileModel> items){
        super(context, layout);
        this.items = items;
        this.items2 =items;

    }
    public static class ViewHolder {
        TextView nama;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row;
        row = convertView;
        ProfileAdapter.ViewHolder viewHolder;

        if (row == null) {
            row = LayoutInflater.from(getContext()).inflate(R.layout.list_profile, parent, false);
            viewHolder = new ProfileAdapter.ViewHolder();

            viewHolder.nama = (TextView) row.findViewById(R.id.profileName);
            row.setTag(viewHolder);
        } else {
            viewHolder = (ProfileAdapter.ViewHolder) row.getTag();
        }

        ProfileModel supplier = items2.get(position);
        viewHolder.nama.setText(supplier.getName());

        return row;
    }

    private class ProfileSuppFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            constraint = constraint.toString().toLowerCase();
            FilterResults result = new FilterResults();
            if (constraint.toString().length() > 0) {
                ArrayList<ProfileModel> filteredItems = new ArrayList<>();

                for (int i = 0, l = items.size(); i < l; i++) {
                    ProfileModel filtered = items.get(i);
                    if (filtered.toString().toLowerCase().contains(constraint))
                        filteredItems.add(filtered);
                }
                result.count = filteredItems.size();
                result.values = filteredItems;
            } else {
                synchronized (this) {
                    result.values = items;
                    result.count = items.size();
                }
            }
            return result;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            items2 = (ArrayList<ProfileModel>) results.values;
            notifyDataSetChanged();
            clear();
            for (int i = 0, l = items2.size(); i < l; i++) {
                add(items2.get(i));
                notifyDataSetInvalidated();
            }
        }
    }
}