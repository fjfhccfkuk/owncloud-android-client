package com.example.hsx.ui;

/**
 * Created by hsx on 17-8-7.
 */

public class OwnText {

    public interface Presenter {
        void getPhotoName(int index);
    }

    public interface ViewText {
        void showText(String s);
        void resetText(String s);
    }
}
