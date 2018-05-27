package com.android.kt.secondary;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.android.kt.listeners.RecyclerTouchListener;
import com.android.kt.adapters.WordListAdapter;
import com.android.kt.entity.Word;
import com.android.kt.kgosnitachiwin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DictionaryActivity extends AppCompatActivity {
    private EditText txtSearch;
    private List<Word> wordList;
    private SwitchCompat tipoBusqueda;
    private RecyclerView listResult;
    private WordListAdapter wordListAdapter;
    private FirebaseFirestore db;
    private String TAG = "Firelog";
    private String TAG_SYSTEMLOG = "SystemLog";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listResult = findViewById(R.id.listResultId);
        txtSearch = findViewById(R.id.txtSearchId);
        tipoBusqueda = findViewById(R.id.tipoBusquedaId);

        tipoBusqueda.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    tipoBusqueda.setText("Totonaco - Español");
                }else{
                    tipoBusqueda.setText("Español - Totonaco");
                }
            }
        });

        wordList = new ArrayList<>();
        wordListAdapter = new WordListAdapter(wordList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        listResult.setLayoutManager(mLayoutManager);
        listResult.setItemAnimator(new DefaultItemAnimator());
        listResult.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        listResult.setAdapter(wordListAdapter);

        listResult.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), listResult, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Word word = wordList.get(position);
                Toast.makeText(getApplicationContext(), word.getSpanish() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        db = FirebaseFirestore.getInstance();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadDataFromCollection(String field, String value){
        try{
            wordList.clear();
            db.collection("dictionary")
                    .whereEqualTo(field, value)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(DocumentSnapshot document : task.getResult()){
                                    wordList.add(
                                            new Word(
                                                    document.getString("linked"),
                                                    document.getString("spanish"),
                                                    document.getString("tutunaku")
                                            )
                                    );
                                    wordListAdapter.notifyDataSetChanged();
                                }
                            }else{
                                Log.d(TAG, "Error al obtener los documentos", task.getException());
                            }
                        }
                    });
        }catch(Exception e){
            Log.d(TAG_SYSTEMLOG, e.getMessage());
        }
    }

    public void findInDictionary(View view){
        try{
            if(tipoBusqueda.isChecked()){
                loadDataFromCollection("tutunaku", txtSearch.getText().toString());
            }else {
                loadDataFromCollection("spanish", txtSearch.getText().toString());
            }
        }catch(Exception e){
            Log.d(TAG_SYSTEMLOG, e.getStackTrace().toString());
        }
    }
}
