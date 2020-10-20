package id.com.ervsoftware.ysl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class JatuhTempoAdapter extends ArrayAdapter {
    private ArrayList<JatuhTempoModel> items;
    private ArrayList<JatuhTempoModel> items2;
    private JatuhTempoAdapter.JatuhTempoFilter filter;

    @NonNull
    @Override
    public Filter getFilter() {
        if(filter==null){
            filter = new JatuhTempoAdapter.JatuhTempoFilter();
        }
        return filter;
    }

    JatuhTempoAdapter(Context context, int layout, ArrayList<JatuhTempoModel> items){
        super(context, layout);
        this.items = items;
        this.items2 =items;

    }
    public static class ViewHolder {
        TextView nomor;
        TextView tanggal;
        TextView jatuhTempo;
        TextView nilai;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row;
        row = convertView;
        JatuhTempoAdapter.ViewHolder viewHolder;

        if (row == null) {
            row = LayoutInflater.from(getContext()).inflate(R.layout.list_jatuh_tempo, parent, false);
            viewHolder = new JatuhTempoAdapter.ViewHolder();

            viewHolder.nomor = (TextView) row.findViewById(R.id.jtNomor);
            viewHolder.tanggal = (TextView) row.findViewById(R.id.jtTanggal);
            viewHolder.jatuhTempo = (TextView) row.findViewById(R.id.jtJatuhTempo);
            viewHolder.nilai = (TextView) row.findViewById(R.id.jtNilai);
            row.setTag(viewHolder);

            if(position % 2 == 1) {
                row.setBackgroundColor(Color.LTGRAY);
            }
        } else {
            viewHolder = (JatuhTempoAdapter.ViewHolder) row.getTag();
        }

        JatuhTempoModel data = items2.get(position);
        viewHolder.nomor.setText(data.getNomor());

        viewHolder.tanggal.setText(data.getTanggal());
        viewHolder.jatuhTempo.setText(data.getJatuhTempo());
        viewHolder.nilai.setText(data.getNilai());

        return row;
    }

    private class JatuhTempoFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            constraint = constraint.toString().toLowerCase();
            FilterResults result = new FilterResults();
            if (constraint.toString().length() > 0) {
                ArrayList<JatuhTempoModel> filteredItems = new ArrayList<JatuhTempoModel>();

                for (int i = 0, l = items.size(); i < l; i++) {
                    JatuhTempoModel nomor = items.get(i);
                    if (nomor.toString().contains(constraint))
                        filteredItems.add(nomor);
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
            items2 = (ArrayList<JatuhTempoModel>) results.values;
            notifyDataSetChanged();
            clear();
            for (int i = 0, l = items2.size(); i < l; i++) {
                add(items2.get(i));
                notifyDataSetInvalidated();
            }
        }
    }
}
