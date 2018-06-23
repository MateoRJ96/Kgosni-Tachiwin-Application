package com.android.kt.kgosnitachiwin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.kt.secondary.DictionaryActivity;
import com.android.kt.secondary.HearTraslateActivity;
import com.android.kt.secondary.TasksApplicationActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickButtonActivity1(View view){
        Intent intent = new Intent(this, TasksApplicationActivity.class);
        startActivity(intent);
    }

    public void onClickButtonActivity2(View view){
        Intent intent = new Intent(this, HearTraslateActivity.class);
        startActivity(intent);
    }

    public void onClickButtonActivity3(View view){
        Intent intent = new Intent(this, DictionaryActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.itemDictionary:
                onClickButtonActivity3(null);
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
    }
}
