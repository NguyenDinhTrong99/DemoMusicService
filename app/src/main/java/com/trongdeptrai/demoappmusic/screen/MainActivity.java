package com.trongdeptrai.demoappmusic.screen;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.trongdeptrai.demoappmusic.R;
import com.trongdeptrai.demoappmusic.service.MusicService;
import com.trongdeptrai.demoappmusic.utils.MusicController;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {
    private static final long TIME_DELAY = 100L;
    private ImageButton btnPlay;
    private SeekBar sbSong;
    private TextView txtTimePlay, txtDuration, txtNameSong;
    private ImageView imgAvatar;
    public static boolean isPlay = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        sbSong = findViewById(R.id.sbSong);
        txtTimePlay = findViewById(R.id.txtTimePlay);
        txtDuration = findViewById(R.id.txtDuration);
        txtNameSong = findViewById(R.id.txtNameSong);
        imgAvatar = findViewById(R.id.imgAvatar);
        btnPlay = findViewById(R.id.btnPlay);
        sbSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (MusicService.mController != null) {
                    MusicService.mController.fastForWard(sbSong.getProgress());
                }
            }
        });
    }

    private void setIconPlay() {
        if (isPlay) {
            btnPlay.setImageResource(R.drawable.ic_play);
        } else {
            btnPlay.setImageResource(R.drawable.ic_pause);
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private void setNameSong() {
        txtNameSong.setText(MusicController.listSong
                          .get(MusicController.position).getTitle());
    }

    private void setDuration() {
        String time =
                convertDurationToTime(String.valueOf(MusicController.sMediaPlayer.getDuration()));
        txtDuration.setText(time);
        sbSong.setProgress(0);
    }

    private void setAnimationMusic() {
        Animation animator = AnimationUtils
                                 .loadAnimation(this, R.anim.anim_cricle_music);
        imgAvatar.setAnimation(animator);
    }

    //hàm đổi thời gian về đúng định dạng phút giây của bài hát
    @SuppressLint("SimpleDateFormat")
    private String convertDurationToTime(String duration) {
        int temp = Integer.parseInt(duration);
        sbSong.setMax(Integer.parseInt(duration));
        return new SimpleDateFormat("mm:ss").format(temp);
    }

    private void updateTimeDuration() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
                txtTimePlay.setText(sdf.format(MusicController.sMediaPlayer.getCurrentPosition()));
                new Handler().postDelayed(this, 300);
            }
        }, TIME_DELAY);
    }

    public void ControllerListener(View v) {
        switch (v.getId()) {
            case R.id.btnPlay:
                if (isPlay) {
                    setIconPlay();
                    unbindService(mConnection);
                    isPlay = !isPlay;
                } else {
                    setIconPlay();
                    setAnimationMusic();
                    if (MusicService.mController == null) {
                        //khởi tạo class để initData
                        MusicController musicController = new MusicController(this);
                        setNameSong();
                        setDuration();
                        updateTimeDuration();
                    }
                    Intent intent = new Intent(this, MusicService.class);
                    bindService(intent, mConnection, BIND_AUTO_CREATE);
                    isPlay = !isPlay;
                }
                break;
            case R.id.btnNext:
                if (MusicService.mController != null) {
                    MusicService.mController.next();
                    setNameSong();
                    setDuration();
                    updateTimeDuration();
                }
                break;
            case R.id.btnPrevious:
                if (MusicService.mController != null) {
                    MusicService.mController.previous();
                    setNameSong();
                    setDuration();
                    updateTimeDuration();
                }
                break;
        }
    }
}
