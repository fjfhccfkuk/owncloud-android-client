package com.example.hsx.ui.Widgets;

/**
 * Created by hsx on 17-8-26.
 */

public interface IPointBtn {

    public interface Forcus {
        public void forcusOn();
        public void forcusOff();
    }

    public interface Change {
        public void onShow(PointBtn v);
        public void onDismiss(PointBtn v);
    }
}
