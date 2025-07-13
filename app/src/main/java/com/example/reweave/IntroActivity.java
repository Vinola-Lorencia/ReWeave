package com.example.reweave;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class IntroActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        ViewPager2 viewPager = findViewById(R.id.introViewPager);
        TabLayout tabLayout = findViewById(R.id.introTabLayout);
        ImageButton btnNext = findViewById(R.id.btnNext);

        viewPager.setUserInputEnabled(false);
        viewPager.setAdapter(new FragmentStateAdapter(this) {
            @Override
            public int getItemCount() {
                return 2;
            }

            @Override
            public Fragment createFragment(int position) {
                return IntroFragment.newInstance(position);
            }
        });

        new TabLayoutMediator(tabLayout, viewPager, (tab, pos) -> {}).attach();

        btnNext.setOnClickListener(v -> {
            int current = viewPager.getCurrentItem();
            if (current < 1) {
                viewPager.setCurrentItem(current + 1, true);
            } else {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }
        });
    }
}
