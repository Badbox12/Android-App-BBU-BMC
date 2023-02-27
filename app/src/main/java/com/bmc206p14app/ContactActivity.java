package com.bmc206p14app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bmc206p14app.adapters.ContactAdapter;
import com.bmc206p14app.models.ContactItems;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    List<ContactItems> lstContact = new ArrayList<ContactItems>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_layout);

        // ContactItems con = new ContactItems(R.drawable.icon_contact_72,"Sothy","012345678","sothy@gmail.com");
        lstContact.add(new ContactItems(R.drawable.icon_contact_72,"Sothy","012345678","sothy@gmail.com"));
        lstContact.add(new ContactItems(R.drawable.icon_contact_72,"Enchy","012345677","enchy@gmail.com"));
        lstContact.add(new ContactItems(R.drawable.icon_contact_72,"Samnang","012345679","samnang@gmail.com"));
        lstContact.add(new ContactItems(R.drawable.icon_group_72,"Dara","012345670","dara@gmail.com"));

        ContactAdapter adapter = new ContactAdapter(ContactActivity.this, lstContact);
        ListView LV = findViewById(R.id.LvContacts);
        LV.setAdapter(adapter);
        LV.setOnItemClickListener(this);
        LV.setOnItemLongClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ImageView img = view.findViewById(R.id.imgContact);
        TextView tvname = view.findViewById(R.id.tvContactName);
        TextView tvphone = view.findViewById(R.id.tvPhoneNumber);
        TextView tvemail = view.findViewById(R.id.tvContactEmail);

        Intent in = new Intent(ContactActivity.this, ContactDetailActivity.class);
        in.putExtra("CONTACT_IMAGE", img.getTag().toString());
        in.putExtra("CONTACT_NAME", tvname.getText().toString());
        in.putExtra("CONTACT_PHONE", tvphone.getText().toString());
        in.putExtra("CONTACT_EMAIL", tvemail.getText().toString());
        startActivity(in);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final String[] actions = {"Add New","Edit","Delete","View","Add to Favorites"};
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setItems(actions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(actions[which].equals("View")){
                    ImageView img = view.findViewById(R.id.imgContact);
                    TextView tvname = view.findViewById(R.id.tvContactName);
                    TextView tvphone = view.findViewById(R.id.tvPhoneNumber);
                    TextView tvemail = view.findViewById(R.id.tvContactEmail);

                    Intent in = new Intent(ContactActivity.this, ContactDetailActivity.class);
                    in.putExtra("CONTACT_IMAGE", img.getTag().toString());
                    in.putExtra("CONTACT_NAME", tvname.getText().toString());
                    in.putExtra("CONTACT_PHONE", tvphone.getText().toString());
                    in.putExtra("CONTACT_EMAIL", tvemail.getText().toString());
                    startActivity(in);
                }
            }
        });
        ad.show();
        return true;
    }
}