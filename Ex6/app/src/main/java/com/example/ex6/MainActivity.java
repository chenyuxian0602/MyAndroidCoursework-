package com.example.ex6;

import android.animation.ObjectAnimator;
import android.app.Notification;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button playBtn;
    private Button stopBtn;
    private Button quitBtn;
    private TextView textView;
    private TextView totalTime;
    private TextView playingTime;
    private SeekBar seekBar;
    private ImageView hxw;
    private int angle = 0;
    private MusicService musicService;

    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicService = ((MusicService.MyBinder)service).getMusicService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicService = null;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playBtn = (Button)findViewById(R.id.playButton);
        stopBtn = (Button)findViewById(R.id.stopButton);
        quitBtn = (Button)findViewById(R.id.quitButton);
        textView = (TextView)findViewById(R.id.state);
        totalTime =(TextView)findViewById(R.id.endTime);
        playingTime = (TextView)findViewById(R.id.beginTime);
        hxw = (ImageView)findViewById(R.id.rotateImg);

        playBtn.setText("play");
        //Log.w("duriation",""+musicService.mediaPlayer.getDuration());
        //Log.w("duriation", timeTrans(musicService.mediaPlayer.getDuration()));
        totalTime.setText("4:39");

        musicService = new MusicService();
        Intent intent = new Intent(MainActivity.this, MusicService.class);
        startService(intent);
        bindService(intent, sc, this.BIND_AUTO_CREATE);

        seekBar = (SeekBar)findViewById(R.id.seekBar);
        seekBar.setProgress(musicService.mediaPlayer.getCurrentPosition());
        seekBar.setMax(musicService.mediaPlayer.getDuration());

        //musicService.playOrPause();
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicService.playOrPause();
                if (playBtn.getText() == "play") {
                    playBtn.setText("pause");
                    textView.setText("Playing");
                } else {
                    playBtn.setText("play");
                    textView.setText("Paused");
                }
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicService.stop();
                if (playBtn.getText() == "pause") {
                    playBtn.setText("play");
                }
                textView.setText("stopped");
            }
        });

        quitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicService.stop();
                finish();
            }
        });
        //Log.w("duriation", timeTrans(musicService.mediaPlayer.getDuration()));
    }

    public Handler handler = new Handler();
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            playingTime.setText(timeTrans(musicService.mediaPlayer.getCurrentPosition()));
            seekBar.setProgress(musicService.mediaPlayer.getCurrentPosition());
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        musicService.mediaPlayer.seekTo(progress);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            String state = textView.getText().toString();
            if (state.equals("Playing")) angle += 1;
            else if (state.equals("Paused"));
            else if (state.equals("stopped"))angle = 0;
            angle %= 360;
            hxw.setRotation(angle);
            handler.postDelayed(runnable, 50);
        }
    };

    @Override
    public void onResume() {
        seekBar.setProgress(musicService.mediaPlayer.getCurrentPosition());
        seekBar.setMax(musicService.mediaPlayer.getDuration());
        handler.post(runnable);
        super.onResume();
    }

    public String timeTrans(int t) {
        int tt = t/1000;
        return tt/60 + ":" + tt%60;
    }
}
