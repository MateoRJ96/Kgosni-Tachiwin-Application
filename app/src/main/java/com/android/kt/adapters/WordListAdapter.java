package com.android.kt.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.kt.entity.Word;
import com.android.kt.kgosnitachiwin.R;

import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.ViewHolder>{
    private List<Word> wordList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_base_row_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Word word = wordList.get(position);
        holder.spanish.setText(word.getSpanish());
        holder.tutunaku.setText(word.getTutunaku());
    }

    @Override
    public int getItemCount() {
        return wordList.size();
    }

    public WordListAdapter(List<Word> wordList){
        this.wordList = wordList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView spanish, tutunaku;

        public ViewHolder(View view){
            super(view);
            spanish = view.findViewById(R.id.spanishId);
            tutunaku = view.findViewById(R.id.tutunakuId);
        }
    }


}
