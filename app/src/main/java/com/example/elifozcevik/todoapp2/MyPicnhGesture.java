package com.example.elifozcevik.todoapp2;

import android.util.Log;
import android.view.ScaleGestureDetector;

public class MyPicnhGesture extends ScaleGestureDetector.SimpleOnScaleGestureListener {

    public boolean onScale(ScaleGestureDetector scaleGestureDetector){
        Log.d("UBE","onFling:" +scaleGestureDetector);
        return true;
    }
}
