package com.android.kt.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.android.kt.entity.Word;
import com.android.kt.kgosnitachiwin.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;
import java.util.Random;

public class FragmentMatching extends Fragment {

    private FirebaseFirestore db;

    private TextView txtKeyWord;
    private Button btnVerify;
    private Button btnOmmited;
    private ImageButton btnImage1;
    private ImageButton btnImage2;
    private ImageButton btnImage3;
    private ImageButton btnImage4;

    public FragmentMatching() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ConstraintLayout view = (ConstraintLayout) inflater.inflate(R.layout.fragment_matching, container, false);

        db = FirebaseFirestore.getInstance();

        txtKeyWord = getView().findViewById(R.id.txtKeyWordId);
        btnVerify = getView().findViewById(R.id.btnVerify2Id);
        btnOmmited = getView().findViewById(R.id.btnOmmited2Id);
        btnImage1 = getView().findViewById(R.id.imageButton1);
        btnImage2 = getView().findViewById(R.id.imageButton2);
        btnImage3 = getView().findViewById(R.id.imageButton3);
        btnImage4 = getView().findViewById(R.id.imageButton4);

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Hi", Toast.LENGTH_SHORT).show();
            }
        });

        btnOmmited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnOmmited.setText(R.string.omitted);
                getDocumentFromCollection();
            }
        });

        btnImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnImage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    public int randoming(){
        Random random = new Random();
        int i = random.nextInt(14);
        return i;
    }

    public void getDocumentFromCollection(){
        try{
            txtKeyWord.setText("");
            String index = String.valueOf(randoming());
            DocumentReference docRef = db.collection("dictionary").document(index);
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Word word = documentSnapshot.toObject(Word.class);
                    if(word != null){
                        txtKeyWord.setText(word.getTutunaku());
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
