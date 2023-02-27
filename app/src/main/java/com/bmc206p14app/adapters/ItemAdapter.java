package com.bmc206p14app.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bmc206p14app.R;
import com.bmc206p14app.models.ItemModel;

import java.util.List;

public class ItemAdapter extends ArrayAdapter {
    // data members
    private Context context;
    private List<ItemModel> lstItem;

    // constructor
    public ItemAdapter(@NonNull Context context, int resource, @NonNull List<ItemModel> lst) {
        super(context, resource, lst);
        this.context = context;
        this.lstItem = lst;
    }
    // getter methods

    @Override
    public int getCount() {
        return lstItem.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView.inflate(context, R.layout.items_layout, null);

        ItemModel item = lstItem.get(position);

        ImageView img = v.findViewById(R.id.imgItemPhoto);
        img.setImageResource(item.getImageID());
        img.setTag(String.valueOf(item.getImageID()));

        TextView tvname = v.findViewById(R.id.tvItemName);
        tvname.setText(item.getItemName());

        TextView tvprice = v.findViewById(R.id.tvItemPrice);
        tvprice.setText("$" + item.getItemPrice());

        TextView tvstatus = v.findViewById(R.id.tvItemStatus);
        tvstatus.setText(item.getItemStatus());

        return v;
    }
}
