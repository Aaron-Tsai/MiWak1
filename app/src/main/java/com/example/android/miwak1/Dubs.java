package com.example.android.miwak1;

import android.widget.ImageView;

/**
 * Created by Aaron on 10/5/2017.
 */

public class Dubs {
    private String eng = "";
    private String miw = "";
    private int img = NO_IMAGE_PROVIDED;
    private int audio = 0;

    private static final int NO_IMAGE_PROVIDED = -1;




    public Dubs(String s, String u, int i, int j) {
        eng = s;
        miw = u;
        img = i;
        audio = j;
    }
    public Dubs(String s, String u, int j) {
        eng = s;
        miw = u;
        audio = j;
    }

    public String getEng() {return eng;}
    public String getMiw() {return miw;}
    public int getImg() {return img;}
    public int getAudio() {return audio;}
    public boolean hasImage() {
        return img != NO_IMAGE_PROVIDED;
    }


}
