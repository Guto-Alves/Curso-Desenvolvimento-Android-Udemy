package com.gutotech.aprendaingles.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gutotech.aprendaingles.R;
import com.gutotech.aprendaingles.fragment.BichosFragment;
import com.gutotech.aprendaingles.fragment.NumerosFragment;
import com.gutotech.aprendaingles.fragment.VogaisFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class MainActivity extends AppCompatActivity {
    private SmartTabLayout smartTabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Aprenda Inglês");

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("Bichos", BichosFragment.class)
                .add("Números", NumerosFragment.class)
                .add("Vogais", VogaisFragment.class)
                .create()
        );

        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        smartTabLayout = findViewById(R.id.smarttablayout);
        smartTabLayout.setViewPager(viewPager);
    }
}
