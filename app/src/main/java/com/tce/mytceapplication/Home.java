package com.tce.mytceapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigationView=findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new EventFragment()).commit();

    }

    @Override
    public void onBackPressed() {

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener= new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedfragment = null;
            switch (item.getItemId()) {

                case R.id.navigation_event:
                    selectedfragment = new EventFragment();
                    break;
                case R.id.navigation_food:
                    selectedfragment=new FoodFragment();
                    break;
                case R.id.navigation_profile:
                    selectedfragment=new ProfileFragment();
                    break;
                case R.id.navigation_map:
                    selectedfragment=new MapFragment();
                    break;



            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedfragment).commit();
            return true;
        }
    };
}
