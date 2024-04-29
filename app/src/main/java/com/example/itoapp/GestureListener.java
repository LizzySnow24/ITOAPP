package com.example.itoapp;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;

public class GestureListener extends GestureDetector.SimpleOnGestureListener {
    private ImageView imageView;
    private float offsetX = 0f;
    private float offsetY = 0f;

    public GestureListener(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        // Actualizar el desplazamiento de la imagen
        offsetX -= distanceX;
        offsetY -= distanceY;

        // Aplicar el desplazamiento a la imagen
        imageView.setTranslationX(offsetX);
        imageView.setTranslationY(offsetY);

        return true;
    }
}
