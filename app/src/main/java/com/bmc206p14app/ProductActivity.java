package com.bmc206p14app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.bmc206p14app.adapters.ItemAdapter;
import com.bmc206p14app.models.ItemModel;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {
    List<ItemModel> lstItem = new ArrayList<ItemModel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_layout);
        lstItem.add(new ItemModel(R.drawable.coca_cola,"Coca Cola",10.25,"In Stock"));
        lstItem.add(new ItemModel(R.drawable.fanta,"Fanta",10.15,"In Stock"));
        lstItem.add(new ItemModel(R.drawable.abc_beer,"ABC Beer",24.35,"In Stock"));
        lstItem.add(new ItemModel(R.drawable.anchor_beer,"Anchor Beer",17.45,"In Stock"));
        lstItem.add(new ItemModel(R.drawable.abc_beer,"ABC Beer",24.35,"In Stock"));
        lstItem.add(new ItemModel(R.drawable.anchor_beer,"Anchor Beer",17.45,"In Stock"));

        ItemAdapter adapter = new ItemAdapter(this, R.layout.items_layout, lstItem);
        GridView GV = findViewById(R.id.gvItems);
        GV.setAdapter(adapter);
    }
}