  package com.willi_vstn.media_player_app;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;

  public class MainActivity extends AppCompatActivity {

    //Buttons
    Button forward_btn, back_btn, play_btn, stop_btn;
    TextView time_txt, title_txt;

    //Seekbar
      SeekBar seekbar;

    //Media Player
      MediaPlayer mediaPlayer;

      //Handlers
      Handler handler = new Handler();

      //variables
      double startTime = 0;
      double finalTime = 0;
      int forwardTime = 10000;
      int backwardTime = 10000;
      static int oneTimeOnly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play_btn = findViewById(R.id.playBtn);
        stop_btn = findViewById(R.id.pause);
        back_btn = findViewById(R.id.rewindBtn);
        forward_btn = findViewById(R.id.forwardBtn);

        title_txt = findViewById(R.id.song_title);
        time_txt = findViewById(R.id.leftTime);

        seekbar = findViewById(R.id.seekBar);

        //media player
        mediaPlayer = MediaPlayer.create(this, R.raw.bushes_of_love);

        title_txt.setText(getResources().getIdentifier(
                "bushes_of_love",
                "raw",
                getPackageName()
        ));

        seekbar.setClickable(false);

        //Functionalities for buttons
        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayMusic();
            }

        });

        stop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();

            }
        });

        forward_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int temp = (int) startTime;
                if ((temp + forwardTime) <= finalTime){
                    startTime = startTime + forwardTime;
                    mediaPlayer.seekTo((int) startTime);

                }else{
                    Toast.makeText(MainActivity.this, "" +
                            "Song over", Toast.LENGTH_SHORT).show();
                }

            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int temp = (int) startTime;

                if((temp - backwardTime) >0){
                    startTime = startTime - backwardTime;
                    mediaPlayer.seekTo((int) startTime);
                }else{
                    Toast.makeText(MainActivity.this, "Cant rewind now", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


            private void PlayMusic() {
                mediaPlayer.start();

                finalTime = mediaPlayer.getDuration();
                startTime = mediaPlayer.getCurrentPosition();

                if (oneTimeOnly == 0){
                    seekbar.setMax((int) finalTime);
                    oneTimeOnly = 1;
                }

                time_txt.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                        TimeUnit.MILLISECONDS.toSeconds(TimeUnit.MILLISECONDS.toMinutes(
                                (long) finalTime))
                        ));

                seekbar.setProgress((int) startTime);
                handler.postDelayed(UpdateSongTime, 100);

            }








    //Creating the Runnable
      private Runnable UpdateSongTime = new Runnable() {
        @Override
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            time_txt.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime)
                            - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime))
            ));

            seekbar.setProgress((int) startTime);
            handler.postDelayed(this, 100);
        }

    };

  }











