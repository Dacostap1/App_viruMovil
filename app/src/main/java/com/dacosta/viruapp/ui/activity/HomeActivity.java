package com.dacosta.viruapp.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.dacosta.viruapp.R;
import com.dacosta.viruapp.ui.activity.fragments.CreatePromotionFragment;
import com.dacosta.viruapp.ui.activity.fragments.GaleriaFragment;
import com.dacosta.viruapp.ui.activity.fragments.ModulDetailFragment;
import com.dacosta.viruapp.ui.activity.fragments.ModulListFragment;
import com.dacosta.viruapp.ui.activity.fragments.PromotionListFragment;
import com.dacosta.viruapp.ui.activity.fragments.StudentListFragment;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, StudentListFragment.OnFragmentInteractionListener,
        PromotionListFragment.OnFragmentInteractionListener, CreatePromotionFragment.OnFragmentInteractionListener,
        GaleriaFragment.OnFragmentInteractionListener, ModulListFragment.OnFragmentInteractionListener, ModulDetailFragment.OnFragmentInteractionListener {

    private TextView txt_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        View HeadView = navigationView.getHeaderView(0);  //Obtenemos primero la referencia al NavHeader
        txt_user = HeadView.findViewById(R.id.usuario);

        SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        String user = preferences.getString("user","");
        txt_user.setText(user);

        Fragment fragment = new PromotionListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_home, fragment).commit();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment miFragment = null;
        boolean fragmentSelected = false;

        if (id == R.id.nav_promo) {
            miFragment = new PromotionListFragment();
            fragmentSelected = true;
        } else if (id == R.id.nav_gallery) {
            miFragment = new GaleriaFragment();
            fragmentSelected = true;
        } else if (id == R.id.nav_create) {
            miFragment = new CreatePromotionFragment();
            fragmentSelected = true;
        } else if (id == R.id.nav_exit) {
            SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("user", "");
            editor.putString("pass", "");
            editor.commit();
            finish();
        }

        if(fragmentSelected){
            getSupportFragmentManager().beginTransaction().replace(R.id.content_home, miFragment).commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
