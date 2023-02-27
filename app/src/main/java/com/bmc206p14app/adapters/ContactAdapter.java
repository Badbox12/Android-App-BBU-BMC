package com.bmc206p14app.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bmc206p14app.R;
import com.bmc206p14app.models.ContactItems;

import java.util.List;

public class ContactAdapter extends BaseAdapter {
    // data member
    private List<ContactItems> lstContact;
    private Context context;

    // constructor
    public ContactAdapter(Context context, List<ContactItems> lst){
        this.context = context;
        this.lstContact = lst;
    }

    // getter method
    @Override
    public int getCount() {
        return lstContact.size();
    }
    @Override
    public Object getItem(int position) {
        // ContactItems contactItems = lstContact.get(position);
        // return contactItems;
        return lstContact.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView.inflate(context, R.layout.contact_items, null);

        ContactItems contact = lstContact.get(position);

        ImageView img = v.findViewById(R.id.imgContact);
        img.setImageResource(contact.getImageID());
        img.setTag(String.valueOf(contact.getImageID()));

        TextView tvname = v.findViewById(R.id.tvContactName);
        tvname.setText(contact.getName());

        TextView tvphone = v.findViewById(R.id.tvPhoneNumber);
        tvphone.setText(contact.getPhone());

        TextView tvemail = v.findViewById(R.id.tvContactEmail);
        tvemail.setText(contact.getEmail());

        return v;
    }
}
