package com.example.ex6;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class MusicService extends Service {
    public static MediaPlayer mediaPlayer = new MediaPlayer();
    public MusicService() {
        try {
            mediaPlayer.setDataSource("/data/UntilUCame.mp3");
            mediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final IBinder binder = new MyBinder();
    public class MyBinder extends Binder {
        MusicService getMusicService() {
            return MusicService.this;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return binder;
    }

    public void playOrPause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        } else {
            mediaPlayer.start();
        }
    }

    public void stop() {
        if (mediaPlayer != null){
            mediaPlayer.stop();
            try{
                mediaPlayer.prepare();
                mediaPlayer.seekTo(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
