package com.example.elifozcevik.todoapp2;

import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

public class MyGestureListner extends GestureDetector.SimpleOnGestureListener {
    public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
        Log.d("UBE", "onFling:" + event1.toString() + event2.toString());
        return true;
    }

}
