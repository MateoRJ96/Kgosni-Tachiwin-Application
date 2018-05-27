package com.android.kt.secondary;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.android.kt.fragments.FragmentDragAndDrop;
import com.android.kt.fragments.FragmentMatching;
import com.android.kt.fragments.FragmentSentences;
import com.android.kt.kgosnitachiwin.MainActivity;
import com.android.kt.kgosnitachiwin.R;
import com.android.kt.adapters.ViewPagerAdapter;

public class TasksApplicationActivity extends MainActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_application);
        tabLayout = (TabLayout)findViewById(R.id.idTabLayout);
        viewPager = (ViewPager)findViewById(R.id.idViewPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentSentences(), "Aprende Oraciones");
        adapter.addFragment(new FragmentDragAndDrop(), "Interactúa");
        adapter.addFragment(new FragmentMatching(), "Relaciona contenido");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.itemDictionary:
                mainActivity.onClickButtonActivity3(null);
                return true;
            case R.id.itemAcercaDe:
                return true;
            case R.id.itemSalir:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_secondary_actions, menu);
        return true;
    }*/
}
