package com.example.android.miwak1;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.miwak1.Dubs;
import com.example.android.miwak1.DubsAdapter;
import com.example.android.miwak1.R;

import java.util.ArrayList;

import static android.media.AudioManager.AUDIOFOCUS_GAIN;
import static android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT;
import static android.media.AudioManager.AUDIOFOCUS_LOSS;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;
import static android.media.AudioManager.STREAM_MUSIC;

/**
 * A simple {@link Fragment} subclass.
 */
public class NumbersFragment extends Fragment {

    private MediaPlayer soundPlayer;
    private AudioManager mAudioManager;

    private AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (soundPlayer != null) {
                switch (focusChange) {
                    case AUDIOFOCUS_GAIN:
                    case AUDIOFOCUS_GAIN_TRANSIENT:
                        soundPlayer.start();
                        break;
                    case AUDIOFOCUS_LOSS:
                        releaseMediaPlayer();
                        break;
                    case AUDIOFOCUS_LOSS_TRANSIENT:
                        soundPlayer.pause();
                        soundPlayer.seekTo(0);
                        break;
                }
            }
        }
    };

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        final ArrayList<Dubs> dubsList = new ArrayList<Dubs>();
        dubsList.add(new Dubs("one", "uno", R.drawable.number_one, R.raw.number_one));
        dubsList.add(new Dubs("two", "dos", R.drawable.number_two, R.raw.number_two));
        dubsList.add(new Dubs("three", "tres", R.drawable.number_three, R.raw.number_three));
        dubsList.add(new Dubs("four", "cuatro", R.drawable.number_four, R.raw.number_four));
        dubsList.add(new Dubs("five", "cinco", R.drawable.number_five, R.raw.number_five));
        dubsList.add(new Dubs("six", "seis", R.drawable.number_six, R.raw.number_six));
        dubsList.add(new Dubs("seven", "siete", R.drawable.number_seven, R.raw.number_seven));
        dubsList.add(new Dubs("eight", "ocho", R.drawable.number_eight, R.raw.number_eight));
        dubsList.add(new Dubs("nine", "nueve", R.drawable.number_nine, R.raw.number_nine));
        dubsList.add(new Dubs("ten", "diez", R.drawable.number_ten, R.raw.number_ten));

        DubsAdapter itemsAdapter = new DubsAdapter(getActivity(), dubsList, R.color.category_numbers
        );
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Dubs currentWord = dubsList.get(position);
                int audioID = currentWord.getAudio();
                releaseMediaPlayer();
                mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
                int result = mAudioManager.requestAudioFocus(mAudioFocusChangeListener, STREAM_MUSIC, AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    soundPlayer = MediaPlayer.create(getActivity(), audioID);
                    soundPlayer.start();
                    soundPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
        return rootView;
    }

    public NumbersFragment() {
        // Required empty public constructor
    }

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (soundPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            soundPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            soundPlayer = null;
            mAudioManager.abandonAudioFocus(mAudioFocusChangeListener);
        }
    }
}
