package com.elkanah.roemichs.ui.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elkanah.roemichs.R;
import com.elkanah.roemichs.ui.TestData;
import com.elkanah.roemichs.ui.fragments.ClassroomFragment;
import com.elkanah.roemichs.ui.model.OptiontModel;
import com.elkanah.roemichs.utils.AudioPlayerUtilities;

import java.io.IOException;
import java.util.ArrayList;

public class PlayAudioAdapter extends RecyclerView.Adapter<PlayAudioAdapter.ViewHolder> implements SeekBar.OnSeekBarChangeListener, MediaPlayer.OnCompletionListener {

    private Context context;
    private ArrayList<String> urlArrayList;
    public static MediaPlayer mediaPlayer;
    AudioPlayerUtilities utils;
    Handler mHandler;

    public   TextView audioCurrentTime;
    public  TextView audioEndTime;
    public ImageButton imgPlayPause;
    public  SeekBar seekBar;

    public PlayAudioAdapter(Context ctx, ArrayList<String> urlArrayList) {
        this.context = ctx;
        this.urlArrayList = urlArrayList;
        utils = new AudioPlayerUtilities();
        mediaPlayer = new MediaPlayer();
        mHandler = new Handler();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(this);
    }

    @NonNull
    @Override
    public PlayAudioAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.play_audio_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        seekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public int getItemCount() {
        return urlArrayList.size();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        seekBar.setProgress(0);
        mediaPlayer.stop();
        imgPlayPause.setImageDrawable(context.getResources().getDrawable(R.drawable.play));
        audioCurrentTime.setText(utils.milliSecondsToTimer(0));
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    private boolean isPlaying =false;
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            seekBar = itemView.findViewById(R.id.seekBarAudio);
            seekBar.setProgress(0);
            seekBar.setMax(100);
            audioCurrentTime = itemView.findViewById(R.id.txt_audio_current_time);
            audioEndTime = itemView.findViewById(R.id.txt_audio_end_time);
            imgPlayPause = itemView.findViewById(R.id.imgBtnPlay);
            imgPlayPause.setOnClickListener(v -> {
                if(mediaPlayer != null){
                    if(seekBar.getProgress()!=0){
                        if(isPlaying) {
                            mediaPlayer.pause();
                            imgPlayPause.setImageResource(R.drawable.play);
                            isPlaying=false;
                        }else {
                            mediaPlayer.start();
                            imgPlayPause.setImageResource(R.drawable.pause);
                            isPlaying=true;
                        }
                    } else {
                    playAudioRecorded(urlArrayList.get(getAdapterPosition()));
                    imgPlayPause.setImageResource(R.drawable.pause);
                    isPlaying=true;
                    }
                }
            });
        }
    }

    private boolean playAudioRecorded(String audioSavePathInDevice) {
        boolean flag = false;
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(audioSavePathInDevice);
            mediaPlayer.prepare();
            mediaPlayer.start();
            flag = true;
            // update progress bar
            mHandler.postDelayed(mUpdateTimeTask, 100);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = mediaPlayer.getDuration();
            long currentDuration = mediaPlayer.getCurrentPosition();

            // Displaying Total Duration time
            audioEndTime.setText(utils.milliSecondsToTimer(totalDuration));
            // Displaying time completed playing
            audioCurrentTime.setText(utils.milliSecondsToTimer(currentDuration));

            // Updating progress bar
            int progress = (int)(utils.getProgressPercentage(currentDuration, totalDuration));
            seekBar.setProgress(progress);

            // Running this thread after 100 milliseconds
            mHandler.postDelayed(this, 100);
        }
    };

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // remove message Handler from updating progress bar
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
        int totalDuration =  mediaPlayer.getDuration();
        int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);

        // forward or backward to certain seconds
        mediaPlayer.seekTo(currentPosition);

        // update timer progress again //update progress bar
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mediaPlayer.release();
    }

}

