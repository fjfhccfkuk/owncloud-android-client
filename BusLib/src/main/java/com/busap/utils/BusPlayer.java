package com.busap.utils;

import android.media.MediaPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hsx on 17-5-15.
 */

public final class BusPlayer {
    private static int mMaxPlayerSize = 2;
    private static int mCurPlayer = 0;
    private static List<MyPlayer> mPlayer;

    private static com.busap.utils.BusPlayer mObj = null;

    private BusPlayer() {
        mPlayer = new ArrayList<MyPlayer>();
        for (int i = 0; i < mMaxPlayerSize; i ++) {
            MyPlayer mp = new MyPlayer(new MediaPlayer());
            mPlayer.add(mp);
        }
    }

    public static MyPlayer getCurPlayer() {
        if (mObj == null)
            mObj = new com.busap.utils.BusPlayer();

        return mPlayer.get(mCurPlayer);
    }

    public static MyPlayer getNextPlayer() {
        if (mObj == null)
            mObj = new com.busap.utils.BusPlayer();

        mCurPlayer = (mCurPlayer + 1) % 2;
        return mPlayer.get(mCurPlayer);
    }

    private class MyPlayer implements IBusPlayer {
        private MediaPlayer mPlayer = null;
        private String mTag = "" + mCurPlayer;
        private String mMedia = "";
        private String mPlayTime = "0";
        private String mState = "inited";


        //初始化设置 mPlayer 实例
        public MyPlayer (MediaPlayer mp) {
            if (mp == null) {
                new RuntimeException(" com.busap.utils->MyPlayer , mp cannot be null");
                return;
            }

            if (mPlayer != null)
                return;

            mPlayer = mp;
            init();
        }

        public void init() {
            mMedia = "";
            mPlayTime = "0";
            mState = "inited";
            mPlayer.reset();
        }

        public String getTag() {return mTag;}

        public String getMediaName() {return mMedia;}
        public void setMediaName(String media) {mMedia = media;}

        public String getPlayTime() {return mPlayTime;}
        public void setPlayTime(String time) {mPlayTime = time;}

        public String getmState(){return mState;}
        public void setState(String s) {mState = s;}
    }

    public interface IBusPlayer {
        public void init();
        public String getTag();
        public String getMediaName();
        public void setMediaName(String media);
        public String getPlayTime();
        public void setPlayTime(String time);
        public String getmState();
        public void setState(String s);
    }
}
