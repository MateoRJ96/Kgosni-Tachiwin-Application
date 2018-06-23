package com.android.kt.services;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

public class AudioStreaming {
    private final String TAG = "AudioStreaming";

    private String urlAudioStreaming;
    private MediaPlayer mediaPlayer;

    private boolean statusImage;

    private boolean playPause;
    private boolean initialStage = true;

    public void setPlayPause(boolean playPause) {
        this.playPause = playPause;
    }

    public boolean getStatusImage() {
        return statusImage;
    }

    public void setStatusImage(boolean statusImage) {
        this.statusImage = statusImage;
    }

    public String getUrlAudioStreaming() {
        return urlAudioStreaming;
    }

    public void setUrlAudioStreaming(String urlAudioStreaming) {
        this.urlAudioStreaming = urlAudioStreaming;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public void startSimpleAudioStreaming(){
        try{
            if(getMediaPlayer().isPlaying()){
                getMediaPlayer().stop();
                getMediaPlayer().reset();
                setStatusImage(false);
            }
            getMediaPlayer().setDataSource(getUrlAudioStreaming());
            getMediaPlayer().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    setStatusImage(false);
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                }
            });
            getMediaPlayer().prepare();
            getMediaPlayer().start();
            setStatusImage(true);
        }catch(Exception e){
            Log.e(TAG, e.getMessage());
        }
    }

    public void startAudioStreaming(){
        try{
            if (!playPause) {
                setStatusImage(true);
                if (initialStage) {
                    new Player().execute(getUrlAudioStreaming());
                } else {
                    if (!getMediaPlayer().isPlaying())
                        getMediaPlayer().start();
                }

                playPause = true;

            } else {
                setStatusImage(false);
                if (getMediaPlayer().isPlaying()) {
                    getMediaPlayer().pause();
                }

                playPause = false;
            }
        }catch(Exception e){
            Log.e(TAG, e.getMessage());
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class Player extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            Boolean prepared;

            try {
                getMediaPlayer().setDataSource(strings[0]);
                getMediaPlayer().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        initialStage = true;
                        playPause = false;
                        setStatusImage(false);
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });

                getMediaPlayer().prepare();
                prepared = true;

            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                prepared = false;
            }

            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            getMediaPlayer().start();
            initialStage = false;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

}
