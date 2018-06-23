package com.android.kt.secondary;

import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.kt.kgosnitachiwin.MainActivity;
import com.android.kt.entity.Word;
import com.android.kt.kgosnitachiwin.R;
import com.android.kt.services.AudioStreaming;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;
import java.util.Random;

public class HearTraslateActivity extends MainActivity {

    private FirebaseFirestore db;
    private AudioStreaming streaming;
    private Word word;

    private EditText txtSpanish;
    private TextView txtTutunaku;
    private TextView temp;
    private Button btnVerify;
    private Button btnOmmited;
    private ImageButton btnPlay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hear_and_traslate);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        temp = findViewById(R.id.temp);
        txtSpanish = findViewById(R.id.txtInputSpanishId);
        txtTutunaku = findViewById(R.id.txtOutputTutunakuId);
        btnVerify = findViewById(R.id.btnVerifyId);
        btnOmmited = findViewById(R.id.btnOmmitedId);
        btnPlay = findViewById(R.id.playAudioId);

        db = FirebaseFirestore.getInstance();

        streaming = new AudioStreaming();
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        streaming.setMediaPlayer(mediaPlayer);

        btnOmmited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    btnOmmited.setText(R.string.omitted);
                    getDocumentFromCollection();
                }catch(Exception e){
                    Log.d("log", e.getMessage());
                }
            }
        });

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(!(txtSpanish.getText().toString().equals("")) && !(temp.getText().equals(""))){
                        boolean value = txtSpanish.getText().toString().toLowerCase().equals(temp.getText().toString().toLowerCase());
                        showAlertDialog(value);
                    }
                }catch(Exception e){
                    Log.d("log", e.getMessage());
                }
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(!streaming.getUrlAudioStreaming().equals("") || streaming.getUrlAudioStreaming() != null){
                        Toast.makeText(HearTraslateActivity.this, "Reproduciendo...", Toast.LENGTH_SHORT).show();
                        streaming.startAudioStreaming();
                    }
                }catch(Exception e){
                    Log.d("log", e.getMessage());
                }
            }
        });
    }

    public void showAlertDialog(boolean value){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_title2);
        if(value){
            builder.setMessage(R.string.dialog_message2);
            builder.setPositiveButton(R.string.ok2, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getDocumentFromCollection();
                }
            });
        }else{
            builder.setMessage(R.string.dialog_message3);
            builder.setPositiveButton(R.string.ok2, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getDocumentFromCollection();
                }
            });
            builder.setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    txtSpanish.setText(word.getSpanish());
                    btnOmmited.setText(R.string.ok2);
                }
            });
        }
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void cleanObject(){
        streaming.setUrlAudioStreaming("");
        txtTutunaku.setText("");
        txtSpanish.setText("");
        temp.setText("");
    }

    public int randoming(){
        Random random = new Random();
        int i = random.nextInt(14);
        return i;
    }

    public void getDocumentFromCollection(){
        try{
            cleanObject();
            String index = String.valueOf(randoming());
            DocumentReference docRef = db.collection("sentences").document(index);
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    word = documentSnapshot.toObject(Word.class);
                    if(word != null){
                        temp.setText(word.getSpanish());
                        txtTutunaku.setText(word.getTutunaku());
                        streaming.setUrlAudioStreaming(word.getUrl());
                    }else{
                        Log.d("log", "Objeto no encontrado");
                    }
                }
            });
        }catch(Exception e){
            Log.e("log", e.getMessage());
        }
    }
}
