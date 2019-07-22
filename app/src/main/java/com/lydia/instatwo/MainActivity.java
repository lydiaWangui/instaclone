package com.lydia.instatwo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lydia.instatwo.fragments.FavoriteFragment;
import com.lydia.instatwo.fragments.HomeFragment;
import com.lydia.instatwo.fragments.ProfileFragment;
import com.lydia.instatwo.fragments.SearchFragment;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView mbottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mbottomNavigationView = findViewById(R.id.button_nav_bar);

        mbottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_fragment, new HomeFragment())
                .commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;

            switch (menuItem.getItemId()){
                case R.id.action_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.action_search:
                    selectedFragment = new SearchFragment();
                    break;
                case R.id.action_add:
                    startActivity(new Intent(MainActivity.this, PostActivity.class));
                    finish();
                    break;
                case R.id.action_fav:
                    selectedFragment = new FavoriteFragment();
                    break;

                case R.id.action_profile:
                    selectedFragment = new ProfileFragment();
                    break;
            }

            if (selectedFragment  != null){
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container_fragment,selectedFragment)
                        .addToBackStack(null)
                        .commit();



            }

            return false;
        }
    };
}
