package com.android.kt.secondary;

import android.media.AudioManager;
import android.media.MediaPlayer;
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
import com.android.kt.services.AudioStreaming;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class DictionaryActivity extends AppCompatActivity {
    private EditText txtSearch;
    //private TextView txtTotalBusqueda;
    private List<Word> wordList;
    private SwitchCompat tipoBusqueda;
    private WordListAdapter wordListAdapter;
    private FirebaseFirestore db;

    private AudioStreaming streaming = new AudioStreaming();
    private MediaPlayer mediaPlayer = new MediaPlayer();

    private String TAG = "Firelog";
    private String TAG_SYSTEMLOG = "SystemLog";
    private String TAG_STREAMING = "Streaming";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        db = FirebaseFirestore.getInstance();

        final RecyclerView listResult = findViewById(R.id.listResultId);
        txtSearch = findViewById(R.id.txtSearchId);
        tipoBusqueda = findViewById(R.id.tipoBusquedaId);
        //txtTotalBusqueda = findViewById(R.id.txtTotalBusquedaId);

        tipoBusqueda.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    tipoBusqueda.setText(R.string.espaniol_tutunaku);
                }else{
                    tipoBusqueda.setText(R.string.tutunaku_espaniol);
                }
            }
        });

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        streaming.setMediaPlayer(mediaPlayer);

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
                try{
                    Word word = wordList.get(position);
                    streaming.setUrlAudioStreaming(word.getUrl());
                    streaming.startSimpleAudioStreaming();
                    if(streaming.getStatusImage()){
                        Toast.makeText(DictionaryActivity.this, "Reproduciendo...", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    Log.e(TAG_STREAMING, e.getMessage());
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
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
                                                    document.getString("spanish"),
                                                    document.getString("tutunaku"),
                                                    document.getString("url")
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
                loadDataFromCollection("spanish", txtSearch.getText().toString());
            }else {
                loadDataFromCollection("tutunaku", txtSearch.getText().toString());
            }

        }catch(Exception e){
            Log.d(TAG_SYSTEMLOG, Arrays.toString(e.getStackTrace()));
        }
    }
}
