package com.bmc206p14app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bmc206p14app.functions.ConvertImage;
import com.bmc206p14app.functions.Sessions;
import com.google.android.material.navigation.NavigationView;
import com.mikhaellopez.circularimageview.CircularImageView;

public class MainAppActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    Sessions sessions;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // create session
        sessions = new Sessions(this);
        setContentView(R.layout.main_app_dashboard);

        Toolbar toolbar = findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerID);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.openDrawer,R.string.closeDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView nav = findViewById(R.id.navID);
        nav.setNavigationItemSelectedListener(this);

        View v = nav.getHeaderView(0);
        TextView tvLoginUserName = v.findViewById(R.id.tvLoginName);
        TextView tvLoginEmail = v.findViewById(R.id.tvLoginEmail);

        tvLoginUserName.setText(sessions.getUserFullName());
        tvLoginEmail.setText(sessions.getUserEmail());

        bitmap = ConvertImage.StringToImage(sessions.getUserImage());
        CircularImageView image = v.findViewById(R.id.imgLoginPhoto);
        image.setImageBitmap(bitmap);
//        new AlertDialog.Builder(this)
//                .setMessage(" " + bitmap)
//                .show();
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menuNewOrder:
                Intent in = new Intent(MainAppActivity.this, NewOrderActivity.class);
                startActivity(in);
                break;
            case R.id.menuMyCart:
                Intent mycart = new Intent(MainAppActivity.this, MyCartActivity.class);
                startActivity(mycart);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.mnMyOrders:
                Intent inOrder = new Intent(MainAppActivity.this, MyOrderActivity.class);
                startActivity(inOrder);
                break;
            case R.id.mnProducts:
                Intent inP = new Intent(MainAppActivity.this, ProductActivity.class);
                startActivity(inP);
                break;
            case R.id.mnContacts:
                Intent inContact = new Intent(MainAppActivity.this, ContactActivity.class);
                startActivity(inContact);
                break;
            case R.id.mnEditProfile:
                Intent edit = new Intent(MainAppActivity.this, EditProfileActivity.class);
                startActivity(edit);
                break;
        }
        return true;
    }
    public void ViewContacts(View v){
        Intent inContact = new Intent(MainAppActivity.this, ContactActivity.class);
        startActivity(inContact);
    }
}