package com.trongdeptrai.demoappmusic.utils;

import android.content.Context;
import android.media.MediaPlayer;
import com.trongdeptrai.demoappmusic.R;
import com.trongdeptrai.demoappmusic.data.model.Song;
import com.trongdeptrai.demoappmusic.screen.MainActivity;
import java.util.ArrayList;

public class MusicController {
    private Context mContext;
    public static MediaPlayer sMediaPlayer;
    public static ArrayList<Song> listSong;
    public static int position = 0;
    public MusicController(Context context) {
        mContext = context;
        listSong = new ArrayList<>();
        initSong();
        setSong(position);
    }

    private void initSong(){
        listSong.add(new Song("Chiều Hôm Ấy", R.raw.chieuhomay));
        listSong.add(new Song("Đã Lỡ Yêu Em Nhiều", R.raw.daloyeuemnhieu));
        listSong.add(new Song("Một Bước Yêu Vạn Dặm Bước Đau", R.raw.motbuocyeuvandambuocdau));
        listSong.add(new Song("Vài Tháng Sau", R.raw.vaithangsau));
    }

    public void setSong(int position){
        sMediaPlayer = MediaPlayer.create(mContext, listSong.get(position).getResource());
    }
    public void play(){
        if(sMediaPlayer.isPlaying()){
            sMediaPlayer.stop();
            setSong(position);
        }else {
            setSong(position);
        }
        sMediaPlayer.start();
    }

    public void stop(){
        sMediaPlayer.stop();
    }

    public void next(){
        MainActivity.isPlay = false;
        position+= 1;
        if(position >= listSong.size()) position = 0;
        play();
        MainActivity.isPlay = true;
    }
    public void previous(){
        MainActivity.isPlay = false;
        position-= 1;
        if(position < 0 ) position = listSong.size() -1;
        play();
        MainActivity.isPlay = true;
    }

    public void fastForWard(int position){
        sMediaPlayer.seekTo(position);
    }
}
