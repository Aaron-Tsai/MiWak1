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
public class FamilyFragment extends Fragment {

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

        //Creates an ArrayList that contains Dubs objects
        final ArrayList<Dubs>  dubsList = new ArrayList<Dubs>();
        dubsList.add(new Dubs("father", "padre", R.drawable.family_father, R.raw.family_father));
        dubsList.add(new Dubs("mother", "madre", R.drawable.family_mother, R.raw.family_mother));
        dubsList.add(new Dubs("brother", "hermano", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        dubsList.add(new Dubs("sister", "hermana", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        dubsList.add(new Dubs("son", "nino", R.drawable.family_son, R.raw.family_son));
        dubsList.add(new Dubs("daughter", "nina", R.drawable.family_daughter, R.raw.family_daughter));
        dubsList.add(new Dubs("aunt", "tia", R.drawable.family_older_sister, R.raw.family_older_sister));
        dubsList.add(new Dubs("uncle", "tio", R.drawable.family_older_brother, R.raw.family_older_brother));
        dubsList.add(new Dubs("grandfather", "abuelo", R.drawable.family_grandfather, R.raw.family_grandfather));
        dubsList.add(new Dubs("grandmother", "abuela", R.drawable.family_grandmother, R.raw.family_grandmother));

        DubsAdapter itemsAdapter = new DubsAdapter(getActivity(), dubsList, R.color.category_family
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

    public FamilyFragment() {
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
