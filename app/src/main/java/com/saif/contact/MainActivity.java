package com.saif.contact;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;
import com.saif.contact.Fragment.ContactFragment;
import com.saif.contact.Fragment.RecentFragment;
import com.saif.contact.databinding.ActivityMainBinding;
import android.Manifest;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkpermission();
        }

        activityMainBinding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.recent){
                    loadFragment(new RecentFragment(),false);
                }else{
                    loadFragment(new ContactFragment(),true);
                }
                return true;
            }
        });

        activityMainBinding.bottomNavigation.setSelectedItemId(R.id.contacts);
    }

    public void loadFragment(Fragment fragment, boolean flag){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FrameLayout fl = (FrameLayout) findViewById(R.id.container1);
        fl.removeAllViews();

        if (flag) {
            fragmentTransaction.add(R.id.container1, fragment);
        }else{
            fragmentTransaction.replace(R.id.container1,fragment);
        }
        fragmentTransaction.commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public  void checkpermission(){
        if (checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_DENIED ||
        checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED) {
            String[] permission = { Manifest.permission.READ_MEDIA_IMAGES,Manifest.permission.CALL_PHONE};
            requestPermissions(permission, 66);
        }
        return;
    }
}