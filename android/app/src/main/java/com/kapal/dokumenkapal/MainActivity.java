package com.kapal.dokumenkapal;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.kapal.dokumenkapal.ui.masalayar.MasaLayarFormFragment;
import com.kapal.dokumenkapal.ui.masalayar.MasaLayarFragment;
import com.kapal.dokumenkapal.util.SharedPrefManager;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public FloatingActionButton fab;

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.fab);

        sharedPrefManager = new SharedPrefManager(this);
//      ButterKnife.bind(this);
//      NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                MasaLayarFragment masalayarFragment = (MasaLayarFragment) getSupportFragmentManager().findFragmentByTag("TAG_MASALAYAR_FRAGMENT");

                if (masalayarFragment != null && masalayarFragment.isVisible()) {
                    MasaLayarFormFragment mf = new MasaLayarFormFragment();
                    FragmentTransaction ft = getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.nav_host_fragment, mf, "TAG_FORM_MASALAYAR_FRAGMENT")
                            .addToBackStack(null);
                    ft.commit();
                }


                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.navheader_tvName);
        navUsername.setText(sharedPrefManager.getSPNama());

        TextView navEmail = headerView.findViewById(R.id.navheader_tvEmail);
        navEmail.setText(sharedPrefManager.getSPEmail());

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile, R.id.nav_permohonan,
                R.id.nav_tools, R.id.nav_share, R.id.nav_signout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
